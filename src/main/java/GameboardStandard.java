import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;

public class GameboardStandard extends GameBoard implements MouseListener {

    public GameboardStandard(SaveState s) {
        super(s);
        deck.addMouseListener(this);
    }

    public GameboardStandard(int players) {
        super(players);
        deck.addMouseListener(this);
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        boolean skipped_flag = false;
        Player currentPlayer = playerList.get(currentTurn - 1);

        System.out.println("Card Drawn");
        WoSCard newCard = null;
        if (currentPlayer.getName().equals("Dad")) {
          newCard = cardDeck.riggedDraw(currentPlayer, this);
        }
        else {
          newCard = cardDeck.drawCard();
        }

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
            JOptionPane.showMessageDialog(new JFrame(), currentPlayer.getName() + " drew a double " + newCard.getCardType());
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
                skipped_flag = true;
                JOptionPane.showMessageDialog(new JFrame(), currentPlayer.getName() + "'s turn is skipped.");
            } else if (newCard.getCardType() == "goToLicorice") {
                goToLicoriceText.setVisible(true);
                goToLollipopText.setVisible(false);
                goToGummyBearText.setVisible(false);
                goToJellyBeansText.setVisible(false);
                goToIceCreamConeText.setVisible(false);
                skipTurnText.setVisible(false);
                doubleText.setVisible(false);
                skipped_flag = false;
                JOptionPane.showMessageDialog(new JFrame(), currentPlayer.getName() + " goes to the Licorice tile.");
            } else if (newCard.getCardType() == "goToLollipop") {
                goToLicoriceText.setVisible(false);
                goToLollipopText.setVisible(true);
                goToGummyBearText.setVisible(false);
                goToJellyBeansText.setVisible(false);
                goToIceCreamConeText.setVisible(false);
                skipTurnText.setVisible(false);
                doubleText.setVisible(false);
                skipped_flag = false;
                JOptionPane.showMessageDialog(new JFrame(), currentPlayer.getName() + " goes to the Lollipop tile.");
            } else if (newCard.getCardType() == "goToGummyBear") {
                goToLicoriceText.setVisible(false);
                goToLollipopText.setVisible(false);
                goToGummyBearText.setVisible(true);
                goToJellyBeansText.setVisible(false);
                goToIceCreamConeText.setVisible(false);
                skipTurnText.setVisible(false);
                doubleText.setVisible(false);
                skipped_flag = false;
                JOptionPane.showMessageDialog(new JFrame(), currentPlayer.getName() + " goes to the Gummy Bear tile.");
            } else if (newCard.getCardType() == "goToJellyBeans") {
                goToLicoriceText.setVisible(false);
                goToLollipopText.setVisible(false);
                goToGummyBearText.setVisible(false);
                goToJellyBeansText.setVisible(true);
                goToIceCreamConeText.setVisible(false);
                skipTurnText.setVisible(false);
                doubleText.setVisible(false);
                skipped_flag = false;
                JOptionPane.showMessageDialog(new JFrame(), currentPlayer.getName() + " goes to the Jelly Beans tile.");
            } else if (newCard.getCardType() == "goToIceCreamCone") {
                goToLicoriceText.setVisible(false);
                goToLollipopText.setVisible(false);
                goToGummyBearText.setVisible(false);
                goToJellyBeansText.setVisible(false);
                goToIceCreamConeText.setVisible(true);
                skipTurnText.setVisible(false);
                doubleText.setVisible(false);
                skipped_flag = false;
                JOptionPane.showMessageDialog(new JFrame(), currentPlayer.getName() + " goes to the Ice Cream Cone tile.");
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
                JOptionPane.showMessageDialog(new JFrame(), currentPlayer.getName() + " drew a single " + newCard.getCardType());
            }
        }
        if (!skipped_flag) {
            System.out.println("Moving player index " + (currentTurn - 1));
            movePlayer(currentPlayer, newCard);
        }
        System.out.println("Validating");
        refresh();

        // Cycle Turns
        currentTurn++;
        if (currentTurn > playerList.size()) {
            currentTurn = 1;
        }
        JOptionPane.showMessageDialog(new JFrame(), playerList.get(currentTurn) + "'s Turn!");
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
}
