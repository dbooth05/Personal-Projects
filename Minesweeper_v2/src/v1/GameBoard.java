package v1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

public class GameBoard {

    private GameTile[][] board;
    private int difficulty;
    private int size;

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

        coords = coords.replaceAll(" ", "");
        String[] coord = coords.split(",");

        int x = Integer.parseInt(coord[1]);
        int y = Integer.parseInt(coord[0]);

        boolean cont = true;

        if (move.equalsIgnoreCase("r")) {

            if (board[x][y].getIsBomb()) {
                cont = board[x][y].click();
                System.out.println("You lost");
            } else {
                revealZerosRec(x, y);
            }
        } else if (move.equalsIgnoreCase("f")) {
            board[x][y].setFlag();
        }

        if (checkWin()) {
            cont = false;
            System.out.println("You won");
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

    /**
     * Reveals all adjacent spots to a zero
     * @param x the x coordinate of spot being revealed and checking adjacents
     * @param y the y coordinate of spot being revealed and checking adjacents
     */
    private void revealZerosRec(int x, int y) {

        if (x < 0 || y < 0 || x >= size || y >= size) {
            return;
        } else if (board[x][y].getAround() != 0) {
            board[x][y].reveal();
            return;
        } else if (board[x][y].getIsRevealed()) {
            return;
        } else {
            board[x][y].reveal();
            revealZerosRec(x-1, y);
            revealZerosRec(x, y-1);
            revealZerosRec(x, y+1);
            revealZerosRec(x+1, y);
            revealZerosRec(x-1, y-1);
            revealZerosRec(x-1, y+1);
            revealZerosRec(x+1, y-1);
            revealZerosRec(x+1, y+1);

        }
    }

    /**
     * Checks to determine whether the game is done.
     * @return false if the game is over, and true if the game is not over.
     */
    private boolean checkWin() {

        boolean win = true;

        for (int i = 0; i < size; i++) {

            for (int j = 0; j < size; j++) {

                if (!board[i][j].getIsRevealed() && !board[i][j].getIsBomb()) {
                    win = false;
                }

            }

        }

        return win;
    }
}