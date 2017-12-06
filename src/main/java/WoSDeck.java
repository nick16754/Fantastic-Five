import java.util.ArrayList;
import java.util.Random;
import java.awt.Color;

public class WoSDeck{

  private ArrayList<WoSCard> deck = new ArrayList<WoSCard>(); //deck class holding all the cards

  Random draw = new Random();

  public WoSDeck(){
    fillDeck();
  }

  public int getSize(){
    return deck.size();
  }

  public void fillDeck(){
    // For loop for adding colored cards (singles and doubles) to deck
    for (int i = 0; i < 5; i++){
      String color = "";
      if(i == 0){
        color = "red";
      }
      else if (i == 1){
        color = "yellow";
      }
      else if (i == 2){
        color = "blue";
      }
      else if (i == 3){
        color = "green";
      }
      else{
        color = "orange";
      }

      for (int j = 0; j < 10; j++){
        WoSCard newCard = new WoSCard(false, color);
        deck.add(newCard);
      }
      for (int k = 0; k < 2; k++){
        WoSCard newCard = new WoSCard(true, color);
        deck.add(newCard);
      }
    }
    // For loop for adding skipTurn cards to deck
    for (int s = 0; s < 5; s++){
      WoSCard newCard = new WoSCard(false, "skipTurn");
      deck.add(newCard);
    }
    // Add the five "Go To [X]" cards to the deck
    deck.add(new WoSCard(false, "goToLollipop"));
    deck.add(new WoSCard(false, "goToLicorice"));
    deck.add(new WoSCard(false, "goToGummyBear"));
    deck.add(new WoSCard(false, "goToIceCreamCone"));
    deck.add(new WoSCard(false, "goToJellyBeans"));
  }

  public WoSCard drawCard(){
    WoSCard nextDraw = null;
    if(deck.size() == 0){
      fillDeck();
    }
    if(deck.size() > 0){
      int next = draw.nextInt(deck.size());
      nextDraw = deck.get(next);
      deck.remove(next);
    }
    return nextDraw;
  }

