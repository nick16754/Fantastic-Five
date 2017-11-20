import java.util.ArrayList;

public class SaveState {

    ArrayList<Tile> tiles = new ArrayList<>();
    ArrayList<Player> playerList = new ArrayList<>();
    WoSDeck cardDeck = new WoSDeck();


    public SaveState(ArrayList<Tile> t, ArrayList<Player> p, WoSDeck w) {
        this.tiles = t;
        this.playerList = p;
        this.cardDeck = w;
    }

}
