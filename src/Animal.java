import static java.lang.Math.random;

public abstract class Animal extends Organism {
    protected boolean attackedThisTurn;

    protected int moveRange;

    protected void move(direction dir) {
        switch (dir) {
            case NOWHERE:
                break;
            case LEFT:
                if (getPos().getX() > 0) {
                    current.setX(current.getX() - 1);
                }
                break;
            case RIGHT:
                if (getPos().getX() + 1 < world.getWidth()) {
                    current.setX(current.getX() + 1);
                }
                break;
            case UP:
                if (getPos().getY() > 0) {
                    current.setY(current.getY() - 1);
                }
                break;
            case DOWN:
                if (getPos().getY() + 1 < world.getHeight()) {
                    current.setY(current.getY() + 1);
                }
                break;
        }
    }

    protected direction getDirection() {
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
        return direction.NOWHERE;
    }

    @Override
    protected void takeHit(Organism attacker) {
    }

    @Override
    public void action() {
        if(dead){
            return;
        }
        attackedThisTurn = false;
        if (stunned) {
            stunned = false;
            return;
        }
        direction dir = getDirection();
        for(int i = 0; i < moveRange; i++){
            last = new Position(current);
            move(dir);
        }
    }

    public Animal(World world, int strength, int initiative, String name) {
        super(world, strength, initiative, name);
        attackedThisTurn = false;
        moveRange = 1;
    }
}
