import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class CannonBall {

    private int x, y;
    private float yVel, xVel;
    private BufferedImage image;
    private int width, height;

    private Rectangle hitbox;

    public CannonBall(int x, int y, float xVel, float yVel) {
        width = 30;
        height = 30;

        this.yVel = yVel;
        this.xVel = xVel;

        this.x = x;
        this.y = y;

        try {
            image = ImageIO.read(getClass().getResourceAsStream("cannon_ball.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        hitbox = new Rectangle(x, y, width, height);
    }

    public void update() {
        x += xVel;
        y -= yVel;

        yVel -= 0.3;

        hitbox.x = x;
        hitbox.y = y;
    }

    public void draw(Graphics g) {
        g.drawImage(image, x, y, width, height, null);

        // TODO: DEBUG HIT BOX
        g.setColor(new Color(255, 0, 0, 100));
        //g.fillRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
    }

    public Point getPos() {
        return new Point(x, y);
    }
}
