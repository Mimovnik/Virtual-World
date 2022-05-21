import java.util.Random;

import static java.lang.Math.random;

public abstract class Animal extends Organism {
    protected boolean attackedThisTurn;

    protected int moveRange;

    protected void move(direction dir) {
        String moveEvent = getName();
        switch (dir) {
            case NOWHERE:
                moveEvent += " doesn't move.";
                break;
            case LEFT:
                if (getPos().getX() > 0) {
                    moveEvent += " moves left.";
                    current.setX(current.getX() - 1);
                    break;
                }
                moveEvent += " tries to move left but hits a wall.";
                break;
            case RIGHT:
                if (getPos().getX() + 1 < world.getWidth()) {
                    current.setX(current.getX() + 1);
                    moveEvent += " moves right";
                    break;
                }
                moveEvent += " tries to move right but hits a wall.";
                break;
            case UP:
                if (getPos().getY() > 0) {
                    current.setY(current.getY() - 1);
                    moveEvent += " moves up";
                    break;
                }
                moveEvent += " tries to move up but hits a wall.";
                break;
            case DOWN:
                if (getPos().getY() + 1 < world.getHeight()) {
                    current.setY(current.getY() + 1);
                    moveEvent += " moves down";
                    break;
                }
                moveEvent += " tries to move down but hits a wall.";
                break;
        }
        events.add(new OrganismEvent(moveEvent));
    }

    protected direction getDirection() {
        Random ran = new Random();
        int rnd = ran.nextInt(4);
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
        return direction.NOWHERE;
    }

    @Override
    protected void takeHit(Organism attacker) {
        events.add(new OrganismEvent(getName() + " took a hit from " + attacker.getName() + " and died.", null));
        die();
    }

    @Override
    protected void action() {
        if (dead) {
            return;
        }
        attackedThisTurn = false;
        if (stunned) {
            stunned = false;
            return;
        }
        direction dir = getDirection();
        for (int i = 0; i < moveRange; i++) {
            last = new Position(current);
            move(dir);
            Organism defender = world.getColliderWith(this);
            if (defender != null) {
                if (defender.getClass() == getClass()) {
                    breed(defender);
                    return;
                }
                attackedThisTurn = true;
                events.add(new OrganismEvent(getName() + " attacks " + defender.getName()));
                collide(defender);
            }
        }
    }

    protected void breed(Organism partner) {
        moveBack();
        if ((int) (random() * 2) == 0) {
            return;
        }
        int nearX = (int) (random() * 3) - 1;
        int nearY = (int) (random() * 3) - 1;
        Position birthPos = getPos().add(new Position(nearX, nearY));
        if (birthPos.getX() < 0 || birthPos.getX() >= world.getWidth()
                || birthPos.getY() < 0 || birthPos.getY() >= world.getHeight()) {
            return;
        }
        if (world.getOrganismByPos(birthPos) == null) {
            events.add(new OrganismEvent(getName() + " have a baby with " + partner.getName() + "."));
            world.addOrganism(giveBirth(), birthPos);
        }
    }

    protected abstract Animal giveBirth();

    public Animal(World world, int strength, int initiative, String name) {
        super(world, strength, initiative, name);
        attackedThisTurn = false;
        moveRange = 1;
    }
}
