import javax.swing.*;
import java.awt.*;

public class Sheep extends  Animal{
    Sheep(World world) {
        super(world, 4, 4, "Sheep");
        JLabel skin = new JLabel();
        skin.setBackground(new Color(231,232,168));
        skin.setText("S");
        setSkin(skin);
    }
    @Override
    protected Animal giveBirth(){
        return new Sheep(world);
    }
}
