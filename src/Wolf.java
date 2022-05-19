import javax.swing.*;
import java.awt.*;

public class Wolf extends Animal{
    Wolf(World world) {
        super(world, 9, 5, "Wolf");
        JLabel skin = new JLabel();
        skin.setBackground(new Color(56,56,56));
        skin.setText("W");
        setSkin(skin);
    }
}
