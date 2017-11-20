import java.util.ArrayList;
import java.util.Random;

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
    if(deck.size() > 0){
      int next = draw.nextInt(deck.size());
      nextDraw = deck.get(next);
      deck.remove(next);
    }
    if(deck.size() == 0){
      fillDeck();
    }
    return nextDraw;
  }
}
