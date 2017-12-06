import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import javax.swing.JOptionPane;
import javax.swing.JFrame;
import java.io.File;
import java.io.FileReader;

public class WorldOfSweets {

    public static void main(String[] args) {

        JFrame frame = new JFrame("World of Sweets");
        Object[] options = {"New Game", "Load Game"};
        int n = JOptionPane.showOptionDialog(frame,
                "Welcome!",
                "World of Sweets",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,     //do not use a custom Icon
                options,  //the titles of buttons
                options[0]); //default button title


        // If we hit new game
        if (n == 0) {
            GameboardStandard g = new GameboardStandard(promptNumberPlayers());
        } else if (n == 1) {

            boolean valid = false;
            String saveName = "";

            while (!valid) {
                saveName = JOptionPane.showInputDialog("Enter save name: ");

                if (saveName.length() > 0) {
                    if (!saveName.endsWith(".json")) {
                        saveName = saveName + ".json";
                    }
                    File f = new File(saveName);

                    if ((saveName.length() > 0) && f.exists()) {
                        valid = true;

                        try {
                            Gson g = new GsonBuilder().setExclusionStrategies(new SaveExclusionStrategy()).create();
                            JsonReader reader = new JsonReader(new FileReader(saveName));
                            SaveState s = g.fromJson(reader, SaveState.class);

                            GameboardStandard ga = new GameboardStandard(s);
                        } catch (Exception e) {
                            e.printStackTrace();
                            valid = false;
                        }
                    }

                    if (!f.exists()) {
                        System.out.println("File not found");
                    }
                }
            }
        }




    }

    public static int promptNumberPlayers() {
        int num_players = -1;
        while (num_players < 0) {
            String user_input = JOptionPane.showInputDialog("Please enter number of players: ");
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
