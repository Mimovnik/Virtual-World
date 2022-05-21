import static java.lang.Math.random;

public abstract class Plant extends Organism {
    protected int spreadTries;

    @Override
    protected void takeHit(Organism attacker) {
        events.add(new OrganismEvent(getName() + " got eaten by " + attacker.getName() + "."));
        die();
    }

    protected void spread() {
        int nearX = (int) (random() * 3) - 1;
        int nearY = (int) (random() * 3) - 1;
        Position sowPos = getPos().add(new Position(nearX, nearY));
        if (sowPos.getX() < 0 || sowPos.getX() >= world.getWidth()
                || sowPos.getY() < 0 || sowPos.getY() >= world.getHeight()) {
            return;
        }
        if(world.getOrganismByPos(sowPos) == null){
            events.add(new OrganismEvent( getName() + " is spreading."));
            world.addOrganism(getSapling(), sowPos);
        }
    }

    protected abstract Plant getSapling();

    public Plant(World world, int strength, int initiative, String name) {
        super(world, strength, initiative, name);
        spreadTries = 1;
    }

    @Override
    protected void action() {
        if(dead){
            return;
        }
        if(stunned){
            stunned = false;
            return;
        }
        for(int i = 0; i < spreadTries; i++){
            if((int) (random() * 2) == 0){
                spread();
            }
        }
    }

}
