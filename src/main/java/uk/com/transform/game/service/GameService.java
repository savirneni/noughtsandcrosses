/**
 * 
 */
package uk.com.transform.game.service;

import uk.com.transform.game.dto.GameResponse;
import uk.com.transform.game.exception.GameFinishedException;
import uk.com.transform.game.exception.GameInvalidMarkedSpaceException;
import uk.com.transform.game.exception.GameNotFoundException;
import uk.com.transform.game.exception.GameNotStartedException;

/**
 * @author Avirneni's
 * GameService Interface
 */
public interface GameService {
	
	/*
	 * Start the game
	 */
	public GameResponse startGame();
	
	/*
	 * To store the player mark of the game
	 */
	public GameResponse markGame(Integer gameId,String rowId, String columnId) throws GameNotStartedException, GameFinishedException, GameInvalidMarkedSpaceException;
	
	/*
	 * To find the result of the game
	 */
	public GameResponse findResult(Integer gameId) throws GameNotFoundException;
}
