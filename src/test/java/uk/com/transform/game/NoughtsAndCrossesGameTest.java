package uk.com.transform.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import uk.com.transform.game.businessobject.GameBO;
import uk.com.transform.game.common.GameMark;
import uk.com.transform.game.dto.GameResponse;
import uk.com.transform.game.exception.GameFinishedException;
import uk.com.transform.game.exception.GameInvalidMarkedSpaceException;
import uk.com.transform.game.exception.GameNotFoundException;
import uk.com.transform.game.exception.GameNotStartedException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NoughtsAndCrossesGameTest {

	private GameBO gameBO;
	
	@Autowired
	public void setGameBO(GameBO gameBO) {
		this.gameBO = gameBO;
	}
	
	/*
	 * Test the Find Game
	 */
	@Test
    public void startGameTest() {
		GameResponse gameResponse = gameBO.startGame();
		assertEquals(GameMark.CROSS.getCode(), gameResponse.getGame().getPlayerTurn());
    }
	
	/*
	 * Test the GameNotStartedScenario exception in MarkGame
	 */
	@Test(expected = GameNotStartedException.class)
    public void markGameNotStartedTest() throws GameNotStartedException, GameFinishedException, GameInvalidMarkedSpaceException {
		gameBO.markGame(100000, "1", "1");
	}
	
	
	/*
	 * Test the MarkGame
	 */
	@Test
    public void markGameTest() throws GameNotStartedException, GameFinishedException, GameInvalidMarkedSpaceException {
		GameResponse gameResponse = gameBO.startGame();		
		gameBO.markGame(gameResponse.getGame().getGameId(), "1", "1");
		assertEquals(GameMark.CROSS.getCode(), gameResponse.getGame().getGameMap().get("11"));
	}
	
	
	/*
	 * Test the MarkGame - Player1 win scenario in 5 marks
	 */
	@Test
    public void markGamePlayer1WinInFiveMarksTest() throws GameNotStartedException, GameFinishedException, GameInvalidMarkedSpaceException {
		GameResponse gameResponse = gameBO.startGame();		
		gameBO.markGame(gameResponse.getGame().getGameId(), "1", "1");
		assertEquals(GameMark.CROSS.getCode(), gameResponse.getGame().getGameMap().get("11"));
		gameBO.markGame(gameResponse.getGame().getGameId(), "2", "1");
		assertEquals(GameMark.NOUGHT.getCode(), gameResponse.getGame().getGameMap().get("21"));
		gameBO.markGame(gameResponse.getGame().getGameId(), "1", "2");
		assertEquals(GameMark.CROSS.getCode(), gameResponse.getGame().getGameMap().get("12"));
		gameBO.markGame(gameResponse.getGame().getGameId(), "2", "2");
		assertEquals(GameMark.NOUGHT.getCode(), gameResponse.getGame().getGameMap().get("22"));
		gameResponse = gameBO.markGame(gameResponse.getGame().getGameId(), "1", "3");
		assertEquals(GameMark.CROSS.getCode(), gameResponse.getGame().getGameMap().get("13"));
		assertTrue(gameResponse.getGame().isComplete());
		assertEquals("Congratulations!!! Player1 won", gameResponse.getResult());
		
	}
	
	/*
	 * Test the MarkGame - Player2 win scenario in 6 marks
	 */
	@Test
    public void markGamePlayer2WinInSixMarksTest() throws GameNotStartedException, GameFinishedException, GameInvalidMarkedSpaceException {
		GameResponse gameResponse = gameBO.startGame();		
		gameBO.markGame(gameResponse.getGame().getGameId(), "1", "1");
		assertEquals(GameMark.CROSS.getCode(), gameResponse.getGame().getGameMap().get("11"));
		gameBO.markGame(gameResponse.getGame().getGameId(), "2", "1");
		assertEquals(GameMark.NOUGHT.getCode(), gameResponse.getGame().getGameMap().get("21"));
		gameBO.markGame(gameResponse.getGame().getGameId(), "1", "2");
		assertEquals(GameMark.CROSS.getCode(), gameResponse.getGame().getGameMap().get("12"));
		gameBO.markGame(gameResponse.getGame().getGameId(), "2", "2");
		assertEquals(GameMark.NOUGHT.getCode(), gameResponse.getGame().getGameMap().get("22"));
		gameBO.markGame(gameResponse.getGame().getGameId(), "3", "1");
		assertEquals(GameMark.CROSS.getCode(), gameResponse.getGame().getGameMap().get("31"));
		gameResponse = gameBO.markGame(gameResponse.getGame().getGameId(), "2", "3");
		assertEquals(GameMark.NOUGHT.getCode(), gameResponse.getGame().getGameMap().get("23"));		
		assertTrue(gameResponse.getGame().isComplete());
		assertEquals("Congratulations!!! Player2 won", gameResponse.getResult());
		
	}
	
	/*
	 * Test the MarkGame - Player1 win scenario in 7 marks
	 */
	@Test
    public void markGamePlayer1WinInSevenMarksTest() throws GameNotStartedException, GameFinishedException, GameInvalidMarkedSpaceException {
		GameResponse gameResponse = gameBO.startGame();		
		gameBO.markGame(gameResponse.getGame().getGameId(), "2", "2");
		assertEquals(GameMark.CROSS.getCode(), gameResponse.getGame().getGameMap().get("22"));
		gameBO.markGame(gameResponse.getGame().getGameId(), "1", "1");
		assertEquals(GameMark.NOUGHT.getCode(), gameResponse.getGame().getGameMap().get("11"));
		gameBO.markGame(gameResponse.getGame().getGameId(), "3", "1");
		assertEquals(GameMark.CROSS.getCode(), gameResponse.getGame().getGameMap().get("31"));
		gameBO.markGame(gameResponse.getGame().getGameId(), "1", "2");
		assertEquals(GameMark.NOUGHT.getCode(), gameResponse.getGame().getGameMap().get("12"));
		gameBO.markGame(gameResponse.getGame().getGameId(), "3", "2");
		assertEquals(GameMark.CROSS.getCode(), gameResponse.getGame().getGameMap().get("32"));
		gameBO.markGame(gameResponse.getGame().getGameId(), "2", "1");
		assertEquals(GameMark.NOUGHT.getCode(), gameResponse.getGame().getGameMap().get("21"));
		gameResponse = gameBO.markGame(gameResponse.getGame().getGameId(), "3", "3");
		assertEquals(GameMark.CROSS.getCode(), gameResponse.getGame().getGameMap().get("33"));
		assertTrue(gameResponse.getGame().isComplete());
		assertEquals("Congratulations!!! Player1 won", gameResponse.getResult());
		
	}
	
	/*
	 * Test the MarkGame - Player2 win scenario in 8 marks
	 */
	@Test
    public void markGamePlayer2WinInEightMarksTest() throws GameNotStartedException, GameFinishedException, GameInvalidMarkedSpaceException {
		GameResponse gameResponse = gameBO.startGame();		
		gameBO.markGame(gameResponse.getGame().getGameId(), "1", "2");
		assertEquals(GameMark.CROSS.getCode(), gameResponse.getGame().getGameMap().get("12"));
		gameBO.markGame(gameResponse.getGame().getGameId(), "1", "1");
		assertEquals(GameMark.NOUGHT.getCode(), gameResponse.getGame().getGameMap().get("11"));
		gameBO.markGame(gameResponse.getGame().getGameId(), "1", "3");
		assertEquals(GameMark.CROSS.getCode(), gameResponse.getGame().getGameMap().get("13"));
		gameBO.markGame(gameResponse.getGame().getGameId(), "2", "1");
		assertEquals(GameMark.NOUGHT.getCode(), gameResponse.getGame().getGameMap().get("21"));
		gameBO.markGame(gameResponse.getGame().getGameId(), "2", "3");
		assertEquals(GameMark.CROSS.getCode(), gameResponse.getGame().getGameMap().get("23"));
		gameBO.markGame(gameResponse.getGame().getGameId(), "2", "2");
		assertEquals(GameMark.NOUGHT.getCode(), gameResponse.getGame().getGameMap().get("22"));
		gameBO.markGame(gameResponse.getGame().getGameId(), "3", "1");
		assertEquals(GameMark.CROSS.getCode(), gameResponse.getGame().getGameMap().get("31"));
		gameResponse = gameBO.markGame(gameResponse.getGame().getGameId(), "3", "3");
		assertEquals(GameMark.NOUGHT.getCode(), gameResponse.getGame().getGameMap().get("33"));
		assertTrue(gameResponse.getGame().isComplete());
		assertEquals("Congratulations!!! Player2 won", gameResponse.getResult());
		
	}	
	
	
	/*
	 * Test the MarkGame - Player1 win scenario in 9 marks
	 */
	@Test
    public void markGamePlayer1WinInNineMarksTest() throws GameNotStartedException, GameFinishedException, GameInvalidMarkedSpaceException {
		GameResponse gameResponse = gameBO.startGame();		
		gameBO.markGame(gameResponse.getGame().getGameId(), "1", "3");
		assertEquals(GameMark.CROSS.getCode(), gameResponse.getGame().getGameMap().get("13"));
		gameBO.markGame(gameResponse.getGame().getGameId(), "2", "1");
		assertEquals(GameMark.NOUGHT.getCode(), gameResponse.getGame().getGameMap().get("21"));
		gameBO.markGame(gameResponse.getGame().getGameId(), "1", "2");
		assertEquals(GameMark.CROSS.getCode(), gameResponse.getGame().getGameMap().get("12"));
		gameBO.markGame(gameResponse.getGame().getGameId(), "2", "2");
		assertEquals(GameMark.NOUGHT.getCode(), gameResponse.getGame().getGameMap().get("22"));
		gameBO.markGame(gameResponse.getGame().getGameId(), "2", "3");
		assertEquals(GameMark.CROSS.getCode(), gameResponse.getGame().getGameMap().get("23"));
		gameBO.markGame(gameResponse.getGame().getGameId(), "3", "1");
		assertEquals(GameMark.NOUGHT.getCode(), gameResponse.getGame().getGameMap().get("31"));
		gameBO.markGame(gameResponse.getGame().getGameId(), "3", "2");
		assertEquals(GameMark.CROSS.getCode(), gameResponse.getGame().getGameMap().get("32"));
		gameBO.markGame(gameResponse.getGame().getGameId(), "3", "3");
		assertEquals(GameMark.NOUGHT.getCode(), gameResponse.getGame().getGameMap().get("33"));
		gameResponse = gameBO.markGame(gameResponse.getGame().getGameId(), "1", "1");
		assertEquals(GameMark.CROSS.getCode(), gameResponse.getGame().getGameMap().get("11"));
		assertTrue(gameResponse.getGame().isComplete());
		assertEquals("Congratulations!!! Player1 won", gameResponse.getResult());
		
	}
	
	
	/*
	 * Test the MarkGame - Draw scenario
	 */
	@Test
    public void markGameDrawTest() throws GameNotStartedException, GameFinishedException, GameInvalidMarkedSpaceException {
		GameResponse gameResponse = gameBO.startGame();		
		gameBO.markGame(gameResponse.getGame().getGameId(), "1", "1");
		assertEquals(GameMark.CROSS.getCode(), gameResponse.getGame().getGameMap().get("11"));
		gameBO.markGame(gameResponse.getGame().getGameId(), "1", "2");
		assertEquals(GameMark.NOUGHT.getCode(), gameResponse.getGame().getGameMap().get("12"));
		gameBO.markGame(gameResponse.getGame().getGameId(), "1", "3");
		assertEquals(GameMark.CROSS.getCode(), gameResponse.getGame().getGameMap().get("13"));
		gameBO.markGame(gameResponse.getGame().getGameId(), "2", "2");
		assertEquals(GameMark.NOUGHT.getCode(), gameResponse.getGame().getGameMap().get("22"));
		gameBO.markGame(gameResponse.getGame().getGameId(), "2", "1");
		assertEquals(GameMark.CROSS.getCode(), gameResponse.getGame().getGameMap().get("21"));
		gameBO.markGame(gameResponse.getGame().getGameId(), "2", "3");
		assertEquals(GameMark.NOUGHT.getCode(), gameResponse.getGame().getGameMap().get("23"));
		gameBO.markGame(gameResponse.getGame().getGameId(), "3", "2");
		assertEquals(GameMark.CROSS.getCode(), gameResponse.getGame().getGameMap().get("32"));
		gameBO.markGame(gameResponse.getGame().getGameId(), "3", "1");
		assertEquals(GameMark.NOUGHT.getCode(), gameResponse.getGame().getGameMap().get("31"));
		gameResponse = gameBO.markGame(gameResponse.getGame().getGameId(), "3", "3");
		assertEquals(GameMark.CROSS.getCode(), gameResponse.getGame().getGameMap().get("33"));
		assertTrue(gameResponse.getGame().isComplete());
		assertEquals("Game Draw", gameResponse.getResult());
		
	}	
	
	/*
	 * Test the GameNotStartedScenario exception in MarkGame
	 */
	@Test(expected = GameFinishedException.class)
    public void markGameFinishedExceptionTest() throws GameNotStartedException, GameFinishedException, GameInvalidMarkedSpaceException {
		GameResponse gameResponse = gameBO.startGame();		
		gameBO.markGame(gameResponse.getGame().getGameId(), "1", "1");
		assertEquals(GameMark.CROSS.getCode(), gameResponse.getGame().getGameMap().get("11"));
		gameBO.markGame(gameResponse.getGame().getGameId(), "2", "1");
		assertEquals(GameMark.NOUGHT.getCode(), gameResponse.getGame().getGameMap().get("21"));
		gameBO.markGame(gameResponse.getGame().getGameId(), "1", "2");
		assertEquals(GameMark.CROSS.getCode(), gameResponse.getGame().getGameMap().get("12"));
		gameBO.markGame(gameResponse.getGame().getGameId(), "2", "2");
		assertEquals(GameMark.NOUGHT.getCode(), gameResponse.getGame().getGameMap().get("22"));
		gameResponse = gameBO.markGame(gameResponse.getGame().getGameId(), "1", "3");
		assertEquals(GameMark.CROSS.getCode(), gameResponse.getGame().getGameMap().get("13"));
		gameResponse = gameBO.markGame(gameResponse.getGame().getGameId(), "1", "3");
		assertEquals(GameMark.NOUGHT.getCode(), gameResponse.getGame().getGameMap().get("13"));		
		assertTrue(gameResponse.getGame().isComplete());
		
	}
	
	/*
	 * Test the GameInvalidMarkedSpaceException exception in MarkGame
	 */
	@Test(expected = GameInvalidMarkedSpaceException.class)
    public void markGameInvalidMarkedSpaceExceptionTest() throws GameNotStartedException, GameFinishedException, GameInvalidMarkedSpaceException {
		GameResponse gameResponse = gameBO.startGame();		
		gameBO.markGame(gameResponse.getGame().getGameId(), "1", "1");
		assertEquals(GameMark.CROSS.getCode(), gameResponse.getGame().getGameMap().get("11"));
		gameBO.markGame(gameResponse.getGame().getGameId(), "2", "1");
		assertEquals(GameMark.NOUGHT.getCode(), gameResponse.getGame().getGameMap().get("21"));
		gameBO.markGame(gameResponse.getGame().getGameId(), "2", "1");
		assertEquals(GameMark.NOUGHT.getCode(), gameResponse.getGame().getGameMap().get("21"));
		
	}
	
	
	/*
	 * Test the FindResult
	 */
	@Test(expected = GameNotFoundException.class)
    public void findResultGameTest() throws GameNotFoundException {
		gameBO.findResult(1000999);
	}	
	
	/*
	 * Test the FindResult - Player1 win scenario in 7 marks
	 */
	@Test
    public void findResultPlayer1WinInSevenMarksTest() throws GameNotStartedException, GameFinishedException, GameInvalidMarkedSpaceException, GameNotFoundException {
		GameResponse gameResponse = gameBO.startGame();		
		gameBO.markGame(gameResponse.getGame().getGameId(), "2", "2");
		assertEquals(GameMark.CROSS.getCode(), gameResponse.getGame().getGameMap().get("22"));
		gameBO.markGame(gameResponse.getGame().getGameId(), "1", "1");
		assertEquals(GameMark.NOUGHT.getCode(), gameResponse.getGame().getGameMap().get("11"));
		gameBO.markGame(gameResponse.getGame().getGameId(), "3", "1");
		assertEquals(GameMark.CROSS.getCode(), gameResponse.getGame().getGameMap().get("31"));
		gameBO.markGame(gameResponse.getGame().getGameId(), "1", "2");
		assertEquals(GameMark.NOUGHT.getCode(), gameResponse.getGame().getGameMap().get("12"));
		gameBO.markGame(gameResponse.getGame().getGameId(), "3", "2");
		assertEquals(GameMark.CROSS.getCode(), gameResponse.getGame().getGameMap().get("32"));
		gameBO.markGame(gameResponse.getGame().getGameId(), "2", "1");
		assertEquals(GameMark.NOUGHT.getCode(), gameResponse.getGame().getGameMap().get("21"));
		gameBO.markGame(gameResponse.getGame().getGameId(), "3", "3");
		assertEquals(GameMark.CROSS.getCode(), gameResponse.getGame().getGameMap().get("33"));
		gameResponse = gameBO.findResult(gameResponse.getGame().getGameId());
		assertTrue(gameResponse.getGame().isComplete());
		assertNull(gameResponse.getGame().getPlayerTurn());
		assertEquals("Congratulations!!! Player1 won", gameResponse.getResult());
		
	}
	
	/*
	 * Test the MarkGame - Player2 win scenario in 8 marks
	 */
	@Test
    public void findResultPlayer2WinInEightMarksTest() throws GameNotStartedException, GameFinishedException, GameInvalidMarkedSpaceException, GameNotFoundException {
		GameResponse gameResponse = gameBO.startGame();		
		gameBO.markGame(gameResponse.getGame().getGameId(), "1", "2");
		assertEquals(GameMark.CROSS.getCode(), gameResponse.getGame().getGameMap().get("12"));
		gameBO.markGame(gameResponse.getGame().getGameId(), "1", "1");
		assertEquals(GameMark.NOUGHT.getCode(), gameResponse.getGame().getGameMap().get("11"));
		gameBO.markGame(gameResponse.getGame().getGameId(), "1", "3");
		assertEquals(GameMark.CROSS.getCode(), gameResponse.getGame().getGameMap().get("13"));
		gameBO.markGame(gameResponse.getGame().getGameId(), "2", "1");
		assertEquals(GameMark.NOUGHT.getCode(), gameResponse.getGame().getGameMap().get("21"));
		gameBO.markGame(gameResponse.getGame().getGameId(), "2", "3");
		assertEquals(GameMark.CROSS.getCode(), gameResponse.getGame().getGameMap().get("23"));
		gameBO.markGame(gameResponse.getGame().getGameId(), "2", "2");
		assertEquals(GameMark.NOUGHT.getCode(), gameResponse.getGame().getGameMap().get("22"));
		gameBO.markGame(gameResponse.getGame().getGameId(), "3", "1");
		assertEquals(GameMark.CROSS.getCode(), gameResponse.getGame().getGameMap().get("31"));
		gameBO.markGame(gameResponse.getGame().getGameId(), "3", "3");
		assertEquals(GameMark.NOUGHT.getCode(), gameResponse.getGame().getGameMap().get("33"));
		gameResponse = gameBO.findResult(gameResponse.getGame().getGameId());
		assertTrue(gameResponse.getGame().isComplete());
		assertNull(gameResponse.getGame().getPlayerTurn());
		assertEquals("Congratulations!!! Player2 won", gameResponse.getResult());
		
	}	
	

}
