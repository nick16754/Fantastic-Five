import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import javax.swing.JOptionPane;
import javax.swing.JFrame;
import java.io.File;
import java.io.FileReader;

public class WorldOfSweets {

    public static void main(String[] args) {

        String saveName = "";
        if (args.length == 0) {
            GameBoard g = new GameBoard(promptNumberPlayers());
        } else {
            if (args.length == 1) {
                saveName = args[0];
            }

            try {
                if (saveName.length() > 0 && saveName.endsWith(".json")) {
                    File f = new File(saveName);
                    if (f.exists()) {
                        Gson g = new GsonBuilder().setExclusionStrategies(new SaveExclusionStrategy()).create();
                        JsonReader reader = new JsonReader(new FileReader(saveName));
                        SaveState s = g.fromJson(reader, SaveState.class);

                        GameBoard ga = new GameBoard(s);

                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }



    }

    public static int promptNumberPlayers() {
        int num_players = -1;
        while (num_players < 0) {
            String user_input = JOptionPane.showInputDialog("Welcome to World of Sweets!!! Please enter number of players: ");
            try {
                num_players = Integer.parseInt(user_input);
                if (num_players < 2 || num_players > 4) {
                    throw new Exception();
                }
            } catch (Exception e) {
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
