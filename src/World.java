import javax.swing.*;
import java.awt.*;

public class World {
    private JPanel terrain;
    private Organism[] organisms;
    private int width, height;

    World() {
        terrain = new JPanel();
        terrain.setPreferredSize(new Dimension(500, 500));
        terrain.setBackground(new Color(255, 202, 103));
    }

    public JPanel getTerrain() {
        return terrain;
    }

    public void createGrid(int width, int height) {
        this.width = width;
        this.height = height;
        int gap = Integer.min(100 / height, 100 / width);
        GridLayout gridLayout = new GridLayout(height, width, gap, gap);
        this.terrain.setLayout(gridLayout);
        for (int i = 0; i < width * height; i++) {
            JLabel label = new JLabel("W");
            label.setFont(new Font("Noto Sans", Font.BOLD,
                    Integer.min((500 - gridLayout.getColumns() * gridLayout.getHgap()) / gridLayout.getColumns(),
                            (500 - gridLayout.getRows() * gridLayout.getVgap()) / gridLayout.getRows())));
            label.setForeground(Color.white);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setBackground(Color.lightGray);
            label.setOpaque(true);
            this.terrain.add(label);

        }
        this.terrain.repaint();
        this.terrain.revalidate();
    }
}
