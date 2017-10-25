import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.text.*;
import java.io.*;

public class GameBoard {

    static JFrame _frame = new JFrame("World of Sweets");

    JPanel[][] squares = new JPanel[10][10];
    LinkedList<JPanel> tileList = new LinkedList<>();

    public GameBoard() {
        create_board();
        initialize();
    }

    private void initialize() {

        ArrayList<Color> colors = new ArrayList<>();
        colors.add(Color.RED);
        colors.add(Color.YELLOW);
        colors.add(Color.BLUE);
        colors.add(Color.GREEN);
        colors.add(Color.ORANGE);


        tileList.add(squares[1][0]);
        tileList.add(squares[1][1]);
        tileList.add(squares[2][1]);
        tileList.add(squares[3][1]);
        tileList.add(squares[4][1]);
        tileList.add(squares[5][1]);
        tileList.add(squares[6][1]);
        tileList.add(squares[7][1]);
        tileList.add(squares[8][1]);
        tileList.add(squares[9][1]);
        tileList.add(squares[9][2]);
        tileList.add(squares[9][3]);
        tileList.add(squares[8][3]);
        tileList.add(squares[7][3]);
        tileList.add(squares[6][3]);
        tileList.add(squares[5][3]);
        tileList.add(squares[4][3]);
        tileList.add(squares[3][3]);
        tileList.add(squares[2][3]);
        tileList.add(squares[2][4]);
        tileList.add(squares[2][5]);
        tileList.add(squares[2][6]);
        tileList.add(squares[2][7]);
        tileList.add(squares[3][7]);
        tileList.add(squares[4][7]);
        tileList.add(squares[5][7]);
        tileList.add(squares[5][6]);
        tileList.add(squares[5][5]);
        tileList.add(squares[6][5]);
        tileList.add(squares[7][5]);
        tileList.add(squares[7][6]);
        tileList.add(squares[7][7]);
        tileList.add(squares[7][8]);
        tileList.add(squares[7][9]);

        // Set first tile black
        tileList.get(0).setBackground(Color.BLACK);

        // cycle the rest of the colors
        int colorCounter = 0;
        for (int i = 1; i < tileList.size(); i++) {

            if (colorCounter >= 5) {
                colorCounter = 0;
            }

            tileList.get(i).setBackground(colors.get(colorCounter));
            colorCounter++;
        }


    }

    public Boolean create_board() {
        // Center the frame
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - _frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - _frame.getHeight()) / 2);
        _frame.setLocation(x, y);

        _frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        _frame.setSize(1000, 1000);


        JPanel MainPanel = new JPanel();

        _frame.add(MainPanel);

        GridLayout experimentLayout = new GridLayout(10, 10);
        MainPanel.setLayout(experimentLayout);



        int rowCounter = 0;
        int rows = 0;

        for (int i = 0; i < 100; i++) {

            JPanel sub = new JPanel();
//            sub.setBorder(BorderFactory.createLineBorder(Color.black));
//            sub.setBackground(Color.red);
            sub.setSize(100, 100);
            sub.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    System.out.println("Click");
                }
            });


            if (rowCounter >= 10) {
                rowCounter = 0;
                rows++;
            }

            System.out.println("Adding square at row " + rows + ", col " + (i % 10));
            squares[rows][i % 10] = sub;


            MainPanel.add(sub);

            rowCounter++;
        }

        squares[1][1].setBackground(Color.green);

        _frame.setVisible(true);

        return true;
    }

}
