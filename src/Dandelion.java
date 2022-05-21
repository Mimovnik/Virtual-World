import javax.swing.*;
import java.awt.*;

public class Dandelion extends Plant{
    public Dandelion(World world){
        super(world, 0, 0, "Dandelion");
        JLabel skin = new JLabel();
        skin.setBackground(new Color(248, 255, 48));
        skin.setText("D");
        setSkin(skin);

        spreadTries = 3;
    }
    @Override
    protected Plant getSapling() {
        return new Dandelion(world);
    }
}
