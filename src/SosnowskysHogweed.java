import javax.swing.*;
import java.awt.*;

public class SosnowskysHogweed extends Plant{
    public SosnowskysHogweed(World world){
        super(world, 10, 0,"Sosnowsky's Hogweed");
        JLabel skin = new JLabel();
        skin.setBackground(new Color(255, 255, 255));
        skin.setText("P");
        setSkin(skin);
    }

    @Override
    public boolean isStronger(Organism other){
        return true;
    }

    @Override
    protected Plant getSapling() {
        return new SosnowskysHogweed(world);
    }
}
