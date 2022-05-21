import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    private World world;
    private JButton nextTurnButton;
    private JPanel gui;
    private JPanel combatLog;
    private JLabel turnCounterDisplay;
    private JPanel terrain;
    private int turnCounter = 0;

    GameWindow() {
        this.setTitle("virtualWorld- Jakub Kwidziński 188647");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(801, 629);
        this.setVisible(true);
        this.setResizable(true);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        terrain = new JPanel();
        terrain.setPreferredSize(new Dimension(500, 500));
        terrain.setBackground(new Color(255, 202, 103));
        this.add(terrain, BorderLayout.WEST);

        gui = new JPanel();
        gui.setLayout(new FlowLayout(FlowLayout.CENTER));
        gui.setPreferredSize(new Dimension(800, 100));
        gui.setBackground(new Color(35, 158, 213));
        nextTurnButton = new JButton();
        nextTurnButton.setEnabled(false);
        nextTurnButton.setText("Next turn");
        nextTurnButton.setFont(new Font("Helvetica", Font.PLAIN, 12));
        nextTurnButton.setFocusable(false);
        nextTurnButton.setPreferredSize(new Dimension(100, 40));
        gui.add(nextTurnButton);
        this.add(gui, BorderLayout.SOUTH);

        combatLog = new JPanel();
        combatLog.setLayout(new FlowLayout(FlowLayout.CENTER));
        combatLog.setPreferredSize(new Dimension(300, 200000));
        combatLog.setBackground(new Color(203, 203, 203));

        JLabel header = new JLabel("Combat log:");
        header.setPreferredSize(new Dimension(300, 40));
        header.setOpaque(true);
        header.setBackground(new Color(109,109,109));
        header.setFont(new Font("Noto Sans", Font.PLAIN, 14));
        combatLog.add(header);

        JScrollPane scrollPane = new JScrollPane(combatLog);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setWheelScrollingEnabled(true);
        scrollPane.getVerticalScrollBar().setUnitIncrement(5);
        this.add(scrollPane, BorderLayout.EAST);

        ImageIcon icon = new ImageIcon("earthIcon.png");
        this.setIconImage(icon.getImage());
        this.repaint();
        this.revalidate();
    }

    private void createGrid(int width, int height) {
        int gap = Integer.min(100 / height, 100 / width);
        GridLayout gridLayout = new GridLayout(height, width, gap, gap);
        this.terrain.setLayout(gridLayout);
        this.terrain.repaint();
        this.terrain.revalidate();
    }

    public void initialize() {
        JPanel worldSizeQuery = new JPanel();
        worldSizeQuery.setPreferredSize(new Dimension(200, 100));
        worldSizeQuery.setBackground(new Color(45, 109, 181));
        worldSizeQuery.setOpaque(true);

        JLabel infoText = new JLabel("Enter width and height of the world");
        infoText.setFont(new Font("Helvetica", Font.PLAIN, 10));
        worldSizeQuery.add(infoText);

        JTextField widthField = new JTextField("20");
        widthField.setPreferredSize(new Dimension(50, 20));
        worldSizeQuery.add(widthField);

        JTextField heightField = new JTextField("20");
        heightField.setPreferredSize(new Dimension(50, 20));
        worldSizeQuery.add(heightField);

        JButton submit = new JButton("Submit");
        submit.setFont(new Font("Helvetica", Font.PLAIN, 15));
        submit.setPreferredSize(new Dimension(100, 20));
        submit.setFocusable(false);
        submit.addActionListener(actionEvent -> {
            int width = 0, height = 0;
            try {
                width = Integer.parseInt(widthField.getText());
                height = Integer.parseInt(heightField.getText());
            } catch (NumberFormatException nfe) {
                JLabel badInputText = new JLabel("Please enter integers.");
                badInputText.setFont(new Font("Helvetica", Font.PLAIN, 10));
                worldSizeQuery.add(badInputText);
                worldSizeQuery.repaint();
                worldSizeQuery.revalidate();
                return;
            }
            createGrid(width, height);
            world = new World(width, height, combatLog);
            world.renderCells(terrain);
            worldSizeQuery.remove(widthField);
            worldSizeQuery.remove(heightField);
            worldSizeQuery.remove(submit);
            gui.remove(worldSizeQuery);

            turnCounterDisplay = new JLabel();
            turnCounterDisplay.setBackground(Color.white);
            turnCounterDisplay.setOpaque(true);
            turnCounterDisplay.setText("0");
            turnCounterDisplay.setFont(new Font("Noto Sans", Font.PLAIN, 20));
            turnCounterDisplay.setHorizontalAlignment(JLabel.CENTER);
            turnCounterDisplay.setPreferredSize(new Dimension(50, 20));
            gui.add(turnCounterDisplay);

            gui.repaint();
            gui.revalidate();
            nextTurnButton.setEnabled(true);
            nextTurnButton.addActionListener(actionEvent1 -> {
                nextTurnButton.setEnabled(false);
                startTurn();
                nextTurnButton.setEnabled(true);
            });

        });
        worldSizeQuery.add(submit);
        gui.add(worldSizeQuery);
        gui.repaint();
        gui.revalidate();
    }

    private void updateTurnCounter() {
        turnCounter++;
        turnCounterDisplay.setText(Integer.toString(turnCounter));
    }

    private void draw() {
        world.draw(terrain);
        repaint();
        revalidate();
    }

    private void newTurnHeader(){
        JLabel header = new JLabel("Turn "+ turnCounter + " :");
        header.setPreferredSize(new Dimension(300, 20));
        header.setFont(new Font("Noto Sans", Font.PLAIN, 14));
        header.setOpaque(true);
        header.setBackground(new Color(129,129,129));
        combatLog.add(header);
    }

    public void startTurn() {
        updateTurnCounter();
        newTurnHeader();
        draw();
        world.makeActions(terrain);
        draw();
    }
}
