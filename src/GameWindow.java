import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    private World world;
    private JPanel gui;

    GameWindow() {
        this.setTitle("virtualWorld- Jakub Kwidziński 188647");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 629);
        this.setVisible(true);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        world = new World();
        this.add(world.getTerrain(), BorderLayout.NORTH);
        gui = new JPanel();
        gui.setLayout(new FlowLayout(FlowLayout.CENTER));
        gui.setPreferredSize(new Dimension(500, 100));
        gui.setBackground(new Color(35, 158, 213));
        JButton nextTurn = new JButton();
        nextTurn.setText("Next turn");
        nextTurn.setFocusable(false);
        nextTurn.setPreferredSize(new Dimension(100, 40));
        gui.add(nextTurn);
        this.add(gui, BorderLayout.SOUTH);

        ImageIcon icon = new ImageIcon("earthIcon.png");
        this.setIconImage(icon.getImage());
        this.repaint();
        this.revalidate();
    }

    public void startGame() {
        JPanel worldSizeQuery = new JPanel();
        worldSizeQuery.setPreferredSize(new Dimension(200, 100));
        worldSizeQuery.setBackground(Color.green);
        worldSizeQuery.setOpaque(true);

        JLabel infoText = new JLabel("Enter width and height of the world");
        infoText.setFont(new Font("Serif", Font.PLAIN, 10));
        worldSizeQuery.add(infoText);

        JTextField widthField = new JTextField();
        widthField.setPreferredSize(new Dimension(100, 20));
        worldSizeQuery.add(widthField);

        JTextField heightField = new JTextField();
        heightField.setPreferredSize(new Dimension(100, 20));
        worldSizeQuery.add(heightField);

        JButton submit = new JButton("Submit");
        submit.setPreferredSize(new Dimension(100, 20));
        submit.setFocusable(false);
        submit.addActionListener(actionEvent -> {
            int width = 0, height = 0;
            try {
                width = Integer.parseInt(widthField.getText());
                height = Integer.parseInt(heightField.getText());
            } catch (NumberFormatException nfe) {
                return;
            }
            System.out.println("wpisane: " + width + " " + height);
            worldSizeQuery.remove(widthField);
            worldSizeQuery.remove(heightField);
            worldSizeQuery.remove(submit);
            gui.remove(worldSizeQuery);
            gui.repaint();
            gui.revalidate();
        });
        worldSizeQuery.add(submit);
        gui.add(worldSizeQuery);
        gui.repaint();
        gui.revalidate();
    }

}
