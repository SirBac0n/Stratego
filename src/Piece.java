public class Piece {
    private String teamName;

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
        //Keith: I don't know if we need our setters because we can just do this inside the constructor
        // and we will never need to reset it.
        this.value = value;
        this.teamName = teamName;
        //im thinking of representing the flag as a 0 and bombs as -1 but am open to changing it
        if (value == 0 || value == -1) {
            movable = false;
        } else {
            movable = true;
        }
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
