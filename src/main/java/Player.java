import com.google.gson.annotations.Expose;

public class Player {

    private String playerName;
    private Piece piece;
    private int currentTileIndex;
    private boolean ai_status = false;

    private Tile currentTile;

    // Constructor
    public Player(String s, Piece p, boolean ai) {
        this.playerName = s;
        this.piece = p;
        this.ai_status = ai;
    }
    public Player(String s, Piece p) {
        this.playerName = s;
        this.piece = p;
    }

    // Actions
    public void moveToTile(GameBoard g, Tile t) {

        // Check if the piece is already on a tile
        if (currentTile != null) {
            currentTile.removePiece(g, piece);
            currentTile.revalidate();
            currentTile.repaint();
        }

        t.placePiece(g, piece);
        currentTile = t;
        currentTile.revalidate();
        currentTile.repaint();

        currentTileIndex = currentTile.getIndex();

        g.revalidate();
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

    public int getCurrentTileIndex() {
        return currentTileIndex;
    }
    public boolean get_ai_status(){
      return this.ai_status;
    }


    // Mutators
    public void setName(String s) {
        this.playerName = s;
    }

    public void setPiece(Piece s) {
        this.piece = s;
    }
    public void set_AiPlayer(boolean ai){
        this.ai_status = ai;
    }
}
