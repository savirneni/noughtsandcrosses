package uk.com.transform.game.model;

import java.util.concurrent.ConcurrentHashMap;

import uk.com.transform.game.common.GameMark;
/**
 * @author Avirneni's
 *
 */
public class Game {
	
	private static final String CROSS = GameMark.CROSS.getCode();
	private static final String NOUGHT = GameMark.NOUGHT.getCode();

	private Integer gameId;
	private ConcurrentHashMap<String,String> gameMap = new ConcurrentHashMap<>();
	private String playerTurn = CROSS;
	private boolean complete = false;
	
	public Integer getGameId() {
		return gameId;
	}

	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}

	public ConcurrentHashMap<String, String> getGameMap() {
		return gameMap;
	}
	
	public void setGameMap(ConcurrentHashMap<String, String> gameMap) {
		this.gameMap = gameMap;
	}
	
	public String getPlayerTurn() {
		return playerTurn;
	}
	
	public void setPlayerTurn(String nextTurn) {
		this.playerTurn = nextTurn;
	}
	
	public boolean isComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}
	
	public String opponentPlayer() {
		return CROSS.equals(playerTurn) ? NOUGHT : CROSS ;
	}

}
