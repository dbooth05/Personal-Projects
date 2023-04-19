import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @author Dylan Booth
 */

public class GameBoardGUI {

    private GamePiece[][] board;
    private final int size = 8;
    private JFrame frame;
    private JButton[][] buttons;
    private Container grid;

    public GameBoardGUI() {
        frame = new JFrame();

        frame.setSize(900, 900);
        frame.setLayout(new BorderLayout());

        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

        frame.setBackground(Color.gray);

        playAgain();
    }

    private void playAgain() {

        frame.getContentPane().removeAll();

        genGame();

        frame.revalidate();
        frame.repaint();

    }

    private void genGame() {

        frame.getContentPane().removeAll();

        board = new GamePiece[size][size];
        buttons = new JButton[size][size];

        grid = new Container();

        fillBoard();
        printBoard();

        //intiallize buttons
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                //TODO change this to show gamepiece with icon
                if (buttons[i][j] == null) {
                    buttons[i][j] = new JButton();
                } else {
                    buttons[i][j] = new JButton(board[i][j].getPiece());
                }
                buttons[i][j].setBorder(new LineBorder(Color.BLACK));
                buttons[i][j].addMouseListener(new MouseClickListener(i, j));
                grid.add(buttons[i][j]);
            }
        }

        frame.add(grid, BorderLayout.CENTER);

        frame.revalidate();
        frame.repaint();
    }

    static class MouseClickListener implements MouseListener {

        private int x;
        private int y;
        public MouseClickListener (int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    private void fillBoard() {
        board[0][0] = new GamePiece("rook", "black");
        board[0][1] = new GamePiece("knight", "black");
        board[0][2] = new GamePiece("bishop", "black");
        board[0][3] = new GamePiece("queen", "black");
        board[0][4] = new GamePiece("king", "black");
        board[0][5] = new GamePiece("bishop", "black");
        board[0][6] = new GamePiece("knight", "black");
        board[0][7] = new GamePiece("rook", "black");
        for (int i = 0; i < size; i++) {
            board[1][i] = new GamePiece("pawn", "black");
        }
        board[7][0] = new GamePiece("rook", "white");
        board[7][1] = new GamePiece("knight", "white");
        board[7][2] = new GamePiece("bishop", "white");
        board[7][3] = new GamePiece("queen", "white");
        board[7][4] = new GamePiece("king", "white");
        board[7][5] = new GamePiece("bishop", "white");
        board[7][6] = new GamePiece("knight", "white");
        board[7][7] = new GamePiece("rook", "white");
        for (int i = 0; i < size; i++) {
            board[6][i] = new GamePiece("pawn", "white");
        }
    }

    private void printBoard() {
        for (int i = 0; i < size; i++) {
            String row = "";
            for (int j = 0; j < size; j++) {

                if (board[i][j] == null) {
                    row += "   ";
                } else {
                    row += board[i][j].getPiece() + "\t";
                }
            }
            System.out.println(row);
        }
    }

}
