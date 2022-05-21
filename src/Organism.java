import javax.swing.*;
import java.awt.*;

public abstract class Organism {
    protected JLabel skin;
    protected int strength;
    protected int initiative;
    protected Position current;
    protected Position last;
    protected int birthDate;
    protected String name;
    protected World world;
    protected boolean stunned;
    protected boolean dead;
    protected static int counter = 0;

    public boolean isDead() {
        return dead;
    }

    public int getBirthDate() {
        return birthDate;
    }

    public void setPos(Position position) {
        this.current = new Position(position);
        this.last = new Position(position);
    }

    public void stun() {
        stunned = true;
    }

    public String getName() {
        return name;
    }

    protected int getStrength() {
        return strength;
    }

    protected enum direction {
        NOWHERE,
        LEFT,
        RIGHT,
        UP,
        DOWN
    }

    protected void collide(Organism defender) {
        if(defender.isStronger(this)){
            takeHit(defender);
        }else{
            defender.takeHit(this);
        }
    }

    protected abstract void takeHit(Organism attacker);

    protected void setSkin(JLabel skin) {
        this.skin = skin;
    }

    public Organism() {
        stunned = false;
        dead = false;
        current = new Position();
        last = new Position(current);
    }

    public int compareTo(Organism other) {
        if (initiative != other.initiative) {
            if (initiative > other.initiative) return 1;
            else return 0;
        }
        if (birthDate < other.birthDate) return 1;
        else return 0;
    }

    public Organism(World world, int strength, int initiative, String name) {
        counter++;
        this.name = name;
        this.stunned = false;
        this.dead = false;
        this.world = world;
        this.strength = strength;
        this.initiative = initiative;
        this.current = world.getRandomEmptyPos();
        if(current != null){
            this.last = new Position(current);
        }
        this.birthDate = counter;
    }

    public abstract void action();

    public boolean isStronger(Organism other) {
        return strength >= other.strength;
    }

    protected void die(){ dead = true;}

    public void moveBack() {
        current = new Position(last);
    }

    public Position getPos() {
        return current;
    }

    public JLabel getSkin() {
        return skin;
    }

}
