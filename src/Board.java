import java.util.*;

public class Board {
    private ArrayList<ArrayList<Piece>> board;

    private String player1;
    private String player2;

    /**
     * the board constructor
     */
    public Board(String player1, String player2) {
        this.player1 = player1;
        this.player2 = player2;

        //startingPieces1 = createLinkedLists(this.player1);
        //startingPieces2 = createLinkedLists(this.player2);

        //initializing the board
        board = new ArrayList<ArrayList<Piece>>();
        for (int i = 0; i < 10; i++) {
            ArrayList<Piece> col = new ArrayList<>(10);
            for (int j = 0; j < 10; j++) {
                //was originally going to make empty spaces null but it didnt seem to be working the way
                // I wanted it to. if you have a better idea go for it
                Piece empty = new Piece("Empty", -3);
                col.add(empty);
            }
            board.add(col);
        }

        //putting obstacles on the board
        for (int i = 2; i < 8; i++) {
            for (int j = 4; j < 6; j++) {
                if (i == 4 || i == 5) {
                    continue;
                } else {
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
        LinkedList<Piece> startingPieces = createLinkedLists(player);
        Scanner scan = new Scanner(System.in);


        //minRow and maxRow are used later on to make sure that the player adds pieces in the part of the board where they are allowed to
        int minRow = -1;
        int maxRow = -1;
        if (player1.equals(player)) {
            minRow = 0;
            maxRow = 3;
        } else if (player2.equals(player)) {
            minRow = 6;
            maxRow = 9;
        }

        //keep looping while there are still Pieces in the linkedlist que
        while (startingPieces.size() > 0) {
            //This scanner takes in a line from the console
            Scanner lineScan;

            //This line removes the Pieces from the que
            Piece p = startingPieces.remove(0);
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
    private LinkedList<Piece> createLinkedLists(String player) {
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
        //im only counting 39 pieces not 40, one website said there are 5 captains instead of 4
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
    public boolean isFilled(int row, int col) {
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
        //____________________-------------------
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
    public void move(String currentPlayer, String opponent, int row1, int col1, int row2, int col2) throws IllegalArgumentException {
        checkMoveConditions(currentPlayer, row1, col1, row2, col2);
        Piece current = getPiece(row1, col1);
        Piece newLocation = getPiece(row2, col2);
        if (newLocation.getTeamName().equals(opponent)) {
            attack(row1, col1, row2, col2);
        } else {
            Piece empty = new Piece("Empty",-3);
            board.get(row2).set(col2, current);
            board.get(row1).set(col1, empty);
        }
    }

    /**
     * gets the piece at the specified location
     * @param row row of the piece
     * @param col column of the piece
     * @return the location of the piece
     */
    public Piece getPiece(int row, int col) {
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

        int thisRow;
        int thisCol;

        //this is my attempt, it looks long and ugly. if you can think of a better way of doing this feel free to change it

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
        else if (!isFilled(row - 1, col) || !getPiece(row - 1,col).getTeamName().equals(p.getTeamName())) {
            return true;
        }
        else if (!isFilled(row, col - 1) || !getPiece(row,col - 1).getTeamName().equals(p.getTeamName())) {
            return true;
        }
        else if (!isFilled(row, col + 1) || !getPiece(row,col + 1).getTeamName().equals(p.getTeamName())) {
            return true;
        }
        return !isFilled(row + 1, col) || !getPiece(row + 1, col).getTeamName().equals(p.getTeamName());

        /*//If a movable spot is found, return true
        //Checks first column to the left of piece
        //do we need to check the columns to the left and right? I dont think pieces can move diagonally
        for (int i = 0; i < 3; i++) {
            thisRow = i-1+row;
            thisCol = -1 + col;
            if ((thisRow >= 0) && (thisRow <= 9) && (thisCol >= 0) && (thisCol <= 9) && (getPiece(thisRow,thisCol).getValue() == -3) || !getPiece(thisRow,thisCol).getTeamName().equals(p.getTeamName())){return true;}
        }

        //Checks the spaces above and below the piece
        thisRow = row - 1;
        thisCol = col;
        if ((thisRow >= 0) && (thisRow <= 9) && (thisCol >= 0) && (thisCol <= 9) && (getPiece(thisRow,thisCol).getValue() == -3) || !getPiece(thisRow,thisCol).getTeamName().equals(p.getTeamName())) {return true;}

        thisRow = row + 1;
        if ((thisRow >= 0) && (thisRow <= 9) && (thisCol >= 0) && (getPiece(thisRow,thisCol).getValue() == -3) || !getPiece(thisRow,thisCol).getTeamName().equals(p.getTeamName())) {return true;}

        //Checks the column to the right of the piece
        for (int i = 0; i < 3; i++) {
            thisRow = i-1+row;
            thisCol = 1 + col;
            if ((thisRow >= 0) && (thisRow <= 9) && (thisCol >= 0) && (getPiece(thisRow,thisCol).getValue() == -3) || !getPiece(thisRow,thisCol).getTeamName().equals(p.getTeamName())) {return true;}
        }*/
    }

    public boolean canMovePiece(int row, int col, String currentPlayer) {
        if (!canMovePiece(row, col)) {
            return false;
        }
        if (!getPiece(row,col).getTeamName().equals(currentPlayer)) {
            return false;
        }
        return true;
    }

    /**
     * sets a piece on the board
     * @param p the piece to be placed
     * @param row the row to place the piece
     * @param col the column to place the piece
     * @throws IllegalArgumentException if the parameters are invalid
     */
    public void setPiece(Piece p, int row, int col) throws IllegalArgumentException {
        if (row < 0 || row > 9) {
            throw new IllegalArgumentException("Invalid row index");
        } else if (col < 0 || col > 9) {
            throw new IllegalArgumentException("Invalid column index");
        } else if (isFilled(row, col)) {
            throw new IllegalArgumentException("Space on the board is already filled");
        } else {
            //I looked it up online and found this way to change things in 2D arraylists
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
    public void attack(int attackerRow, int attackerCol, int defenderRow, int defenderCol) {
        Piece attacker = getPiece(attackerRow, attackerCol);
        Piece defender = getPiece(defenderRow, defenderCol);
        Piece empty = new Piece("Empty", -3);
        if (attacker.getValue() == 1 && defender.getValue() == 10) {
            board.get(defenderRow).set(defenderCol, attacker);
            board.get(attackerRow).set(attackerCol, empty);
        } else if (defender.getValue() == -1 && attacker.getValue() != 3) {
            board.get(attackerRow).set(attackerCol, empty);
        } else if (defender.getValue() == -1 && attacker.getValue() == 3) {
            board.get(defenderRow).set(defenderCol, attacker);
            board.get(attackerRow).set(attackerCol, empty);
        } else if (attacker.getValue() > defender.getValue()) {
            board.get(defenderRow).set(defenderCol, attacker);
            board.get(attackerRow).set(attackerCol, empty);
        } else if (attacker.getValue() < defender.getValue()) {
            board.get(attackerRow).set(attackerCol, empty);
        }
        //if they are equal both pieces are removed
        else {
            board.get(attackerRow).set(attackerCol, empty);
            board.get(defenderRow).set(defenderCol, empty);
        }
    }

    /**
     * checks the piece that is trying to be moved and the space the player is trying to move it to ensuring the move is valid
     * @param row1 row of the piece that is trying to be moved
     * @param col1 column of the piece that is trying to be moved
     * @param row2 row of the location the player is trying to move the piece to
     * @param col2 column of the location the player is trying to move the piece to
     * @throws IllegalArgumentException if the attempted move is invalid
     */
    public void checkMoveConditions(String currentPlayer, int row1, int col1, int row2, int col2) throws IllegalArgumentException {
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
        if (opponent.equals(player1)) {
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

    /**
     * method that automatically fills the board
     * @param auto boolean variable that determines whether to automatically fill or not
     */
    public void autoFill(boolean auto,String player1, String player2) {
        if (auto) {
            LinkedList<Piece> pieces = createLinkedLists(player1);
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 10; j++) {
                    Piece p = pieces.remove();
                    setPiece(p,i,j);
                }
            }
            LinkedList<Piece> pieces2 = createLinkedLists(player2);
            for (int i = 6; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    Piece p = pieces2.remove();
                    setPiece(p,i,j);
                }
            }
        }
    }
}
