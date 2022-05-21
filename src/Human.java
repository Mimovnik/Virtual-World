import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.CountDownLatch;

import static java.lang.Math.random;

public class Human extends Animal {
    private int abilityTurns;
    private boolean abilityActive;
    private boolean abilityOnCooldown;
    JLabel inputHints;
    KeyListener keyListener;
    direction dir;

    CountDownLatch latch;

    Human(World world) {
        super(world, 5, 4, "Human");
        JLabel skin = new JLabel();
        skin.setBackground(new Color(255, 195, 170));
        skin.setText("H");
        setSkin(skin);
        dir = direction.LEFT;

        abilityActive = false;
        abilityOnCooldown = false;
        abilityTurns = 0;

        inputHints = new JLabel();
        inputHints.setOpaque(true);
        inputHints.setBackground(Color.green);
        inputHints.setPreferredSize(new Dimension(400, 50));
        inputHints.setFont(new Font("Noto Sans", Font.PLAIN, 10));

        String hint = "You're a human [H], arrows- movement";
        if (abilityActive) {
            hint += ", ability is active for next " + (5 - abilityTurns) + " turns.";
        } else if (abilityOnCooldown) {
            hint += ", ability is on cooldown for next " + abilityTurns + " turns.";
        } else {
            hint += ", e- activate a special ability.";
        }
        inputHints.setText(hint);

        world.window.gui.add(inputHints);
        keyListener = new KeyListener() {

            @Override
            public void keyTyped(KeyEvent keyEvent) {

            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                if(latch == null){
                    return;
                }
                switch (keyEvent.getKeyCode()) {
                    // Arrow left
                    case 37:
                        dir = direction.LEFT;
                        break;
                    // Arrow up
                    case 38:
                        dir = direction.UP;
                        break;
                    // Arrow right
                    case 39:
                        dir = direction.RIGHT;
                        break;
                    // Arrow down
                    case 40:
                        dir = direction.DOWN;
                        break;
                }
                boolean abilityKeyPressed = keyEvent.getKeyChar() == 'e';
                if( dir != direction.NOWHERE){
                    latch.countDown();
                }
                if (abilityKeyPressed && !abilityActive && !abilityOnCooldown) {
                    abilityActive = true;
                }
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
            }
        };
        world.window.addKeyListener(keyListener);
    }

    @Override
    public void action() {
        if (isDead()) {
            return;
        }

        attackedThisTurn = false;
        if (stunned) {
            stunned = false;
            return;
        }

        direction dir = getDirection();
        moveRange = 1;
        world.window.repaint();
        world.window.revalidate();

        if (abilityActive) {
            abilityTurns++;
            if (abilityTurns <= 3) {
                moveRange = 2;
            } else {
                if ((int) (random() * 2) == 0) {
                    world.writeEvent("Human lucky", Color.green);
                    moveRange = 2;
                }
            }
            if (abilityTurns >= 5) {
                abilityActive = false;
                abilityOnCooldown = true;
            }
        } else {
            abilityTurns--;
            if (abilityTurns <= 0) {
                abilityOnCooldown = false;
            }
        }

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
                world.writeEvent(getName() + " attacks " + defender.getName(), null);
                collide(defender);
            }
        }
    }

    @Override
    protected direction getDirection() {
        String hint = "You're a human [H], arrows- movement";
        if (abilityActive) {
            hint += ", ability is active for next " + (5 - abilityTurns) + " turns.";
        } else if (abilityOnCooldown) {
            hint += ", ability is on cooldown for next " + abilityTurns + " turns.";
        } else {
            hint += ", e- activate a special ability.";
        }
        inputHints.setText(hint);

        try {
            latch = new CountDownLatch(1);
            latch.await();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return dir;
    }

    @Override
    protected Animal giveBirth() {
        return new Human(world);
    }

    @Override
    protected void die() {
        dead = true;
        world.window.gui.remove(inputHints);
    }
}
