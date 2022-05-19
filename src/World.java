import javax.swing.*;
import java.awt.*;
import java.io.PipedOutputStream;
import java.util.Vector;

import static java.lang.Math.random;

public class World {
    private int width, height;
    private Vector<Organism> organisms;
    private JLabel[] cells;
    private int turnCount;

    private JLabel getCell(int x, int y) {
        if (x > width || x < 0) {
            throw new RuntimeException("Bad x coordinate in getCell()");
        }
        if (y > height || y < 0) {
            throw new RuntimeException("Bad y coordinate in getCell()");
        }
        return cells[y * width + x];
    }

    private JLabel getCell(Position position) {
        if (position.getX() > width || position.getX() < 0) {
            throw new RuntimeException("Bad x coordinate in getCell()");
        }
        if (position.getY() > height || position.getY() < 0) {
            throw new RuntimeException("Bad y coordinate in getCell()");
        }
        return cells[position.getY() * width + position.getX()];
    }

    private void stampOrganismsOnCells() {
        for (int i = 0; i < organisms.size(); i++) {
            Position pos = organisms.elementAt(i).getPos();
            cells[pos.getY() * width + pos.getX()] = organisms.elementAt(i).getSkin();
        }
    }

    private void wipeCells() {
        for (int i = 0; i < cells.length; i++) {
            cells[i] = new JLabel();
            cells[i].setBackground(Color.lightGray);
            cells[i].setForeground(Color.white);
            cells[i].setOpaque(true);
            cells[i].setHorizontalAlignment(JLabel.CENTER);
        }
    }

    public World(int width, int height) {
        this.width = width;
        this.height = height;
        cells = new JLabel[width * height];
        wipeCells();
        // Organism density on terrain in %
        int organismDensity = 10;
        int organismsNumber = organismDensity * width * height / 100;

        organisms = new Vector<>();
        organisms.addElement(new Human(this));
    }

    public void makeTurns() {
        for(int i = 0; i < organisms.size(); i++){
            organisms.elementAt(i).action();
        }
    }

    public Position getRandomEmptyPos() {
        if(organisms.size() >= width * height){
            throw new RuntimeException("Couldn't find random empty position due to full world");
        }
        Position pos = new Position((int) (random() * width), (int) (random() * height));
        for (int i = 0; i < organisms.size(); i++) {
            if (pos.equals(organisms.elementAt(i).getPos())) {
                pos.setX((int) (random() * width));
                pos.setY((int) (random() * height));
                i = 0;
            }
        }
        return pos;
    }

    public void renderCells(JPanel terrain) {
        wipeCells();
        stampOrganismsOnCells();
        terrain.removeAll();
        GridLayout gridLayout = (GridLayout) terrain.getLayout();
        for (int i = 0; i < cells.length; i++) {
            cells[i].setFont(new Font("Noto Sans", Font.BOLD,
                    Integer.min((500 - gridLayout.getColumns() * gridLayout.getHgap()) / gridLayout.getColumns(),
                            (500 - gridLayout.getRows() * gridLayout.getVgap()) / gridLayout.getRows())));
            cells[i].setForeground(Color.white);
            cells[i].setOpaque(true);
            cells[i].setHorizontalAlignment(JLabel.CENTER);
            terrain.add(cells[i]);
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight(){
        return height;
    }
}
