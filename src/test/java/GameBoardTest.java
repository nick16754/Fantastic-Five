import static org.junit.Assert.*;
import org.junit.*;

public class GameBoardTest{
    @Test
    public void GameBoardOfFivePlayersResultsInPlayerListSize5()
    {
      GameBoard testboard = new GameBoard(5);
      assertEquals(testboard.playerList.size(), 5);
      testboard.test_quit();
    }

    //test assures gameboard displays all 34 tiles

    @Test
    public void test_gmae_board_tiles()
    {
      GameBoard testboard2 = new GameBoard(5);
      assertEquals(testboard2.tileList.size(), 34);
      testboard2.test_quit();
    }

    // test ensures that we return the players correct name
    @Test
    public void test_player_name()
    {
      Piece game_piece = new Piece("placeholder_piece.png");
      String test_p = "player 1";
      Player p = new Player(test_p, game_piece);
      assertEquals("player 1",p.getName());
    }
    // test that correct number of game pieces are on correct tiles.
    @Test
    public void test_tile_pieces()
    {
      GameBoard testboard = new GameBoard(3);
      assertEquals(testboard.tileList.get(0).currentPieces.size(),3);
      testboard.test_quit();

    }

    // makes sure we can remove a piece
    @Test
    public void test_remove_pieces()
    {
      Piece game_piece = new Piece("placeholder_piece.png");
      GameBoard testboard = new GameBoard(3);
      testboard.tileList.get(0).removePiece(game_piece);
      assertEquals(testboard.tileList.get(0).currentPieces.size(),2);
      testboard.test_quit();
    }






}
