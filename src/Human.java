import javax.swing.*;
import java.awt.*;

public class Human extends Animal {
    Human(World world) {
        super(world, 1, 1, "Human");
        JLabel skin = new JLabel();
        skin.setBackground(Color.red);
        skin.setText("H");
        setSkin(skin);
    }

}
