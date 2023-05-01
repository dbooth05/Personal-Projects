import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @author Dylan Booth
 */

public class GameBoardGUI {

    private static GamePiece[][] board;
    private static final int size = 8;
    private static JFrame frame;
    private static JButton[][] buttons;
    private Container grid;

    static boolean turn;

    private static final Color black = new Color(0, 0, 0);
    private static final Color white = new Color(255,255, 255);
    private static final Color highlight = new Color(255, 243, 115);

    static GamePiece selected;

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
        grid.setLayout(new GridLayout(size, size));

        fillBoard();
        printBoard();

        //intiallize buttons
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                //TODO change this to show gamepiece with icon
                if (board[i][j].getPiece().equals("null")) {
                    buttons[i][j] = new JButton();
                } else {
                    buttons[i][j] = new JButton(board[i][j].getPiece());
                }

                Color tmp = board[i][j].getColor();
                if (tmp != null) {
                    buttons[i][j].setForeground(tmp);
                }
                buttons[i][j].setBorder(new LineBorder(Color.BLACK));
                buttons[i][j].addMouseListener(new MouseClickListener(i, j));
                buttons[i][j].setFont(new Font(buttons[i][j].getFont().getFontName(), buttons[i][j].getFont().getStyle(), 20));

                grid.add(buttons[i][j]);

                if ((i + j) % 2 == 0) {
                    buttons[i][j].setBackground(new Color(85, 85, 85));
                } else {
                    buttons[i][j].setBackground(new Color(140, 140, 140));
                }

            }
        }

        frame.add(grid, BorderLayout.CENTER);

        frame.setResizable(false);

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
            if (selected == null) {
                selectPiece(x, y);
            } else {
                makeMove(selected.getX(), selected.getY(), x, y);
            }
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
        board[0][0] = new GamePiece("rook", "black", 0, 0);
        board[0][1] = new GamePiece("knight", "black", 0, 1);
        board[0][2] = new GamePiece("bishop", "black", 0, 2);
        board[0][3] = new GamePiece("queen", "black", 0, 3);
        board[0][4] = new GamePiece("king", "black", 0, 4);
        board[0][5] = new GamePiece("bishop", "black", 0, 5);
        board[0][6] = new GamePiece("knight", "black", 0, 6);
        board[0][7] = new GamePiece("rook", "black", 0, 7);
        for (int i = 0; i < size; i++) {
            board[1][i] = new GamePiece("pawn", "black", 1, i);
        }
        board[7][0] = new GamePiece("rook", "white", 7, 0);
        board[7][1] = new GamePiece("knight", "white", 7, 1);
        board[7][2] = new GamePiece("bishop", "white", 7, 2);
        board[7][3] = new GamePiece("queen", "white", 7, 3);
        board[7][4] = new GamePiece("king", "white", 7, 4);
        board[7][5] = new GamePiece("bishop", "white", 7, 5);
        board[7][6] = new GamePiece("knight", "white", 7, 6);
        board[7][7] = new GamePiece("rook", "white", 7, 7);
        for (int i = 0; i < size; i++) {
            board[6][i] = new GamePiece("pawn", "white", 6, i);
        }

        for (int i = 2; i < 6; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = new GamePiece("", "", i, j);
            }
        }

        turn = true;
    }

    private static void printBoard() {
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

    /**
     * Private method that refreshes the board after each move
     * @TODO work on this method
     */
    private static void guiUpdate() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!board[i][j].getPiece().equals("null")) {
                    buttons[i][j].setText(board[i][j].getPiece());
                } else {
                    buttons[i][j].setText("");
                }
            }
        }

        frame.revalidate();
        frame.repaint();
        System.out.println("\n\n");
        printBoard();
    }

    /**
     * Private method for handling player making moves
     * @TODO work on this method
     */
    private static void makeMove(int oldX, int oldY, int x, int y) {

        GamePiece tmp = board[x][y];
        board[x][y] = selected;
        board[oldX][oldY] = tmp;

        selected = null;
        reActivate();
        guiUpdate();

    }

    /**
     * Private method for selecting piece, show available moves
     * TODO do this method
     */
    private static void selectPiece(int x, int y) {

        if (selected == board[x][y]) {
            reActivate();
            return;
        } else if (board[x][y].getPiece().equals("null")) {
            return;
        } else if ((board[x][y].getColor().getRGB() == white.getRGB() && !turn) || (board[x][y].getColor().getRGB() == black.getRGB() && turn)) {
            return;
        }
//        else if (selected == null) {
//            return;
//        }
        else {
            selected = board[x][y];
        }

        switch (selected.getPiece()) {
            case "pawn":
                if (turn) {
                    if (board[x][y].isHighlighted()) {
                        makeMove(selected.getX(), selected.getY(), x, y);
                    } else {
                        deActivate();
                        buttons[x][y].setEnabled(true);
                        buttons[x-2][y].setBackground(highlight);
                        buttons[x-2][y].setEnabled(true);
                        buttons[x-1][y].setBackground(highlight);
                        buttons[x-1][y].setEnabled(true);
                    }
                } else {

                }
                break;
            case "rook":
                break;
            case "knight":
                break;
        }
        guiUpdate();
    }

    /**
     * TODO make method to prevent clicking of other buttons when a piece is selected
     * JBUTTON.setEnabled(false);
     */
    private static void deActivate() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                buttons[i][j].setEnabled(false);
            }
        }
    }

    private static void reActivate() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                buttons[i][j].setEnabled(true);

                if ((i + j) % 2 == 0) {
                    buttons[i][j].setBackground(new Color(85, 85, 85));
                } else {
                    buttons[i][j].setBackground(new Color(140, 140, 140));
                }

            }
        }
    }

}
