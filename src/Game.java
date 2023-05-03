import java.util.Scanner;

public class Game {
    private Board gameBoard;

    private String currentPlayer;

    private String player1;

    private String player2;

    /**
     * the game constructor
     */
    public Game() {
        Scanner in = new Scanner(System.in);
        System.out.print("Player 1 enter your name: ");
        player1 = in.next();
        System.out.print("Player 2 enter your name: ");
        player2 = in.next();
        gameBoard = new Board(player1, player2);
        currentPlayer = player1;
    }

    /**
     * runs a game of Stratego
     */
    public void gameLoop() {
        gameBoard = new Board(player1,player2);
        //using autofilled board for now
        gameBoard.autoFill(true);
        Scanner in = new Scanner(System.in);
        int curRow, curCol, newRow, newCol;
        while (true) {
            System.out.println("\n" + gameBoard);
            if (!gameBoard.canPlay(currentPlayer)) {
                System.out.println(currentPlayer + " lost because they don't have any pieces they can move");
                break;
            }
            //loops until gets a valid move
            while (true) {
                System.out.print(currentPlayer + " enter the location of the piece you would like to move: ");
                try (Scanner curLocation = new Scanner(in.nextLine())){
                    curRow = curLocation.nextInt();
                    curCol = curLocation.nextInt();
                } catch (Exception e) {
                    System.out.println("\nNot a valid number");
                    continue;
                }
                System.out.print(currentPlayer + " enter the location that you would like to move the piece to: ");
                try (Scanner newLocation = new Scanner(in.nextLine())) {
                    newRow = newLocation.nextInt();
                    newCol = newLocation.nextInt();
                } catch (Exception e) {
                    System.out.println("\nNot a valid number");
                    continue;
                }
                if (currentPlayer.equals(player1)) {
                    try {
                        gameBoard.move(currentPlayer, player2, curRow, curCol, newRow, newCol);
                        break;
                    } catch (IllegalArgumentException e) {
                        System.out.println("\n" + e.getMessage());
                    }
                } else {
                    try {
                        gameBoard.move(currentPlayer, player1, curRow, curCol, newRow, newCol);
                        break;
                    } catch (IllegalArgumentException e) {
                        System.out.println("\n" + e.getMessage());
                    }
                }
            }
            //checks if the player has won
            if (currentPlayer.equals(player1) && gameBoard.hasWon(player2)) {
                System.out.println("\n" + gameBoard);
                System.out.println(currentPlayer + " has won the game!");
                break;
            } else if (gameBoard.hasWon(player1)) {
                System.out.println("\n" + gameBoard);
                System.out.println(currentPlayer + " has won the game!");
                break;
            }
            switchPlayer();
        }
        in.close();
    }

    /**
     * switch the current player
     */
    private void switchPlayer() {
        if (currentPlayer.equals(player1)) {
            currentPlayer = player2;
        } else {
            currentPlayer = player1;
        }
    }
}
