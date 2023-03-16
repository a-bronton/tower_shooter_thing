import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private final int SCREEN_WIDTH = 500;
    private final int SCREEN_HEIGHT = 500;

    private Player player;
    private MouseHandler mouseHandler;

    public GamePanel() {
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));

        player = new Player(this);

        mouseHandler = new MouseHandler(this);
        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);

        setFocusable(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        player.update();
        player.draw(g2);

        repaint();
    }

    public Player getPlayer() {
        return player;
    }
}
