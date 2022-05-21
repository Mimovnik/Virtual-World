import java.awt.*;

public class Position {
    private int x;
    private int y;

    public Position() {
        x = 0;
        y = 0;
    }

    public Position(Position other) {
        this.x = other.x;
        this.y = other.y;
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public double getMagnitude() {
        return Math.sqrt(x * x + y * y);
    }

    public Position add(Position other) {
        return new Position(x + other.x, y + other.y);
    }

    public Position diff(Position other) {
        return new Position(x - other.x, y - other.y);
    }

    public Position rescale(double factor) {
        return new Position((int) (x * factor), (int) (y * factor));
    }

    public Position rescale(Position other) {
        double ratio = other.getMagnitude() / this.getMagnitude();
        return rescale(ratio);
    }

    public boolean equals(Position other){
        return x == other.x && y == other.y;
    }

}
