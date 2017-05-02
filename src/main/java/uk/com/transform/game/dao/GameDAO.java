/**
 * 
 */
package uk.com.transform.game.dao;

import uk.com.transform.game.model.Game;

/**
 * @author Avirneni's
 * Game Repository
 */
public interface GameDAO {
	
	/**
	 * Create Game
	 */
	Game createGame(Game game); 
	
	/**
	 * Update Game
	 */
	Game updateGame(Game game);
	
	/**
	 * Find Game
	 */
	Game findGame(Integer gameId);	
	
}
