import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;


public class GameBoard extends JPanel {

    private final int WINDOW_HEIGHT = 1000;
    private final int WINDOW_WIDTH = 1400;

    private final int TILES_X = 10;
    private final int TILES_Y = 10;

    private int currentTurn = 1;
    private int numberOfPlayers;

    private JFrame _frame = new JFrame("World of Sweets");

    private JPanel[][] tiles = new JPanel[TILES_X][TILES_Y];

    private ArrayList<Player> playerList = new ArrayList<>();
    private ArrayList<Tile> tileList;

    private WoSDeck cardDeck = new WoSDeck();

    // Constructor
    public GameBoard(int players) {
        numberOfPlayers = players;

        for (int i = 1; i < players + 1; i++) {
            String player_name = String.format("Player %s", String.valueOf(i));
            playerList.add(new Player(player_name, new Piece("placeholder_piece.png")));
        }
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


        tileList = generateTileList();

        // cycle the rest of the colors
        int colorCounter = 0;
        for (int i = 1; i < tileList.size() - 1; i++) {

            if (colorCounter >= 5) {
                colorCounter = 0;
            }

            tileList.get(i).getPanel().setBackground(colors.get(colorCounter));
            tileList.get(i).setColor(colors.get(colorCounter));
            colorCounter++;
        }


        // Place each player's token
        for (Player p : playerList) {
            p.moveToTile(this, tileList.get(0));
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
        MainPanel.add(subPanel, c);

        JPanel cardPanel = new JPanel();
        cardPanel.setBackground(Color.DARK_GRAY);
        cardPanel.setSize(400, 1000);
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 0.4;
        c.weighty = 1;
        MainPanel.add(cardPanel, c);


        int rowCounter = 0;
        int rows = 0;

        // Create all of the subpanels
        for (int i = 0; i < (TILES_X * TILES_Y); i++) {
            if (i == 79 || i == 10) {
                try {
                    BufferedImage image = ImageIO.read(new File(photo_input(i)));
                    JPanel sub = new JPanel() {
                        @Override
                        protected void paintComponent(Graphics g) {
                            super.paintComponent(g);
                            g.drawImage(image, 0, 0, 100, 70, null);
                        }
                    };

                    sub.setSize((WINDOW_WIDTH / TILES_X), (WINDOW_HEIGHT / TILES_Y));
                    sub.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mousePressed(MouseEvent e) {
                            System.out.println("Click");
                            refresh();
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

                } catch (Exception e) {
                    e.printStackTrace();
                }


            } else {

                JPanel sub = new JPanel();
//                sub.setBorder(BorderFactory.createLineBorder(Color.black));
//                sub.setBackground(Color.red);
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

        addDeck(cardPanel);
    }

    private void addDeck(JPanel cardPanel){
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.PAGE_AXIS));
        cardPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        //cardPanel.setMinimumSize(new Dimension(400, 1000));
        //cardPanel.setMinimumSize(new Dimension(400, 1000));

        JPanel deck = new JPanel();
        deck.setBackground(Color.WHITE);
        deck.setLayout(new GridBagLayout());
        deck.setMaximumSize(new Dimension (200, 100));
        deck.setMinimumSize(new Dimension (200, 100));

        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setLayout(new GridBagLayout());
        card.setMaximumSize(new Dimension (200, 100));
        card.setMinimumSize(new Dimension (200, 100));

        //JLabel deckLabel = new JLabel("Deck");
        //deckLabel.setFont(deckLabel.getFont().deriveFont(64f));
        //deck.add(deckLabel);

        JLabel doubleText = new JLabel("2x");
        doubleText.setFont(doubleText.getFont().deriveFont(24f));
        doubleText.setMaximumSize(new Dimension (200, 100));
        doubleText.setMinimumSize(new Dimension (200, 100));
        card.add(doubleText);
        doubleText.setVisible(false);

        JLabel skipTurnText = new JLabel("Skip Turn");
        skipTurnText.setFont(skipTurnText.getFont().deriveFont(24f));
        skipTurnText.setMaximumSize(new Dimension (200, 100));
        skipTurnText.setMinimumSize(new Dimension (200, 100));
        card.add(skipTurnText);
        skipTurnText.setVisible(false);

        JLabel goToMiddleText = new JLabel("Go To Middle");
        goToMiddleText.setFont(goToMiddleText.getFont().deriveFont(24f));
        goToMiddleText.setMaximumSize(new Dimension (200, 100));
        goToMiddleText.setMinimumSize(new Dimension (200, 100));
        card.add(goToMiddleText);
        goToMiddleText.setVisible(false);

        JPanel deckPanel = new JPanel();
        deckPanel.setMaximumSize(new Dimension(400, 400));
        deckPanel.setMinimumSize(new Dimension(400, 400));
        deckPanel.setBackground(Color.PINK);
        deckPanel.setLayout(new BoxLayout(deckPanel, BoxLayout.PAGE_AXIS));
        deckPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        TitledBorder deckTitle = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Deck");
        deckTitle.setTitleJustification(TitledBorder.LEFT);
        deckPanel.setBorder(deckTitle);
        deckPanel.add(Box.createRigidArea(new Dimension(0,50)));
        deckPanel.add(deck);
        deckPanel.add(Box.createRigidArea(new Dimension(0,50)));
        deckPanel.add(card);

        JPanel playerPanel = new JPanel();
        playerPanel.setBackground(Color.PINK);
        playerPanel.setMaximumSize(new Dimension(400, 600));
        playerPanel.setMinimumSize(new Dimension(400, 600));
        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.PAGE_AXIS));
        playerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        TitledBorder playerTitle = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Player Information");
        playerTitle.setTitleJustification(TitledBorder.LEFT);
        playerPanel.setBorder(playerTitle);

        JTextArea playerInfo = new JTextArea();
        playerPanel.add(playerInfo);
        playerInfo.setEditable(false);
        playerInfo.append("There are currently "+numberOfPlayers+" players in the game.\nThe discard pile is on the bottom and the deck is on top.\nClick on the deck to draw a card.");

        cardPanel.add(deckPanel);
        cardPanel.add(playerPanel);

        deck.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //System.out.println(cardDeck.getSize());
                System.out.println("Card Drawn");
                WoSCard newCard = cardDeck.drawCard();
                if(newCard.getCardType().equals("red")){
                  card.setBackground(Color.RED);
                }
                else if(newCard.getCardType().equals("yellow")){
                  card.setBackground(Color.YELLOW);
                }
                else if(newCard.getCardType().equals("blue")){
                  card.setBackground(Color.BLUE);
                }
                else if(newCard.getCardType().equals("green")){
                  card.setBackground(Color.GREEN);
                }
                else if(newCard.getCardType().equals("orange")){
                  card.setBackground(Color.ORANGE);
                }
                else if(newCard.getCardType().equals("skipTurn")){
                  card.setBackground(Color.WHITE);
                }
                else if(newCard.getCardType().equals("goToMiddle")){
                  card.setBackground(Color.WHITE);
                }

                if(newCard.getDoubleCard()){
                  doubleText.setVisible(true);
                  skipTurnText.setVisible(false);
                  goToMiddleText.setVisible(false);
                  JOptionPane.showMessageDialog(new JFrame(), "Player " + currentTurn + " drew a double " + newCard.getCardType());
                }
                else{
                  if(newCard.getCardType() == "skipTurn"){
                    skipTurnText.setVisible(true);
                    doubleText.setVisible(false);
                    goToMiddleText.setVisible(false);
                    JOptionPane.showMessageDialog(new JFrame(), "Player " + currentTurn + "'s turn is skipped.");
                    //TODO: set flag to continue turn loop
                  }
                  else if(newCard.getCardType() == "goToMiddle"){
                    goToMiddleText.setVisible(true);
                    skipTurnText.setVisible(false);
                    doubleText.setVisible(false);
                    JOptionPane.showMessageDialog(new JFrame(), "Player " + currentTurn + " goes to the middle square of the board.");
                    //TODO: set flag to move player to middle
                  }
                  else{
                    goToMiddleText.setVisible(false);
                    skipTurnText.setVisible(false);
                    doubleText.setVisible(false);
                    JOptionPane.showMessageDialog(new JFrame(), "Player " + currentTurn + " drew a single " + newCard.getCardType());                }
                  }

                System.out.println("Moving player index " + (currentTurn-1));
                movePlayer(playerList.get(currentTurn-1), newCard);
                System.out.println("Validating");
                refresh();

                // Cycle Turns
                currentTurn++;
                if (currentTurn > numberOfPlayers) {
                    currentTurn = 1;
                  }
                  JOptionPane.showMessageDialog(new JFrame(), "Player " + currentTurn + "'s Turn!");
                }
          });
       }


    private boolean movePlayer(Player p, WoSCard card) {
        HashMap<String, Color> colorMap = new HashMap<>();
        colorMap.put("red", Color.RED);
        colorMap.put("yellow", Color.YELLOW);
        colorMap.put("blue", Color.BLUE);
        colorMap.put("green", Color.GREEN);
        colorMap.put("orange", Color.ORANGE);

        int playerCurrentTile = p.getCurrentTile().getIndex();

        Color target = colorMap.get(card.getCardType().toLowerCase());

        if (p.getCurrentTile().getIndex() < tileList.size()-5) {
            if (card.getDoubleCard()) {
                boolean skipped = false;
                for (int i = playerCurrentTile+1; i < getTileList().size(); i++) {
                    if (getTileList().get(i).getColor() == target) {

                        if (skipped) {
                            System.out.println("Moving Player to tile " + i);
                            p.moveToTile(this, getTileList().get(i));
                            return true;
                        } else {
                            skipped = true;
                        }
                    }
                }
            } else {
                for (int i = playerCurrentTile+1; i < getTileList().size(); i++) {
                    if (getTileList().get(i).getColor() == target) {
                        System.out.println("Moving Player to tile " + i);
                        p.moveToTile(this, getTileList().get(i));
                        return true;
                    }
                }
            }
        } else {
            if (card.getDoubleCard()) {
                showWinDialog(p);
            } else {
                boolean success = false;
                for (int i = playerCurrentTile+1; i < getTileList().size(); i++) {
                    if (getTileList().get(i).getColor() == target) {
                        System.out.println("Moving Player to tile " + i);
                        p.moveToTile(this, getTileList().get(i));
                        success = true;
                    }
                }

                if (!success) {
                    showWinDialog(p);
                }
            }
        }



        System.out.println("unable to move the player");
        // We were not able to successfully move the player
        return false;
    }

    private ArrayList<Tile> generateTileList() {
        System.out.println("Generating Tile List");
        ArrayList<Tile> t = new ArrayList<>();
        t.add(new Tile(tiles[1][0], 1, 0, 0));
        t.add(new Tile(tiles[1][1], 1, 1, 1));
        t.add(new Tile(tiles[2][1], 2, 1, 2));
        t.add(new Tile(tiles[3][1], 3, 1, 3));
        t.add(new Tile(tiles[4][1], 4, 1, 4));
        t.add(new Tile(tiles[5][1], 5, 1, 5));
        t.add(new Tile(tiles[6][1], 6, 1, 6));
        t.add(new Tile(tiles[7][1], 7, 1, 7));
        t.add(new Tile(tiles[8][1], 8, 1, 8));
        t.add(new Tile(tiles[9][1], 9, 1, 9));
        t.add(new Tile(tiles[9][2], 9, 2, 10));
        t.add(new Tile(tiles[9][3], 9, 3, 11));
        t.add(new Tile(tiles[8][3], 8, 3, 12));
        t.add(new Tile(tiles[7][3], 7, 3, 13));
        t.add(new Tile(tiles[6][3], 6, 3, 14));
        t.add(new Tile(tiles[5][3], 5, 3, 15));
        t.add(new Tile(tiles[4][3], 4, 3, 16));
        t.add(new Tile(tiles[3][3], 3, 3, 17));
        t.add(new Tile(tiles[2][3], 2, 3, 18));
        t.add(new Tile(tiles[2][4], 2, 4, 19));
        t.add(new Tile(tiles[2][5], 2, 5, 20));
        t.add(new Tile(tiles[2][6], 2, 6, 21));
        t.add(new Tile(tiles[2][7], 2, 7, 22));
        t.add(new Tile(tiles[3][7], 3, 7, 23));
        t.add(new Tile(tiles[4][7], 4, 7, 24));
        t.add(new Tile(tiles[5][7], 5, 7, 25));
        t.add(new Tile(tiles[5][6], 5, 6, 26));
        t.add(new Tile(tiles[5][5], 5, 5, 27));
        t.add(new Tile(tiles[6][5], 6, 5, 28));
        t.add(new Tile(tiles[7][5], 7, 5, 29));
        t.add(new Tile(tiles[7][6], 7, 6, 30));
        t.add(new Tile(tiles[7][7], 7, 7, 31));
        t.add(new Tile(tiles[7][8], 7, 8, 32));
        t.add(new Tile(tiles[7][9], 7, 9, 33));

        return t;
    }

    public void showWinDialog(Player p) {
        JOptionPane.showMessageDialog(new JFrame(), "Player " + p.getName() + " wins!");
        System.exit(0);
    }

    public void refresh() {
        _frame.validate();
        _frame.repaint();
    }

    public String photo_input(int i) {
        if (i == 10) {
            return "src/assets/Home_tile.png";
        } else
            return "src/assets/house-th.png";
    }

    public void test_quit() {
        System.exit(0);
    }


    public ArrayList<Player> getPlayerList() {
        return playerList;
    }

    public ArrayList<Tile> getTileList() {
        return tileList;
    }
}
