package ui;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class ShopButton {

    private int x, y, width, height;
    private int goToPage;

    public final static int STATS = 0;
    public final static int UPGRADES = 1;

    private BufferedImage buttonImage, hoverSelector;

    private Rectangle hitBox;

    private boolean mouseOver, selected;

    private GamePanel gp;

    public ShopButton(int x, int y, int width, int height, int goToPage, GamePanel gp) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.gp = gp;

        this.goToPage = goToPage;
        hitBox = new Rectangle(x, y, width, height);

        initImages();
    }

    public void initImages() {
        try {
            hoverSelector = ImageIO.read(getClass().getResourceAsStream("/ui/shop/shop_selector.png"));

            if (goToPage == STATS) {
                buttonImage = ImageIO.read(getClass().getResourceAsStream("/ui/shop/stats_button.png"));
            } else if (goToPage == UPGRADES) {
                buttonImage = ImageIO.read(getClass().getResourceAsStream("/ui/shop/upgrades_button.png"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {

    }

    public void mouseMoved(MouseEvent e) {
        mouseOver = false;
        if (hitBox.contains(e.getPoint())) {
            mouseOver = true;
        }
    }

    public void mousePressed(MouseEvent e) {
        selected = false;
        if (hitBox.contains(e.getPoint())) {
            selected = true;
            gp.getUi().getShop().setPage(goToPage);
        }
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.RED);
        if (buttonImage != null) {
            g2.drawImage(buttonImage, x, y, width, height, null);
        } else {
            g2.fillRect(x, y, width, height);
        }

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        if (mouseOver) {
            g2.drawImage(hoverSelector, x, y, width, height, null);
        }

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        if (selected) {
            g2.drawImage(hoverSelector, x, y, width, height, null);
        }
    }
}
