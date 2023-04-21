import org.junit.jupiter.api.Test;

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
}
