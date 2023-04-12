package v1;

/**
  @author Dylan Booth
 **/

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

public class GameBoardGUI extends JFrame {

    private GameTile[][] board;
    private int difficulty;
    private int size;

    //JFrame and GUI stuff
    private JFrame frame;
    private JButton reset;
    private JButton giveUp;
    private JPanel buttonPanel;

    private JPanel title;
    private Container grid;
    private JButton[][] buttons;

    private String diff;

    boolean cont;

    public GameBoardGUI () {

        cont = true;

        frame = new JFrame();

        frame.setSize(900, 900);
        frame.setLayout(new BorderLayout());

        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);


        playAgain();

//        buttonPanel = new JPanel();
//        title = new JPanel();
//        grid = new Container();
//
//        reset = new JButton();
//        reset.setText("Reset");
//        giveUp = new JButton();
//        giveUp.setText("Give Up");
//
//        frame.setSize(900, 900);
//        frame.setLayout(new BorderLayout());
//        frame.add(title, BorderLayout.NORTH);
//        frame.add(buttonPanel, BorderLayout.SOUTH);
//        reset.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                resetBoard();
//            }
//        });
//        giveUp.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                endGame(false);
//            }
//        });
//
//        grid.setLayout(new GridLayout(size, size));
//
//        //initialize each button and adds to grid to allow button clicks
//        for (int i = 0; i < size; i++) {
//            for (int j = 0; j < size; j++) {
//                buttons[i][j] = new JButton();
//                buttons[i][j].setBorder(new LineBorder(Color.BLACK));
//                buttons[i][j].setBackground(Color.GRAY);
//                buttons[i][j].addMouseListener(new MouseClickListener(i, j));
//                grid.add(buttons[i][j]);
//            }
//        }
//
//        //adding buttons to panel
//        buttonPanel.add(reset);
//        buttonPanel.add(giveUp);
//
//        JTextField t = new JTextField("Mine Sweeper \t Difficulty: " + diff);
//        t.setEditable(false);
//        t.setSize(200, 50);
//        title.add(t);
//
//        //functionaly calling game to start?
//        frame.add(grid, BorderLayout.CENTER);
//        fillBoard();
//
//        //frame stuff
//        frame.setLocationRelativeTo(null);
//        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
//        frame.setVisible(true);

    }

    private void genGame() {

        frame.getContentPane().removeAll();

        cont = true;

        board = new GameTile[size][size];
        buttons = new JButton[size][size];

        buttonPanel = new JPanel();
        title = new JPanel();
        grid = new Container();

        reset = new JButton();
        reset.setText("Reset");
        giveUp = new JButton();
        giveUp.setText("Give Up");

        frame.setSize(900, 900);
        frame.setLayout(new BorderLayout());
        frame.add(title, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetBoard();
            }
        });
        giveUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                endGame(false);
            }
        });

        grid.setLayout(new GridLayout(size, size));

        //initialize each button and adds to grid to allow button clicks
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setBorder(new LineBorder(Color.BLACK));
                buttons[i][j].setBackground(Color.GRAY);
                buttons[i][j].addMouseListener(new MouseClickListener(i, j));
                grid.add(buttons[i][j]);
            }
        }

        //adding buttons to panel
        buttonPanel.add(reset);
        buttonPanel.add(giveUp);

        JTextField t = new JTextField("Mine Sweeper \t Difficulty: " + diff);
        t.setEditable(false);
        t.setSize(200, 50);
        title.add(t);

        //functionally calling game to start?
        frame.add(grid, BorderLayout.CENTER);
        fillBoard();

        frame.validate();
        frame.repaint();


    }

    class MouseClickListener implements MouseListener {
        private int x;
        private int y;
        public MouseClickListener (int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (SwingUtilities.isLeftMouseButton(e)) {
                System.out.println("left click");
                makeMove("left", x, y);
            } else if (SwingUtilities.isRightMouseButton(e)) {
                System.out.println("right click");
                makeMove("right", x, y);
            }

            guiUpdate();
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

        printEnd();

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
     * Makes the desired move on tile,
     * @param click which move to be made, flag or reveal
     * @param x the x coordinate of tile having move made on
     * @param y the y coordinate of tile having move made on
     */
    public void makeMove(String click, int x, int y) {

        System.out.println("Made move: " + click + " at " + x + ", " + y);

        if (click.equals("left")) {

            if (board[x][y].getIsFlagged()) {
                return;
            }

            if (board[x][y].getIsBomb()) {
                cont = board[x][y].click();
                endGame(false);
                System.out.println("lost");
            } else {
                revealZerosRec(x, y);
            }

        } else if (click.equals("right")) {
            board[x][y].setFlag();
        }

        guiUpdate();

    }

    /**
     * updates gui elements
     */
    public void guiUpdate() {

        if (!cont) {
            endGame(false);
        }

        if (checkWin()) {
            endGame(true);
        }

        System.out.println("made it here");

        for (int i = 0; i < size; i++) {

            for (int j = 0; j < size; j++) {

                if (board[i][j].getIsRevealed() && !board[i][j].getIsBomb()) {
                    int num = board[i][j].getAround();
                    buttons[i][j].setText(String.valueOf(num));
                    buttons[i][j].setBackground(Color.white);
                } else if (board[i][j].getIsRevealed() && board[i][j].getIsBomb()) {
                    buttons[i][j].setText("B");
                } else if (board[i][j].getIsFlagged()) {
                    buttons[i][j].setText("Flagged");
                } else {
                    buttons[i][j].setText("");
                }

            }
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
                    break;
                }

            }

        }

        return win;
    }

    /**
     * @TODO Do this method, maybe
     */
    private void resetBoard() {
        System.out.println("reset");
    }

    /**
     * @TODO Do this method, ends game asks whether to play another game
     */
    private void endGame(boolean win) {

        frame.getContentPane().removeAll();

        JPanel j = new JPanel();

        JTextArea a = new JTextArea();
        a.setFont(new Font(a.getFont().getFontName(), a.getFont().getStyle(), 80));


        if (win) {
            a.setText("YOU WON");
            System.out.println("win");
        } else {
            a.setText("YOU LOSE");
            System.out.println("lose");
        }

        j.add(a);

        frame.add(j, BorderLayout.NORTH);

        JButton againY = new JButton("Yes");
        JButton againN = new JButton("No");

        againY.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playAgain();
            }
        });
        againN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JPanel again = new JPanel();
        JTextArea b = new JTextArea("Play Again?");
        b.setFont(new Font(b.getFont().getFontName(), b.getFont().getStyle(), 30));

        again.add(b);
        again.add(againY, BorderLayout.WEST);
        again.add(againN, BorderLayout.EAST);

        frame.add(again, BorderLayout.CENTER);

        frame.revalidate();
        frame.repaint();

    }

    /**
     * Shows play again options
     * @TODO make able to customise the difficulty and size separately
     */
    private void playAgain() {

        frame.getContentPane().removeAll();

        JPanel differ = new JPanel();
        JTextArea a = new JTextArea("Select Difficulty");
        JButton easy = new JButton("Easy");
        JButton med = new JButton("Medium");
        JButton hard = new JButton("Hard");
        JButton extr = new JButton("Extreme");

        easy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                new GameBoardGUI(5, 10, "Easy");
                size = 5;
                difficulty = 10;
                diff = "Easy";
                genGame();
            }
        });
        med.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                new GameBoardGUI(10, 20, "Medium");
                size = 10;
                difficulty = 20;
                diff = "Medium";
                genGame();
            }
        });
        hard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                new GameBoardGUI(15, 25, "Hard");
                size = 15;
                difficulty = 25;
                diff = "Hard";
                genGame();
            }
        });
        extr.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                new GameBoardGUI(25, 40, "Extreme");
                size = 25;
                difficulty = 40;
                diff = "Extreme";
                genGame();
            }
        });

        differ.add(a);
        differ.add(easy);
        differ.add(med);
        differ.add(hard);
        differ.add(extr);

        frame.add(differ);

        frame.revalidate();
        frame.repaint();

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

}
