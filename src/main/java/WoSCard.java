public class WoSCard{
  private boolean doubleCard; //does card move one or two spaces (false for special cards)
  private String typeCard; //color or special (Skip Turn or Go To Middle)

  public WoSCard(boolean number, String colorOrSpecial){
    this.doubleCard = number;
    this.typeCard = colorOrSpecial;
  }

  public void setDoubleCard (boolean oneOrTwo){
    this.doubleCard = oneOrTwo;
  }

  public boolean getDoubleCard (){
    return this.doubleCard;
  }

  public void setCardType (String colorOrSpecial){
    this.typeCard = colorOrSpecial;
  }

  public String getCardType (){
    return this.typeCard;
  }
}
