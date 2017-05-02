package uk.com.transform.game.dto;

import uk.com.transform.game.model.Game;

/**
 * @author Avirneni's
 *
 */
public class GameResponse {

	private Game game;
	private String result="No Result";
	
	public GameResponse(Game game) {
		this.game = game;
	}
	
	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
}
