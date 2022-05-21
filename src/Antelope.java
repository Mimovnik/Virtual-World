import javax.swing.*;
import java.awt.*;

public class Antelope extends Animal{
    Antelope(World world) {
        super(world, 4, 4, "Antelope");
        JLabel skin = new JLabel();
        skin.setBackground(new Color(238,182,95));
        skin.setText("A");
        setSkin(skin);
    }
    @Override
    protected Animal giveBirth(){
        return new Antelope(world);
    }
}
