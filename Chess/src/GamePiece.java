import java.awt.*;
import java.lang.ref.SoftReference;

/**
 * @author Dylan Booth
 */

public class GamePiece {

    private String piece = "";
    private Color color;
    private boolean isStartPos;
    private boolean isHighlighted;

    private int x;
    private int y;

    public GamePiece(String piece, String color, int x, int y) {
        if (piece != "") {
            this.piece = piece;
        } else {
            this.piece = "null";
        }
        if (color.equals("black")) {
            this.color = new Color(0, 0, 0);
        } else if (color.equals("white")){
            this.color = new Color(255, 255, 255);
        } else {
            color = null;
        }
        isStartPos = true;
        isHighlighted = false;

        this.x = x;
        this.y = y;
    }

    public Color getColor() {
        return  color;
    }

    /**
     * Method to return piece type
     * @return the type of game piece
     */
    public String getPiece() {
        return piece;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isStartingPos() {
        return isStartPos;
    }

    public void setNotStartPos() {
        isStartPos = false;
    }
    public boolean isHighlighted() {
        return isHighlighted;
    }

    public void highlight() {
        isHighlighted = !isHighlighted;
    }

}
