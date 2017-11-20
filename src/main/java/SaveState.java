import java.util.ArrayList;

public class SaveState {

    private ArrayList<Player> playerList = new ArrayList<>();
    private WoSDeck cardDeck = new WoSDeck();


    public SaveState(ArrayList<Player> p, WoSDeck w) {
        this.playerList = p;
        this.cardDeck = w;
    }

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }

    public WoSDeck getCardDeck() {
        return cardDeck;
    }
}
