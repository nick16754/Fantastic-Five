import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Tile extends JPanel{

    int currentPlayers = 0;
    JPanel panel;

    int xCoord, yCoord;

    ArrayList<Piece> currentPieces = new ArrayList<>();

    // Constructor
    public Tile(JPanel p, int x, int y) {
        this.panel = p;
        this.xCoord = x;
        this.yCoord = y;
    }


    // Actions
    public void placePiece(Piece p) {
        currentPlayers++;
        currentPieces.add(p);

        refreshPanel();
    }

    public void refreshPanel() {
        GridLayout g = generateLayout();

        panel.removeAll();
        panel.setLayout(g);

        for (Piece p : currentPieces) {
            panel.add(p.getLabel());
        }

        GameBoard.refresh();
    }

    public void removePiece(Piece rm) {
        currentPieces.remove(rm);
        currentPlayers--;

        refreshPanel();
    }

    private GridLayout generateLayout() {
        if (currentPlayers == 1) {
            return new GridLayout(1, 1);
        } else if (currentPlayers == 2) {
            return new GridLayout(1, 2);
        } else if (currentPlayers >= 3) {
            return new GridLayout(2, 2);
        }

        return null;
    }


    // Accessors
    public JPanel getPanel() {
        return panel;
    }

    public int getxCoord() {
        return xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }


}
