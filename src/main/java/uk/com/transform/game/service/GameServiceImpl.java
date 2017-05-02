/**
 * 
 */
package uk.com.transform.game.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.com.transform.game.businessobject.GameBO;
import uk.com.transform.game.dto.GameResponse;
import uk.com.transform.game.exception.GameFinishedException;
import uk.com.transform.game.exception.GameInvalidMarkedSpaceException;
import uk.com.transform.game.exception.GameNotFoundException;
import uk.com.transform.game.exception.GameNotStartedException;

/**
 * @author Avirneni's
 *
 */
@Service
public class GameServiceImpl implements GameService {

	
	GameBO gameBO;
	
	@Autowired
	public void setGameBO(GameBO gameBO) {
		this.gameBO = gameBO;
	}	

	@Override
	public GameResponse startGame() {
		return gameBO.startGame();
	}


	@Override
	public GameResponse markGame(Integer gameId, String rowId, String columnId) throws GameNotStartedException, GameFinishedException, GameInvalidMarkedSpaceException {
		return gameBO.markGame(gameId, rowId, columnId);
	}


	@Override
	public GameResponse findResult(Integer gameId) throws GameNotFoundException {
		return gameBO.findResult(gameId);
	}

}
