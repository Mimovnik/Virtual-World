import javax.swing.*;
import java.awt.*;
import static java.lang.Math.random;

public class Turtle extends  Animal{

    Turtle(World world) {
        super(world, 2, 1, "Turtle");
        JLabel skin = new JLabel();
        skin.setBackground(new Color(88,39,24));
        skin.setText("T");
        setSkin(skin);
    }
    @Override
    protected Animal giveBirth(){
        return new Turtle(world);
    }

    @Override
    protected direction getDirection(){
        if((int) (random() * 4) == 0){
            int rnd = (int) (random() * 4);
            switch (rnd) {
                case 0:
                    return direction.UP;
                case 1:
                    return direction.DOWN;
                case 2:
                    return direction.LEFT;
                case 3:
                    return direction.RIGHT;
            }
        }
        return direction.NOWHERE;
    }

    @Override
    protected void takeHit(Organism attacker){
        if(attacker.getStrength() < 5 && !attackedThisTurn){
            world.writeEvent(getName() + " resisted a hit from " + attacker.getName() + ".", null);
            attacker.moveBack();
        }else{
            world.writeEvent(getName() + " took a hit from " + attacker.getName() + " and died.", null);
            die();
        }
    }
}
