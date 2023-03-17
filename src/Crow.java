import java.awt.*;
import java.awt.image.BufferedImage;

public class Crow extends Enemy {

    public Player player;

    public Crow(int x, int y, int width, int height, GamePanel gp) {
        super(x, y, width, height, gp);

        player = gp.getPlayer();

        animationList = new BufferedImage[5];
        initImages();
    }
    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics g) {

    }

    public void initImages() {

    }
}
