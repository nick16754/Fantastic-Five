import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.text.*;
import java.io.*;

public class GameBoard
{

  static JFrame _frame = new JFrame("World of Sweets");

  public GameBoard() {
    create_board();
  }

  public Boolean create_board()
  {
    // Center the frame
    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (int) ((dimension.getWidth() - _frame.getWidth()) / 2);
    int y = (int) ((dimension.getHeight() - _frame.getHeight()) / 2);
    _frame.setLocation(x, y);

    _frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    _frame.setSize(1000, 1000);


    JPanel MainPanel = new JPanel();

    _frame.add(MainPanel);

    GridLayout experimentLayout = new GridLayout(10,10);
    MainPanel.setLayout(experimentLayout);

    JPanel[][] squares = new JPanel[10][10] ;

    int rowCounter = 0;
    int rows = 0;

    for (int i = 0; i < 100; i++) {

      JPanel sub = new JPanel();
//            sub.setBorder(BorderFactory.createLineBorder(Color.black));
//            sub.setBackground(Color.red);
      sub.setSize(100, 100);
      sub.addMouseListener(new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
          System.out.println("Click");
        }
      });


      if (rowCounter >= 10) {
        rowCounter = 0;
        rows++;
      }

      System.out.println("Adding square at row " + rows + ", col " + (i%10));
      squares[rows][i%10] = sub;


      MainPanel.add(sub);

      rowCounter++;
    }

    squares[1][1].setBackground(Color.green);

    _frame.setVisible(true);

    return true;
  }

}
