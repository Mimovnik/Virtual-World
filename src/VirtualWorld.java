import java.awt.*;

public class VirtualWorld {
    public static void main(String[] args) {
        try {
            GameWindow gameWindow = new GameWindow();
            gameWindow.askWorldSize();
            gameWindow.startGame();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}