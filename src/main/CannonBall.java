package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class CannonBall {

    private int x, y;
    private float yVel, xVel;
    private BufferedImage image;
    private int width, height;

    private Rectangle hitBox;

    public CannonBall(int x, int y, float xVel, float yVel) {
        width = 30;
        height = 30;

        this.yVel = yVel;
        this.xVel = xVel;

        this.x = x;
        this.y = y;

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/cannon_ball.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        hitBox = new Rectangle(x, y, width, height);
    }

    public void update() {
        x += xVel;
        y -= yVel;

        yVel -= 0.3;

        hitBox.x = x;
        hitBox.y = y;
    }

    public void draw(Graphics g) {
        g.drawImage(image, x, y, width, height, null);

        // TODO: DEBUG HIT BOX
        g.setColor(new Color(255, 0, 0, 100));
        //g.fillRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);

        // TODO: DEBUG TRAJECTORY
//        int xTest = x;
//        int yTest = y;
//        for (int i = 0; i < 100; i++) {
//            int xDest = xTest + (int) (xVel * i);
//            int yDest = yTest - (int) (yVel - (0.3 * i));
//            g.drawLine(xTest, yTest, xDest, yDest);
//
//            xTest = xDest;
//            yTest = yDest;
//        }
    }

    public Point getPos() {
        return new Point(x, y);
    }

    public Rectangle getHitBox() {
        return hitBox;
    }
}
