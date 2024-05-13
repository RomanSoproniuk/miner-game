import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Objects;
import sweeper.Box;
import sweeper.Coordinate;
import sweeper.Game;
import sweeper.Ranges;

public class JavaSweeper extends JFrame {
    private Game game;
    private final int COLS = 9;
    private final int ROWS = 9;
    private final int BOMBS = 10;
    private final int IMAGE_SIZE = 50;
    private JLabel label;
    private JPanel panel;

    public static void main(String[] args) {
        new JavaSweeper();
    }

    private Image getImage(String name) {
        String filename = "img"
                + File.separator + name.toLowerCase() + ".png";
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource(filename)));
        return icon.getImage();
    }

    private JavaSweeper() {
        game = new Game(COLS, ROWS, BOMBS);
        game.start();
        Ranges.setSize(new Coordinate(COLS, ROWS));
        setImages();
        initLabel();
        initPanel();
        initFrame();
    }

    private void initLabel() {
        label = new JLabel("Let's GO!");
        add(label, BorderLayout.SOUTH);
    }

    private void initPanel() {
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (Coordinate coordinate : Ranges.getAllCoordinates()) {
                    g.drawImage((Image) game.getBox(coordinate).image,
                            coordinate.getX() * IMAGE_SIZE, coordinate.getY() * IMAGE_SIZE,
                            this);
                }
            }
        };
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / IMAGE_SIZE;
                int y = e.getY() / IMAGE_SIZE;
                Coordinate coordinate = new Coordinate(x, y);
                if (e.getButton() == MouseEvent.BUTTON1) {
                    game.pressLeftButton(coordinate);
                }
                if (e.getButton() == MouseEvent.BUTTON2) {
                    game.start();
                }
                if (e.getButton() == MouseEvent.BUTTON3) {
                    game.pressRightButton(coordinate);
                }
                label.setText(getMessage());
                panel.repaint();
            }
        });
        panel.setPreferredSize(new Dimension(
                Ranges.getSize().getX() * IMAGE_SIZE,
                Ranges.getSize().getY() * IMAGE_SIZE));
        add(panel);
    }

    private String getMessage() {
        switch (game.getState()) {
            case PLAYED -> {
                return "GO GO GO!";
            }
            case BOMBED -> {
                return "You have bombed LOOOSER!";
            }
            case WINNER -> {
                return "Not bad, You WON!";
            }
        }
        return null;
    }

    private void setImages() {
        for (Box box: Box.values()) {
            box.image = getImage(box.name());
        }
    }

    private void initFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sweeper");
        setResizable(false);
        setVisible(true);
        setIconImage(getImage("icon"));
        pack();
        setLocationRelativeTo(null);
    }
}
