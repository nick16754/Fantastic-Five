import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;


public class GameBoard extends JPanel {

    public final int WINDOW_HEIGHT = 1000;
    public final int WINDOW_WIDTH = 1400;

    public final int TILES_X = 10;
    public final int TILES_Y = 10;

    public int currentTurn = 1;
    public int numberOfPlayers;
    public int finishedPlayers = 0;

    public JFrame _frame = new JFrame("World of Sweets");

    public JPanel[][] tiles = new JPanel[TILES_X][TILES_Y];

    public ArrayList<Player> playerList = new ArrayList<>();
    public ArrayList<Tile> tileList;

    public WoSDeck cardDeck = new WoSDeck();

    public JLabel timer;

    public JPanel cardPanel;

    public long playtime = 0;

    // Default Constructor
    public GameBoard() {

    }

    // Constructor
    public GameBoard(SaveState s) {
        numberOfPlayers = s.getPlayerList().size();

        playerList = s.getPlayerList();
        cardDeck = s.getCardDeck();
        currentTurn = s.getCurrentTurn();

        create_board();
        initialize();

        Runnable gt = new GameTimer(s.getTime(), this);
        Thread t = new Thread(gt);
        t.start();

        int i = 1;
        for (Player p : playerList) {
            p.setPiece(new Piece("piece" + i + ".png"));
            p.moveToTile(this, tileList.get(p.getCurrentTileIndex()));
            i++;
        }

    }

    public GameBoard(int players) {
        this.numberOfPlayers = players;
        populatePlayerList(players);
        create_board();
        initialize();

        Runnable gt = new GameTimer(0, this);
        Thread t = new Thread(gt);
        t.start();

        // Place each player's token
        for (Player p : playerList) {
            p.moveToTile(this, tileList.get(0));
        }
    }

