import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    World world;
    JPanel gui;
    GameWindow() {
        this.setTitle("virtualWorld");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 600);
        this.setVisible(true);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setLayout(null);

        world = new World();
        this.add(world.getTerrain());
        gui = new JPanel();
        gui.setBounds(0, 500, 500, 100);
        gui.setBackground(new Color(35, 158, 213));
        JButton nextTurn = new JButton();
        nextTurn.setText("Next turn");
        nextTurn.setFocusable(false);
        gui.add(nextTurn);
        this.add(gui);

        ImageIcon icon = new ImageIcon("earthIcon.png");
        this.setIconImage(icon.getImage());
    }

}
