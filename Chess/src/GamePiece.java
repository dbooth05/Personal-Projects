import java.awt.*;
import java.lang.ref.SoftReference;

/**
 * @author Dylan Booth
 */

public class GamePiece {

    private String piece;
    private Color color;

    public GamePiece(String piece, String color) {
        this.piece = piece;
        if (color.equals("black")) {
            this.color = new Color(0, 0, 0);
        } else {
            this.color = new Color(255, 255, 255);
        }
    }

    public Color getColor() {
        return  color;
    }

    public String getPiece() {
        return piece;
    }

}
