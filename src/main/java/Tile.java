import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Tile extends JPanel {

    private int currentPlayers = 0;
    private JPanel panel;
    private int xCoord, yCoord;
    private int index;

    private Color color;

    private ArrayList<Piece> currentPieces = new ArrayList<>();

    // Constructor
    public Tile(JPanel p, int x, int y, int i) {
        this.panel = p;
        this.xCoord = x;
        this.yCoord = y;
        this.index = i;
    }


    // Actions
    public void placePiece(GameBoard g, Piece p) {
        currentPlayers++;
        currentPieces.add(p);

        refreshPanel(g);
    }

    public void refreshPanel(GameBoard gb) {
        GridLayout g = generateLayout();

        panel.removeAll();
        panel.setLayout(g);

        for (Piece p : currentPieces) {
            panel.add(p.getLabel());
        }

        gb.refresh();
    }

    public void removePiece(GameBoard g, Piece rm) {
        currentPieces.remove(rm);
        currentPlayers--;

        refreshPanel(g);
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

    public ArrayList<Piece> getCurrentPieces() {
        return currentPieces;
    }

    public int getIndex() {
        return index;
    }


    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
