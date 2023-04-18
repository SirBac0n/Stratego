public class Piece {
    private String teamName;

    private int value;

    private boolean movable;

    /**
     * sets the name of the team that the piece belongs to
     * @param teamName name of the team
     */
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    /**
     * sets the value of the piece
     * @param value the value of the piece
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * gets the name of the team
     * @return the name of the team
     */
    public String getTeamName() {
        return null;
    }

    /**
     * gets the value of the piece
     * @return the value of the piece
     */
    public int getValue() {
        return -1;
    }

    /**
     * checks if the piece can be moved
     * @return if the piece is movable
     */
    public boolean isMovable() {
        return false;
    }

    /**
     * piece constructor
     * @param teamName name of the team
     * @param value value of the piece
     */
    public Piece(String teamName, int value) {

    }
}
