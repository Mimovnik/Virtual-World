import javax.swing.*;
import java.awt.*;

public class Fox extends  Animal{
    Fox(World world) {
        super(world, 3, 7, "Fox");
        JLabel skin = new JLabel();
        skin.setBackground(new Color(247,150,0));
        skin.setText("F");
        setSkin(skin);
    }
    @Override
    protected Animal giveBirth(){
        return new Fox(world);
    }
}
