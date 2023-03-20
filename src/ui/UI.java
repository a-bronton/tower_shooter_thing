package ui;

import buttons.RestartWaveButton;
import interactive.TreasureChest;
import items.Coin;
import items.Diamond;
import main.GamePanel;
import main.Utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class UI {

    private GamePanel gp;

    // TODO: PLAYING
    private BufferedImage lifeBarImage;
    private int lifeBarX, lifeBarY, lifeBarWidth, lifeBarHeight;
    private Heart[] hearts;

    // TODO: PAUSED/SHOP
    private Shop shop;
    private BufferedImage shopBg;

    // TODO: ONSCREEN COINS
    private ArrayList<Coin> onScreenCoins;
    private BufferedImage coinImage;

    // TODO: ONSCREEN DIAMONDS
    private ArrayList<Diamond> onScreenDiamonds;
    private BufferedImage diamondImage;

    // TODO: BOARDS
    private BufferedImage tallyBoardWithChains;
    private int treasureTallyBoardX, treasureTallyBoardY;
    private int treasureTallyBoardWidth, treasureTallyBoardHeight;

    // TODO: OTHER
    private BufferedImage waveProgressBar;
    private int waveProgressBarX, waveProgressBarY;
    private int waveProgressBarWidth, waveProgressBarHeight;

    // TODO: GAME OVER
    private RestartWaveButton restartWaveButton;
    private BufferedImage gameOverText;

    public UI(GamePanel gp) {
        this.gp = gp;

        shop = new Shop(gp);

        lifeBarWidth = (int) (132 * 1.5);
        lifeBarHeight = (int) (68 * 1.5);
        lifeBarX = 20;
        lifeBarY = 360;

        hearts = new Heart[3];
        initHearts();

        onScreenCoins = new ArrayList<Coin>();
        onScreenDiamonds = new ArrayList<Diamond>();

        // TODO: TREASURE TALLY BOARD
        treasureTallyBoardWidth = (int) (134 * 1.3);
        treasureTallyBoardHeight = (int) (74 * 1.3);
        treasureTallyBoardX = 50;
        treasureTallyBoardY = 0;

        // TODO: WAVE PROGRESS BAR
        waveProgressBarWidth = 200;
        waveProgressBarHeight = 35;
        waveProgressBarX = (int) gp.getPreferredSize().getWidth() - waveProgressBarWidth - 20;
        waveProgressBarY = 20;

        // TODO: GAME OVER
        restartWaveButton = new RestartWaveButton((int) (gp.getPreferredSize().getWidth() - 104) / 2, 300, 2, gp);

        // TODO: LAST
        initImages();
    }

    public void initImages() {
        try {
            lifeBarImage = ImageIO.read(getClass().getResourceAsStream("/ui/lifeBar.png"));

            shopBg = ImageIO.read(getClass().getResourceAsStream("/ui/shop/shop_background.png"));

            coinImage = ImageIO.read(getClass().getResourceAsStream("/items/coin.png"));
            diamondImage = ImageIO.read(getClass().getResourceAsStream("/items/diamond.png"));
            tallyBoardWithChains = ImageIO.read(getClass().getResourceAsStream("/objects/tally_board_and_chains.png"));

            waveProgressBar = ImageIO.read(getClass().getResourceAsStream("/ui/wave_progress_bar.png"));

            gameOverText = ImageIO.read(getClass().getResourceAsStream("/ui/game_over_text.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {
        for (Heart h : hearts) {
            h.update();
        }

        shop.update();
    }

    public void draw(Graphics g) {
        // TODO: PLAYING
        //if (gp.getGameState() == gp.PLAYING || gp.getGameState() == gp.GAME_OVER) {
            g.drawImage(lifeBarImage, lifeBarX, lifeBarY, lifeBarWidth, lifeBarHeight, null);

            for (int i = 0; i < gp.getPlayer().getLife(); i++) {
                hearts[i].draw(g);
            }

            for (Coin c : onScreenCoins) {
                c.update();
                c.draw(g);
            }

            for (Diamond d : onScreenDiamonds) {
                d.update();
                d.draw(g);
            }
        //}

        drawCoinCountUI(g);
        drawDiamondCountUI(g);

        drawWaveProgressBar(g);

        if (gp.getGameState() == gp.PAUSED) {
            shop.draw(g);
        }

        if (gp.getGameState() == gp.GAME_OVER) {
            restartWaveButton.update();
            drawGameOver(g);
            restartWaveButton.draw(g);
        }
    }

    public void drawCoinCountUI(Graphics g) {
        // TODO: TALLY BOARD
        g.drawImage(tallyBoardWithChains, treasureTallyBoardX, treasureTallyBoardY, treasureTallyBoardWidth, treasureTallyBoardHeight, null);
        g.setFont(getShop().getMaruMon().deriveFont(Font.BOLD, 30));
        g.setColor(Color.WHITE);
        g.drawImage(coinImage, treasureTallyBoardX + 10, treasureTallyBoardY + 45, 32, 32, null);
        g.drawString(String.valueOf(gp.getPlayer().getPlayerData().getCoins()),treasureTallyBoardX + 42, treasureTallyBoardY + 73);
    }

    public void drawDiamondCountUI(Graphics g) {
        // TODO: TALLY BOARD
        g.drawImage(tallyBoardWithChains, treasureTallyBoardX, treasureTallyBoardY + treasureTallyBoardHeight, treasureTallyBoardWidth, treasureTallyBoardHeight, null);
        g.setFont(getShop().getMaruMon().deriveFont(Font.BOLD, 30));
        g.setColor(Color.WHITE);
        g.drawImage(diamondImage, treasureTallyBoardX + 10, treasureTallyBoardY + treasureTallyBoardHeight + 45, 32, 32, null);
        g.drawString(String.valueOf(gp.getPlayer().getPlayerData().getDiamonds()),treasureTallyBoardX + 42, treasureTallyBoardY + treasureTallyBoardHeight + 73);
    }

    public void drawWaveProgressBar(Graphics g) {
        g.drawImage(waveProgressBar, waveProgressBarX, waveProgressBarY, waveProgressBarWidth, waveProgressBarHeight, null);

        g.setColor(Color.GREEN);
        double enemiesKilledRatio = (double) gp.getCurrentWaveKills() / gp.getCurrentWaveEnemyCount();
        g.fillRect(waveProgressBarX + 36, waveProgressBarY + 15, (int) (enemiesKilledRatio * (waveProgressBarWidth - 44)), 3);

        g.setFont(getShop().getMaruMon().deriveFont(Font.BOLD, 25));

        // TODO: WAVE # TEXT
        g.setColor(new Color(0, 0, 0, 100));
        //g.drawString("Wave: " + gp.getPlayer().getPlayerData().getWaveNum(), waveProgressBarX + 11, waveProgressBarY + 61);
        g.fillRoundRect(waveProgressBarX + 25, waveProgressBarY + 35, waveProgressBarWidth - 50, 30, 25, 25);
        g.setColor(Color.WHITE);
        g.drawString("Wave: " + gp.getPlayer().getPlayerData().getWaveNum(), waveProgressBarX + 30, waveProgressBarY + 60);
    }

    boolean soundPlayed = false;
    public void drawGameOver(Graphics g) {
        g.setColor(new Color(0, 0, 0, 176));
        g.fillRect(0, 0, (int) gp.getPreferredSize().getWidth(), (int) gp.getPreferredSize().getHeight());

        //g.drawImage(gameOverText, (int) (gp.getPreferredSize().getWidth() - 764) / 2, (int) (gp.getPreferredSize().getHeight() - 84) / 3, 764, 84, null);

        g.setFont(g.getFont().deriveFont(Font.BOLD, 90));
        g.setColor(Color.WHITE);
        g.drawString("DEFEAT", Utils.getCenterXForString(g, "DEFEAT", (int) gp.getPreferredSize().getWidth()), (int) (gp.getPreferredSize().getHeight() / 2.5));
        if (!soundPlayed) {
            gp.pauseMusic();
            gp.playSoundEffect(1);
            soundPlayed = true;
        }
    }

    public void addCoin(int x, int y, int xVel, int initialYVel, int value) {
        onScreenCoins.add(new Coin(x, y, xVel, initialYVel, value, gp));
    }

    public void addDiamond(int x, int y, int xVel, int initialYVel, int value) {
        onScreenDiamonds.add(new Diamond(x, y, xVel, initialYVel, value, gp));
    }

    public void initHearts() {
        int x = 45;
        int j = 3;
        for (int i = 0; i < hearts.length; i++) {
            hearts[i] = new Heart(lifeBarX + x, lifeBarY + ((lifeBarHeight - 32) / 2) + 2);
            hearts[i].animationIndex = j;
            j--;
            x += 32;
        }
    }

    public Shop getShop() {
        return shop;
    }

    public ArrayList<Coin> getOnScreenCoins() {
        return onScreenCoins;
    }

    public ArrayList<Diamond> getOnScreenDiamonds() {
        return onScreenDiamonds;
    }

    public RestartWaveButton getRestartWaveButton() {
        return restartWaveButton;
    }






    public static class Heart {

        private BufferedImage heartRaster;
        private BufferedImage[] animationList;

        private int animationIndex;
        private int animationTick;
        private int animationSpeed = 10;

        private int animationWaitTick;
        private int animationWait = 150;

        private int x, y;

        private boolean doAnimation = true;

        public Heart(int x, int y) {
            this.x = x;
            this.y = y;
            animationList = new BufferedImage[8];

            initImages();
        }

        public void initImages() {
            try {
                heartRaster = ImageIO.read(getClass().getResourceAsStream("/ui/heartRaster.png"));

                for (int i = 0; i < animationList.length; i++) {
                    animationList[i] = heartRaster.getSubimage(i * 18, 0, 18, 14);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void update() {
            if (!doAnimation) {
                animationWaitTick++;
                if (animationWaitTick > animationWait) {
                    animationWaitTick = 0;
                    doAnimation = true;
                }
            }

            if (doAnimation) {
                animationTick++;
                if (animationTick > animationSpeed) {
                    animationTick = 0;
                    animationIndex++;
                }

                if (animationIndex > animationList.length - 1) {
                    animationIndex = 0;
                    doAnimation = false;
                }
            }
        }
        public void draw(Graphics g) {
            g.drawImage(animationList[animationIndex], x, y, 36, 28, null);
        }
    }

}
