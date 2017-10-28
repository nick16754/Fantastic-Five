import javax.swing.JOptionPane;

public class WorldOfSweets {

    public static void main(String[] args) {
        String user_input= JOptionPane.showInputDialog("Welcome to World of Sweets!!! Please enter number of players: ");
        int num_players = Integer.parseInt(user_input);
        GameBoard g = new GameBoard(num_players);
    }

}
