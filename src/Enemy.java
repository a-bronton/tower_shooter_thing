import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Enemy {

    protected int x, y, width, height;
    protected Rectangle hitBox;
    protected BufferedImage[] animationList;

    protected GamePanel gp;

    public Enemy(int x, int y, int width, int height, GamePanel gp) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.gp = gp;
    }

    public abstract void update();

    public abstract void draw(Graphics g);
}
