import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Player {

    // TODO: PLAYER FIELDS
    private float x, y;
    private final int width, height;
    private final GamePanel gp;
    private double angleDegrees;
    private float centerX, centerY;
    // Attributes
    private int maxLife = 3;
    private int life = maxLife;

    // TODO: OTHER FIELDS
    private float mouseX, mouseY;

    // TODO: DEBUG
    private boolean showDebugData = false;

    // TODO: IMAGES
    private BufferedImage cannonBody, peg1, peg2;

    // TODO: DIST FROM MOUSE TO PLAYER
    float xDelta;
    float yDelta;

    // TODO: ROTATION UTILITIES
    private AffineTransform affineTransform;

    // TODO: ANIMATION
    private BufferedImage cannonRaster;
    private BufferedImage[] animationList;
    private boolean shooting = false;
    private int animationTick;
    private int animationSpeed = 5;
    private int animationIndex;

    public Player(GamePanel gp) {
        this.gp = gp;

        width = (int) (44 * 2.5);
        height = (int) (28 * 2.5);
        x = 60;
        y = (int) (gp.getPreferredSize().getHeight() - height) / 2f;

        try {
            cannonBody = ImageIO.read(getClass().getResourceAsStream("cannonBody.png"));
            peg1 = ImageIO.read(getClass().getResourceAsStream("cannonPeg1.png"));
            peg2 = ImageIO.read(getClass().getResourceAsStream("cannonPeg2.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        initImages();
    }

    public void initImages() {
        try {
            cannonRaster = ImageIO.read(getClass().getResourceAsStream("cannonAnimation.png"));
            animationList = new BufferedImage[4];

            for (int i = 0; i < animationList.length; i++) {
                animationList[i] = cannonRaster.getSubimage(i * 44, 0, 44, 28);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {
        y = (int) (gp.getSize().getHeight() - height) / 2f;

        // TODO: ANIMATION
        if (shooting) {
            animationTick++;
            if (animationTick > animationSpeed) {
                animationTick = 0;
                animationIndex++;

                if (animationIndex == 3) {
                    addCannonBall((int) mouseX + (Utils.CURSOR_SIZE / 2), (int) mouseY + (Utils.CURSOR_SIZE / 2));
                    gp.playSoundEffect(0);
                }

                if (animationIndex > animationList.length - 1) {
                    animationIndex = 0;
                    shooting = false;
                }
            }
        }

        if (life > maxLife) {
            life = maxLife;
        }

        if (life < 0) {
            life = 0;
        }
    }

    public void shoot() {
        if (shooting) {
            return;
        }

        shooting = true;
        animationIndex = 0;
    }

    public void draw(Graphics2D g2) {
        AffineTransform defaultTx = g2.getTransform();
        g2.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));

        affineTransform = AffineTransform.getRotateInstance(Math.toRadians(angleDegrees), centerX, centerY);
        //affineTransformOp = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_BILINEAR);

        centerX = x + (width / 2f);
        centerY = y + (height / 2f);

        g2.drawImage(peg2, (int) centerX - (width / 3), (int) centerY - (height / 2), width, height, null);

        g2.setTransform(affineTransform);
        if (shooting) {
            g2.drawImage(animationList[animationIndex], (int) centerX - (width / 3), (int) centerY - (height / 2), width, height, null);
        } else {
            g2.drawImage(cannonBody, (int) centerX - (width / 3), (int) centerY - (height / 2), width, height, null);
        }

        g2.setTransform(defaultTx);
        g2.drawImage(peg1, (int) centerX - (width / 3), (int) centerY - (height / 2), width, height, null);

        if (showDebugData) {
            g2.setStroke(new BasicStroke(3));
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));


            g2.setFont(new Font("DIALOG", Font.BOLD, 22));
            // TODO: OPPOSITE
            g2.setColor(Color.GREEN);
            g2.drawLine((int) mouseX, (int) mouseY, (int) mouseX, (int) centerY);
            g2.drawString(String.valueOf(Math.abs(yDelta)), xDelta  + (int) g2.getFontMetrics().getStringBounds(String.valueOf(Math.abs(yDelta)), g2).getWidth() * 2, centerY + (yDelta / 2));

            // TODO: HYPOTENOUS
            g2.setColor(Color.RED);
            g2.drawLine((int) mouseX, (int) mouseY, (int) centerX, (int) centerY);

            // TODO: ADJACENT
            g2.setColor(Color.BLUE);
            g2.drawLine((int) mouseX, (int) centerY, (int) centerX, (int) centerY);
            g2.drawString(String.valueOf(xDelta), centerX + (xDelta / 2), y + height);

            // TODO: MOUSE POINTER DOT
            g2.setColor(Color.BLACK);
            g2.fillOval((int) (mouseX - 5), (int) (mouseY - 5), 10, 10);

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
    }

    public void updateRotation(float x, float y) {
        // TODO: GET ANGLE
        xDelta = x - centerX;
        yDelta = y - centerY;

        angleDegrees = Math.toDegrees(Math.atan2(yDelta, xDelta));
        //System.out.println(angleDegrees);

        mouseX = x;
        mouseY = y;

        if (angleDegrees > 60) {
            angleDegrees = 60;
        }

        if (angleDegrees < -60) {
            angleDegrees = -60;
        }
    }

    public void addCannonBall(int mouseX, int mouseY) {
        float yVel = (int) (y -  mouseY) / 15f;
        float xVel = mouseX / 30f;

        //double y = Math.tan(angleDegrees) * width;

        gp.addCannonBall((int) x + (width / 2), (int) y + 10, xVel, yVel);
    }

    public double getAngleDegrees() {
        return angleDegrees;
    }

    public void setShowDebugData(boolean showDebugData) {
        this.showDebugData = showDebugData;
    }

    public boolean isShowDebugData() {
        return showDebugData;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }
}
