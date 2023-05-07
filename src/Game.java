import java.util.Scanner;

public class Game {
    private Board gameBoard;

    private String currentPlayer;

    private final String player1;

    private final String player2;

    /**
     * the game constructor
     */
    public Game() {
        Scanner in = new Scanner(System.in);
        System.out.print("Player 1 enter your name: ");
        player1 = in.next();

        System.out.print("Player 2 enter your name: ");
        player2 = in.next();
        /*
        while (true) {
            System.out.print("Player 2 enter your name: ");
            String name = in.next();
            if (!name.equals(player1)) {
                player2 = name;
                break;
            }
            System.out.println("The name you entered is already taken.");
        }*/
        gameBoard = new Board(player1, player2);
        currentPlayer = player1;
    }

    /**
     * runs a game of Stratego
     */
    public void gameLoop() {
        gameBoard = new Board(player1,player2);

        //Sets up the board for the two players.
        Scanner scan = new Scanner(System.in);
        System.out.println(player1 + ", please choose how to enter your pieces.");
        while (true) {
            System.out.println("If you would like to create your own board, enter \"c\". If you would like a preset board, enter \"p\":");
            char firstChar = scan.nextLine().toUpperCase().charAt(0);
            if (firstChar == 'C') {
                gameBoard.setPlayerPieces(player1);
                break;
            }
            else if (firstChar == 'P') {
                gameBoard.presetBoard(player1);
                break;
            }
        }

        System.out.println(player2 + ", please choose how to enter your pieces.");
        while (true) {
            System.out.println("If you would like to create your own board, enter \"c\". If you would like a preset board, enter \"p\":");
            char firstChar = scan.next().toUpperCase().charAt(0);
            if (firstChar == 'C') {
                gameBoard.setPlayerPieces(player2);
                break;
            }
            else if (firstChar == 'P') {
                gameBoard.presetBoard(player2);
                break;
            }
        }
        scan.nextLine();


        int curRow, curCol, newRow, newCol;
        while (true) {
            if (!gameBoard.canPlay(currentPlayer)) {
                System.out.println(currentPlayer + " lost because they don't have any pieces they can move");
                break;
            }
            //checks if there is a draw
            if (!gameBoard.movablePiecesLeft()) {
                System.out.println("Game over – Draw");
                break;
            }

            //prints out 20 lines so the previous board cannot be seen
            for (int i = 0; i < 10; i++) {
                System.out.println("\n");
            }

            //prints out the board
            System.out.println("\n" + gameBoard.toString(currentPlayer));


            //loops until gets a valid move
            while (true) {
                System.out.print(currentPlayer + ", enter the location of the piece you would like to move with the row, a space, and then a column: ");
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
                    System.out.println("\nInvalid row\n");
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
                if (currentPlayer.equals(player1)) {
                    try {
                        gameBoard.move(currentPlayer, player2, curRow, curCol, newRow, newCol);
                        break;
                    } catch (IllegalArgumentException e) {
                        System.out.println("\n" + e.getMessage() + "\n");
                    }
                } else {
                    try {
                        gameBoard.move(currentPlayer, player1, curRow, curCol, newRow, newCol);
                        break;
                    } catch (IllegalArgumentException e) {
                        System.out.println("\n" + e.getMessage() + "\n");
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
        scan.close();
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
