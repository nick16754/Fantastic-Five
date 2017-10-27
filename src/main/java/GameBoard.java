import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;


public class GameBoard {

    private static final int WINDOW_HEIGHT = 1000;
    private static final int WINDOW_WIDTH = 1000;

    static JFrame _frame = new JFrame("World of Sweets");

    JPanel[][] squares = new JPanel[10][10];
    LinkedList<Tile> tileList = new LinkedList<>();

    ArrayList<Player> playerList = new ArrayList<>();

    public GameBoard() {

        Player one = new Player("Player 1", new Piece("placeholder_piece.png"));
        playerList.add(one);

        Player two = new Player("Player 2", new Piece("placeholder_piece.png"));
        playerList.add(two);

        Player three = new Player("Player 3", new Piece("placeholder_piece.png"));
        playerList.add(three);

        create_board();
        initialize();
    }

    private void initialize() {
        // Initialize color pattern for game board
        ArrayList<Color> colors = new ArrayList<>();
        colors.add(Color.RED);
        colors.add(Color.YELLOW);
        colors.add(Color.BLUE);
        colors.add(Color.GREEN);
        colors.add(Color.ORANGE);


        // Build the path of the game board
        //todo: clean this up
        tileList.add(new Tile(squares[1][0], 1, 0));
        tileList.add(new Tile(squares[1][1], 1, 1));
        tileList.add(new Tile(squares[2][1], 2, 1));
        tileList.add(new Tile(squares[3][1], 3, 1));
        tileList.add(new Tile(squares[4][1], 4, 1));
        tileList.add(new Tile(squares[5][1], 5, 1));
        tileList.add(new Tile(squares[6][1], 6, 1));
        tileList.add(new Tile(squares[7][1], 7, 1));
        tileList.add(new Tile(squares[8][1], 8, 1));
        tileList.add(new Tile(squares[9][1], 9, 1));
        tileList.add(new Tile(squares[9][2], 9, 2));
        tileList.add(new Tile(squares[9][3], 9, 3));
        tileList.add(new Tile(squares[8][3], 8, 3));
        tileList.add(new Tile(squares[7][3], 7, 3));
        tileList.add(new Tile(squares[6][3], 6, 3));
        tileList.add(new Tile(squares[5][3], 5, 3));
        tileList.add(new Tile(squares[4][3], 4, 3));
        tileList.add(new Tile(squares[3][3], 3, 3));
        tileList.add(new Tile(squares[2][3], 2, 3));
        tileList.add(new Tile(squares[2][4], 2, 4));
        tileList.add(new Tile(squares[2][5], 2, 5));
        tileList.add(new Tile(squares[2][6], 2, 6));
        tileList.add(new Tile(squares[2][7], 2, 7));
        tileList.add(new Tile(squares[3][7], 3, 7));
        tileList.add(new Tile(squares[4][7], 4, 7));
        tileList.add(new Tile(squares[5][7], 5, 7));
        tileList.add(new Tile(squares[5][6], 5, 6));
        tileList.add(new Tile(squares[5][5], 5, 5));
        tileList.add(new Tile(squares[6][5], 6, 5));
        tileList.add(new Tile(squares[7][5], 7, 5));
        tileList.add(new Tile(squares[7][6], 7, 6));
        tileList.add(new Tile(squares[7][7], 7, 7));
        tileList.add(new Tile(squares[7][8], 7, 8));
        tileList.add(new Tile(squares[7][9], 7, 9));


        // Set first tile black
        tileList.get(0).getPanel().setBackground(Color.BLACK);


        // cycle the rest of the colors
        int colorCounter = 0;
        for (int i = 1; i < tileList.size(); i++) {

            if (colorCounter >= 5) {
                colorCounter = 0;
            }

            tileList.get(i).getPanel().setBackground(colors.get(colorCounter));
            colorCounter++;
        }


        // Paint the remaining squares a gray background color
        //todo: Fix this
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
//                if ((!validX.contains(i)) && (!validY.contains(j))) {
//                    squares[i][j].setBackground(Color.BLUE);
//                    System.out.println("x"+i+",y"+j);
//
//                }
            }
        }


        // Place placeholder piece on first square
        for (Player p : playerList) {
            p.moveToTile(tileList.get(0));
        }

        _frame.setVisible(true);


    }

    private void create_board() {
        // Center the frame
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) (dimension.getWidth() / 2) - (WINDOW_WIDTH / 2);
        int y = (int) (dimension.getHeight() / 2) - (WINDOW_HEIGHT / 2);
        _frame.setLocation(x, y);

        _frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        _frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        JPanel MainPanel = new JPanel();

        _frame.add(MainPanel);

        GridLayout experimentLayout = new GridLayout(10, 10);
        MainPanel.setLayout(experimentLayout);


        int rowCounter = 0;
        int rows = 0;

        // Create all of the subpanels
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

            // Place the subpanel into the 2d array
            squares[rows][i % 10] = sub;

            MainPanel.add(sub);

            rowCounter++;
        }

        _frame.setVisible(true);
    }
}
