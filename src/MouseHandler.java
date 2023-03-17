import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseHandler implements MouseListener, MouseMotionListener {

    private GamePanel gp;

    public MouseHandler(GamePanel gp) {
        this.gp = gp;
    }

    // TODO: MOUSE INPUT
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        //gp.getPlayer().addCannonBall(e.getX() + (Utils.CURSOR_SIZE / 2), e.getY() + (Utils.CURSOR_SIZE / 2));
        //System.out.println("Added cannonball");
        gp.getPlayer().shoot();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        gp.getPlayer().updateRotation(e.getX() + (Utils.CURSOR_SIZE / 2f), e.getY() + (Utils.CURSOR_SIZE / 2f));
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    // TODO: MOUSE MOTION

    @Override
    public void mouseDragged(MouseEvent e) {
        gp.getPlayer().updateRotation(e.getX() + (Utils.CURSOR_SIZE / 2f), e.getY() + (Utils.CURSOR_SIZE / 2f));
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        gp.getPlayer().updateRotation(e.getX() + (Utils.CURSOR_SIZE / 2f), e.getY() + (Utils.CURSOR_SIZE / 2f));
    }
}
