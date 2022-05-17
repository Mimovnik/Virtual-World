import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class World {
    private Vector<Organism> organisms;
    private JLabel[] cells;
    private int width, height;

    World(int width, int height) {
        this.width = width;
        this.height = height;
        cells = new JLabel[width * height];
        for (int i = 0; i < cells.length; i++) {
            cells[i] = new JLabel();
//            label.setText("W");
//            label.setFont(new Font("Noto Sans", Font.BOLD,
//                    Integer.min((500 - gridLayout.getColumns() * gridLayout.getHgap()) / gridLayout.getColumns(),
//                            (500 - gridLayout.getRows() * gridLayout.getVgap()) / gridLayout.getRows())));
            cells[i].setForeground(Color.white);
            cells[i].setBackground(Color.lightGray);
            cells[i].setOpaque(true);
            cells[i].setHorizontalAlignment(JLabel.CENTER);
        }
    }

    private void stampOrganismsOnCells() {

    }

    public void renderCells(JPanel terrain) {
        stampOrganismsOnCells();
        terrain.removeAll();
        for (int i = 0; i < cells.length; i++) {
            terrain.add(cells[i]);
        }
    }
}
