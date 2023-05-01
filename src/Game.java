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
        while (true) {
            if (!gameBoard.canPlay(currentPlayer)) {
                break;
            } else {

            }
        }
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
