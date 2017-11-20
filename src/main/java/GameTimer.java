
public class GameTimer implements Runnable {

    long sessionStart;
    long currentPlayTime;
    GameBoard g;

    public GameTimer(long ms, GameBoard g) {
        this.currentPlayTime = ms;
        this.g = g;
        sessionStart = System.currentTimeMillis();
    }

    @Override
    public void run() {

        while (true) {
            try {
                g.updateTimer(currentPlayTime+(System.currentTimeMillis()-sessionStart));
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
}
