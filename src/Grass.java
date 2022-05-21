import javax.swing.*;
import java.awt.*;

public class Grass extends Plant{
    public Grass(World world){
        super(world, 0,0,"Grass");
        JLabel skin = new JLabel();
        skin.setBackground(new Color(0, 151, 23));
        skin.setText("G");
        setSkin(skin);
    }
    @Override
    protected Plant getSapling() {
        return new Grass(world);
    }
}
