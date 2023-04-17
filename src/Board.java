import java.util.ArrayList;
import java.util.LinkedList;

public class Board {
    private ArrayList<ArrayList<Piece>> board;

    private LinkedList<Piece> startingPieces1;

    private LinkedList<Piece> startingPieces2;

    /**
     * the board constructor
     */
    public Board() {

    }

    /**
     * checks if location on the board is filled
     * @param row row to be checked
     * @param col column to be checked
     * @return if the space is empty or not
     */
    public boolean isFilled(int row, int col) {
        return false;
    }

    @Override
    public String toString() {
        return null;
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
        return null;
    }

    /**
     * sets a piece on the board
     * @param p the piece to be placed
     * @param row the row to place the piece
     * @param col the column to place the piece
     * @throws IllegalArgumentException if the parameters are invalid
     */
    public void setPiece(Piece p, int row, int col) throws IllegalArgumentException {

    }
}
