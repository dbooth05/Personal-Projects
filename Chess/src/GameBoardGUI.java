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

    private static int oX;
    private static int oY;

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

    /**
     * Private class that implements MouseListener to listen for button
     * input. Also, keeps the x,y coordinates of the button to used in
     * comparison with the board (GamePiece[][] object)
     */
    private static class MouseClickListener implements MouseListener {

        private final int x;
        private final int y;
        public MouseClickListener (int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void mouseClicked(MouseEvent e) {

            JButton b = (JButton) e.getSource();
            if (b.isEnabled()) {
                if (selected == null) {
                    selectPiece(x, y);
                } else {
                    makeMove(x, y);
                }
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

    /**
     * private method that fills the board with game pieces in
     * correct positions.
     */
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

        for (int i = 2; i < 6; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = new GamePiece("", "");
            }
        }

        turn = true;
    }

    /**
     * Private method to display game board in terminal,
     * used to see game progression for debugging and finding
     * issues related to gameplay
     */
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
     */
    private static void guiUpdate() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!board[i][j].getPiece().equals("null")) {
                    buttons[i][j].setText(board[i][j].getPiece());
                    if(board[i][j].getColor().getRGB() == white.getRGB()) {
                        buttons[i][j].setForeground(white);
                    } else {
                        buttons[i][j].setForeground(black);
                    }
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
     * @param x the x coordinate of where the selected piece is moving to
     * @param y the y coordinate of where the selected piece is moving to
     * TODO work on this method
     */
    private static void makeMove(int x, int y) {

        GamePiece tmp = board[x][y];

        if (tmp.getPiece().equals("null")) {
            board[x][y] = board[oX][oY];
            board[oX][oY] = tmp;
            selected = null;

            if (board[x][y].isStartingPos()) {
                board[x][y].setNotStartPos();
            }

            reActivate();
            turn = !turn;

        } else if (tmp.equals(selected)) {
            reActivate();
            selected = null;
        } else if (!tmp.getPiece().equals("null")) {
            board[x][y] = board[oX][oY];
            board[oX][oY] = new GamePiece("", "");

            selected = null;
            reActivate();
            turn = !turn;
        }

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
        } else if (selected != null && selected.equals(board[x][y])) {
            reActivate();
            return;
        } else {
            selected = board[x][y];
            oX = x;
            oY = y;
        }

        deActivate();
        switch (selected.getPiece()) {
            case "pawn":
                if (turn) {
                    checkAttackWhite(x, y);
                    if (board[x][y].isStartingPos()) {
                        buttons[x][y].setEnabled(true);
                        buttons[x-2][y].setBackground(highlight);
                        buttons[x-2][y].setEnabled(true);
                        buttons[x-1][y].setBackground(highlight);
                        buttons[x-1][y].setEnabled(true);
                    } else {
                        buttons[x][y].setEnabled(true);
                        if (board[x-1][y].getPiece().equals("null")) {
                            buttons[x-1][y].setBackground(highlight);
                            buttons[x-1][y].setEnabled(true);
                        }
                    }
                } else {
                    checkAttackBlack(x, y);
                    if (board[x][y].isStartingPos()) {
                        buttons[x][y].setEnabled(true);
                        buttons[x+2][y].setBackground(highlight);
                        buttons[x+2][y].setEnabled(true);
                        buttons[x+1][y].setBackground(highlight);
                        buttons[x+1][y].setEnabled(true);
                    } else {
                        buttons[x][y].setEnabled(true);
                        if (board[x+1][y].getPiece().equals("null")) {
                            buttons[x+1][y].setBackground(highlight);
                            buttons[x+1][y].setEnabled(true);
                        }
                    }
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

    /**
     * private method to check and highlight any space where a white pawn may attack if not part
     * of general move direction
     */
    private static void checkAttackWhite(int x, int y) {

        if (y - 1 < 0) {
            if (!board[x-1][y+1].getPiece().equals("null")) {
                buttons[x-1][y+1].setEnabled(true);
                buttons[x-1][y+1].setBackground(highlight);
            }
        } else if (y + 1 >= size) {
            if (!board[x-1][y-1].getPiece().equals("null")) {
                buttons[x-1][y-1].setEnabled(true);
                buttons[x-1][y-1].setBackground(highlight);
            }
        } else {
            if (!board[x - 1][y + 1].getPiece().equals("null")) {
                buttons[x - 1][y + 1].setEnabled(true);
                buttons[x - 1][y + 1].setBackground(highlight);
            }
            if (!board[x - 1][y - 1].getPiece().equals("null")) {
                buttons[x - 1][y - 1].setEnabled(true);
                buttons[x - 1][y - 1].setBackground(highlight);
            }
        }

    }

    /**
     * private method to check and highlight any space where a black pawn may attack if not part
     * of general move direction
     */
    private static void checkAttackBlack(int x, int y) {

        if (y - 1 < 0) {
            if (!board[x + 1][y + 1].getPiece().equals("null")) {
                buttons[x + 1][y + 1].setEnabled(true);
                buttons[x + 1][y + 1].setBackground(highlight);
            }
        } else if (y + 1 >= size) {
            if (!board[x + 1][y - 1].getPiece().equals("null")) {
                buttons[x + 1][y - 1].setEnabled(true);
                buttons[x + 1][y - 1].setBackground(highlight);
            }
        } else {
            if (!board[x + 1][y + 1].getPiece().equals("null")) {
                buttons[x + 1][y + 1].setEnabled(true);
                buttons[x + 1][y + 1].setBackground(highlight);
            }
            if (!board[x + 1][y - 1].getPiece().equals("null")) {
                buttons[x + 1][y - 1].setEnabled(true);
                buttons[x + 1][y - 1].setBackground(highlight);
            }
        }
    }
}
