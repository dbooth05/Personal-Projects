import java.awt.*;
import java.lang.ref.SoftReference;

/**
 * @author Dylan Booth
 */

public class GamePiece {

    private String piece = ""; //String to hold what type of piece it is
    private Color color; //TODO get rid of this
    private boolean isStartPos; //Boolean value holding if piece is in starting position
    private final String colorString; //The team piece belongs to as a string

    /**
     * Constructor for gamePiece elements
     * @param piece the type of piece it is (i.e. rook)
     * @param color the color of the team piece belongs to
     */
    public GamePiece(String piece, String color) {
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
            color = "";
        }
        isStartPos = true;
        colorString = color;

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

    /**
     * Getter method for the starting position, only matters for pawn
     * @return the value of whether piece is in starting position
     */
    public boolean isStartingPos() {
        return isStartPos;
    }

    /**
     * Changes the element from being in starting position, only matters for pawn
     */
    public void setNotStartPos() {
        isStartPos = false;
    }

    /**
     * Getter method for the color of piece as string
     * @return which team the piece belongs to, white or black
     */
    public String getColorString() {
        return colorString;
    }


}
