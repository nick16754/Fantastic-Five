import javax.swing.JOptionPane;
import javax.swing.JFrame;

public class WorldOfSweets {

    public static void main(String[] args) {

        GameBoard g = new GameBoard(promptNumberPlayers());
    }

    public static int promptNumberPlayers()
    {
      int num_players = -1;
      while(num_players<0)
      {
        String user_input = JOptionPane.showInputDialog("Welcome to World of Sweets!!! Please enter number of players: ");
        try
        {
          num_players = Integer.parseInt(user_input);
          if(num_players<2 || num_players>4)
          {
            throw new Exception();
          }
        }
        catch (Exception e)
        {
          JOptionPane.showMessageDialog(new JFrame(),
              "Number of players must be an integer between 2 and 4 (inclusive).",
              "Invalid input.",
              JOptionPane.ERROR_MESSAGE);
          num_players = -1;
        }
      }
      return num_players;
    }

}
