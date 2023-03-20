package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Enemy {

    protected int x, y, width, height;
    protected Rectangle hitBox;
    protected BufferedImage[] animationList;
    protected int animationIndex, animationTick, animationSpeed;
    protected int speed;

    protected int maxLife;
    protected int life;
    protected BufferedImage healthBarImage;
    protected int healthBarX, healthBarY;

    protected boolean alive = true, removable = false;

    protected GamePanel gp;

    public Enemy(int x, int y, int width, int height, GamePanel gp) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        hitBox = new Rectangle(x, y, width, height);

        this.gp = gp;

        initHealthBar();
    }

    public void update() {
        hitBox.x = x;
        hitBox.y = y;

        if (life <= 0) {
            alive = false;
        }

        healthBarX = x - speed;
        healthBarY = y - 20;
    }

    public void draw(Graphics g) {
        // TODO: HEALTH BAR
        g.drawImage(healthBarImage, healthBarX, healthBarY, width, 13, null);
        double lifeRatio = (double) life / maxLife;
        g.setColor(Color.RED);
        g.fillRect(healthBarX + 6, healthBarY + 6, (int) (lifeRatio * width) - 12, 13 - 12);
    }

    public void initHealthBar() {
        try {
            healthBarImage = ImageIO.read(getClass().getResourceAsStream("/ui/enemy_health_bar.png"));

            healthBarX = x;
            healthBarY = y - 20;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public abstract void die();

    public Point getPos() {
        return new Point(x, y);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isRemovable() {
        return removable;
    }
}
