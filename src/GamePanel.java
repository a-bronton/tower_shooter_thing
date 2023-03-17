import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GamePanel extends JPanel {

    private final int SCREEN_WIDTH = 800;
    private final int SCREEN_HEIGHT = 600;

    private Player player;
    private MouseHandler mouseHandler;
    private KeyHandler keyHandler;
    private UI ui;
    private Sound soundEffect;
    private Sound music;

    private ArrayList<CannonBall> cannonBalls;

    private BufferedImage towerImage;
    private BufferedImage backgroundLayer1;
    private BufferedImage backgroundLayer2;

    public GamePanel() {
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));

        player = new Player(this);
        cannonBalls = new ArrayList<CannonBall>();
        ui = new UI(this);

        soundEffect = new Sound();
        music = new Sound();

        mouseHandler = new MouseHandler(this);
        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);

        keyHandler = new KeyHandler(this);
        addKeyListener(keyHandler);

        setFocusable(true);


        try {
            towerImage = ImageIO.read(getClass().getResourceAsStream("Tower.png"));
            backgroundLayer1 = ImageIO.read(getClass().getResourceAsStream("bg_layer_1.png"));
            backgroundLayer2 = ImageIO.read(getClass().getResourceAsStream("bg_layer_2.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        playMusic(0);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g.drawImage(backgroundLayer1, 0, 0, backgroundLayer1.getWidth() * 3, backgroundLayer1.getHeight() * 3,  null);
        g.drawImage(backgroundLayer2, 0, 0, backgroundLayer2.getWidth() * 3, backgroundLayer2.getHeight() * 4,null);

        player.update();
        player.draw(g2);

        for (int i = 0; i < cannonBalls.size(); i++) {
            cannonBalls.get(i).update();
            cannonBalls.get(i).draw(g);

            if (cannonBalls.get(i).getPos().getX() > getSize().getWidth() || cannonBalls.get(i).getPos().getY() > getSize().getWidth()) {
                cannonBalls.remove(i);
            }
        }

        g.drawImage(towerImage, 0, 0, null);

        ui.update();
        ui.draw(g);

        try {
            Thread.sleep(10);
        } catch (Exception e) {
            e.printStackTrace();
        }

        repaint();
    }

    public Player getPlayer() {
        return player;
    }

    public void addCannonBall(int x, int y, float xVel, float yVel) {
        cannonBalls.add(new CannonBall(x, y, xVel, yVel));
    }

    public void playSoundEffect(int index) {
        soundEffect.playSoundEffect(index);
    }

    public void playMusic(int index) {
        music.playMusic(index);
    }

    public MouseHandler getMouseHandler() {
        return mouseHandler;
    }
}
