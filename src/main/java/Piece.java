import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Piece {

    JLabel label;
    String pieceString;

    // Constructor
    public Piece(String p) {
        this.pieceString = p;
        try {
            BufferedImage image = ImageIO.read(new File("src/assets/" + p));
            label = new JLabel(new ImageIcon(new ImageIcon(image).getImage().getScaledInstance(35, 35, Image.SCALE_DEFAULT)));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Accessors
    public JLabel getLabel() {
        return label;
    }

    public String getPieceString() {
        return pieceString;
    }


}
