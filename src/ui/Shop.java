package ui;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Shop {

    private GamePanel gp;

    // TODO: IMAGES
    private BufferedImage shopBg;

    // TODO: BUTTONS
    private ShopButton[] pageButtons;

    private int currentPage;

    private Font maruMon;

    public Shop(GamePanel gp) {
        this.gp = gp;

        pageButtons = new ShopButton[2];
        initButtons();

        try {
            maruMon = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/MaruMonica.ttf"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        initImages();
    }

    public void initImages() {
        try {
            shopBg = ImageIO.read(getClass().getResourceAsStream("/ui/shop/shop_background.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initButtons() {
        // TODO: STATS
        pageButtons[0] = new ShopButton(155, 180, 58, 58, ShopButton.STATS, gp);

        // TODO: UPGRADES
        pageButtons[1] = new ShopButton(155, 250, 58, 58, ShopButton.UPGRADES, gp);
    }

    public void update() {
        for (ShopButton sb : pageButtons) {
            sb.update();
        }
    }

    public void draw(Graphics g) {
        g.setColor(new Color(0, 0, 0, 100));
        g.fillRect(0, 0, (int) gp.getPreferredSize().getWidth(), (int) gp.getPreferredSize().getHeight());
        g.drawImage(shopBg, 0, 0, (int) gp.getPreferredSize().getWidth(), (int) gp.getPreferredSize().getHeight(), null);

        for (ShopButton sb : pageButtons) {
            sb.draw(g);
        }

        drawStats(g);
    }

    public void drawStats(Graphics g) {
        g.setFont(maruMon.deriveFont(Font.BOLD, 25));
        g.setColor(Color.WHITE);
        g.drawString("Kills: " + gp.getPlayer().getPlayerData().getKills(), 600, 200);
    }

    public ShopButton[] getPageButtons() {
        return pageButtons;
    }

    public void setPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public Font getMaruMon() {
        return maruMon;
    }
}
