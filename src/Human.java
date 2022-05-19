import javax.swing.*;
import java.awt.*;

public class Human extends Animal {
    Human(World world) {
        super(world, 1, 1, "Human");
        JLabel skin = new JLabel();
        skin.setBackground(new Color(255, 195, 170));
        skin.setText("H");
        setSkin(skin);
    }

}
