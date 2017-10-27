import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Tile {

    int currentPlayers = 0;
    JPanel panel;

    int xCoord, yCoord;

    // Constructor
    public Tile(JPanel p, int x, int y) {
        this.panel = p;
        this.xCoord = x;
        this.yCoord = y;
    }

    public void placePiece(Piece p) {
        panel.setLayout(new GridLayout(1, 1));
        panel.add(p.getLabel());

        currentPlayers++;
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
