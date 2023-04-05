package v1;

public class GameTile {

    private boolean isBomb;
    private boolean isFlagged;
    private boolean isRevealed;
    private int numAround;

    public GameTile(boolean isBomb, boolean isFlagged, boolean isRevealed) {
        this.isBomb = isBomb;
        this.isFlagged = isFlagged;
        this.isRevealed = isRevealed;
        numAround = 0;
    }


    /**
     * Sets the number of bombs around that tile
     * @param num number of bombs on surrounding tiles
     */
    public void addAround(int num) {
        numAround += num;
    }

    public int getAround() {
        return numAround;
    }

    /**
     * Getter method to return whether tile is a bomb or not
     * @return the value of isBomb
     */
    public boolean getIsBomb() {
        return isBomb;
    }

    /**
     * Getter method to return whether tile is flagged
     * @return the value of isFlagged
     */
    public boolean getIsFlagged() {
        return isFlagged;
    }


    public boolean getIsRevealed() {
        return isRevealed;
    }


    /**
     * Setter method to set flag on tile
     */
    public void setFlag() {
        isFlagged = true;
    }

    /**
     * Takes turn on tile, also checks if tile is a bomb
     * @return true if tile is empty, and false if its a bomb
     */
    public boolean click() {
        if (isBomb) {
            return false;
        } else {
            isRevealed = true;
            return true;
        }
    }

}
