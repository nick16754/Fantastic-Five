import java.util.ArrayList;

public class SaveState {

    private ArrayList<Player> playerList = new ArrayList<>();
    private WoSDeck cardDeck = new WoSDeck();

    private int currentTurn;


    public SaveState(ArrayList<Player> p, WoSDeck w, int currentTurn) {
        this.playerList = p;
        this.cardDeck = w;
        this.currentTurn = currentTurn;
    }

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }

    public WoSDeck getCardDeck() {
        return cardDeck;
    }

    public int getCurrentTurn() {
        return currentTurn;
    }
}
