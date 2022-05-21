import javax.swing.*;
import java.awt.*;

public class Wolfberry extends Plant{
    public Wolfberry(World world){
        super(world, 99, 0, "Wolfberry");
        JLabel skin = new JLabel();
        skin.setBackground(new Color(154, 25, 25));
        skin.setText("B");
        setSkin(skin);
    }

    @Override
    public boolean isStronger(Organism other){
        return true;
    }

    @Override
    protected Plant getSapling() {
        return new Wolfberry(world);
    }
}
