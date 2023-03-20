package entity;

import main.GamePanel;
import main.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Crow extends Enemy {

    private Player player;
    private BufferedImage imageRaster;

    public Crow(int x, int y, int width, int height, GamePanel gp) {
        super(x, y, width, height, gp);
        speed = 2;
        animationSpeed = 10;

        maxLife = 3;
        life = maxLife;

        player = gp.getPlayer();

        animationList = new BufferedImage[2];
        initImages();
    }
    @Override
    public void update() {
        super.update();

        animationTick++;
        if (animationTick > animationSpeed) {
            animationTick = 0;
            animationIndex++;

            if (animationIndex > animationList.length - 1) {
                animationIndex = 0;
            }
        }

        x -= speed;
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        g.drawImage(animationList[animationIndex], x, y, width, height, null);

        //g.setColor(new Color(255, 0, 0, 100));
        //g.fillRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
    }

    public void die() {
        int rand = (int) (Math.random() * 10);

        if (rand >= 3) {
            gp.getUi().addCoin(x, y, speed, -5, 1);
        }

        if (rand == 1) {
            gp.getUi().addDiamond(x, y, speed + rand, -5, 1);
        }

        removable = true;
    }

    public void initImages() {
        try {
            imageRaster = ImageIO.read(getClass().getResourceAsStream("/enemies/crow_animation.png"));

            for (int i = 0; i < animationList.length; i++) {
                animationList[i] = imageRaster.getSubimage(i * 32, 0, 32, 32);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
