package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Utils {

    public static Cursor crossHair;
    public static Cursor shopCursor;

    static {
        createCursors();
    }

    public static void createCursors() {
        try {
            // TODO: CROSS HAIR
            BufferedImage crossHairImage = ImageIO.read(Utils.class.getResourceAsStream("/ui/cross_hair.png"));
            crossHair = Toolkit.getDefaultToolkit().createCustomCursor(crossHairImage, new Point(0, 0), "Crosshair");

            // TODO: SHOP
            BufferedImage shopCursorImage = ImageIO.read(Utils.class.getResourceAsStream("/gameIcon.png"));
            shopCursor = Toolkit.getDefaultToolkit().createCustomCursor(shopCursorImage, new Point(5, 5), "Crosshair");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getCenterXForString(Graphics g, String text, int relativeWidth) {
        int textLength = (int) g.getFontMetrics().getStringBounds(text, g).getWidth();

        return (relativeWidth - textLength) / 2;
    }

    public static final int CURSOR_SIZE = 32;
}
