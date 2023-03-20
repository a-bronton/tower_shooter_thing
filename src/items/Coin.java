package items;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Coin {

    private BufferedImage coinRaster;

    private BufferedImage[] animationList;
    private int animationIndex, animationTick, animationSpeed = 5;

    private int showValueAnimationTick;
    private int showAnimationLength = 200;

    private float x, y;
    private int xVel;
    private float yVel;

    private int value;
    private float valueTextY;

    private boolean removable = false;

    private GamePanel gp;

    public Coin(int x, int y, int xVel, float initialYVel, int value, GamePanel gp) {
        this.x = x;
        this.y = y;
        this.xVel = xVel;
        this.yVel = initialYVel;
        this.value = value;

        valueTextY = (int) gp.getPreferredSize().getHeight() - 30;

        this.gp = gp;

        initImages();
    }

    public void update() {
        if (y < gp.getPreferredSize().getHeight()) {
            x -= xVel;
            y += yVel;
            yVel += 0.3;

            animationTick++;
            if (animationTick > animationSpeed) {
                animationTick = 0;
                animationIndex++;

                if (animationIndex > animationList.length - 1) {
                    animationIndex = 0;
                }
            }
        } else {
            if (showValueAnimationTick < 1) {
                gp.getPlayer().getPlayerData().addCoins(value);
            }
            showValueAnimationTick++;

            if (showValueAnimationTick > showAnimationLength) {
                removable = true;
            }
        }
    }

    public void draw(Graphics g) {
        g.drawImage(animationList[animationIndex], (int) x, (int) y, 32, 32, null);

        if (showValueAnimationTick > 0) {
            g.setFont(gp.getUi().getShop().getMaruMon().deriveFont(Font.BOLD, 35));

            g.setColor(Color.BLACK);
            g.drawString("+" + value, (int) x - 1, (int) valueTextY + 1);

            g.setColor(Color.WHITE);
            g.drawString("+" + value, (int) x, (int) valueTextY);

            valueTextY -= 0.3;
        }
    }

    public void initImages() {
        try {
            coinRaster = ImageIO.read(getClass().getResourceAsStream("/items/coin_raster.png"));
            animationList = new BufferedImage[4];

            for (int i = 0; i < animationList.length; i++) {
                animationList[i] = coinRaster.getSubimage(i * 16, 0, 16, 16);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isRemovable() {
        return removable;
    }

    public int getValue() {
        return value;
    }
}
