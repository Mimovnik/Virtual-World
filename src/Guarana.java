import javax.swing.*;
import java.awt.*;

public class Guarana extends Plant{
    public Guarana(World world){
        super(world, 0,0,"Guarana");
        JLabel skin = new JLabel();
        skin.setBackground(new Color(245, 59, 59));
        skin.setText("U");
        setSkin(skin);
    }

    @Override
    protected Plant getSapling() {
        return new Guarana(world);
    }

    @Override
    protected void takeHit(Organism attacker){
        events.add(new OrganismEvent(getName() + " got eaten by " + attacker.getName() + ".", null));
        die();
        attacker.buff(3);
    }
}
