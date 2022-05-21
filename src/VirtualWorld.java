import static java.lang.Math.random;

public class VirtualWorld {
    public static void main(String[] args) {
        try {
            GameWindow gameWindow = new GameWindow();
            gameWindow.initialize();
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}