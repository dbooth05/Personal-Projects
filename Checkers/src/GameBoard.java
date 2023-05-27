import javax.swing.*;

/**
 * @author Dylan Booth
 */

public class GameBoard {

    private GamePiece[][] board;
    private final int size = 8;

    public GameBoard() {

        JFrame frame = new JFrame();
        frame.setSize(900, 900);

        board = new GamePiece[8][8];

        


    }

}
