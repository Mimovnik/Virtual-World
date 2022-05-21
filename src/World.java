import javax.lang.model.util.Elements;
import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.random;

public class World {
    private int width, height;
    private Vector<Organism> organisms;
    private JLabel[] cells;

    public JPanel combatLog;
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
            cells[pos.getY() * width + pos.getX()].setBorder(BorderFactory.createLineBorder(Color.black, 1));
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

    public World(int width, int height, JPanel combatLog) {
        this.width = width;
        this.height = height;
        this.combatLog = combatLog;

        cells = new JLabel[width * height];
        wipeCells();
        // Organism density on terrain in %
        int organismDensity = 10;
        int organismsNumber = organismDensity * width * height / 100;

        organisms = new Vector<>();
        organisms.addElement(new Human(this));

        for (int i = 0; i < organismsNumber; i++) {
            int whichOne = (int) (random() * 10);
            switch (whichOne) {
                case 0:
                    organisms.addElement(new Antelope(this));
                    break;
                case 1:
                    organisms.addElement(new Fox(this));
                    break;
                case 2:
                    organisms.addElement(new Sheep(this));
                    break;
                case 3:
                    organisms.addElement(new Turtle(this));
                    break;
                case 4:
                    organisms.addElement(new Wolf(this));
                    break;
//                case 5:
//                    organisms.push_back(new Dandelion(this));
//                    break;
//                case 6:
//                    organisms.push_back(new Grass(this));
//                    break;
//                case 7:
//                    organisms.push_back(new Guarana(this));
//                    break;
//                case 8:
//                    organisms.push_back(new PineBorscht(this));
//                    break;
//                case 9:
//                    organisms.push_back(new Wolfberry(this));
//                    break;
            }
        }
    }
    public void draw(JPanel terrain) {
        renderCells(terrain);
        terrain.repaint();
        terrain.revalidate();
    }
    public void writeEvent(String event, Color color){
        JLabel entry = new JLabel(event);
        entry.setPreferredSize(new Dimension(200, 15));
        entry.setFont(new Font("Noto Sans", Font.PLAIN, 10));
        if(color != null) {
            entry.setOpaque(true);
            entry.setBackground(color);
        }
        combatLog.add(entry);
    }
    private void sortOrganisms() {
        organisms.sort((left, right) -> {
            if (left.initiative != right.initiative) {
                return right.initiative - left.initiative;
            }
            return left.birthDate - right.birthDate;
        });
    }
    private void removeDeadOrganisms(){
        for(int i = 0; i < organisms.size(); i++){
            if(organisms.elementAt(i).isDead()){
                organisms.removeElementAt(i);
            }
        }
    }
    public void makeActions(JPanel terrain) {
        sortOrganisms();
        for (int i = 0; i < organisms.size(); i++) {
            writeEvent("This is " + organisms.elementAt(i).getName() + "'s turn.", Color.lightGray);
            organisms.elementAt(i).action();
            draw(terrain);
//            try {
//                TimeUnit.MILLISECONDS.sleep(100);
//            } catch (Exception e) {
//                System.err.println(e);
//            }
            removeDeadOrganisms();
        }
    }
    Organism getColliderWith(Organism attacker){
        for(int i = 0; i < organisms.size();i++) {
            if (attacker.getBirthDate() != organisms.elementAt(i).getBirthDate() && attacker.getPos().equals(organisms.elementAt(i).getPos())) {
                return organisms.elementAt(i);
            }
        }
        return null;
    }
    public Position getRandomEmptyPos() {
        if (organisms.size() >= width * height) {
            return null;
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
                            (500 - gridLayout.getRows() * gridLayout.getVgap()) / gridLayout.getRows()) - 2));
            cells[i].setForeground(Color.white);
            cells[i].setOpaque(true);
            cells[i].setHorizontalAlignment(JLabel.CENTER);
            terrain.add(cells[i]);
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Organism getOrganismByPos(Position position) {
        for(int i = 0; i < organisms.size(); i++){
            if(organisms.elementAt(i).getPos().equals(position)){
                return organisms.elementAt(i);
            }
        }
        return null;
    }

    public void addOrganism(Animal newborn, Position birthPos) {
        newborn.setPos(birthPos);
        newborn.stun();
        organisms.addElement(newborn);
    }
}
