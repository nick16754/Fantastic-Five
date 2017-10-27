import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class Tile {

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

    private GridLayout generateLayout() {
        if (currentPlayers == 1) {
            return new GridLayout(1,1);
        } else if (currentPlayers == 2) {
            return new GridLayout(1, 2);
        } else if (currentPlayers >= 3) {
            return new GridLayout(2, 2);
        }

        return null;
    }

    public void removePiece(Piece rm) {
        currentPieces.remove(rm);
        currentPlayers--;

        refreshPanel();
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
