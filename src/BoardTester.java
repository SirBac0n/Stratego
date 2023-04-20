import org.junit.jupiter.api.Test;

public class BoardTester {
    @Test
    public void isFilledTester() {
        Board board = new Board("player1", "player2");
        System.out.println(board.isFilled(5,5));
    }
}
