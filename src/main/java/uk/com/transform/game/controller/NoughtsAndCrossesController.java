package uk.com.transform.game.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import uk.com.transform.game.dto.GameRequest;
import uk.com.transform.game.dto.GameResponse;
import uk.com.transform.game.exception.GameFinishedException;
import uk.com.transform.game.exception.GameInvalidMarkedSpaceException;
import uk.com.transform.game.exception.GameNotFoundException;
import uk.com.transform.game.exception.GameNotStartedException;
import uk.com.transform.game.service.GameService;
/**
 * @author Avirneni's
 *
 */
@RestController
@RequestMapping("/ncservice")
public class NoughtsAndCrossesController {

	GameService gameService;

	@Autowired
	public void setGameService(GameService gameService) {
		this.gameService = gameService;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/games")
    public GameResponse startGame() {
        return gameService.startGame();
    }
    
    @RequestMapping(method = RequestMethod.PUT, value = "/games/{gameId}") 
    public GameResponse markGame(@PathVariable(value="gameId") Integer gameId,
    		@Valid @RequestBody GameRequest gameRequest ) throws GameNotStartedException, GameFinishedException, GameInvalidMarkedSpaceException {
    	return gameService.markGame(gameId, String.valueOf(gameRequest.getRowId()), String.valueOf(gameRequest.getColumnId()));
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/games/{gameId}")
    public GameResponse findResult(@PathVariable(value="gameId") Integer gameId) throws GameNotFoundException {
        return gameService.findResult(gameId);
    }    
}