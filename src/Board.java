import java.util.*;

public class Board {
    private ArrayList<ArrayList<Piece>> board;

    private final String PLAYER1;
    private final String PLAYER2;

    /**
     * the board constructor
     */
    public Board(String player1, String player2) {
        this.PLAYER1 = player1;
        this.PLAYER2 = player2;

        //initializing the board
        board = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ArrayList<Piece> col = new ArrayList<>(10);
            for (int j = 0; j < 10; j++) {
                Piece empty = new Piece("Empty", -3);
                col.add(empty);
            }
            board.add(col);
        }

        //putting obstacles on the board
        for (int i = 2; i < 8; i++) {
            for (int j = 4; j < 6; j++) {
                if (!(i == 4 || i == 5)) {
                    Piece obstacle = new Piece("Obstacle", -2);
                    setPiece(obstacle, j, i);
                }
            }
        }
    }

    /**
     * This method can be called at the beginning of a game to find the
     * @param player the player whose pieces are being added
     */
    public void setPlayerPieces(String player) {
        Queue<Piece> startingPieces = createLinkedLists(player);
        Scanner scan = new Scanner(System.in);


        //minRow and maxRow are used later on to make sure that the player adds pieces in the part of the board where they are allowed to
        int minRow = -1;
        int maxRow = -1;
        if (PLAYER1.equals(player)) {
            minRow = 0;
            maxRow = 3;
        } else if (PLAYER2.equals(player)) {
            minRow = 6;
            maxRow = 9;
        }

        //keep looping while there are still Pieces in the linked-list que
        while (startingPieces.size() > 0) {
            //This scanner takes in a line from the console
            Scanner lineScan;

            //This line removes the Pieces from the que
            Piece p = startingPieces.remove();
            System.out.println(this);
            while (true) {
                System.out.println(player + ", your next piece is a " + p.getPieceName() + ". Where do you want to place it? Enter the row index (" + minRow + " - " + maxRow + ") and a space and then the column index (0-9).");
                lineScan = new Scanner(scan.nextLine());
                try {
                    //If they did not input integers, it will cause an error
                    int row = lineScan.nextInt();
                    int col = lineScan.nextInt();

                    //makes sure that the row entered is valid (for that player)
                    if ((row < minRow) || (row > maxRow))  {
                        throw new IndexOutOfBoundsException("Invalid row index");
                    }

                    //If the columns are invalid or if the space is already taken, this will cause an error
                    setPiece(p,row,col);

                    break;
                }
                catch (NoSuchElementException e) {
                    System.out.println("Invalid row or column.");
                }
                catch (IndexOutOfBoundsException | IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
            lineScan.close();
        }
        scan.close();
    }

    /**
     * This method is used in the constructor to create the linked list of the pieces that need to be placed on the board
     * @param player the name of the player that these pieces will belong to
     * @return this method returns a linked list of all the pieces that still need to be added to the board
     */
    private Queue<Piece> createLinkedLists(String player) {
        LinkedList<Piece> startingPieces = new LinkedList<>();
        startingPieces.add(new Piece(player,0));
        for (int i = 0; i < 6; i++) {
            startingPieces.add(new Piece(player, -1));
        }
        startingPieces.add(new Piece(player, 1));
        for (int i = 0; i < 8; i++) {
            startingPieces.add(new Piece(player, 2));
        }
        for (int i = 0; i < 5; i++) {
            startingPieces.add(new Piece(player, 3));
        }
        for (int i = 0; i < 4; i++) {
            startingPieces.add(new Piece(player, 4));
        }
        for (int i = 0; i < 4; i++) {
            startingPieces.add(new Piece(player, 5));
        }
        for (int i = 0; i < 4; i++) {
            startingPieces.add(new Piece(player, 6));
        }
        for (int i = 0; i < 3; i++) {
            startingPieces.add(new Piece(player, 7));
        }
        startingPieces.add(new Piece(player, 8));
        startingPieces.add(new Piece(player, 8));
        startingPieces.add(new Piece(player, 9));
        startingPieces.add(new Piece(player, 10));

        return startingPieces;
    }

    /**
     * checks if location on the board is filled
     * @param row row to be checked
     * @param col column to be checked
     * @return if the space is empty or not
     */
    private boolean isFilled(int row, int col) {
        return getPiece(row, col).getValue() != -3;
    }

    @Override
    public String toString() {
        //All the pieces get displayed as their value number except 10s ("0"), bombs ("B"), flags ("F"), and obstacles (" ").
        StringBuilder sb = new StringBuilder();
        sb.append("    ");
        for (int i = 0; i < 10; i++) {
            sb.append(i).append(" ");
        }
        sb.append("\n");
        sb.append("    - - - - - - - - - - \n");
        for (int i = 0; i < board.size(); i++) {
            ArrayList<Piece> rowList = board.get(i);
            sb.append(i).append(" | ");
            for (Piece p : rowList) {
                if (p.getValue() > 0 && p.getValue() < 10) {
                    sb.append(p.getValue());
                }
                else if (p.getValue() == 10) {
                    sb.append(0);
                }
                else if (p.getValue() == 0) {
                    sb.append("F");
                }
                else if (p.getValue() == -1) {
                    sb.append("B");
                }
                else if (p.getValue() == -2) {
                    sb.append(" ");
                }
                else if (p.getValue() == -3){
                    //I found this special dot character that is in the middle of the space for the char
                    sb.append("·");
                }
                sb.append(" ");
             }
            sb.append("\n");
        }

        return sb.toString();
    }

    public String toString(String player) {
        //All the pieces get displayed as their value number except 10s ("0"), bombs ("B"), flags ("F"), and obstacles (" ").
        StringBuilder sb = new StringBuilder();

        //This code finds the other player
        ArrayList<String> players = new ArrayList<>(Arrays.asList(PLAYER1, PLAYER2));
        players.remove(player);
        String otherPlayer = players.get(0);

        sb.append("    ");
        for (int i = 0; i < 10; i++) {
            sb.append(i).append(" ");
        }
        sb.append("\n");
        sb.append("    - - - - - - - - - - \n");
        for (int i = 0; i < board.size(); i++) {
            ArrayList<Piece> rowList = board.get(i);
            sb.append(i).append(" | ");
            for (Piece p : rowList) {
                //All the pieces that are the other players show up as Xs
                if (p.getTeamName().equals(otherPlayer)) {
                    sb.append("X");
                }
                else if (p.getValue() > 0 && p.getValue() < 10) {
                    sb.append(p.getValue());
                }
                else if (p.getValue() == 10) {
                    sb.append(0);
                }
                else if (p.getValue() == 0) {
                    sb.append("F");
                }
                else if (p.getValue() == -1) {
                    sb.append("B");
                }
                else if (p.getValue() == -2) {
                    sb.append(" ");
                }
                else if (p.getValue() == -3){
                    //I found this special dot character that is in the middle of the space for the char
                    sb.append("·");
                }
                sb.append(" ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    /**
     * checks if there are any movable pieces left
     * @return if there are any movable pieces left
     */
    public boolean movablePiecesLeft() {
        for (int row = 0; row < board.size(); row++) {
            for (int col = 0; col < board.get(0).size(); col++) {
                if (canMovePiece(row,col)) {return true;}
            }
        }
        return false;
    }

    /**
     * move the selected piece to the desired location
     * @param row1 original row of the piece to be moved
     * @param col1 original column of the piece to be moved
     * @param row2 row that the piece will be moved to
     * @param col2 column that the piece will be moved to
     * @throws IllegalArgumentException if the parameters are invalid
     */
    public String move(String currentPlayer, String opponent, int row1, int col1, int row2, int col2) throws IllegalArgumentException {
        checkMoveConditions(currentPlayer, row1, col1, row2, col2);
        Piece current = getPiece(row1, col1);
        Piece newLocation = getPiece(row2, col2);
        if (newLocation.getTeamName().equals(opponent)) {
            return attack(row1, col1, row2, col2);
        } else {
            Piece empty = new Piece("Empty",-3);
            board.get(row2).set(col2, current);
            board.get(row1).set(col1, empty);
        }
        return currentPlayer + " moved to " + row2 + ", " + col2 + ".";
    }

    /**
     * gets the piece at the specified location
     * @param row row of the piece
     * @param col column of the piece
     * @return the location of the piece
     */
    private Piece getPiece(int row, int col) {
        return board.get(row).get(col);
    }

    /**
     * This method takes a row and column on the board and finds out if the piece at that position can be moved
     * @param row the row of the piece
     * @param col the column of the piece
     * @return whether the piece can be moved or not
     */
    public boolean canMovePiece(int row, int col) {
        Piece p = getPiece(row,col);

        //If the piece is an immovable piece (bomb or flag) return false
        if (!getPiece(row,col).isMovable()) {
            return false;
        }

        //checks corners
        if (row == 0 && col == 0) {
            //checks adjacent spaces and returns true if the space is empty or there is an opponents piece
            if (!isFilled(0,1) || !getPiece(0,1).getTeamName().equals(p.getTeamName())) {
                return true;
            }
            if (!isFilled(1,0) || !getPiece(1,0).getTeamName().equals(p.getTeamName())) {
                return true;
            }
        } else if (row == 9 && col == 0) {
            if (!isFilled(8, 0) || !getPiece(8,0).getTeamName().equals(p.getTeamName())) {
                return true;
            }
            if (!isFilled(9,1) || !getPiece(9,1).getTeamName().equals(p.getTeamName())) {
                return true;
            }
        } else if (row == 0 && col == 9) {
            if (!isFilled(0,8) || !getPiece(0,8).getTeamName().equals(p.getTeamName())) {
                return true;
            }
            if (!isFilled(1,9) || !getPiece(1,9).getTeamName().equals(p.getTeamName())) {
                return true;
            }
        } else if (row == 9 && col == 9) {
            if (!isFilled(8,9) || !getPiece(8,9).getTeamName().equals(p.getTeamName())) {
                return true;
            }
            if (!isFilled(9,8) || !getPiece(9,8).getTeamName().equals(p.getTeamName())) {
                return true;
            }
        }
        //checks the edges of the board
        else if (row == 0) {
            if (!isFilled(0,col - 1) || !getPiece(0,col - 1).getTeamName().equals(p.getTeamName())) {
                return true;
            }
            if (!isFilled(0,col + 1) || !getPiece(0,col + 1).getTeamName().equals(p.getTeamName())) {
                return true;
            }
            if (!isFilled(1,col) || !getPiece(1,col).getTeamName().equals(p.getTeamName())) {
                return true;
            }
        } else if (row == 9) {
            if (!isFilled(9,col - 1) || !getPiece(9,col - 1).getTeamName().equals(p.getTeamName())) {
                return true;
            }
            if (!isFilled(9,col + 1) || !getPiece(9,col + 1).getTeamName().equals(p.getTeamName())) {
                return true;
            }
            if (!isFilled(8,col) || !getPiece(8,col).getTeamName().equals(p.getTeamName())) {
                return true;
            }
        } else if (col == 0) {
            if (!isFilled(row - 1,0) || !getPiece(row - 1,0).getTeamName().equals(p.getTeamName())) {
                return true;
            }
            if (!isFilled(row + 1,0) || !getPiece(row + 1,0).getTeamName().equals(p.getTeamName())) {
                return true;
            }
            if (!isFilled(row,1) || !getPiece(row,1).getTeamName().equals(p.getTeamName())) {
                return true;
            }
        } else if (col == 9) {
            if (!isFilled(row - 1,9) || !getPiece(row - 1,9).getTeamName().equals(p.getTeamName())) {
                return true;
            }
            if (!isFilled(row + 1,9) || !getPiece(row + 1,9).getTeamName().equals(p.getTeamName())) {
                return true;
            }
            if (!isFilled(row,8) || !getPiece(row,8).getTeamName().equals(p.getTeamName())) {
                return true;
            }
        }
        //base case
        else if (!isFilled(row - 1, col) || (!getPiece(row - 1,col).getTeamName().equals(p.getTeamName()) && !getPiece(row - 1, col).getTeamName().equals("Obstacle"))) {
            return true;
        }
        else if (!isFilled(row, col - 1) || (!getPiece(row,col - 1).getTeamName().equals(p.getTeamName()) && !getPiece(row, col - 1).getTeamName().equals("Obstacle"))) {
            return true;
        }
        else if (!isFilled(row, col + 1) || (!getPiece(row,col + 1).getTeamName().equals(p.getTeamName()) && !getPiece(row, col + 1).getTeamName().equals("Obstacle"))) {
            return true;
        }
        return !isFilled(row + 1, col) || (!getPiece(row + 1, col).getTeamName().equals(p.getTeamName()) && !getPiece(row + 1, col).getTeamName().equals("Obstacle"));
    }

    /**
     * This method takes a row, column, and the current player and finds out if the piece at that position can be moved by the current player
     * @param row the row of the piece
     * @param col the column of the piece
     * @param currentPlayer the current player
     * @return whether the piece can be moved or not
     */
    public boolean canMovePiece(int row, int col, String currentPlayer) {
        if (!canMovePiece(row, col)) {
            return false;
        }
        return getPiece(row, col).getTeamName().equals(currentPlayer);
    }

    /**
     * sets a piece on the board
     * @param p the piece to be placed
     * @param row the row to place the piece
     * @param col the column to place the piece
     * @throws IllegalArgumentException if the parameters are invalid
     */
    private void setPiece(Piece p, int row, int col) throws IllegalArgumentException {
        if (row < 0 || row > 9) {
            throw new IllegalArgumentException("Invalid row index");
        } else if (col < 0 || col > 9) {
            throw new IllegalArgumentException("Invalid column index");
        } else if (isFilled(row, col)) {
            throw new IllegalArgumentException("Space on the board is already filled");
        } else {
            board.get(row).set(col, p);
        }
    }

    /**
     * method that simulates an attack between two pieces
     * @param attackerRow row that the attacker is located in
     * @param attackerCol column that the attacker is located in
     * @param defenderRow row that the defender is located in
     * @param defenderCol column that the defender is located in
     */
    private String attack(int attackerRow, int attackerCol, int defenderRow, int defenderCol) {
        Piece attacker = getPiece(attackerRow, attackerCol);
        Piece defender = getPiece(defenderRow, defenderCol);
        Piece empty = new Piece("Empty", -3);
        String defVal;
        if (defender.getValue() <= 0) {
            defVal = "";
        }
        else {
            defVal = " (" + defender.getValue() + ") ";
        }
        boolean attackerWins = false;
        //When a spy attacks a 10
        if (attacker.getValue() == 1 && defender.getValue() == 10) {
            board.get(defenderRow).set(defenderCol, attacker);
            board.get(attackerRow).set(attackerCol, empty);
            attackerWins = true;
        }
        //When 10 attacks a spy
        else if (defender.getValue() == 1 && attacker.getValue() == 10) {
            board.get(attackerRow).set(attackerCol, empty);
        }
        //When any piece aside from 3 attacks a bomb
        else if (defender.getValue() == -1 && attacker.getValue() != 3) {
            board.get(attackerRow).set(attackerCol, empty);
        }
        //When 3 attacks a bomb
        else if (defender.getValue() == -1 && attacker.getValue() == 3) {
            board.get(defenderRow).set(defenderCol, attacker);
            board.get(attackerRow).set(attackerCol, empty);
            attackerWins = true;
        }
        //When the attacker defeats the defender
        else if (attacker.getValue() > defender.getValue()) {
            board.get(defenderRow).set(defenderCol, attacker);
            board.get(attackerRow).set(attackerCol, empty);
            attackerWins = true;
        }
        //When the defender defeats the attacker
        else if (attacker.getValue() < defender.getValue()) {
            board.get(attackerRow).set(attackerCol, empty);
        }
        //When both pieces are defeated
        else {
            board.get(attackerRow).set(attackerCol, empty);
            board.get(defenderRow).set(defenderCol, empty);
            return "Each player's " + attacker.getPieceName() + " (" +attacker.getValue() + ") was defeated at " + attackerRow + ", " + attackerCol + ".";
        }

        if (attackerWins) {
            return attacker.getTeamName() + "'s " + attacker.getPieceName() + " (" + attacker.getValue() + ") defeated " + defender.getTeamName() + "'s " + defender.getPieceName() + defVal + "defeated "  + attackerRow + ", " + attackerCol + ".";
        }

        return defender.getTeamName() + "'s " + defender.getPieceName() + defVal + "defeated " + attacker.getTeamName() + "'s " + attacker.getPieceName() + " (" + attacker.getValue() + ") at "  + attackerRow + ", " + attackerCol + ".";

    }

    /**
     * checks the piece that is trying to be moved and the space the player is trying to move it to ensuring the move is valid
     * @param row1 row of the piece that is trying to be moved
     * @param col1 column of the piece that is trying to be moved
     * @param row2 row of the location the player is trying to move the piece to
     * @param col2 column of the location the player is trying to move the piece to
     * @throws IllegalArgumentException if the attempted move is invalid
     */
    private void checkMoveConditions(String currentPlayer, int row1, int col1, int row2, int col2) throws IllegalArgumentException {
        if (row1 < 0 || row1 > 9 || col1 < 0 || col1 > 9) {
            throw new IllegalArgumentException("Invalid board position");
        }
        if (row2 < 0 || row2 > 9 || col2 < 0 || col2 > 9) {
            throw new IllegalArgumentException("Invalid location to move to");
        }
        Piece current = getPiece(row1, col1);
        Piece newLocation = getPiece(row2, col2);
        if (!canMovePiece(row1, col1)) {
            throw new IllegalArgumentException("This piece can not be moved");
        } else if (getPiece(row2,col2).getTeamName().equals(currentPlayer)) {
            throw new IllegalArgumentException("You already have a piece in that location");
        } else if (row1 == row2 && col1 == col2) {
            throw new IllegalArgumentException("Piece is already in this location");
        } else if (current.getValue() != 2 && (row2 > row1 + 1 || row2 < row1 - 1 || col2 > col1 + 1 || col2 < col1 - 1)) {
            throw new IllegalArgumentException("Cannot move the piece to that location");
        } else if (row1 != row2 && col1 != col2) {
            throw new IllegalArgumentException("Cannot move the piece diagonally");
        } else if (current.getValue() == 2 && row1 == row2) {
            //check to make sure there is nothing in the way
            if (col1 < col2) {
                for (int i = col1 + 1; i < col2; i++) {
                    if (isFilled(row1, i)) {
                        throw new IllegalArgumentException("There is a piece in the way");
                    }
                }
            } else {
                for (int i = col2 + 1; i < col1; i++) {
                    if (isFilled(row1,i)) {
                        throw new IllegalArgumentException("There is a piece in the way");
                    }
                }
            }
        } else if (current.getValue() == 2 && col1 == col2) {
            if (row1 < row2) {
                for (int i = row1 + 1; i < row2; i++) {
                    if (isFilled(i, col1)) {
                        throw new IllegalArgumentException("There is a piece in the way");
                    }
                }
            } else {
                for (int i = row2 + 1; i < row1; i++) {
                    if (isFilled(i,col1)) {
                        throw new IllegalArgumentException("There is a piece in the way");
                    }
                }
            }
        } else if (newLocation.getValue() == -2) {
            throw new IllegalArgumentException("Cannot move the piece to that location");
        } else if (!current.getTeamName().equals(currentPlayer)) {
            throw new IllegalArgumentException("That is not one of your pieces");
        }
    }

    /**
     * method that checks if the current player has won the game
     * @param opponent the name of the opponent
     * @return true if the current player has won the game
     */
    public boolean hasWon(String opponent) {
        //if the opponent has a flag the player has not won yet
        if (opponent.equals(PLAYER1)) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 10; j++) {
                    Piece p = getPiece(i,j);
                    if (p.getValue() == 0) {
                        return false;
                    }
                }
            }
        } else {
            for (int i = 6; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    Piece p = getPiece(i,j);
                    if (p.getValue() == 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * checks to see if the current player can make a move
     * @param currentPlayer the name of the current player
     * @return true if the player can make a move
     */
    public boolean canPlay(String currentPlayer) {
        //checks the board for the current player's pieces
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Piece p = getPiece(i,j);
                if (p.getTeamName().equals(currentPlayer) && canMovePiece(i,j)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void presetBoard(String player) {
        ArrayList<ArrayList<Piece>> playerBoard;
        Scanner scan = new Scanner(System.in);

        playerBoard = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            ArrayList<Piece> row = new ArrayList<>(10);
            for (int j = 0; j < 10; j++) {
                Piece empty = new Piece("Empty", -3);
                row.add(empty);
            }
            playerBoard.add(row);
        }
        Queue<Piece> piecesLeft = createLinkedLists(player);

        //Set flag
        playerBoard.get(3).set(9, piecesLeft.remove());

        //Set bombs
        playerBoard.get(1).set(4 , piecesLeft.remove());
        playerBoard.get(2).set(4 , piecesLeft.remove());
        playerBoard.get(2).set(9 , piecesLeft.remove());
        playerBoard.get(3).set(1 , piecesLeft.remove());
        playerBoard.get(3).set(3 , piecesLeft.remove());
        playerBoard.get(3).set(8 , piecesLeft.remove());

        //Set 1
        playerBoard.get(2).set(2 , piecesLeft.remove());

        //Set 2s
        playerBoard.get(0).set(1 , piecesLeft.remove());
        playerBoard.get(0).set(5 , piecesLeft.remove());
        playerBoard.get(0).set(6 , piecesLeft.remove());
        playerBoard.get(0).set(8 , piecesLeft.remove());
        playerBoard.get(1).set(1 , piecesLeft.remove());
        playerBoard.get(1).set(5 , piecesLeft.remove());
        playerBoard.get(2).set(5 , piecesLeft.remove());
        playerBoard.get(3).set(5 , piecesLeft.remove());

        //Set 3s
        playerBoard.get(1).set(9 , piecesLeft.remove());
        playerBoard.get(2).set(3 , piecesLeft.remove());
        playerBoard.get(3).set(0 , piecesLeft.remove());
        playerBoard.get(3).set(6 , piecesLeft.remove());
        playerBoard.get(3).set(7 , piecesLeft.remove());

        //Set 4s
        playerBoard.get(0).set(2 , piecesLeft.remove());
        playerBoard.get(2).set(0 , piecesLeft.remove());
        playerBoard.get(3).set(2 , piecesLeft.remove());
        playerBoard.get(3).set(4 , piecesLeft.remove());

        //Set 5s
        playerBoard.get(1).set(0 , piecesLeft.remove());
        playerBoard.get(1).set(3 , piecesLeft.remove());
        playerBoard.get(2).set(7 , piecesLeft.remove());
        playerBoard.get(2).set(8 , piecesLeft.remove());

        //Set 6s
        playerBoard.get(0).set(0 , piecesLeft.remove());
        playerBoard.get(0).set(4 , piecesLeft.remove());
        playerBoard.get(0).set(9 , piecesLeft.remove());
        playerBoard.get(2).set(6 , piecesLeft.remove());

        //Set 7s
        playerBoard.get(1).set(2 , piecesLeft.remove());
        playerBoard.get(1).set(6 , piecesLeft.remove());
        playerBoard.get(1).set(7 , piecesLeft.remove());

        //Set 8s
        playerBoard.get(2).set(1 , piecesLeft.remove());
        playerBoard.get(1).set(8 , piecesLeft.remove());

        //Set 9
        playerBoard.get(0).set(3 , piecesLeft.remove());

        //Set 10
        playerBoard.get(0).set(7 , piecesLeft.remove());

        if (player.equals(PLAYER1)) {
            //set the last four rows to playerBoard

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 10; j++) {
                    setPiece(playerBoard.get(i).get(j), (i+6), j);
                }
            }
        }
        else {
            //set the first four rows to the transpose of playerBoard

            //This transposes playerBoard
            Collections.reverse(playerBoard);
            for (int i = 0; i < 4; i++) {
                Collections.reverse(playerBoard.get(i));
            }

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 10; j++) {
                    setPiece(playerBoard.get(i).get(j), (i), j);
                }
            }
        }

        System.out.println(toString(player));
        while (true) {
            System.out.println(player + ", would you like to swap the position of any of you pieces? Enter \"y\" or \"n\".");
            char c = scan.next().toUpperCase().charAt(0);
            if (c == 'Y') {
                int row1, col1, row2, col2;
                while (true) {
                    System.out.println(toString(player));
                    System.out.println("Enter the location (row, then a space, column) of the first piece you want to swap. If you are done enter -1.");
                    try {
                        row1 = scan.nextInt();
                        if (row1 == -1) {
                            return;
                        }
                        col1 = scan.nextInt();
                        Piece p = getPiece(row1,col1);
                        if (!p.getTeamName().equals(player)) {
                            System.out.println("You cannot move that piece.");
                            continue;
                        }
                    }
                    catch (Exception e) {
                        System.out.println("\nNot a valid location\n");
                        continue;
                    }

                    System.out.println("Enter the location (row, then a space, column) of the second piece you want to swap.");
                    try {
                        row2 = scan.nextInt();
                        col2 = scan.nextInt();
                        getPiece(row2,col2);
                        if ((row1 == row2) && (col1 == col2)) {
                            System.out.println("That is the same location as first piece.");
                            continue;
                        }
                        Piece p = getPiece(row2,col2);
                        if (!p.getTeamName().equals(player)) {
                            System.out.println("You cannot move that piece.");
                            continue;
                        }
                    }
                    catch (Exception e) {
                        System.out.println("\nNot a valid location\n");
                        continue;
                    }

                    swapPieces(row1, col1, row2, col2);
                }
                //break;
            }
            else if (c == 'N') {
                break;
            }
            else {
                System.out.println("Invalid entry.");
                scan.nextLine();
            }
        }

    }

    private void swapPieces(int row1, int col1, int row2, int col2) {
        Piece savePiece = getPiece(row1,col1);
        board.get(row1).set(col1,getPiece(row2,col2));
        board.get(row2).set(col2,savePiece);
    }
}
