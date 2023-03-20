package main;

import entity.Crow;
import entity.Enemy;
import inputs.KeyHandler;
import inputs.MouseHandler;
import interactive.TreasureChest;
import items.Coin;
import ui.Shop;
import ui.UI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GamePanel extends JPanel {

    // TODO: SCREEN FIELDS
    private final int SCREEN_WIDTH = 800;
    private final int SCREEN_HEIGHT = 600;

    // TODO: COMPONENTS AND UTILITIES
    private final Player player;
    private TreasureChest treasureChest;
    private final MouseHandler mouseHandler;
    private final KeyHandler keyHandler;
    private final UI ui;
    private final Sound soundEffect;
    private final Sound music;
    private boolean musicPaused = false;

    private final ArrayList<CannonBall> cannonBalls;

    // TODO: IMAGES
    private BufferedImage towerImage;
    private BufferedImage backgroundLayer1;
    private BufferedImage backgroundLayer2;

    // TODO: ENEMIES
    private int enemySpawnTick;
    private final int enemySpawnSpeed = 120;
    private final ArrayList<Enemy> enemies;

    private WaveHandler waveHandler;
    private int currentWaveKills;
    private int enemiesToKill;

    private int gameState;
    public final int PLAYING = 0;
    public final int GAME_OVER = 1;
    public final int PAUSED = 2;

    public GamePanel() {
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));

        player = new Player(this);
        treasureChest = new TreasureChest(0, 450, 1.7f);
        cannonBalls = new ArrayList<>();
        enemies = new ArrayList<>();

        waveHandler = new WaveHandler(player.getPlayerData().getWaveNum());
        enemiesToKill = waveHandler.getEnemyCount();

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
            towerImage = ImageIO.read(getClass().getResourceAsStream("/Tower.png"));
            backgroundLayer1 = ImageIO.read(getClass().getResourceAsStream("/bg_layer_1.png"));
            backgroundLayer2 = ImageIO.read(getClass().getResourceAsStream("/bg_layer_2.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        playMusic(0);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (gameState == PLAYING) {
            update();
        }
        draw(g);

        try {
            Thread.sleep(10);
        } catch (Exception e) {
            e.printStackTrace();
        }

        repaint();
    }

    public void draw(Graphics g) {
        g.drawImage(backgroundLayer1, 0, 0, backgroundLayer1.getWidth() * 3, backgroundLayer1.getHeight() * 3, null);
        g.drawImage(backgroundLayer2, 0, 0, backgroundLayer2.getWidth() * 3, backgroundLayer2.getHeight() * 4, null);

        player.draw(g);

        for (CannonBall cannonBall : cannonBalls) {
            cannonBall.draw(g);
        }

        for (Enemy enemy : enemies) {
            enemy.draw(g);
        }

        g.drawImage(towerImage, 0, 0, null);

        treasureChest.draw(g);

        ui.draw(g);
    }

    public void update() {

        player.update();
        treasureChest.update();

        for (int i = 0; i < cannonBalls.size(); i++) {
            cannonBalls.get(i).update();

            if (cannonBalls.get(i).getPos().getX() > getSize().getWidth() || cannonBalls.get(i).getPos().getY() > getSize().getWidth()) {
                cannonBalls.remove(i);
            }
        }

        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).update();

            if (enemies.get(i).getPos().getX() < -enemies.get(i).getWidth()) {
                player.setLife(player.getLife() - 1);
                enemies.remove(i);
            }

            if (!enemies.get(i).isAlive()) {
                enemies.get(i).die();
                player.getPlayerData().addKills(1);
            }

            if (enemies.get(i).isRemovable()) {
                enemies.remove(i);
                currentWaveKills++;
                System.out.println("Current Wave Kills: " + currentWaveKills);
            }
        }

        for (int i = 0; i < ui.getOnScreenCoins().size(); i++) {
            if (ui.getOnScreenCoins().get(i).isRemovable()) {
                ui.getOnScreenCoins().remove(i);
            }
        }

        for (int i = 0; i < ui.getOnScreenDiamonds().size(); i++) {
            if (ui.getOnScreenDiamonds().get(i).isRemovable()) {
                ui.getOnScreenDiamonds().remove(i);
            }
        }

        if (currentWaveKills >= getCurrentWaveEnemyCount()) {
            endWave();
        }

        ui.update();

        enemySpawnTick++;
        if (enemySpawnTick > enemySpawnSpeed) {
            enemySpawnTick = 0;
            spawnEnemy();
        }

        checkCollisions();
    }

    public void addCannonBall(int x, int y, float xVel, float yVel) {
        cannonBalls.add(new CannonBall(x, y, xVel, yVel));
    }

    public void spawnEnemy() {
        if (currentWaveKills < enemiesToKill) {
            enemies.add(new Crow((int) getPreferredSize().getWidth(), (int) (Math.random() * (getPreferredSize().getHeight() - 58) + 58) / 2, 58, 58, this));
        }
    }

    public void endWave() {
        waveHandler.nextWave();
        enemiesToKill = waveHandler.getEnemyCount();
        currentWaveKills = 0;
        System.out.println("New Wave");
        player.getPlayerData().addWaves(1);
    }

    public void restartWave() {
        enemiesToKill = waveHandler.getEnemyCount();
        currentWaveKills = 0;
        player.setLife(player.getMaxLife());
        gameState = PLAYING;

        for (int i = 0; i < enemies.size(); i++) {
            enemies.remove(i);
        }

        enemySpawnTick = 0;
    }

    public void checkCollisions() {
        for (int i = 0; i < cannonBalls.size(); i++) {
            for (Enemy e : enemies) {
                if (e != null) {
                    if (i < cannonBalls.size()) {
                        if (e.getHitBox().intersects(cannonBalls.get(i).getHitBox())) {
                            e.setLife(e.getLife() - 1);
                            cannonBalls.remove(i);
                        }
                    }
                }
            }
        }
    }

    public void playSoundEffect(int index) {
        soundEffect.playSoundEffect(index);
    }
    public void playMusic(int index) {
        music.playMusic(index);
    }
    public void pauseMusic() {
        music.pauseMusic();
        musicPaused = true;
    }
    public void unPauseMusic() {
        music.unPauseMusic();
        musicPaused = false;
    }
    public boolean isMusicPaused() {
        return musicPaused;
    }

    public Player getPlayer() {
        return player;
    }

    public MouseHandler getMouseHandler() {
        return mouseHandler;
    }

    public void setGameState(int gameState) {
        this.gameState = gameState;
    }

    public int getGameState() {
        return gameState;
    }

    public UI getUi() {
        return ui;
    }

    public int getCurrentWaveKills() {
        return currentWaveKills;
    }

    public int getCurrentWaveEnemyCount() {
        return waveHandler.getEnemyCount();
    }

    public TreasureChest getTreasureChest() {
        return treasureChest;
    }
}
