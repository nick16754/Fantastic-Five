import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;



public class GameBoard extends JPanel{

    private static final int WINDOW_HEIGHT = 1000;
    private static final int WINDOW_WIDTH = 1400;

    private static final int TILES_X = 10;
    private static final int TILES_Y = 10;

    static JFrame _frame = new JFrame("World of Sweets");

    static JPanel[][] tiles = new JPanel[TILES_X][TILES_Y];
    static LinkedList<Tile> tileList = new LinkedList<>();
    static ArrayList<Player> playerList = new ArrayList<>();


    // Constructor
    public GameBoard(int players) {
        for (int i = 1; i < players+1; i++)
        {
          String player_name = String.format("Player %s", String.valueOf(i));
          playerList.add(new Player(player_name, new Piece("placeholder_piece.png")));
        }
        create_board();
        initialize();
    }


    private static void initialize() {
        // Initialize color pattern for game board
        ArrayList<Color> colors = new ArrayList<>();
        colors.add(Color.RED);
        colors.add(Color.YELLOW);
        colors.add(Color.BLUE);
        colors.add(Color.GREEN);
        colors.add(Color.ORANGE);


        // Build the path of the game board
        //todo: clean this up
        tileList.add(new Tile(tiles[1][0], 1, 0));
        tileList.add(new Tile(tiles[1][1], 1, 1));
        tileList.add(new Tile(tiles[2][1], 2, 1));
        tileList.add(new Tile(tiles[3][1], 3, 1));
        tileList.add(new Tile(tiles[4][1], 4, 1));
        tileList.add(new Tile(tiles[5][1], 5, 1));
        tileList.add(new Tile(tiles[6][1], 6, 1));
        tileList.add(new Tile(tiles[7][1], 7, 1));
        tileList.add(new Tile(tiles[8][1], 8, 1));
        tileList.add(new Tile(tiles[9][1], 9, 1));
        tileList.add(new Tile(tiles[9][2], 9, 2));
        tileList.add(new Tile(tiles[9][3], 9, 3));
        tileList.add(new Tile(tiles[8][3], 8, 3));
        tileList.add(new Tile(tiles[7][3], 7, 3));
        tileList.add(new Tile(tiles[6][3], 6, 3));
        tileList.add(new Tile(tiles[5][3], 5, 3));
        tileList.add(new Tile(tiles[4][3], 4, 3));
        tileList.add(new Tile(tiles[3][3], 3, 3));
        tileList.add(new Tile(tiles[2][3], 2, 3));
        tileList.add(new Tile(tiles[2][4], 2, 4));
        tileList.add(new Tile(tiles[2][5], 2, 5));
        tileList.add(new Tile(tiles[2][6], 2, 6));
        tileList.add(new Tile(tiles[2][7], 2, 7));
        tileList.add(new Tile(tiles[3][7], 3, 7));
        tileList.add(new Tile(tiles[4][7], 4, 7));
        tileList.add(new Tile(tiles[5][7], 5, 7));
        tileList.add(new Tile(tiles[5][6], 5, 6));
        tileList.add(new Tile(tiles[5][5], 5, 5));
        tileList.add(new Tile(tiles[6][5], 6, 5));
        tileList.add(new Tile(tiles[7][5], 7, 5));
        tileList.add(new Tile(tiles[7][6], 7, 6));
        tileList.add(new Tile(tiles[7][7], 7, 7));
        tileList.add(new Tile(tiles[7][8], 7, 8));
        tileList.add(new Tile(tiles[7][9], 7, 9));
        try {
            BufferedImage image = ImageIO.read(new File("src/assets/End_tile.jpg"));
            JPanel pane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, null);
            }
        };


        } catch(Exception e) {
            e.printStackTrace();
        }
        //tileList.get(i).getPanel().setBackground();
        // cycle the rest of the colors
        int colorCounter = 0;
        for (int i = 1; i < tileList.size() - 1; i++) {

            if (colorCounter >= 5) {
                colorCounter = 0;
            }

            tileList.get(i).getPanel().setBackground(colors.get(colorCounter));
            colorCounter++;
        }





        // Paint the remaining tiles a gray background color
        //todo: Fix this
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
//                if ((!validX.contains(i)) && (!validY.contains(j))) {
//                    tiles[i][j].setBackground(Color.BLUE);
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

    private static void create_board() {
        // Center the frame
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) (dimension.getWidth() / 2) - (WINDOW_WIDTH / 2);
        int y = (int) (dimension.getHeight() / 2) - (WINDOW_HEIGHT / 2);
        _frame.setLocation(x, y);

        _frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        _frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        JPanel MainPanel = new JPanel();

        _frame.add(MainPanel);

        GridLayout experimentLayout = new GridLayout(TILES_X, TILES_Y);
        MainPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;

        JPanel subPanel = new JPanel();
        subPanel.setLayout(experimentLayout);
        subPanel.setSize(1000, 1000);
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.6;
        c.weighty = 1;
        MainPanel.add(subPanel,c);

        JPanel cardPanel = new JPanel();
        cardPanel.setBackground(Color.DARK_GRAY);
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 0.4;
        c.weighty = 1;
        MainPanel.add(cardPanel,c);


        int rowCounter = 0;
        int rows = 0;

        // Create all of the subpanels
        for (int i = 0; i < (TILES_X*TILES_Y); i++) {
            if (i ==79 || i == 10)
            {
              try {
                  BufferedImage image = ImageIO.read(new File(photo_input(i)));
                  JPanel sub = new JPanel() {
                  @Override
                  protected void paintComponent(Graphics g) {
                      super.paintComponent(g);
                      g.drawImage(image, 0, 0, null);
                    }
              };

              sub.setSize((WINDOW_WIDTH / TILES_X), (WINDOW_HEIGHT / TILES_Y));
              sub.addMouseListener(new MouseAdapter() {
                  @Override
                  public void mousePressed(MouseEvent e) {
                      System.out.println("Click");
                  }
              });


              if (rowCounter >= TILES_X) {
                  rowCounter = 0;
                  rows++;
              }

              // Place the subpanel into the 2d array
              tiles[rows][i % TILES_X] = sub;

              subPanel.add(sub);

              rowCounter++;

              } catch(Exception e) {
                  e.printStackTrace();
              }


            }
            else{

            JPanel sub = new JPanel();
  //          sub.setBorder(BorderFactory.createLineBorder(Color.black));
//            sub.setBackground(Color.red);
            sub.setSize((WINDOW_WIDTH / TILES_X), (WINDOW_HEIGHT / TILES_Y));
            sub.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    System.out.println("Click");
                }
            });


            if (rowCounter >= TILES_X) {
                rowCounter = 0;
                rows++;
            }

            // Place the subpanel into the 2d array
            tiles[rows][i % TILES_X] = sub;

            subPanel.add(sub);

            rowCounter++;
          }
        }

        _frame.setVisible(true);
    }

    public static void refresh() {
        _frame.validate();
    }
    public static String photo_input(int i){
      if (i == 10)
      {
        return "src/assets/star-for-walker-th.png";
      }
      else
        return "src/assets/house-th.png";
    }
    public void test_quit()
    {
      System.exit(0);
    }
}
