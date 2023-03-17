import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class UI {

    private GamePanel gp;

    private BufferedImage lifeBarImage;
    private int lifeBarX, lifeBarY, lifeBarWidth, lifeBarHeight;

    private Heart[] hearts;

    public UI(GamePanel gp) {
        this.gp = gp;

        lifeBarWidth = (int) (132 * 1.5);
        lifeBarHeight = (int) (68 * 1.5);
        lifeBarX = 20;
        lifeBarY = 20;

        hearts = new Heart[3];
        initHearts();

        initImages();
    }

    public void initImages() {
        try {
            lifeBarImage = ImageIO.read(getClass().getResourceAsStream("ui/lifeBar.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {

    }

    public void draw(Graphics g) {
        g.drawImage(lifeBarImage, lifeBarX, lifeBarY, lifeBarWidth, lifeBarHeight, null);

        for (int i = 0; i < gp.getPlayer().getLife(); i++) {
            hearts[i].draw(g);
        }
    }

    public void initHearts() {
        int x = 45;
        int j = 0;
        for (int i = 0; i < hearts.length; i++) {
            hearts[i] = new Heart(lifeBarX + x, lifeBarY + ((lifeBarHeight - 32) / 2) + 2);
            hearts[i].animationIndex = j;
            j++;
            x += 32;
        }
    }

    public class Heart {

        private BufferedImage heartRaster;
        private BufferedImage[] animationList;

        private int animationIndex;
        private int animationTick;
        private int animationSpeed = 10;

        private int animationWaitTick;
        private int animationWait = 150;

        private int x, y;

        private boolean doAnimation = true;

        public Heart(int x, int y) {
            this.x = x;
            this.y = y;
            animationList = new BufferedImage[8];

            initImages();
        }

        public void initImages() {
            try {
                heartRaster = ImageIO.read(getClass().getResourceAsStream("ui/heartRaster.png"));

                for (int i = 0; i < animationList.length; i++) {
                    animationList[i] = heartRaster.getSubimage(i * 18, 0, 18, 14);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void update() {
            if (!doAnimation) {
                animationWaitTick++;
                if (animationWaitTick > animationWait) {
                    animationWaitTick = 0;
                    doAnimation = true;
                }
            }

            if (doAnimation) {
                animationTick++;
                if (animationTick > animationSpeed) {
                    animationTick = 0;
                    animationIndex++;
                }

                if (animationIndex > animationList.length - 1) {
                    animationIndex = 0;
                    doAnimation = false;
                }
            }
        }
        public void draw(Graphics g) {
            g.drawImage(animationList[animationIndex], x, y, 36, 28, null);
        }
    }
}
