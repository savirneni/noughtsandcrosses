package uk.com.transform.game.dao;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import uk.com.transform.game.model.Game;
/**
 * @author Avirneni's
 *
 */
@Repository
public class GameDAOImpl implements GameDAO {

	Integer gameId = 1000;
	private ConcurrentHashMap<Integer, Game> gamesMap = new ConcurrentHashMap<>();
	
	@Override
	public Game createGame(Game game) {
		gameId++;
		game.setGameId(gameId);
		getGamesMap().put(gameId, game);
		return game;
	}

	@Override
	public Game updateGame(Game game) {
		getGamesMap().replace(gameId, game);		
		return null;
	}
	
	@Override
	public Game findGame(Integer gameId) {
		return getGamesMap().get(gameId);
	}

	public ConcurrentHashMap<Integer, Game> getGamesMap() {
		return gamesMap;
	}

	public void setGamesMap(ConcurrentHashMap<Integer, Game> gamesMap) {
		this.gamesMap = gamesMap;
	}
	
}
