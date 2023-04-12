package v1;

/**
 @author Dylan Booth
 **/

import java.util.Scanner;


public class PlayGame {

    static GameBoard game;

    public static void main(String[] args) {


        Scanner sc = new Scanner(System.in);
        int difficulty;
//        String playAgain = "y"; //UNCOMMENT TO MAKE LOOP WORK
        String playAgain = "n"; //Comment for no gui

        while (playAgain.equals("y")) {

            difficulty = 0;

            while (difficulty > 3 || difficulty < 1) {

                System.out.println("Please select the difficulty");
                System.out.println("[1] easy    [2] medium    [3]    hard");

                System.out.print("Enter here: ");

                difficulty = sc.nextInt();

            }

            if (difficulty == 1) {
                game = new GameBoard(5, 20);
//				game = new GameBoard(10, 20);
            } else if (difficulty == 2) {
                game = new GameBoard(15, 30);
            } else if (difficulty == 3) {
                game = new GameBoard(100, 40);
            }

            System.out.println();
            System.out.println("To enter tile, type coordinates like '3,1' or 'x,y'");
            System.out.println("To place flag on tile enter 'f' or 'r' to reveal");
            System.out.println();

            game.printEnd();

            boolean contGame = true;
            while (contGame) {
                System.out.println("Enter coords: ");
                String coords = sc.next();
                System.out.println();
                System.out.print("Place flag (f) or reveal (r): ");
                String move = sc.next();

                contGame = game.makeMove(coords, move);
                if (!contGame) {
                    game.printEnd();
                }

                if (contGame) {
                    game.printBoard();

                    System.out.println();
                }

            }

            System.out.println();
            System.out.print("Would you like to play again [y/n]: ");
            playAgain = sc.next();
            System.out.println();

        }

        sc.close();



        GameBoardGUI gui = new GameBoardGUI();


        System.out.println("game over");

    }

}
