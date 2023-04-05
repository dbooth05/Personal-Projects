package v1;

import java.util.Random;

public class GameBoard {

    GameTile[][] board;
    int difficulty;
    int size;
    boolean bomb;

    /**
     * Constructor to make new game board of given difficulty and size
     * @param size Width of the square game board
     * @param difficulty Frequency of bombs on the board
     */
    public GameBoard(int size, int difficulty) {

        board = new GameTile[size][size];
        this.difficulty = difficulty;
        this.size = size;

        fillBoard();
        printBoard();

    }

    /**
     * Fills board with tiles, places bombs with a chance based on difficulty
     */
    private void fillBoard() {

        Random rand = new Random();
        GameTile temp;

        for (int i = 0; i < size; i++) {

            for (int j = 0; j < size; j++) {

                int k = rand.nextInt(100);
                if (k <= difficulty) {
                    temp = new GameTile(true, false, false);
                } else {
                    temp = new GameTile(false, false, false);
                }
                board[i][j] = temp;

            }

        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j].addAround(checkBombCount(i, j));
            }
        }

    }

    /**
     * Prints the board into the console
     */
    public void printBoard() {

        System.out.println();

        String line1 = "   | ";
        String line2 = "-----";

        for (int i = 0; i < size; i++) {
            if (i >= 10) {
                line1 += i + " ";
            } else {
                line1 += i + "  ";
            }
            line2 += "---";
        }

        System.out.println(line1);
        System.out.println(line2);

        for (int i = 0; i < size; i++) {

            String row = "";
            if (i < 10) {
                row = i + "  | ";
            } else {
                row = i + " | ";
            }

            for (int j = 0; j < size; j++) {


                if (board[i][j].getIsRevealed()) {

                    if (board[i][j].getIsBomb()) {
                        row += "B  ";
                    } else if (board[i][j].getAround() != 0){
                        row += board[i][j].getAround() + "  ";
                    } else {
                        row += "   ";
                    }

                } else if (board[i][j].getIsFlagged()) {
                    row += "F  ";
                } else {
                    row += "H  ";
                }

            }

            System.out.println(row);

        }

    }

    public void printEnd() {
        System.out.println();

        String line1 = "   | ";
        String line2 = "-----";

        for (int i = 0; i < size; i++) {
            if (i >= 10) {
                line1 += i + " ";
            } else {
                line1 += i + "  ";
            }
            line2 += "---";
        }

        System.out.println(line1);
        System.out.println(line2);

        for (int i = 0; i < size; i++) {

            String row = "";
            if (i < 10) {
                row = i + "  | ";
            } else {
                row = i + " | ";
            }

            for (int j = 0; j < size; j++) {

                if (board[i][j].getIsBomb()) {
                    row += "B  ";
                } else if (board[i][j].getIsFlagged()) {
                    row += "F  ";
                } else if (board[i][j].getAround() != 0) {
                    row += board[i][j].getAround() + "  ";
                } else {
                    row += "   ";
                }

            }

            System.out.println(row);

        }
    }

    /**
     * Makes the desired move on tile,
     * @param coords the tile the player is making move on
     * @param move are they placing flag or revealing tile
     * @return returns true for the game to continue
     */
    public boolean makeMove(String coords, String move) {

        String[] coord = coords.split(",");

        int x = Integer.parseInt(coord[0]);
        int y = Integer.parseInt(coord[1]);

        boolean cont = true;

        if (move.toLowerCase().equals("r")) {
            cont = board[y][x].click();
        } else if (move.toLowerCase().equals("f")) {
            board[x][y].setFlag();
        }

        return cont;
    }

    /**
     * Checks bomb count around a given tile on the game board
     * @param x the x coordinate of the tile to be checked
     * @param y the y coordinate of the tile to be checked
     */
    private int checkBombCount(int x, int y) {
        int count = 0;
        for (int i = x - 1; i <= x + 1; i++) {

            for (int j = y - 1; j <= y + 1; j++) {

                if (i >= 0 && i < size && j >= 0 && j < size) {
                    if (board[i][j].getIsBomb()) {
                        count++;
                    }
                }

            }

        }
        return count;
    }

}