import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

public class GameboardStrategic extends GameBoard implements MouseListener {

    JButton boomerangButton;

    public GameboardStrategic(SaveState s) {
        super(s);

        StrategicInit();
    }

    public GameboardStrategic(int players) {
        super(players, "Standard");

        StrategicInit();
    }


    public void StrategicInit() {
        boomerangPanel = new JPanel();
        boomerangPanel.setBackground(Color.PINK);
        boomerangPanel.setMaximumSize(new Dimension(400, 50));
        boomerangPanel.setMinimumSize(new Dimension(400, 50));
        boomerangPanel.setLayout(new BoxLayout(boomerangPanel, BoxLayout.PAGE_AXIS));
        boomerangPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        TitledBorder boomerangTitle = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Boomerang Panel");
        boomerangTitle.setTitleJustification(TitledBorder.LEFT);
        boomerangPanel.setBorder(boomerangTitle);


        boomerangButton = new JButton("Use Boomerang (Remaining: " + playerList.get(currentTurn - 1).getNumberOfBoomerangs() + ")");
        boomerangButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (playerList.get(currentTurn - 1).getNumberOfBoomerangs() > 0) {
                    boolean valid = false;
                    String pName = "";
                    Player p = null;
                    while (!valid) {
                        pName = JOptionPane.showInputDialog("Enter player number to boomerang! ");

                        for (Player pn : playerList) {
                            if (pn.getName().equalsIgnoreCase(pName)) {
                                valid = true;
                                p = pn;
                            }
                        }
                    }
                    // Decrement the player's boomerangs
                    playerList.get(currentTurn - 1).decrementBoomerangs();
                    System.out.println("Player " + (currentTurn - 1) + " now has " + playerList.get(currentTurn - 1).getNumberOfBoomerangs() + " boomerangs");

                    // Draw a card and apply it to that player
                    handleCardDraw(p);

                }
            }
        });

        boomerangPanel.add(boomerangButton);

        cardPanel.add(boomerangPanel, 1);

        deck.addMouseListener(this);
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        handleCardDraw(null);
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


    public void handleCardDraw(Player p) {

        Boolean boom = false;
        String additional = "";
        if (p != null) {
            boom = true;
            additional = " for boomerang on " + p.getName() + "!";
        }


        boolean skipped_flag = false;
        //System.out.println(cardDeck.getSize());
        System.out.println("Card Drawn");
        WoSCard newCard = cardDeck.drawCard();
        if (newCard.getCardType().equals("red")) {
            card.setBackground(Color.RED);
        } else if (newCard.getCardType().equals("yellow")) {
            card.setBackground(Color.YELLOW);
        } else if (newCard.getCardType().equals("blue")) {
            card.setBackground(Color.BLUE);
        } else if (newCard.getCardType().equals("green")) {
            card.setBackground(Color.GREEN);
        } else if (newCard.getCardType().equals("orange")) {
            card.setBackground(Color.ORANGE);
        } else if (newCard.getCardType().equals("skipTurn")) {
            card.setBackground(Color.WHITE);
        } else if (newCard.getCardType().contains("goTo")) {
            card.setBackground(Color.WHITE);
        }

        if (newCard.getDoubleCard()) {
            doubleText.setVisible(true);
            skipTurnText.setVisible(false);
            goToLicoriceText.setVisible(false);
            goToLollipopText.setVisible(false);
            goToGummyBearText.setVisible(false);
            goToJellyBeansText.setVisible(false);
            goToIceCreamConeText.setVisible(false);
            JOptionPane.showMessageDialog(new JFrame(), playerList.get(currentTurn - 1).getName() + " drew a double " + newCard.getCardType() + additional);
        }
        // Single color or special card
        else {
            if (newCard.getCardType() == "skipTurn") {
                skipTurnText.setVisible(true);
                doubleText.setVisible(false);
                goToLicoriceText.setVisible(false);
                goToLollipopText.setVisible(false);
                goToGummyBearText.setVisible(false);
                goToJellyBeansText.setVisible(false);
                goToIceCreamConeText.setVisible(false);

                if (!boom) {
                    skipped_flag = true;
                    JOptionPane.showMessageDialog(new JFrame(), playerList.get(currentTurn - 1).getName() + "'s turn is skipped.");
                } else {
                    JOptionPane.showMessageDialog(new JFrame(), playerList.get(currentTurn - 1).getName() + " drew a skip turn for boomerang. Nothing happens!");
                }
            } else if (newCard.getCardType() == "goToLicorice") {
                goToLicoriceText.setVisible(true);
                goToLollipopText.setVisible(false);
                goToGummyBearText.setVisible(false);
                goToJellyBeansText.setVisible(false);
                goToIceCreamConeText.setVisible(false);
                skipTurnText.setVisible(false);
                doubleText.setVisible(false);
                skipped_flag = false;

                if (!boom)
                    JOptionPane.showMessageDialog(new JFrame(), playerList.get(currentTurn - 1).getName() + " goes to the Licorice tile.");
                else
                    JOptionPane.showMessageDialog(new JFrame(), p.getName() + " goes to the Licorice tile via boomerang!");
            } else if (newCard.getCardType() == "goToLollipop") {
                goToLicoriceText.setVisible(false);
                goToLollipopText.setVisible(true);
                goToGummyBearText.setVisible(false);
                goToJellyBeansText.setVisible(false);
                goToIceCreamConeText.setVisible(false);
                skipTurnText.setVisible(false);
                doubleText.setVisible(false);
                skipped_flag = false;

                if (!boom)
                    JOptionPane.showMessageDialog(new JFrame(), playerList.get(currentTurn - 1).getName() + " goes to the Lollipop tile.");
                else
                    JOptionPane.showMessageDialog(new JFrame(), p.getName() + " goes to the Lollipop tile via boomerang!");
            } else if (newCard.getCardType() == "goToGummyBear") {
                goToLicoriceText.setVisible(false);
                goToLollipopText.setVisible(false);
                goToGummyBearText.setVisible(true);
                goToJellyBeansText.setVisible(false);
                goToIceCreamConeText.setVisible(false);
                skipTurnText.setVisible(false);
                doubleText.setVisible(false);
                skipped_flag = false;

                if (!boom)
                    JOptionPane.showMessageDialog(new JFrame(), playerList.get(currentTurn - 1).getName() + " goes to the Gummy Bear tile.");
                else
                    JOptionPane.showMessageDialog(new JFrame(), p.getName() + " goes to the Gummy Bear tile via boomerang!");
            } else if (newCard.getCardType() == "goToJellyBeans") {
                goToLicoriceText.setVisible(false);
                goToLollipopText.setVisible(false);
                goToGummyBearText.setVisible(false);
                goToJellyBeansText.setVisible(true);
                goToIceCreamConeText.setVisible(false);
                skipTurnText.setVisible(false);
                doubleText.setVisible(false);
                skipped_flag = false;

                if (!boom)
                    JOptionPane.showMessageDialog(new JFrame(), playerList.get(currentTurn - 1).getName() + " goes to the Jelly Beans tile.");
                else
                    JOptionPane.showMessageDialog(new JFrame(), p.getName() + " goes to the Jelly Beans tile via boomerang!");
            } else if (newCard.getCardType() == "goToIceCreamCone") {
                goToLicoriceText.setVisible(false);
                goToLollipopText.setVisible(false);
                goToGummyBearText.setVisible(false);
                goToJellyBeansText.setVisible(false);
                goToIceCreamConeText.setVisible(true);
                skipTurnText.setVisible(false);
                doubleText.setVisible(false);
                skipped_flag = false;

                if (!boom)
                    JOptionPane.showMessageDialog(new JFrame(), playerList.get(currentTurn - 1).getName() + " goes to the Ice Cream Cone tile.");
                else
                    JOptionPane.showMessageDialog(new JFrame(), p.getName() + " goes to the Ice Cream Cone tile via boomerang!");
            }
            // Single color card
            else {
                goToLicoriceText.setVisible(false);
                goToLollipopText.setVisible(false);
                goToGummyBearText.setVisible(false);
                goToJellyBeansText.setVisible(false);
                goToIceCreamConeText.setVisible(false);
                skipTurnText.setVisible(false);
                doubleText.setVisible(false);
                skipped_flag = false;

                JOptionPane.showMessageDialog(new JFrame(), playerList.get(currentTurn - 1).getName() + " drew a single " + newCard.getCardType() + additional);
            }
        }
        if (!skipped_flag) {
            System.out.println("Moving player index " + (currentTurn - 1));

            if (p == null) {
                System.out.println("moving player forward");
                movePlayer(playerList.get(currentTurn - 1), newCard);
            } else {
                System.out.println("moving player backward");
                movePlayerReverse(p, newCard);
            }
        }
        System.out.println("Validating");
        refresh();

        // Cycle Turns
        currentTurn++;
        if (currentTurn > playerList.size()) {
            currentTurn = 1;
        }

        System.out.println("setting boomerang panel to show " + playerList.get(currentTurn - 1).getNumberOfBoomerangs());
        boomerangButton.setText("Use Boomerang (Remaining: " + playerList.get(currentTurn - 1).getNumberOfBoomerangs() + ")");
        JOptionPane.showMessageDialog(new JFrame(), playerList.get(currentTurn - 1).getName() + "'s Turn!");
        refresh();
    }


    public boolean movePlayerReverse(Player p, WoSCard card) {
        HashMap<String, Color> colorMap = new HashMap<>();
        colorMap.put("orange", Color.ORANGE);
        colorMap.put("green", Color.GREEN);
        colorMap.put("blue", Color.BLUE);
        colorMap.put("yellow", Color.YELLOW);
        colorMap.put("red", Color.RED);

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
        } else if (p.getCurrentTile().getIndex() > 6) {
            if (card.getDoubleCard()) {
                boolean skipped = false;
                for (int i = playerCurrentTile - 1; i > 0; i--) {
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
                for (int i = playerCurrentTile - 1; i > 0; i--) {
                    if (getTileList().get(i).getColor() == target) {
                        System.out.println("Moving Player to tile " + i);
                        p.moveToTile(this, getTileList().get(i));
                        return true;
                    }
                }
            }
        } else {
            if (card.getDoubleCard()) {
                p.moveToTile(this, getTileList().get(0));
            } else {
                for (int i = playerCurrentTile - 1; i > 0; i--) {
                    if (getTileList().get(i).getColor() == target) {
                        System.out.println("Moving Player to tile " + i);
                        p.moveToTile(this, getTileList().get(i));
                    }
                }

            }
        }


        System.out.println("unable to move the player");
        // We were not able to successfully move the player
        return false;
    }
}
