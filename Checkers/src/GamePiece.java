/**
 * @author Dylan Booth
 */

public class GamePiece {

    String color; //holds color value of piece (Black or white)

    int stackSize; //holds value of how large stack is

    /**
     * Constructor to create a game piece
     * @param color the color of team piece belongs to
     */
    public GamePiece (String color) {
        this.color = color;
    }

    /**
     * Getter method for getting color of piece
     * @return the color of the piece
     */
    public String getColor() {
        return color;
    }

    /**
     * Getter method for getting size of stack
     * @return
     */
    public int getSize() {
        return stackSize;
    }

    /**
     * Increases the size of the stack/how tall the piece is
     */
    public void addStack() {
        stackSize++;
    }

}
