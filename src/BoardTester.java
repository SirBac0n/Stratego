import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BoardTester {
    @Test
    public void isFilledTester() {
        Board board = new Board("player1", "player2");
        //tests empty space
        System.out.println(board.isFilled(4,9));
        //tests obstacle
        System.out.println(board.isFilled(4,2));
    }

    @Test
    public void setPieceTester() {
        Board board = new Board("player1", "player2");
        Piece test = new Piece("test", 4);
        //sets a piece on the board then checks to see if it put the piece in properly
        board.setPiece(test, 7, 2);
        System.out.println(board.isFilled(7,2));
    }

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        //System.out.println(scn.nextInt(f));
        Board b = new Board("Bill","Susan");
        b.setPlayerPieces("Bill");
        b.setPiece(new Piece("Bill",10),9,9);
        System.out.println(b);

    }

    @Test
    public void canMovePieceTester() {
        Board b = new Board("Bob", "Joe");
        Piece p = new Piece("Bob", 4);
        //tests corner
        b.setPiece(p, 0, 0);
        //tests edge
        b.setPiece(p, 0,2);
        b.setPiece(p,0,3);
        b.setPiece(p,0,4);
        b.setPiece(p,1,3);
        //tests base case
        b.setPiece(p,7,4);
        b.setPiece(p,8, 3);
        b.setPiece(p,8,4);
        b.setPiece(p,8,5);
        b.setPiece(p,9,4);

        //made canMovePiece public for now just to test it
        //it doesnt seem to be able to check edges yet
        assertTrue(b.canMovePiece(0,0));
        assertFalse(b.canMovePiece(0,3));
        assertFalse(b.canMovePiece(8,4));
    }
}
