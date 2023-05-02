import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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

        //initialize buttons
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                //TODO change this to show game piece with icon
                if (board[i][j].getPiece().equals("null")) {
                    buttons[i][j] = new JButton();
                } else {
                    try {
                        buttons[i][j] = new JButton();
                        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_BYTE_GRAY);
                        switch (board[i][j].getPiece()) {
                            case "king":
                                if (board[i][j].getColorString().equals("white")) {
                                    img = ImageIO.read(new File("white-king.jpg"));
                                } else {
                                    img = ImageIO.read(new File("black-king.png"));
                                }
                                break;
                            case "queen":
                                if (board[i][j].getColorString().equals("white")) {
                                    img = ImageIO.read(new File("white-queen.jpg"));
                                } else {
                                    img = ImageIO.read(new File("black-queen.png"));
                                }
                                break;
                            case "rook":
                                if (board[i][j].getColorString().equals("white")) {
                                    img = ImageIO.read(new File("white-rook.jpg"));
                                } else {
                                    img = ImageIO.read(new File("black-rook.jpg"));
                                }
                                break;
                            case "knight":
                                if (board[i][j].getColorString().equals("white")) {
                                    img = ImageIO.read(new File("white-knight.png"));
                                } else {
                                    img = ImageIO.read(new File("black-knight.jpg"));
                                }
                                break;
                            case "bishop":
                                if (board[i][j].getColorString().equals("white")) {
                                    img = ImageIO.read(new File("white-bishop.jpg"));
                                } else {
                                    img = ImageIO.read(new File("black-bishop.jpg"));
                                }
                                break;
                            case "pawn":
                                if (board[i][j].getColorString().equals("white")) {
                                    img = ImageIO.read(new File("white-pawn.jpg"));
                                } else {
                                    img = ImageIO.read(new File("black-pawn.jpg"));
                                }
                                break;
                        }

                        Image image = new ImageIcon(img).getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
                        Icon icon = new ImageIcon(image);
                        buttons[i][j].setIcon(icon);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                Color tmp = board[i][j].getColor();
//                if (tmp != null) {
//                    buttons[i][j].setForeground(tmp);
//                }
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
     * @param x the X coordinate of the piece to be selected and possible moves highlighted
     * @param y the Y coordinate of the piece to be selected and possible moves highlighted
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
                    checkPawnWhite(x, y);
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
                    checkPawnBlack(x, y);
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
                checkRookVert(x, y);
                checkRookHori(x, y);
                buttons[x][y].setEnabled(true);
                break;
            case "knight":
                checkKnight(x, y);
                buttons[x][y].setEnabled(true);
                break;
            case "bishop":
                checkBishop(x, y);
                buttons[x][y].setEnabled(true);
                break;
            case "queen":
                checkRookVert(x, y);
                checkRookHori(x, y);
                checkBishop(x, y);
                buttons[x][y].setEnabled(true);
                break;
            case "king":
                checkKing(x, y);
                buttons[x][y].setEnabled(true);
                break;
        }
        guiUpdate();
    }

    /**
     * Private method to deactivate all JButtons on the grid.
     * Used for when a piece is selected to disable board,
     * caller method will re-enable any button that needs to function
     */
    private static void deActivate() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                buttons[i][j].setEnabled(false);
            }
        }
    }

    /**
     * Private method that reactivates all JButtons on the grid.
     * Called after a piece is deselected or a move is made.
     */
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
    private static void checkPawnWhite(int x, int y) {

        if (y - 1 < 0) {
            if (!board[x-1][y+1].getPiece().equals("null") && board[x-1][y+1].getColorString().equals("black")) {
                buttons[x-1][y+1].setEnabled(true);
                buttons[x-1][y+1].setBackground(highlight);
            }
        } else if (y + 1 >= size) {
            if (!board[x-1][y-1].getPiece().equals("null") && board[x-1][y-1].getColorString().equals("black")) {
                buttons[x-1][y-1].setEnabled(true);
                buttons[x-1][y-1].setBackground(highlight);
            }
        } else {
            if (!board[x - 1][y + 1].getPiece().equals("null") && board[x-1][y+1].getColorString().equals("black")) {
                buttons[x - 1][y + 1].setEnabled(true);
                buttons[x - 1][y + 1].setBackground(highlight);
            }
            if (!board[x - 1][y - 1].getPiece().equals("null") && board[x-1][y-1].getColorString().equals("black")) {
                buttons[x - 1][y - 1].setEnabled(true);
                buttons[x - 1][y - 1].setBackground(highlight);
            }
        }

    }

    /**
     * private method to check and highlight any space where a black pawn may attack if not part
     * of general move direction
     */
    private static void checkPawnBlack(int x, int y) {

        if (y - 1 < 0) {
            if (!board[x + 1][y + 1].getPiece().equals("null") && board[x+1][y+1].getColorString().equals("white")) {
                buttons[x + 1][y + 1].setEnabled(true);
                buttons[x + 1][y + 1].setBackground(highlight);
            }
        } else if (y + 1 >= size) {
            if (!board[x + 1][y - 1].getPiece().equals("null") && board[x+1][y-1].getColorString().equals("white")) {
                buttons[x + 1][y - 1].setEnabled(true);
                buttons[x + 1][y - 1].setBackground(highlight);
            }
        } else {
            if (!board[x + 1][y + 1].getPiece().equals("null") && board[x+1][y+1].getColorString().equals("white")) {
                buttons[x + 1][y + 1].setEnabled(true);
                buttons[x + 1][y + 1].setBackground(highlight);
            }
            if (!board[x + 1][y - 1].getPiece().equals("null") && board[x+1][y-1].getColorString().equals("white")) {
                buttons[x + 1][y - 1].setEnabled(true);
                buttons[x + 1][y - 1].setBackground(highlight);
            }
        }
    }

    /**
     * Private method to check and highlight any space where a rook may move/attack vertically
     * @param x the X coordinate of the piece needing to be given possible moves
     * @param y the Y coordinate of the piece needing to be given possible moves
     */
    private static void checkRookVert(int x, int y) {
        if (board[x][y].getColor().getRGB() == white.getRGB()) {
            int X = x + 1;
            while (X < size) {
                if (!board[X][y].getPiece().equals("null")) {
                    if (board[X][y].getColorString().equals("black")) {
                        buttons[X][y].setEnabled(true);
                        buttons[X][y].setBackground(highlight);
                    }
                    break;
                }
                buttons[X][y].setEnabled(true);
                buttons[X][y].setBackground(highlight);
                X++;
            }
            X = x - 1;
            while (X >= 0) {
                if (!board[X][y].getPiece().equals("null")) {
                    if (board[X][y].getColorString().equals("black")) {
                        buttons[X][y].setEnabled(true);
                        buttons[X][y].setBackground(highlight);
                    }
                    break;
                }
                buttons[X][y].setEnabled(true);
                buttons[X][y].setBackground(highlight);
                X--;
            }
        } else {
            int X = x + 1;
            while (X < size) {
                if (!board[X][y].getPiece().equals("null")) {
                    if (board[X][y].getColorString().equals("white")) {
                        buttons[X][y].setEnabled(true);
                        buttons[X][y].setBackground(highlight);
                    }
                    break;
                }
                buttons[X][y].setEnabled(true);
                buttons[X][y].setBackground(highlight);
                X++;
            }
            X = x - 1;
            while (X >= 0) {
                if (!board[X][y].getPiece().equals("null")) {
                    if (board[X][y].getColorString().equals("white")) {
                        buttons[X][y].setEnabled(true);
                        buttons[X][y].setBackground(highlight);
                    }
                    break;
                }
                buttons[X][y].setEnabled(true);
                buttons[X][y].setBackground(highlight);
                X--;
            }
        }
    }
    /**
     * Private method to check and highlight any space where a rook may move/attack horizontally
     * @param x the X coordinate of the piece needing to be given possible moves
     * @param y the Y coordinate of the piece needing to be given possible moves
     */
    private static void checkRookHori(int x, int y) {
        if (board[x][y].getColor().getRGB() == white.getRGB()) {
            int Y = y - 1;
            while (Y >= 0) {
                if (!board[x][Y].getPiece().equals("null")) {
                    if (board[x][Y].getColorString().equals("black")) {
                        buttons[x][Y].setEnabled(true);
                        buttons[x][Y].setBackground(highlight);
                    }
                    break;
                }
                buttons[x][Y].setEnabled(true);
                buttons[x][Y].setBackground(highlight);
                Y--;
            }
            Y = y + 1;
            while (Y < size) {
                if (!board[x][Y].getPiece().equals("null")) {
                    if (board[x][Y].getColorString().equals("black")) {
                        buttons[x][Y].setEnabled(true);
                        buttons[x][Y].setBackground(highlight);
                    }
                    break;
                }
                buttons[x][Y].setEnabled(true);
                buttons[x][Y].setBackground(highlight);
                Y++;
            }
        } else {
            int Y = y - 1;
            while (Y >= 0) {
                if (!board[x][Y].getPiece().equals("null")) {
                    if (board[x][Y].getColorString().equals("white")) {
                        buttons[x][Y].setEnabled(true);
                        buttons[x][Y].setBackground(highlight);
                    }
                    break;
                }
                buttons[x][Y].setEnabled(true);
                buttons[x][Y].setBackground(highlight);
                Y--;
            }
            Y = y + 1;
            while (Y < size) {
                if (!board[x][Y].getPiece().equals("null")) {
                    if (board[x][Y].getColorString().equals("white")) {
                        buttons[x][Y].setEnabled(true);
                        buttons[x][Y].setBackground(highlight);
                    }
                    break;
                }
                buttons[x][Y].setEnabled(true);
                buttons[x][Y].setBackground(highlight);
                Y++;
            }
        }
    }

    /**
     * Private method to check and highligh any space where a knight may move/attack
     * @param x the X coordinate of position to be checked
     * @param y the Y coordinate of position to be checked
     */
    private static void checkKnight(int x, int y) {
        if (y - 2 >= 0) {
            if (x - 1 >= 0) {
                if (!board[x - 1][y - 2].getColorString().equals(selected.getColorString())) {
                    buttons[x - 1][y - 2].setEnabled(true);
                    buttons[x - 1][y - 2].setBackground(highlight);
                }
            } if (x + 1 < size) {
                if (!board[x + 1][y - 2].getColorString().equals(selected.getColorString())) {
                    buttons[x + 1][y - 2].setEnabled(true);
                    buttons[x + 1][y - 2].setBackground(highlight);
                }
            }
        } if (y + 2 < size) {
            if (x - 1 >= 0) {
                if (!board[x-1][y+2].getColorString().equals(selected.getColorString())) {
                    buttons[x - 1][y + 2].setEnabled(true);
                    buttons[x - 1][y + 2].setBackground(highlight);
                }
            } if (x + 1 >= 0) {
                if (!board[x + 1][y + 2].getColorString().equals(selected.getColorString())) {
                    buttons[x + 1][y + 2].setEnabled(true);
                    buttons[x + 1][y + 2].setBackground(highlight);
                }
            }
        } if (x - 2 >= 0) {
            if (y - 1 >= 0) {
                if (!board[x - 2][y - 1].getColorString().equals(selected.getColorString())) {
                    buttons[x - 2][y - 1].setEnabled(true);
                    buttons[x - 2][y - 1].setBackground(highlight);
                }
            } if (y + 1 < size) {
                if (!board[x - 2][y + 1].getColorString().equals(selected.getColorString())) {
                    buttons[x - 2][y + 1].setEnabled(true);
                    buttons[x - 2][y + 1].setBackground(highlight);
                }
            }
        } if (x + 2 < size) {
            if (y - 1 >= 0) {
                if (!board[x + 2][y - 1].getColorString().equals(selected.getColorString())) {
                    buttons[x + 2][y - 1].setEnabled(true);
                    buttons[x + 2][y - 1].setBackground(highlight);
                }
            } if (y + 1 < size) {
                if (!board[x+2][y+1].getColorString().equals(selected.getColorString())) {
                    buttons[x+2][y+1].setEnabled(true);
                    buttons[x+2][y+1].setBackground(highlight);
                }
            }
        }
    }

    /**
     * Private method to check and highlight any space where a bishop may move/attack
     * @param x the X coordinate of the position to check from
     * @param y the Y coordinate of the position to check from
     */
    private static void checkBishop(int x, int y) {
        int X = x - 1;
        int Y = y - 1;
        while (X >= 0 && Y >= 0) {
            if (!board[X][Y].getPiece().equals("null")) {
                if (!board[X][Y].getColorString().equals(selected.getColorString())) {
                    buttons[X][Y].setEnabled(true);
                    buttons[X][Y].setBackground(highlight);
                }
                break;
            }
            buttons[X][Y].setEnabled(true);
            buttons[X][Y].setBackground(highlight);
            X--;
            Y--;
        }
        X = x + 1;
        Y = y + 1;
        while (X < size && Y < size) {
            if (!board[X][Y].getPiece().equals("null")) {
                if (!board[X][Y].getColorString().equals(selected.getColorString())) {
                    buttons[X][Y].setEnabled(true);
                    buttons[X][Y].setBackground(highlight);
                }
                break;
            }
            buttons[X][Y].setEnabled(true);
            buttons[X][Y].setBackground(highlight);
            X++;
            Y++;
        }
        X = x - 1;
        Y = y + 1;
        while (X >= 0 && Y < size) {
            if (!board[X][Y].getPiece().equals("null")) {
                if (!board[X][Y].getColorString().equals(selected.getColorString())) {
                    buttons[X][Y].setEnabled(true);
                    buttons[X][Y].setBackground(highlight);
                }
                break;
            }
            buttons[X][Y].setEnabled(true);
            buttons[X][Y].setBackground(highlight);
            X--;
            Y++;
        }
        X = x + 1;
        Y = y - 1;
        while (X < size && Y >= 0) {
            if (!board[X][Y].getPiece().equals("null")) {
                if (!board[X][Y].getColorString().equals(selected.getColorString())) {
                    buttons[X][Y].setEnabled(true);
                    buttons[X][Y].setBackground(highlight);
                }
                break;
            }
            buttons[X][Y].setEnabled(true);
            buttons[X][Y].setBackground(highlight);
            X++;
            Y--;
        }
    }

    /**
     * Private method to check and highlight any space where king may move/attack
     * @param x the X coordinate of position to be checked
     * @param y the Y coordinate of position to be checked
     */
    private static void checkKing(int x, int y) {
        if (x-1 >= 0 && y-1 >= 0 && !board[x-1][y-1].getColorString().equals(selected.getColorString())) {          //top left
            buttons[x-1][y-1].setEnabled(true);
            buttons[x-1][y-1].setBackground(highlight);
        } if (x-1 >= 0 && y+1 < size && !board[x-1][y+1].getColorString().equals(selected.getColorString())) {      //top right
            buttons[x-1][y+1].setEnabled(true);
            buttons[x-1][y+1].setBackground(highlight);
        } if (x-1 >= 0 && !board[x-1][y].getColorString().equals(selected.getColorString())) {                      //above
            buttons[x-1][y].setEnabled(true);
            buttons[x-1][y].setBackground(highlight);
        } if (x+1 < size && y-1 >= 0 && !board[x+1][y-1].getColorString().equals(selected.getColorString())) {      //lower left
            buttons[x+1][y-1].setEnabled(true);
            buttons[x+1][y-1].setBackground(highlight);
        } if (x+1 < size && y+1 < size && !board[x+1][y+1].getColorString().equals(selected.getColorString())) {    //lower right
            buttons[x+1][y+1].setEnabled(true);
            buttons[x+1][y+1].setBackground(highlight);
        } if (x+1 < size && !board[x+1][y].getColorString().equals(selected.getColorString())) {                    //below
            buttons[x+1][y].setEnabled(true);
            buttons[x+1][y].setBackground(highlight);
        } if (y+1 < size && !board[x][y+1].getColorString().equals(selected.getColorString())) {                    //right
            buttons[x][y+1].setEnabled(true);
            buttons[x][y+1].setBackground(highlight);
        } if (y-1 >= 0 && !board[x][y-1].getColorString().equals(selected.getColorString())) {                      //left
            buttons[x][y-1].setEnabled(true);
            buttons[x][y-1].setBackground(highlight);
        }
    }
}
