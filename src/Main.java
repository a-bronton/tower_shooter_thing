import javax.swing.*;

public class Main extends JFrame {
    public static void main(String[] args) {
        new Main().setup();
    }

    public void setup() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        add(new GamePanel());

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}