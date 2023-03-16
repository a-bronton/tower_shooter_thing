import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class Player {

    private float x, y;
    private int width, height;
    private GamePanel gp;
    private double angleDegrees;
    private float centerX, centerY;

    private int mouseX, mouseY;

    private BufferedImage image;

    // TODO: ROTATION UTILITIES
    private AffineTransform affineTransform;
    private AffineTransformOp affineTransformOp;

    public Player(GamePanel gp) {
        this.gp = gp;

        width = 50;
        height = 50;
        x = (int) (gp.getPreferredSize().getWidth() - width / 2);
        y = (int) (gp.getPreferredSize().getHeight() - height / 2);

        try {
            image = ImageIO.read(getClass().getResourceAsStream("gun.jpeg"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {

    }

    public void draw(Graphics2D g) {
        affineTransform = AffineTransform.getRotateInstance(Math.toRadians(angleDegrees), x, y);
        affineTransformOp = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_BILINEAR);

        centerX = x + (width / 2);
        centerY = y + (height / 2);
        g.setTransform(affineTransform);
        g.drawImage(image, (int) centerX, (int) centerY, width, height, null);
    }

    public void updateRotation(int x, int y) {
        // TODO: GET ANGLE
        float xDelta = x - centerX;
        float yDelta = y - centerY;

        angleDegrees = Math.toDegrees(Math.tan(yDelta / xDelta));
        System.out.println(angleDegrees);

        mouseX = x;
        mouseY = y;

        Graphics2D g2 = (Graphics2D) gp.getGraphics();
        g2.setColor(Color.GREEN);
        g2.drawLine(mouseX, mouseY, mouseX, (int) centerY);
        g2.drawLine(mouseX, mouseY, (int) centerX, (int) centerY);

        g2.setColor(Color.RED);
        g2.fillOval(mouseX, mouseY, 20, 20);
    }
}
