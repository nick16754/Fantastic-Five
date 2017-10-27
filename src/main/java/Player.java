public class Player {

    private String playerName;
    private Piece piece;

    int currentTile = 0;


    public Player(String s, Piece p) {
        this.playerName = s;
        this.piece = p;
    }


    // Accessors
    public String getName() {
        return this.playerName;
    }

    public Piece getPiece() {
        return this.piece;
    }

    public int getCurrentTile() {
        return currentTile;
    }

    // Mutators
    public void setName(String s) {
        this.playerName = s;
    }

    public void setPiece(Piece s) {
        this.piece = s;
    }

    public void moveToTile(Tile p) {
        p.placePiece(piece);
        currentTile = 0;
    }

}
