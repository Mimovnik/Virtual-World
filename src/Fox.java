import javax.swing.*;
import java.awt.*;
import java.util.Vector;

import static java.lang.Math.random;

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
    @Override
    protected direction getDirection(){
        direction dir;
        Vector<direction> possibleDirs = new Vector<>();
        Organism right = world.getOrganismByPos(getPos().add(new Position(1, 0)));
        Organism left = world.getOrganismByPos(getPos().add(new Position(-1, 0)));
        Organism down = world.getOrganismByPos(getPos().add(new Position(0, 1)));
        Organism up = world.getOrganismByPos(getPos().add(new Position(0, -1)));

        if(right != null){
            if(!right.isStronger(this)){
                possibleDirs.addElement(direction.RIGHT);
            }
        }
        else{
            possibleDirs.addElement(direction.RIGHT);
        }

        if(left != null){
            if(!left.isStronger(this)){
                possibleDirs.addElement(direction.LEFT);
            }
        }
        else{
            possibleDirs.addElement(direction.LEFT);
        }

        if(down != null){
            if(!down.isStronger(this)){
                possibleDirs.addElement(direction.DOWN);
            }
        }
        else{
            possibleDirs.addElement(direction.DOWN);
        }

        if(up != null){
            if(!up.isStronger(this)){
                possibleDirs.addElement(direction.UP);
            }
        }
        else{
            possibleDirs.addElement(direction.UP);
        }

        if(possibleDirs.isEmpty()){
            dir = direction.NOWHERE;
        }else{
            dir = possibleDirs.elementAt((int) (random() * possibleDirs.size()));
        }
        return dir;
    }
}
