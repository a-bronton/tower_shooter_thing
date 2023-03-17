import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.image.BufferedImage;

public class Main extends JFrame implements WindowFocusListener {

    private GamePanel gp;

    public static void main(String[] args) {
        new Main().setup();
    }

    public void setup() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setCursor(null);
        setResizable(false);

        gp = new GamePanel();
        add(gp);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        addWindowFocusListener(this);

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

    @Override
    public void windowGainedFocus(WindowEvent e) {
        if (gp.isMusicPaused()) {
            gp.unPauseMusic();
        }
    }

    @Override
    public void windowLostFocus(WindowEvent e) {
        gp.pauseMusic();
    }
}