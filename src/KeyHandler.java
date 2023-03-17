import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    private GamePanel gp;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_E) {
            gp.getPlayer().setShowDebugData(!gp.getPlayer().isShowDebugData());
        }

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            gp.getPlayer().setLife(gp.getPlayer().getLife() - 1);
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            gp.getPlayer().setLife(gp.getPlayer().getLife() + 1);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
