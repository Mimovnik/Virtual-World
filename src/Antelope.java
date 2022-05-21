import javax.swing.*;
import java.awt.*;
import java.util.Vector;

import static java.lang.Math.random;

public class Antelope extends Animal {
    Antelope(World world) {
        super(world, 4, 4, "Antelope");
        JLabel skin = new JLabel();
        skin.setBackground(new Color(238, 182, 95));
        skin.setText("A");
        setSkin(skin);
    }

    @Override
    protected Animal giveBirth() {
        return new Antelope(world);
    }

    private void escape() {
        direction dir;
        Vector<direction> possibleDirs = new Vector<>();
        Organism right = world.getOrganismByPos(getPos().add(new Position(1, 0)));
        Organism left = world.getOrganismByPos(getPos().add(new Position(-1, 0)));
        Organism down = world.getOrganismByPos(getPos().add(new Position(0, 1)));
        Organism up = world.getOrganismByPos(getPos().add(new Position(0, -1)));

        if (right != null) {
            if (!right.isStronger(this)) {
                possibleDirs.addElement(direction.RIGHT);
            }
        } else {
            possibleDirs.addElement(direction.RIGHT);
        }

        if (left != null) {
            if (!left.isStronger(this)) {
                possibleDirs.addElement(direction.LEFT);
            }
        } else {
            possibleDirs.addElement(direction.LEFT);
        }

        if (down != null) {
            if (!down.isStronger(this)) {
                possibleDirs.addElement(direction.DOWN);
            }
        } else {
            possibleDirs.addElement(direction.DOWN);
        }

        if (up != null) {
            if (!up.isStronger(this)) {
                possibleDirs.addElement(direction.UP);
            }
        } else {
            possibleDirs.addElement(direction.UP);
        }

        if (possibleDirs.isEmpty()) {
            dir = direction.NOWHERE;
        } else {
            dir = possibleDirs.elementAt((int) (random() * possibleDirs.size()));
        }
        move(dir);
    }

    @Override
    protected void takeHit(Organism attacker) {
        if ((int) (random() * 2) == 0) {
            world.writeEvent(getName() + " escapes from a fight with " + attacker.getName() + ".", null);
            escape();
        } else {
            world.writeEvent(getName() + " took a hit from " + attacker.getName() + " and died.", null);
            die();
        }
    }
}
