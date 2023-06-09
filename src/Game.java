import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Game {
    private Board gameBoard;

    private String currentPlayer;

    private final String PLAYER1;

    private final String PLAYER2;

    /**
     * the game constructor
     */
    public Game() {
        String stratego = "   _____ _             _                   \n" +
                "  / ____| |           | |                  \n" +
                " | (___ | |_ _ __ __ _| |_ ___  __ _  ___  \n" +
                "  \\___ \\| __| '__/ _` | __/ _ \\/ _` |/ _ \\ \n" +
                "  ____) | |_| | | (_| | ||  __/ (_| | (_) |\n" +
                " |_____/ \\__|_|  \\__,_|\\__\\___|\\__, |\\___/ \n" +
                "                                __/ |      \n" +
                "                               |___/       \n";
        System.out.println(stratego);

        System.out.println("Welcome to Stratego!");
        System.out.println("On the board, Flags are represented by \"F\", Bombs are represented by \"B\", Marshals (10s) are represented by \"0\", and the rest of the pieces are represented by their numerical value.");


        Scanner in = new Scanner(System.in);
        System.out.print("Player 1 enter your name: ");
        PLAYER1 = in.next();

        in.nextLine();

        //makes sure the name that player2 enters is different from player1's
        while (true) {
            System.out.print("Player 2 enter your name: ");
            String name = in.next();
            if (!name.equals(PLAYER1)) {
                PLAYER2 = name;
                break;
            }
            System.out.println("The name you entered is already taken.");
        }
        gameBoard = new Board(PLAYER1, PLAYER2);
        currentPlayer = PLAYER1;
    }

    /**
     * runs a game of Stratego
     */
    public void gameLoop() throws InterruptedException {
        gameBoard = new Board(PLAYER1, PLAYER2);
        String printOutput = "";
        boolean firstTurn = true;

        //Sets up the board for the first player.
        Scanner scan = new Scanner(System.in);
        System.out.println(PLAYER1 + ", please choose how to enter your pieces.");
        while (true) {
            System.out.println("If you would like to create your own board, enter \"c\". If you would like a preset board, enter \"p\":");
            char firstChar = scan.nextLine().toUpperCase().charAt(0);
            if (firstChar == 'C') {
                gameBoard.setPlayerPieces(PLAYER1);
                break;
            }
            else if (firstChar == 'P') {
                gameBoard.presetBoard(PLAYER1);
                break;
            }
        }

        //waits about 5 seconds for the next player's turn and creates space so the previous board cannot be seen
        for (int i = 5; i > 0; i--) {
            System.out.println("\n\n" + PLAYER2 + "'s turn to move pieces in " + i + "!\n\n");
            TimeUnit.MILLISECONDS.sleep(750);
        }

        //Sets up the board for the second player
        System.out.println(PLAYER2 + ", please choose how to enter your pieces.");
        while (true) {
            System.out.println("If you would like to create your own board, enter \"c\". If you would like a preset board, enter \"p\":");
            char firstChar = scan.next().toUpperCase().charAt(0);
            if (firstChar == 'C') {
                gameBoard.setPlayerPieces(PLAYER2);
                break;
            }
            else if (firstChar == 'P') {
                gameBoard.presetBoard(PLAYER2);
                break;
            }
            System.out.println("Invalid entry.");
        }
        scan.nextLine();

        //waits about 5 seconds for the next player's turn and creates space so the previous board cannot be seen
        for (int i = 5; i > 0; i--) {
            System.out.println("\n\n" + PLAYER1 + "'s turn in " + i + "!\n\n");
            TimeUnit.MILLISECONDS.sleep(750);
        }

        int curRow, curCol, newRow, newCol;
        while (true) {
            //checks if there is a draw
            if (!gameBoard.movablePiecesLeft()) {
                System.out.println("Game over – Draw");
                break;
            }

            //Checks to make sure that the current player can actually move
            if (!gameBoard.canPlay(currentPlayer)) {
                System.out.println(gameBoard);
                System.out.println(currentPlayer + " lost because they don't have any pieces they can move");
                break;
            }

            //As long as it is not the first turn, this code runs between turns
            if (!firstTurn) {
                TimeUnit.SECONDS.sleep(2);

                //waits about 5 seconds for the next player's turn and creates space so the previous board cannot be seen
                for (int i = 5; i > 0; i--) {
                    System.out.println("\n\n" + currentPlayer + "'s turn in " + i + "!\n\n");
                    TimeUnit.MILLISECONDS.sleep(750);
                }
            }

            //prints out the board
            System.out.println("\n" + gameBoard.toString(currentPlayer));

            //Prints out the result of the last play if there was a last play (if this is not the first turn)
            if (!firstTurn) {
                System.out.println(printOutput + "\n");
            }

            //loops until gets a valid move
            while (true) {
                System.out.print(currentPlayer + ", enter the location of the piece you would like to move with row, space, and then column: ");
                try (Scanner curLocation = new Scanner(scan.nextLine())){
                    curRow = curLocation.nextInt();
                    curCol = curLocation.nextInt();
                } catch (Exception e) {
                    System.out.println("\nNot a valid number\n");
                    continue;
                }

                //makes sure row and column are valid
                if ((curRow < 0) || (curRow > 9)) {
                    System.out.println("\nInvalid row\n");
                    continue;
                }
                if ((curCol < 0) || (curCol > 9)) {
                    System.out.println("\nInvalid column\n");
                    continue;
                }

                //checks if the player can move the piece they selected
                if (!gameBoard.canMovePiece(curRow,curCol,currentPlayer)) {
                    System.out.println("\nCannot move that piece\n");
                    continue;
                }

                System.out.print(currentPlayer + ", enter the location that you would like to move the piece to: ");
                try (Scanner newLocation = new Scanner(scan.nextLine())) {
                    newRow = newLocation.nextInt();
                    newCol = newLocation.nextInt();
                } catch (Exception e) {
                    System.out.println("\nNot a valid number\n");
                    continue;
                }

                //moves for first player
                if (currentPlayer.equals(PLAYER1)) {
                    try {
                        printOutput = gameBoard.move(currentPlayer, PLAYER2, curRow, curCol, newRow, newCol);
                        break;
                    } catch (IllegalArgumentException e) {
                        System.out.println("\n" + e.getMessage() + "\n");
                    }
                }
                //moves for first player
                else {
                    try {
                        printOutput = gameBoard.move(currentPlayer, PLAYER1, curRow, curCol, newRow, newCol);
                        break;
                    } catch (IllegalArgumentException e) {
                        System.out.println("\n" + e.getMessage() + "\n");
                    }
                }
            }
            System.out.println("\n" + printOutput);

            //checks if the player has won
            if (currentPlayer.equals(PLAYER1) && gameBoard.hasWon(PLAYER2)) {
                System.out.println("\n" + gameBoard);
                System.out.println(currentPlayer + " has won the game!");
                break;
            } else if (gameBoard.hasWon(PLAYER1)) {
                System.out.println("\n" + gameBoard);
                System.out.println(currentPlayer + " has won the game!");
                break;
            }

            switchPlayer();
            firstTurn = false;
        }
        scan.close();
    }

    /**
     * switch the current player
     */
    private void switchPlayer() {
        if (currentPlayer.equals(PLAYER1)) {
            currentPlayer = PLAYER2;
        } else {
            currentPlayer = PLAYER1;
        }
    }
}