  public WoSCard riggedDraw(Player dad, GameBoard g) {
    WoSCard worstCard = null;
    int position = dad.getCurrentTileIndex();

    if(deck.size() == 0){
      fillDeck();
      if (position <=6) {
        return deck.remove(60);
      }
      else if (position > 6) {
        return deck.remove(65);
      }
    }

    if (position > 6) {
      //search for goToIceCreamCone
      for (int i = 0; i < deck.size(); i++){
        worstCard = deck.get(i);
        if(worstCard.getCardType().equals("goToIceCreamCone"))
        {
          return deck.remove(i);
        }
      }
    }
    if (position > 13) {
      //search for goToLollipop
      for (int i = 0; i < deck.size(); i++){
        worstCard = deck.get(i);
        if(worstCard.getCardType().equals("goToLollipop"))
        {
          return deck.remove(i);
        }
      }
    }
    if (position > 20) {
      //search for goToJellyBeans
      for (int i = 0; i < deck.size(); i++){
        worstCard = deck.get(i);
        if(worstCard.getCardType().equals("goToJellyBeans"))
        {
          return deck.remove(i);
        }
      }
    }
    if (position > 24) {
      //search for goToGummyBear
      for (int i = 0; i < deck.size(); i++){
        worstCard = deck.get(i);
        if(worstCard.getCardType().equals("goToGummyBear"))
        {
          return deck.remove(i);
        }
      }
    }
    if (position > 32) {
      //search for goToLicorice
      for (int i = 0; i < deck.size(); i++){
        worstCard = deck.get(i);
        if(worstCard.getCardType().equals("goToLicorice"))
        {
          return deck.remove(i);
        }
      }
    }

    //moving backwards impossible so search for a skip card
    for (int i = 0; i < deck.size(); i++){
      worstCard = deck.get(i);
      if(worstCard.getCardType().equals("skipTurn"))
      {
        return deck.remove(i);
      }
    }

    boolean foundRed = false;
    boolean foundYellow = false;
    boolean foundBlue = false;
    boolean foundGreen = false;
    boolean foundOrange = false;
    //not moving closer to the finish impossible, so search for the next spaces' color
    for (int j = 1; j < 12; j++) {
      Tile nextTile = g.tileList.get(position+j);
      if(nextTile.getColor().equals(Color.WHITE)) {
        if(nextTile.getIndex() == 6) {
          for (int i = 0; i < deck.size(); i++){
            worstCard = deck.get(i);
            if(worstCard.getCardType().equals("goToIceCreamCone"))
            {
              return deck.remove(i);
            }
          }
        }
        else if (nextTile.getIndex() == 13) {
          for (int i = 0; i < deck.size(); i++){
            worstCard = deck.get(i);
            if(worstCard.getCardType().equals("goToLollipop"))
            {
              return deck.remove(i);
            }
          }
        }
        else if (nextTile.getIndex() == 20) {
          for (int i = 0; i < deck.size(); i++){
            worstCard = deck.get(i);
            if(worstCard.getCardType().equals("goToJellyBeans"))
            {
              return deck.remove(i);
            }
          }
        }
        else if (nextTile.getIndex() == 24) {
          for (int i = 0; i < deck.size(); i++){
            worstCard = deck.get(i);
            if(worstCard.getCardType().equals("goToGummyBear"))
            {
              return deck.remove(i);
            }
          }
        }
        else if (nextTile.getIndex() == 32) {
          for (int i = 0; i < deck.size(); i++){
            worstCard = deck.get(i);
            if(worstCard.getCardType().equals("goToLicorice"))
            {
              return deck.remove(i);
            }
          }
        }
      }
      else if (nextTile.getColor().equals(Color.RED)) {
        if(!foundRed) {
          foundRed = true;
          for(int i = 0; i < deck.size(); i++) {
            worstCard = deck.get(i);
            if(worstCard.getCardType().equals("red") && !worstCard.getDoubleCard()) {
              return deck.remove(i);
            }
          }
        }
        else {
          for(int i = 0; i < deck.size(); i++) {
            worstCard = deck.get(i);
            if(worstCard.getCardType().equals("red") && worstCard.getDoubleCard()) {
              return deck.remove(i);
            }
          }
        }
      }
      else if (nextTile.getColor().equals(Color.YELLOW)) {
        if(!foundYellow) {
          foundYellow = true;
          for(int i = 0; i < deck.size(); i++) {
            worstCard = deck.get(i);
            if(worstCard.getCardType().equals("yellow") && !worstCard.getDoubleCard()) {
              return deck.remove(i);
            }
          }
        }
        else {
          for(int i = 0; i < deck.size(); i++) {
            worstCard = deck.get(i);
            if(worstCard.getCardType().equals("yellow") && worstCard.getDoubleCard()) {
              return deck.remove(i);
            }
          }
        }
      }
      else if (nextTile.getColor().equals(Color.BLUE)) {
        if (!foundBlue) {
          foundBlue = true;
          for(int i = 0; i < deck.size(); i++) {
            worstCard = deck.get(i);
            if(worstCard.getCardType().equals("blue") && !worstCard.getDoubleCard()) {
              return deck.remove(i);
            }
          }
        }
        else {
          for(int i = 0; i < deck.size(); i++) {
            worstCard = deck.get(i);
            if(worstCard.getCardType().equals("blue") && worstCard.getDoubleCard()) {
              return deck.remove(i);
            }
          }
        }
      }
      else if (nextTile.getColor().equals(Color.GREEN)) {
        if (!foundGreen) {
          foundGreen = true;
          for(int i = 0; i < deck.size(); i++) {
            worstCard = deck.get(i);
            if(worstCard.getCardType().equals("green") && !worstCard.getDoubleCard()) {
              return deck.remove(i);
            }
          }
        }
        else {
          for(int i = 0; i < deck.size(); i++) {
            worstCard = deck.get(i);
            if(worstCard.getCardType().equals("green") && worstCard.getDoubleCard()) {
              return deck.remove(i);
            }
          }
        }
      }
      else if (nextTile.getColor().equals(Color.ORANGE)) {
        if (!foundOrange) {
          foundOrange = true;
          for(int i = 0; i < deck.size(); i++) {
            worstCard = deck.get(i);
            if(worstCard.getCardType().equals("orange") && !worstCard.getDoubleCard()) {
              return deck.remove(i);
            }
          }
        }
        else {
          for(int i = 0; i < deck.size(); i++) {
            worstCard = deck.get(i);
            if(worstCard.getCardType().equals("orange") && worstCard.getDoubleCard()) {
              return deck.remove(i);
            }
          }
        }
      }
    }

    //return statement should never get here, otherwise null is returned and an error has occured
    return worstCard;
  }
}
