import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;


public class GameTimer implements ActionListener {

    private static final int ONE_SECOND = 1000;
    private JLabel timeDisplay;
    private long startTime;
    private Timer timer;
    private int seconds;

    // GameTimer constructor sets timer to start at 1 second by default
    public GameTimer() {
      this.startTime = System.currentTimeMillis();
      this.seconds = 1;
      this.timer = new Timer(ONE_SECOND, this);
      timer.setRepeats(false);
      timer.start();
    }

    public void displayTimer() {
        JFrame frame = new JFrame("Game Timer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new JLabel("0"));
        frame.pack();
        frame.setVisible(true);
    }

    public void setSeconds(int sec) {
      this.seconds = sec;
    }

    public void incrementSeconds() {
      this.seconds++;
    }

    public int getSeconds() {
      return this.seconds;
    }

    public long getStartTime() {
      return this.startTime;
    }

    public void setStartTime(long starting_second) {
      this.startTime = starting_second;
    }

    public void resume() {
        timer.restart();
    }

    public void stop() {
        timer.stop();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        long now = System.currentTimeMillis();
        long elapsed = now - getStartTime();
        incrementSeconds();
        timeDisplay.setText(elapsed + " Milliseconds since start");
        timer.setInitialDelay((int)(getStartTime()+getSeconds()*ONE_SECOND-now));
        timer.start();
    }
}
