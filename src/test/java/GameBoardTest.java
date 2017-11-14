import static org.junit.Assert.*;

import org.junit.*;
import org.mockito.*;

import static org.mockito.Mockito.*;

public class GameBoardTest {
    @Test
    public void GameBoardOfFivePlayersResultsInPlayerListSize5() {
        GameBoard testboard = new GameBoard(5);
        assertEquals(testboard.getPlayerList().size(), 5);
    }

    //test assures gameboard displays all 34 tiles

    @Test
    public void test_gmae_board_tiles() {
        GameBoard testboard2 = new GameBoard(5);
        assertEquals(testboard2.getTileList().size(), 34);
    }

    // test ensures that we return the players correct name
    @Test
    public void test_player_name() {
        Piece game_piece = new Piece("placeholder_piece.png");
        String test_p = "player 1";
        Player p = new Player(test_p, game_piece);
        assertEquals("player 1", p.getName());
    }

    // test that correct number of game pieces are on correct tiles.
    @Test
    public void test_tile_pieces() {
        GameBoard testboard = new GameBoard(3);
        assertEquals(testboard.getTileList().get(0).getCurrentPieces().size(), 3);

    }

    // makes sure we can remove a piece
    @Test
    public void test_remove_pieces() {
        GameBoard testboard = new GameBoard(3);
        Player p = testboard.getPlayerList().get(0);
        testboard.getTileList().get(0).removePiece(testboard, p.getPiece());
        assertEquals(2, testboard.getTileList().get(0).getCurrentPieces().size());
    }

    // makes sure deck has 60 cards to start with
    @Test
    public void test_fill_deck() {
        WoSDeck deck = new WoSDeck();
        assertEquals(deck.getSize(), 68);
    }

    //ensure that deck refills.
    @Test
    public void test_refill_deck() {
        WoSDeck deck = new WoSDeck();
        WoSCard card;
        for (int i = 0; i < 68; i++)
            card = deck.drawCard();
        assertEquals(deck.getSize(), 68);
    }

    // test to make sure players start on the first tile.
    @Test
    public void test_start() {
        GameBoard testboard = new GameBoard(3);
        assertEquals(testboard.getPlayerList().get(0).getCurrentTile().getxCoord(), 1);
        assertEquals(testboard.getPlayerList().get(0).getCurrentTile().getyCoord(), 0);
    }


}
