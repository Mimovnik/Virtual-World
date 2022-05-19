import javax.swing.*;
import java.awt.*;

public class Turtle extends  Animal{

    Turtle(World world) {
        super(world, 2, 1, "Turtle");
        JLabel skin = new JLabel();
        skin.setBackground(new Color(88,39,24));
        skin.setText("T");
        setSkin(skin);
    }
}
