import java.util.ArrayList;
import java.util.LinkedList;

public class Board {
    private ArrayList<ArrayList<Piece>> board;

    private LinkedList<Piece> startingPieces1;

    private LinkedList<Piece> startingPieces2;

    private String player1;
    private String player2;

    /**
     * the board constructor
     */
    public Board(String player1, String player2) {
        this.player1 = player1;
        this.player2 = player2;

        startingPieces1 = initializeStartingPieces(this.player1);
        startingPieces2 = initializeStartingPieces(this.player2);

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
                    setPiece(obstacle, i, j);
                }
            }
        }

        //Still need to ask where the starting pieces go

    }

    /**
     * This method is used in the constructor to create the linked list of the pieces that need to be placed on the board
     * @param player the name of the player that these pieces will belong to
     * @return
     */
    private LinkedList<Piece> initializeStartingPieces(String player) {
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
        return board.get(col).get(row).getValue() != -3;
    }

    @Override
    public String toString() {
        //All the pieces get displayed as their value number except 10s ("0"), bombs ("B"), flags ("F"), and obstacles (" ").
        StringBuilder sb = new StringBuilder();
        sb.append("    ");
        for (int i = 0; i < 10; i++) {
            sb.append(i + " ");
        }
        sb.append("\n");
        //____________________-------------------
        sb.append("    - - - - - - - - - - \n");
        for (int i = 0; i < board.size(); i++) {
            ArrayList<Piece> rowList = board.get(i);
            sb.append(i + " | ");
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
        return false;
    }

    /**
     * move the selected piece to the desired location
     * @param currentPlayer the name of the player
     * @param row1 original row of the piece to be moved
     * @param col1 original column of the piece to be moved
     * @param row2 row that the piece will be moved to
     * @param col2 column that the piece will be moved to
     * @throws IllegalArgumentException if the parameters are invalid
     */
    public void move(String currentPlayer, int row1, int col1, int row2, int col2) throws IllegalArgumentException {
        //will throw some kind of exception
    }

    /**
     * gets the piece at the specified location
     * @param row row of the piece
     * @param col column of the piece
     * @return the location of the piece
     */
    public Piece getPiece(int row, int col) {
        return board.get(col).get(row);
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
            //the only way I could figure out how to implement this was by making a copy method
            //if you can think of a better way of doing this feel free to change it
            getPiece(row, col).copy(p);
        }
    }
}
