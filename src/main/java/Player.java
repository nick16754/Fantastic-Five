public class Player {

    private String playerName;
    private Piece piece;

    private Tile currentTile;


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

    public Tile getCurrentTile() {
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

        // Check if the piece is already on a tile
        if (currentTile != null) {
            currentTile.removePiece(piece);
        }

        p.placePiece(piece);
        currentTile = p;
    }

}
