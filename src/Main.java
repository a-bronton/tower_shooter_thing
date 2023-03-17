import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Main extends JFrame {
    public static void main(String[] args) {
        new Main().setup();
    }

    public void setup() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setCursor(null);
        setResizable(false);

        add(new GamePanel());

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        setCursor();
        setIcon();

        setTitle("Tower Defense Thing");
    }

    public void setCursor() {
        try {
            BufferedImage crossHairImage = ImageIO.read(getClass().getResourceAsStream("crossHair.png"));

            Cursor crossHair = Toolkit.getDefaultToolkit().createCustomCursor(crossHairImage, new Point(0, 0), "Crosshair");
            setCursor(crossHair);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setIcon() {
        ImageIcon icon = new ImageIcon("res/gameIcon.png");
        Image iconScaled = icon.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT);
        setIconImage(iconScaled);
    }
}