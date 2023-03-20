package interactive;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class TreasureChest {

    private int x, y, width, height;

    private BufferedImage chestAtlasOpening, chestAtlasClosing;
    private BufferedImage[] animationListOpening, animationListClosing;
    private int animationTick, animationIndex, animationSpeed = 3;

    private boolean opening, closing, opened;

    private Rectangle hitBox;

    public TreasureChest(int x, int y, float scale) {
        this.x = x;
        this.y = y;

        width = (int) (64 * scale);
        height = (int) (35 * scale);

        hitBox = new Rectangle(x, y, width, height);

        initImages();

        open();
    }

    public void update() {
        if ((opening || closing) && !opened) {
            animationTick++;
            if (animationTick > animationSpeed) {
                animationTick = 0;
                animationIndex++;

                if (animationIndex > animationListOpening.length - 1) {
                    if (opening) {
                        opened = true;
                        animationIndex = animationListOpening.length - 1;
                    }

                    if (closing) {
                        animationIndex = animationListClosing.length - 1;
                    }

                    opening = false;
                    closing = false;
                }
            }
        }

        if (opened) {
            animationIndex = animationListOpening.length - 1;
        }
    }

    public void draw(Graphics g) {
        if (opening) {
            g.drawImage(animationListOpening[animationIndex], x, y, width, height, null);
        } else if (closing) {
            g.drawImage(animationListClosing[animationIndex], x, y, width, height, null);
        } else {
            g.drawImage(animationListOpening[0], x, y, width, height, null);
        }

        //g.setColor(new Color(255, 0, 0, 100));
        //g.fillRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
    }

    public void mouseMoved(MouseEvent e) {
        if (hitBox.contains(e.getPoint())) {
            if (!opening) {
                open();
            }
        } else if (opened) {
            if (!closing) {
                close();
            }
        }
    }

    public void open() {
        animationIndex = 0;
        closing = false;
        opening = true;
    }

    public void close() {
        animationIndex = 0;
        opening = false;
        closing = true;

        opened = false;
    }

    public void initImages() {
        try {
            // TODO: OPENING
            chestAtlasOpening = ImageIO.read(getClass().getResourceAsStream("/objects/chest_opening.png"));
            animationListOpening = new BufferedImage[5];

            for (int i = 0; i < animationListOpening.length; i++) {
                animationListOpening[i] = chestAtlasOpening.getSubimage(i * 64, 0, 64, 35);
            }

            // TODO: CLOSING
            chestAtlasClosing = ImageIO.read(getClass().getResourceAsStream("/objects/chest_closing.png"));
            animationListClosing = new BufferedImage[5];

            for (int i = 0; i < animationListClosing.length; i++) {
                animationListClosing[i] = chestAtlasClosing.getSubimage(i * 64, 0, 64, 35);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
