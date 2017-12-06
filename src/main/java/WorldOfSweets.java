import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.xml.bind.DatatypeConverter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.security.MessageDigest;

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
            //GameboardStrategic g = new GameboardStrategic(promptNumberPlayers());
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

                            BufferedReader br = new BufferedReader(new FileReader(saveName));
                            String json = br.readLine().replace("\n", "").replace("\r","");
                            String hash = br.readLine().replace("\n", "").replace("\r","");


                            if ((json == null) || (hash == null)) {
                                // show file is corrupt
                                JOptionPane.showMessageDialog(new JFrame(), "File is corrupt! Exiting...");
                                System.exit(0);
                            } else {

                                byte[] jsonBytes = json.getBytes("UTF-8");
                                MessageDigest md = MessageDigest.getInstance("MD5");
                                byte[] thedigest = md.digest(jsonBytes);

                                if (hash.equals(DatatypeConverter.printHexBinary(thedigest))) {
                                    // We have a match, read the file in
                                    System.out.println("save match");
                                    SaveState s = g.fromJson(json, SaveState.class);
                                    GameboardStandard ga = new GameboardStandard(s);
                                } else {
                                    // show file is corrupt
                                    JOptionPane.showMessageDialog(new JFrame(), "File is corrupt! Exiting...");
                                    System.exit(0);
                                }

                            }

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
