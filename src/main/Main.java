package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;

public class Main extends JFrame implements WindowFocusListener, WindowListener {

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
        addWindowListener(this);

        setCursor(Utils.crossHair);
        setIcon();

        setTitle("Tower Defense Thing");
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

    @Override
    public void windowClosing(WindowEvent e) {
        System.out.println("Saving Data...");
        gp.getPlayer().getPlayerData().saveData();
        System.out.println("Finished Saving Data");
    }

    @Override
    public void windowOpened(WindowEvent e) {
        System.out.println("Loading Data...");
        gp.getPlayer().getPlayerData().readSavedData();
        System.out.println("Finished Loading Data");
    }







    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}