    // Prompts user for player name and AI status for each player and populates playerList with new Player if valid
    private void populatePlayerList(int players) {
        for (int i = 1; i < players + 1; i++) {
            boolean valid_player = false;
            while (!valid_player) {
              // setup input fields for current player
              JTextArea textArea = new JTextArea(5, 100);
              textArea.setText("");
              JTextField name_input = new JTextField();
              final JCheckBox ai_checkbox = new JCheckBox();
              Object[] input_fields = {"Please enter player "+i+"'s name:", name_input,
                      "Check if player is AI", ai_checkbox};
              boolean is_ai = false;
              int option = JOptionPane.showConfirmDialog(this, input_fields, "Add Player", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

              if (option == JOptionPane.OK_OPTION) {
                    String player_name = name_input.getText();
                    textArea.setText(player_name);
                    try {
                        if (player_name.length() != 0 && player_name != null) {
                            this.playerList.add(new Player(player_name, new Piece("piece"+i+".png"), ai_checkbox.isSelected()));
                            valid_player = true;
                        }
                        else {
                            valid_player = false;
                            throw new Exception();
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(new JFrame(),
                                "Player name cannot be blank.",
                                "Invalid input",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
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
        System.out.println(tileList.size());
        for (int i = 1; i < tileList.size() - 1; i++) {

            if (colorCounter >= 5) {
                colorCounter = 0;
            }
            if (i == 6 || i == 24 || i == 13 || i == 20 || i == 32) {
                tileList.get(i).setColor(Color.WHITE);
                System.out.println("I am here");
            } else {
                tileList.get(i).getPanel().setBackground(colors.get(colorCounter));
                tileList.get(i).setColor(colors.get(colorCounter));
                colorCounter++;
            }
        }

        _frame.setVisible(true);
    }

    public void create_board() {
        // Center the frame
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) (dimension.getWidth() / 2) - (WINDOW_WIDTH / 2);
        int y = (int) (dimension.getHeight() / 2) - (WINDOW_HEIGHT / 2);
        _frame.setLocation(x, y);

        _frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        _frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        JPanel MainPanel = new JPanel();

        _frame.add(MainPanel);

        timer = new JLabel();

        GridLayout experimentLayout = new GridLayout(TILES_X, TILES_Y);
        MainPanel.setLayout(new BoxLayout(MainPanel, BoxLayout.X_AXIS));
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
            if (i == 79 || i == 0 || i == 51 || i == 17 || i == 83 || i == 13 || i == 75) {
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

        addDeck(cardPanel);
        _frame.setVisible(true);
    }

    public JPanel deck;
    public JPanel card;
    public JLabel doubleText;
    public JLabel skipTurnText;
    public JLabel goToIceCreamConeText;
    public JLabel goToLollipopText;
    public JLabel goToJellyBeansText;
    public JLabel goToLicoriceText;
    public JLabel goToGummyBearText;
    public JPanel deckPanel;
    public JPanel playerPanel;
    public JPanel savePanel;

    public void addDeck(JPanel cardPanel) {
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.PAGE_AXIS));
        cardPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        //cardPanel.setMinimumSize(new Dimension(400, 1000));
        //cardPanel.setMinimumSize(new Dimension(400, 1000));

        deck = new JPanel();
        deck.setBackground(Color.WHITE);
        deck.setLayout(new GridBagLayout());
        deck.setMaximumSize(new Dimension(200, 100));
        deck.setMinimumSize(new Dimension(200, 100));

        card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setLayout(new GridBagLayout());
        card.setMaximumSize(new Dimension(200, 100));
        card.setMinimumSize(new Dimension(200, 100));

        doubleText = new JLabel("2x");
        doubleText.setFont(doubleText.getFont().deriveFont(24f));
        doubleText.setMaximumSize(new Dimension(200, 100));
        doubleText.setMinimumSize(new Dimension(200, 100));
        card.add(doubleText);
        doubleText.setVisible(false);

        skipTurnText = new JLabel("Skip Turn");
        skipTurnText.setFont(skipTurnText.getFont().deriveFont(24f));
        skipTurnText.setMaximumSize(new Dimension(200, 100));
        skipTurnText.setMinimumSize(new Dimension(200, 100));
        card.add(skipTurnText);
        skipTurnText.setVisible(false);

        goToIceCreamConeText = new JLabel("Go To Ice Cream Cone");
        goToIceCreamConeText.setFont(goToIceCreamConeText.getFont().deriveFont(24f));
        goToIceCreamConeText.setMaximumSize(new Dimension(200, 100));
        goToIceCreamConeText.setMinimumSize(new Dimension(200, 100));
        card.add(goToIceCreamConeText);
        goToIceCreamConeText.setVisible(false);

        goToLollipopText = new JLabel("Go To Lollipop");
        goToLollipopText.setFont(goToLollipopText.getFont().deriveFont(24f));
        goToLollipopText.setMaximumSize(new Dimension(200, 100));
        goToLollipopText.setMinimumSize(new Dimension(200, 100));
        card.add(goToLollipopText);
        goToLollipopText.setVisible(false);

        goToJellyBeansText = new JLabel("Go To Jellybeans");
        goToJellyBeansText.setFont(goToJellyBeansText.getFont().deriveFont(24f));
        goToJellyBeansText.setMaximumSize(new Dimension(200, 100));
        goToJellyBeansText.setMinimumSize(new Dimension(200, 100));
        card.add(goToJellyBeansText);
        goToJellyBeansText.setVisible(false);

        goToLicoriceText = new JLabel("Go To Licorice");
        goToLicoriceText.setFont(goToLicoriceText.getFont().deriveFont(24f));
        goToLicoriceText.setMaximumSize(new Dimension(200, 100));
        goToLicoriceText.setMinimumSize(new Dimension(200, 100));
        card.add(goToLicoriceText);
        goToLicoriceText.setVisible(false);

        goToGummyBearText = new JLabel("Go To Gummy Bear");
        goToGummyBearText.setFont(goToGummyBearText.getFont().deriveFont(24f));
        goToGummyBearText.setMaximumSize(new Dimension(200, 100));
        goToGummyBearText.setMinimumSize(new Dimension(200, 100));
        card.add(goToGummyBearText);
        goToGummyBearText.setVisible(false);

        deckPanel = new JPanel();
        deckPanel.setMaximumSize(new Dimension(400, 400));
        deckPanel.setMinimumSize(new Dimension(400, 400));
        deckPanel.setBackground(Color.PINK);
        deckPanel.setLayout(new BoxLayout(deckPanel, BoxLayout.PAGE_AXIS));
        deckPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        TitledBorder deckTitle = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Deck");
        deckTitle.setTitleJustification(TitledBorder.LEFT);
        deckPanel.setBorder(deckTitle);
        deckPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        deckPanel.add(deck);
        deckPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        deckPanel.add(card);

        playerPanel = new JPanel();
        playerPanel.setBackground(Color.PINK);
        playerPanel.setMaximumSize(new Dimension(400, 550));
        playerPanel.setMinimumSize(new Dimension(400, 550));
        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.PAGE_AXIS));
        playerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        TitledBorder playerTitle = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Player Information");
        playerTitle.setTitleJustification(TitledBorder.LEFT);
        playerPanel.setBorder(playerTitle);


        savePanel = new JPanel();
        savePanel.setBackground(Color.PINK);
        savePanel.setMaximumSize(new Dimension(400, 50));
        savePanel.setMinimumSize(new Dimension(400, 50));
        savePanel.setLayout(new BoxLayout(savePanel, BoxLayout.PAGE_AXIS));
        savePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton saveButton = new JButton("Save Game");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean valid = false;
                String saveName = "";
                while (!valid) {
                    saveName = JOptionPane.showInputDialog("Enter save name: ");

                    if ((saveName.length() > 0) && (!saveName.contains("."))) {
                        valid = true;
                    }
                }

                saveGame(saveName);
            }
        });
        savePanel.add(saveButton);

        timer.setAlignmentX(Component.RIGHT_ALIGNMENT);
        playerPanel.add(timer);

        TitledBorder saveTitle = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Save Panel");
        saveTitle.setTitleJustification(TitledBorder.LEFT);
        savePanel.setBorder(saveTitle);

        JPanel timerPanel = new JPanel();
        timerPanel.setBackground(Color.PINK);
        timerPanel.setMaximumSize(new Dimension(400, 50));
        timerPanel.setMinimumSize(new Dimension(400, 50));
        timerPanel.setLayout(new BoxLayout(timerPanel, BoxLayout.PAGE_AXIS));
        timerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        timerPanel.add(timer);

        TitledBorder timerTitle = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Timer Panel");
        timerTitle.setTitleJustification(TitledBorder.LEFT);
        timerPanel.setBorder(timerTitle);

        JTextArea playerInfo = new JTextArea();
        playerPanel.add(playerInfo);
        playerInfo.setEditable(false);
        playerInfo.append("There are currently " + playerList.size() + " players in the game.\nThe discard pile is on the bottom and the deck is on top.\nClick on the deck to draw a card.");

        cardPanel.add(deckPanel);
        cardPanel.add(playerPanel);
        cardPanel.add(savePanel);
        cardPanel.add(timerPanel);
    }


