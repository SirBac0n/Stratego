public class Piece {
    private String teamName;

    //Flags have value of 0
    //Bombs have value of -1
    //Obstacles have value of -2
    //Empty spaces have value of -3
    private int value;

    private boolean movable;

    /**
     * gets the name of the team
     * @return the name of the team
     */
    public String getTeamName() {
        return teamName;
    }

    /**
     * gets the value of the piece
     * @return the value of the piece
     */
    public int getValue() {
        return value;
    }

    /**
     * checks if the piece can be moved
     * @return if the piece is movable
     */
    public boolean isMovable() {
        return movable;
    }

    /**
     * piece constructor
     * @param teamName name of the team
     * @param value value of the piece
     */
    public Piece(String teamName, int value) {
        this.value = value;
        this.teamName = teamName;

        //This makes Obstacles have a false movable value as well.
        movable = (value > 0) || (value == -3);


    }

    /**
     * copies the variables of a piece over to another piece
     * @param p the piece to copy from
     */
    public void copy(Piece p) {
        if (p != null) {
            teamName = p.teamName;
            value = p.value;
            movable = p.movable;
        }
    }
}
