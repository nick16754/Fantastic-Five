public class WoSCard{
  private boolean oneOrTwo; //does card move one or two spaces
  private String color; //color

  public WoSCard(boolean number, String whatColor){
    oneOrTwo = number;
    color = whatColor;
  }

  public void setDoubleCard (boolean number){
    oneOrTwo = number;
  }

  public boolean getDoubleCard (){
    return oneOrTwo;
  }

  public void setColor (String whatColor){
    color = whatColor;
  }

  public String getColor (){
    return color;
  }
}