    public boolean movePlayer(Player p, WoSCard card) {
        HashMap<String, Color> colorMap = new HashMap<>();
        colorMap.put("red", Color.RED);
        colorMap.put("yellow", Color.YELLOW);
        colorMap.put("blue", Color.BLUE);
        colorMap.put("green", Color.GREEN);
        colorMap.put("orange", Color.ORANGE);

        int playerCurrentTile = p.getCurrentTile().getIndex();

        Color target = colorMap.get(card.getCardType().toLowerCase());


        if (card.getCardType() == "goToIceCreamCone") {
            p.moveToTile(this, tileList.get(6));
        } else if (card.getCardType() == "goToLollipop") {
            p.moveToTile(this, tileList.get(13));
        } else if (card.getCardType() == "goToJellyBeans") {
            p.moveToTile(this, tileList.get(20));
        } else if (card.getCardType() == "goToGummyBear") {
            p.moveToTile(this, tileList.get(24));
        } else if (card.getCardType() == "goToLicorice") {
            p.moveToTile(this, tileList.get(32));
        } else if (p.getCurrentTile().getIndex() < tileList.size() - 6) {
            if (card.getDoubleCard()) {
                boolean skipped = false;
                for (int i = playerCurrentTile + 1; i < getTileList().size(); i++) {
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
                if (skipped) {
                    showWinDialog(p);
                }
            } else {
                for (int i = playerCurrentTile + 1; i < getTileList().size(); i++) {
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
                for (int i = playerCurrentTile + 1; i < getTileList().size(); i++) {
                    if (getTileList().get(i).getColor() == target) {
                        System.out.println("Moving Player to tile " + i);
                        p.moveToTile(this, getTileList().get(i));
                        success = true;
                    }
                }

                if (!success) {
                    showWinDialog(p);
                    System.out.println();
                }
            }
        }


        System.out.println("unable to move the player");
        // We were not able to successfully move the player
        return false;
    }

    public ArrayList<Tile> generateTileList() {
        System.out.println("Generating Tile List");
        ArrayList<Tile> t = new ArrayList<>();
        t.add(new Tile(tiles[0][0], 0, 0, 0));
        t.add(new Tile(tiles[0][1], 0, 1, 1));
        t.add(new Tile(tiles[1][1], 1, 1, 2));
        t.add(new Tile(tiles[2][1], 2, 1, 3));
        t.add(new Tile(tiles[3][1], 3, 1, 4));
        t.add(new Tile(tiles[4][1], 4, 1, 5));
        t.add(new Tile(tiles[5][1], 5, 1, 6));
        t.add(new Tile(tiles[6][1], 6, 1, 7));
        t.add(new Tile(tiles[7][1], 7, 1, 8));
        t.add(new Tile(tiles[8][1], 8, 1, 9));
        t.add(new Tile(tiles[9][1], 9, 1, 10));
        t.add(new Tile(tiles[9][2], 9, 2, 11));
        t.add(new Tile(tiles[9][3], 9, 3, 12));
        t.add(new Tile(tiles[8][3], 8, 3, 13));
        t.add(new Tile(tiles[7][3], 7, 3, 14));
        t.add(new Tile(tiles[6][3], 6, 3, 15));
        t.add(new Tile(tiles[5][3], 5, 3, 16));
        t.add(new Tile(tiles[4][3], 4, 3, 17));
        t.add(new Tile(tiles[3][3], 3, 3, 18));
        t.add(new Tile(tiles[2][3], 2, 3, 19));
        t.add(new Tile(tiles[1][3], 1, 3, 20));
        t.add(new Tile(tiles[1][4], 1, 4, 21));
        t.add(new Tile(tiles[1][5], 1, 5, 22));
        t.add(new Tile(tiles[1][6], 1, 6, 23));
        t.add(new Tile(tiles[1][7], 1, 7, 24));
        t.add(new Tile(tiles[2][7], 2, 3, 25));
        t.add(new Tile(tiles[3][7], 3, 7, 26));
        t.add(new Tile(tiles[4][7], 4, 7, 27));
        t.add(new Tile(tiles[5][7], 5, 7, 28));
        t.add(new Tile(tiles[5][6], 5, 6, 29));
        t.add(new Tile(tiles[5][5], 5, 5, 30));
        t.add(new Tile(tiles[6][5], 6, 5, 31));
        t.add(new Tile(tiles[7][5], 7, 5, 32));
        t.add(new Tile(tiles[7][6], 7, 6, 33));
        t.add(new Tile(tiles[7][7], 7, 7, 34));
        t.add(new Tile(tiles[7][8], 7, 8, 35));
        t.add(new Tile(tiles[6][8], 6, 8, 36));
        t.add(new Tile(tiles[6][9], 6, 9, 37));
        t.add(new Tile(tiles[7][9], 7, 9, 38));

        return t;
    }

    public void showWinDialog(Player p) {
        playerList.remove(p);
        if (numberOfPlayers - playerList.size() == 1) {
            JOptionPane.showMessageDialog(new JFrame(), p.getName() + " wins!");
        } else if (numberOfPlayers - playerList.size() == 2) {
            JOptionPane.showMessageDialog(new JFrame(), p.getName() + " finished in second place!");
        } else {
            JOptionPane.showMessageDialog(new JFrame(), p.getName() + " finished in third place!");
        }
        p.moveToTile(this, tileList.get(tileList.size() - 1));

        String[] options = {"Yes", "No"};
        int yesOrNo = 0;
        if (playerList.size() > 1) {
            yesOrNo = JOptionPane.showOptionDialog(new JFrame(), "Would you like to keep playing?", "Keep Going?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        }

        if (yesOrNo == 1 || playerList.size() == 1) {
            System.exit(0);
        }
    }

    public void refresh() {
        _frame.validate();
        _frame.repaint();
    }

    public String photo_input(int i) {
        if (i == 0) {
            return "src/assets/Home_tile.png";
        } else if (i == 51) {
            return "src/assets/56595-ice-cream-cone-inflatable__31827.1492709410.jpg";
        } else if (i == 17) {
            return "src/assets/Gummy bear.jpg";

        } else if (i == 83) {
            return "src/assets/halloween_magic_lollipop.png";

        } else if (i == 13) {
            return "src/assets/Jelly_Beans.png";

        } else if (i == 75) {
            return "src/assets/twizzlers-100x100.jpg";

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

    public void updateTimer(long ms) {
        int minutes = 0;
        int seconds = 0;

        playtime = ms;

        while (ms > 60000) {
            minutes++;
            ms -= 60000;
        }
        while (ms > 1000) {
            seconds++;
            ms -= 1000;
        }

        timer.setText("Play Time: " + minutes + " minutes, " + seconds + " seconds.");
    }


    public void saveGame(String saveName) {

        try {
            Writer writer = new FileWriter(saveName + ".json");

            SaveState s = new SaveState(playerList, cardDeck, currentTurn, playtime);
            Gson g = new GsonBuilder().setExclusionStrategies(new SaveExclusionStrategy()).create();
            g.toJson(s, writer);

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
