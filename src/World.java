import javax.swing.*;
import java.awt.*;

public class World {
    private JPanel terrain;
    private Organism[] organisms;
    World(){
        terrain = new JPanel();
        terrain.setPreferredSize(new Dimension(500, 500));
        terrain.setBackground(new Color(255,202,103));
    }
    JPanel getTerrain(){
        return terrain;
    }
}
