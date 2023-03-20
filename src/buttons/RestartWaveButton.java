package buttons;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class RestartWaveButton {

    private int x, y, width, height;
    private boolean mouseOver, mousePressed;

    private BufferedImage stagesAtlas;
    private BufferedImage[] stagesList;
    private int stage;

    private Rectangle hitBox;

    private GamePanel gp;

    public RestartWaveButton(int x, int y, float scale, GamePanel gp) {
        this.gp = gp;
        this.x = x;
        this.y = y;

        width = (int) (56 * scale);
        height = (int) (56 * scale);

        hitBox = new Rectangle(x, y, width, height);

        initImages();
    }

    public void initImages() {
        try {
            stagesAtlas = ImageIO.read(getClass().getResourceAsStream("/ui/buttons/back_button_atlas.png"));
            stagesList = new BufferedImage[3];

            for (int i = 0; i < stagesList.length; i++) {
                stagesList[i] = stagesAtlas.getSubimage(i * 56, 0, 56, 56);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics g) {
        g.drawImage(stagesList[stage], x, y, width, height, null);
    }

    public void update() {

    }

    public void mouseMoved(MouseEvent e) {
        if (hitBox.contains(e.getPoint())) {
            stage = 1;
        } else {
            stage = 0;
        }
    }

    public void mousePressed(MouseEvent e) {
        if (hitBox.contains(e.getPoint())) {
            stage = 2;
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (hitBox.contains(e.getPoint())) {
            restartWave();
        }

        stage = 1;
    }

    public void restartWave() {
        gp.restartWave();
    }
}
