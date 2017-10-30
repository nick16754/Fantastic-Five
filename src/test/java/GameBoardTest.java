import static org.junit.Assert.*;
import org.junit.*;

public class GameBoardTest{
    @Test
    public void GameBoardOfFivePlayersResultsInPlayerListSize5()
    {
      GameBoard testboard = new GameBoard(5);
      assertEquals(testboard.playerList.size(), 5);
    }

}
