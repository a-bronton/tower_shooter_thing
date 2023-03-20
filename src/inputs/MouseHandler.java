package inputs;

import main.GamePanel;
import main.Utils;
import ui.ShopButton;

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
        if (gp.getGameState() == gp.PLAYING) {
            gp.getPlayer().shoot();
        }

        if (gp.getGameState() == gp.PAUSED) {
            for (ShopButton sb : gp.getUi().getShop().getPageButtons()) {
                sb.mousePressed(e);
            }
        }

        if (gp.getGameState() == gp.GAME_OVER) {
            gp.getUi().getRestartWaveButton().mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        gp.getPlayer().updateRotation(e.getX() + (Utils.CURSOR_SIZE / 2f), e.getY() + (Utils.CURSOR_SIZE / 2f));

        if (gp.getGameState() == gp.GAME_OVER) {
            gp.getUi().getRestartWaveButton().mouseReleased(e);
        }
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

        if (gp.getGameState() == gp.PAUSED) {
            for (ShopButton sb : gp.getUi().getShop().getPageButtons()) {
                sb.mouseMoved(e);
            }
        }

        if (gp.getGameState() == gp.GAME_OVER) {
            gp.getUi().getRestartWaveButton().mouseMoved(e);
        }

        gp.getTreasureChest().mouseMoved(e);
    }
}
