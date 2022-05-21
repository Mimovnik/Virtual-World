import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Vector;

import static java.lang.Math.random;

public class World {
    private final int width;
    private final int height;
    private Vector<Organism> organisms;
    private JLabel[] cells;

    public GameWindow window;
    public World(int width, int height, GameWindow window) {
        this.width = width;
        this.height = height;
        this.window = window;

        cells = new JLabel[width * height];
        // Organism density on terrain in %
        int organismDensity = 10;
        int organismsNumber = organismDensity * width * height / 100;

        organisms = new Vector<>();
        organisms.addElement(new Human(this));

        for (int i = 0; i < organismsNumber; i++) {
            int whichOne = (int) (random() * 9);
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
                case 5:
                    organisms.addElement(new Dandelion(this));
                    break;
                case 6:
                    organisms.addElement(new Grass(this));
                    break;
                case 7:
                    organisms.addElement(new Guarana(this));
                    break;
                case 8:
                    organisms.addElement(new SosnowskysHogweed(this));
                    break;
                case 9:
                    organisms.addElement(new Wolfberry(this));
                    break;
            }
        }
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
            int finalI = i;
            cells[i] = new JLabel();
            cells[i].setBackground(Color.lightGray);
            cells[i].setForeground(Color.white);
            cells[i].setOpaque(true);
            cells[i].setHorizontalAlignment(JLabel.CENTER);


            JPopupMenu menu = new JPopupMenu("Add an organism");
            JMenuItem one = new JMenu("1");
            JMenuItem two = new JMenu("2");
            JMenuItem three = new JMenu("3");
            JMenuItem four = new JMenu("4");one.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    System.out.println(finalI + " cell has been clicked;");
                }
            });
            two.addActionListener(actionEvent -> {
                cells[finalI].setBackground(Color.black);
            });
            menu.add(one);
            menu.add(two);
            menu.add(three);
            menu.add(four);

            cells[i].addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e){
                    menu.show(cells[finalI], e.getX(), e.getY());
                }
            });


            window.add(menu);

//            int finalI = i;
//            cells[i].addMouseListener(new MouseListener() {
//                @Override
//                public void mouseClicked(MouseEvent mouseEvent) {
//
//                }
//
//                @Override
//                public void mousePressed(MouseEvent mouseEvent) {
//
//                }
//
//                @Override
//                public void mouseReleased(MouseEvent mouseEvent) {
//
//                }
//
//                @Override
//                public void mouseEntered(MouseEvent mouseEvent) {
//                        cells[finalI].setBackground(Color.white);
//                }
//
//                @Override
//                public void mouseExited(MouseEvent mouseEvent) {
//
//                    cells[finalI].setBackground(Color.lightGray);
//                }
//            });
        }
    }

    public void draw(JPanel terrain) {
        renderCells(terrain);
        terrain.repaint();
        terrain.revalidate();
    }
    public void writeEvent(String event, Color color){
        JLabel entry = new JLabel(event);
        entry.setPreferredSize(new Dimension(300, 15));
        entry.setFont(new Font("Noto Sans", Font.PLAIN, 10));
        if(color != null) {
            entry.setOpaque(true);
            entry.setBackground(color);
        }
        window.combatLog.add(entry);
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
        SwingWorker<Void, String> actions= new SwingWorker<Void, String>() {
            @Override
            protected Void doInBackground() throws Exception {
                sortOrganisms();
                for (int i = 0; i < organisms.size(); i++) {
                    writeEvent("This is " + organisms.elementAt(i).getName() + "'s turn.", Color.lightGray);
                    organisms.elementAt(i).action();
                    removeDeadOrganisms();
                    publish("Repaint");
                }
                return null;
            }
            @Override
            protected void process(List<String> chunks) {
                if(!chunks.isEmpty()){
                    draw(terrain);
                    window.repaint();
                    window.revalidate();
                }
            }
        };
        actions.execute();
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

    public void addOrganism(Organism newborn, Position birthPos) {
        newborn.setPos(birthPos);
        newborn.stun();
        organisms.addElement(newborn);
    }
}
