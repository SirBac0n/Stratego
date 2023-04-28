public class Piece {
    private String teamName;

    //Flags have value of 0
    //Bombs have value of -1
    //Obstacles have value of -2
    //Empty spaces have value of -3
    private int value;

    private boolean movable;

    private String pieceName;

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
        //do we want empty pieces to be movable?
        movable = (value > 0) || (value == -3);
        pieceName = setPieceName(value);
    }

    /**
     * method to set the name of the piece based on the value of the piece
     * @param value value of the piece
     * @return the name of the piece
     */
    public String setPieceName(int value) {
        return switch (value) {
            case -1 -> "Bomb";
            case 0 -> "Flag";
            case 1 -> "Spy";
            case 2 -> "Scout";
            case 3 -> "Miner";
            case 4 -> "Sergeant";
            case 5 -> "Lieutenant";
            case 6 -> "Captain";
            case 7 -> "Major";
            case 8 -> "Colonel";
            case 9 -> "General";
            case 10 -> "Marshal";
            default -> "";
        };
    }

}
