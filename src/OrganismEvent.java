import java.awt.*;

public class OrganismEvent {
    String description;
    Color color;

    public OrganismEvent(String description) {
        this.description = description;
    }

    public OrganismEvent(String description, Color color) {
        this.description = description;
        this.color = color;
    }
}
