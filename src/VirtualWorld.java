
public class VirtualWorld {
    public static void main(String[] args) {
        try {
            GameWindow gameWindow = new GameWindow();
            gameWindow.startGame();
            gameWindow.repaint();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
