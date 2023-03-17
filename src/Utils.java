import java.awt.*;

public class Utils {

    public static int getCenterXForString(Graphics g, String text, int relativeWidth) {
        int textLength = (int) g.getFontMetrics().getStringBounds(text, g).getWidth();

        return (relativeWidth - textLength) / 2;
    }

    public static final int CURSOR_SIZE = 32;
}
