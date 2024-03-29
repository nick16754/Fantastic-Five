import java.util.ArrayList;

public class SaveState {

    private String mode;

    private ArrayList<Player> playerList = new ArrayList<>();
    private WoSDeck cardDeck = new WoSDeck();

    private int currentTurn;
    private long time;


    public SaveState(String m, ArrayList<Player> p, WoSDeck w, int currentTurn, long ms) {
        this.mode = m;
        this.playerList = p;
        this.cardDeck = w;
        this.currentTurn = currentTurn;
        this.time = ms;
    }

    public String getMode() {
        return mode;
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

    public long getTime() {
        return time;
    }
}
