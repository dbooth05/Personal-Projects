package v2;

/**
  @author Dylan Booth
 **/

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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

    private int bombs;
    private int flagsRemaining;

    boolean cont;

    Color c;

    Timer timer;
    int move;
    int time;

    public GameBoardGUI () {

        cont = true;

        c = new Color(78, 78, 78);

        frame = new JFrame();

        frame.setSize(1000, 1000);
        frame.setLayout(new BorderLayout());

        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);


        playAgain();

    }

    private void genGame() {

        frame.getContentPane().removeAll();

        cont = true;
        timer = new Timer(1000, incrementTime);

        board = new GameTile[size][size];
        buttons = new JButton[size][size];

        buttonPanel = new JPanel();
        title = new JPanel();
        grid = new Container();

        reset = new JButton();
        reset.setText("Reset");
        giveUp = new JButton();
        giveUp.setText("Give Up");

        frame.setSize(1000, 1000);
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

        fillBoard();

        //adding buttons to panel
        buttonPanel.add(reset);
        buttonPanel.add(giveUp);

        JTextField t = new JTextField("Mine Sweeper \t Difficulty: " + diff);
        t.setEditable(false);
        t.setSize(500, 50);
        title.add(t);
        JTextField num = new JTextField("Flags Remaining: " + flagsRemaining);
        num.setEditable(false);
        title.add(num, BorderLayout.WEST);

        JTextField timeArea = new JTextField("Time: " + time);
        timeArea.setPreferredSize(new Dimension(60, 20));
        timeArea.setEditable(false);
        title.add(timeArea, BorderLayout.WEST);

        JTextField moves = new JTextField("Moves: " + move);
        moves.setPreferredSize(new Dimension(60, 20));
        moves.setEditable(false);
        title.add(moves, BorderLayout.WEST);

        //functionally calling game to start?
        frame.add(grid, BorderLayout.CENTER);

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
        bombs = 0;
        move = 0;
        time = 0;

        for (int i = 0; i < size; i++) {

            for (int j = 0; j < size; j++) {

                int k = rand.nextInt(100) + 1;
                if (k <= difficulty) {
                    temp = new GameTile(true, false, false);
                    bombs++;
                } else {
                    temp = new GameTile(false, false, false);
                }
                board[i][j] = temp;

            }

        }

        flagsRemaining = bombs;

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


    private ActionListener incrementTime = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            time++;
            JTextField tmp = (JTextField) title.getComponent(2);
            tmp.setText("Time: " + time);
        }
    };

    /**
     * Makes the desired move on tile,
     * @param click which move to be made, flag or reveal
     * @param x the x coordinate of tile having move made on
     * @param y the y coordinate of tile having move made on
     */
    public void makeMove(String click, int x, int y) {

        System.out.println("Made move: " + click + " at " + x + ", " + y);

        if (move == 0) {
            timer.start();
        }

        if (click.equals("left") && board[x][y].getIsRevealed()) {
            return;
        }

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
            if (flagsRemaining == 0) {
                return;
            } else if (board[x][y].setFlag()) {
                flagsRemaining--;
            } else {
                flagsRemaining++;
            }
        }
        move ++;
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

        JTextField tmp = (JTextField) title.getComponent(1);
        tmp.setText("Flags Remaining: " + flagsRemaining);

        tmp = (JTextField) title.getComponent(3);
        tmp.setText("Moves: " + move);

        System.out.println("made it here");

        for (int i = 0; i < size; i++) {

            for (int j = 0; j < size; j++) {

                if (board[i][j].getIsRevealed() && !board[i][j].getIsBomb()) {
                    int num = board[i][j].getAround();
                    buttons[i][j].setText(String.valueOf(num));
                    buttons[i][j].setBackground(new Color(78,78,78));
                    buttons[i][j].setFont(new Font(buttons[i][j].getFont().getFontName(), buttons[i][j].getFont().getStyle(), 20));

                    if (num == 0) {
                        buttons[i][j].setText("");
                    }

                    if (num == 1) {
                        buttons[i][j].setForeground(Color.blue);
                    } else if (num == 2) {
                        buttons[i][j].setForeground(Color.green);
                    } else if (num == 3) {
                        buttons[i][j].setForeground(Color.magenta);
                    } else if (num == 4) {
                        buttons[i][j].setForeground(Color.red);
                    } else {
                        buttons[i][j].setForeground(Color.orange);
                    }

                } else if (board[i][j].getIsRevealed() && board[i][j].getIsBomb()) {
                    buttons[i][j].setText("B");
                } else if (board[i][j].getIsFlagged()) {
                    try {
                        BufferedImage img = ImageIO.read(new File("./res/flag.png"));
                        Image image = new ImageIcon(img).getImage().getScaledInstance(buttons[i][j].getWidth(), buttons[i][j].getHeight(), Image.SCALE_SMOOTH);
                        Icon icon = new ImageIcon(image);
                        buttons[i][j].setIcon((Icon) icon);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
//                    buttons[i][j].setText("Flagged");
                } else if (!board[i][j].getIsRevealed() && !board[i][j].getIsFlagged()) {
                    buttons[i][j].setText("");
                    buttons[i][j].setBackground(Color.gray);
                    buttons[i][j].setIcon(null);
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
     * Resets the game board to display starting screen
     */
    private void resetBoard() {

        for (int i = 0; i < size; i++) {

            for (int j = 0; j < size; j++) {

                board[i][j].reset();

            }

        }
        guiUpdate();
        System.out.println("reset");
    }

    /**
     * Called when game is won or lost. Asks whether they want to play again, if yes
     * calls playAgain() to determine size and difficulty
     * @param win holds the boolean value if player won or lost game
     */
    private void endGame(boolean win) {

        frame.getContentPane().removeAll();

        timer.stop();

        JPanel j = new JPanel();
        j.setBackground(c);

        JTextArea a = new JTextArea();
        a.setFont(new Font(a.getFont().getFontName(), a.getFont().getStyle(), 80));
        a.setEditable(false);
        a.setForeground(Color.white);
        a.setBackground(c);

        JLabel gif;
        Image img;

        if (win) {
            a.setText("YOU WON");
            System.out.println("win");
            img = new ImageIcon("./res/military-dancing.gif").getImage();

        } else {
            a.setText("YOU LOSE");
            System.out.println("lose");
            img = new ImageIcon("./res/explosion-explode.gif").getImage();
        }

        Icon icon = new ImageIcon(img);
        gif = new JLabel(icon);

        j.add(a);

        frame.add(j, BorderLayout.NORTH);

        JButton againY = new JButton("Yes");
        JButton againN = new JButton("No");
        JButton repeat = new JButton("Same Difficulty");

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
        repeat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                genGame();
            }
        });

        JPanel again = new JPanel();
        again.setBackground(c);

        JTextArea b = new JTextArea("Play Again?");
        b.setFont(new Font(b.getFont().getFontName(), b.getFont().getStyle(), 30));
        b.setBackground(c);
        b.setForeground(Color.white);

        again.add(b);
        again.add(againY, BorderLayout.WEST);
        again.add(againN, BorderLayout.EAST);
        again.add(repeat, BorderLayout.EAST);

        frame.add(again, BorderLayout.CENTER);

        frame.add(gif, BorderLayout.SOUTH);

        frame.revalidate();
        frame.repaint();


    }

    /**
     * Shows play again options
     */
    private void playAgain() {

        frame.getContentPane().removeAll();
        frame.getContentPane().setBackground(c);

        JPanel differ = new JPanel();
        differ.setBackground(c);

        JPanel tl = new JPanel();
        tl.setBackground(c);
        tl.setSize(200, 600);

        JTextArea name = new JTextArea("Minesweeper");
        name.setFont(new Font(name.getFont().getFontName(), name.getFont().getStyle(), 40));
        name.setBackground(c);
        name.setForeground(Color.white);

        JTextArea author = new JTextArea("Dylan Booth");
        author.setFont(new Font(author.getFont().getFontName(), author.getFont().getStyle(), 20));
        author.setBackground(c);
        author.setForeground(Color.white);

        tl.add(name, BorderLayout.NORTH);
        tl.add(author, BorderLayout.SOUTH);
        frame.add(tl, BorderLayout.NORTH);

        JTextArea a = new JTextArea("Select Difficulty");
        a.setBackground(c);
        a.setForeground(Color.white);
        JButton easy = new JButton("Easy");
        JButton med = new JButton("Medium");
        JButton hard = new JButton("Hard");
        JButton extr = new JButton("Extreme");
        JButton cust = new JButton("Custom Difficulty");

        a.setEditable(false);

        easy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                new GameBoardGUI(5, 10, "Easy");
                size = 5;
                difficulty = 20;
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
                difficulty = 20;
                diff = "Hard";
                genGame();
            }
        });
        extr.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                new GameBoardGUI(25, 40, "Extreme");
                size = 25;
                difficulty = 35;
                diff = "Extreme";
                genGame();
            }
        });
        cust.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                customDiff();
            }
        });

        differ.add(a);
        differ.add(easy);
        differ.add(med);
        differ.add(hard);
        differ.add(extr);
        differ.add(cust);

        frame.add(differ, BorderLayout.CENTER);

        frame.revalidate();
        frame.repaint();

    }

    /**
     * Method to generate jframe for a custom game difficulty
     * Customize size of board and the chance of a bomb on a tile.
     */
    private void customDiff() {

        frame.getContentPane().removeAll();

        JPanel custDiff = new JPanel();
        frame.setBackground(c);
        custDiff.setBackground(c);

        // Selects size of board
        JPanel siz = new JPanel();
        JTextArea a = new JTextArea("Please select board size");
        a.setFont(new Font(a.getFont().getFontName(), a.getFont().getStyle(), 30));
        siz.add(a);

        JSlider sizeSlide = new JSlider(5, 50);
        JTextArea sizeNum = new JTextArea(String.valueOf(sizeSlide.getValue()));
        sizeNum.setFont(new Font(sizeNum.getFont().getFontName(), sizeNum.getFont().getStyle(), 30));
        sizeSlide.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                sizeNum.setText(String.valueOf(sizeSlide.getValue()));
            }
        });

        siz.add(sizeSlide);
        siz.add(sizeNum);


        // Selects chance of bomb
        JPanel dif = new JPanel();
        JTextArea d = new JTextArea("Please select chance of tile being bomb");
        d.setFont(new Font(d.getFont().getFontName(), d.getFont().getStyle(), 30));
        dif.add(d);

        JSlider diffSlide = new JSlider(5, 80);
        JTextArea diffNum = new JTextArea(String.valueOf(diffSlide.getValue()));
        diffNum.setFont(new Font(diffNum.getFont().getFontName(), diffNum.getFont().getStyle(), 30));
        diffSlide.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                diffNum.setText(String.valueOf(diffSlide.getValue()));
            }
        });
        dif.add(diffSlide);
        dif.add(diffNum);


        custDiff.add(siz, BorderLayout.NORTH);
        custDiff.add(dif, BorderLayout.CENTER);

        JButton play = new JButton("Play");
        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                size = sizeSlide.getValue();
                difficulty = diffSlide.getValue();
                diff = "Custom";
                genGame();
            }
        });

        custDiff.add(play);

        frame.add(custDiff);

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
