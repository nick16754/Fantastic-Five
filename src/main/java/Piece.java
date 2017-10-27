import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Piece {

    JLabel label;

    public Piece(String pieceString) {
        try {
            BufferedImage image = ImageIO.read(new File("src/assets/"+pieceString));
             label = new JLabel(new ImageIcon(new ImageIcon(image).getImage().getScaledInstance(35, 35, Image.SCALE_DEFAULT)));

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public JLabel getLabel() {
        return label;
    }



}
