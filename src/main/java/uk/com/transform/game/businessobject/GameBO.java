package uk.com.transform.game.businessobject;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.com.transform.game.common.GameMark;
import uk.com.transform.game.dao.GameDAO;
import uk.com.transform.game.dto.GameResponse;
import uk.com.transform.game.exception.GameFinishedException;
import uk.com.transform.game.exception.GameInvalidMarkedSpaceException;
import uk.com.transform.game.exception.GameNotFoundException;
import uk.com.transform.game.exception.GameNotStartedException;
import uk.com.transform.game.model.Game;
import uk.com.transform.game.validator.GameValidator;
/**
 * @author Avirneni's
 *
 */
@Component
public class GameBO {
	
	private static final String CROSS = GameMark.CROSS.getCode();
	private static final String NOUGHT = GameMark.NOUGHT.getCode();
	private static final String PLAYER1_WON = "Congratulations!!! Player1 won";
	private static final String PLAYER2_WON = "Congratulations!!! Player2 won";
	private static final String DRAW = "Game Draw";
	private static final int TOTAL_TURNS = 9;
	private static final int FOUR_TURNS = 4;
	private static final int ROWS = 3;
	private static final int COLUMNS = 3;
	private static final int NCCOUNT = 3;
	private String winner = DRAW;	
	 
	private GameDAO gameDao;
	private GameValidator validator;
	

	@Autowired
	public void setGameDao(GameDAO gameDao) {
		this.gameDao = gameDao;
	}
	
	@Autowired
	public void setValidator(GameValidator validator) {
		this.validator = validator;
	}

	public GameResponse startGame() {
		Game newGame = new Game();
		newGame = gameDao.createGame(newGame);
		return new GameResponse(newGame);
	}	
	
	public GameResponse markGame(Integer gameId, String rowId, String columnId) throws GameNotStartedException, GameFinishedException, GameInvalidMarkedSpaceException {

		Game game = gameDao.findGame(gameId);
		if(game == null){
			throw new GameNotStartedException("Game not started yet. Please start the game");
		}

		validator.validate(game, rowId, columnId);
		if(!validator.isValid()) {
			sendFailureResponse();
		}
		
		game.getGameMap().put(rowId.concat(columnId), game.getPlayerTurn());
		printCurrentBoard(game.getPlayerTurn(), game.getGameMap());		

		return findGameResult(game);
	}
	
	public GameResponse findResult(Integer gameId) throws GameNotFoundException {
		
		Game game = gameDao.findGame(gameId);
		if(game == null){
			throw new GameNotFoundException("Game not found");
		}		
		
		return findGameResult(game);
	}

	
	private GameResponse findGameResult(Game game){
		GameResponse gameResponse = new GameResponse(game);
		//Check if the total moves less than or equal to 4
		if(game.getGameMap().size() <= FOUR_TURNS) {
			gameResponse.getGame().setPlayerTurn(game.opponentPlayer());
		}
		else {
			gameResponse = findWinner(game);
		}

		return gameResponse;
	}
	
	private GameResponse findWinner(Game game) {
		
		//Check if the total moves equal to 9
		GameResponse gameResponse = new GameResponse(game);	
		if(isAnyPlayerWon(game) || game.getGameMap().size() == TOTAL_TURNS ){
			setResult(gameResponse,winner);
			game.setPlayerTurn(null);
			game.setComplete(true);
		}
		else {
			gameResponse.getGame().setPlayerTurn(game.opponentPlayer());	
		}
				
		return gameResponse;	
	}
	
	private boolean isAnyPlayerWon(Game game) {
		
		final ConcurrentHashMap<String,String> currentGameMap = game.getGameMap();
		winner = calculateWinner(currentGameMap);
		return (!winner.isEmpty());	
	} 
	
	private void setResult(GameResponse gameResponse, String winner) {
		
		if(GameMark.CROSS.getCode().equals(winner)){
			gameResponse.setResult(PLAYER1_WON);
		} else if(GameMark.NOUGHT.getCode().equals(winner)) {
			gameResponse.setResult(PLAYER2_WON);
		} else {
			gameResponse.setResult(DRAW);
		}
		
	}
	
	private String calculateWinner(ConcurrentHashMap<String,String> currentGameMap) {

		String winner = null;
		//Rows
		winner = checkIfThreeMarksInRow(currentGameMap, winner);
		if(!winner.isEmpty())
			return winner;
		
		//Columns
		winner = checkIfThreeMarksInColumn(currentGameMap, winner);
		if(!winner.isEmpty())
			return winner;		
		
		//Diagonal downwards
		winner = checkIfThreeMarksDiagonalDownwards(currentGameMap, winner);
		if(!winner.isEmpty())
			return winner;
		
		//Diagonal upwards
		winner = checkIfThreeMarksDiagonalUpwards(currentGameMap, winner);
		
		return winner;
		
	}

	private String checkIfThreeMarksDiagonalUpwards(ConcurrentHashMap<String, String> currentGameMap, String winner) {
		int crossCount = 0;
		int noughtCount = 0;
		for(int r=ROWS;r<=1;r--) {
			for(int c=1;c<=COLUMNS;c++) {
				boolean firstLoop = true;
				String rc = new StringBuilder().append(r).append(c).toString();
				if (CROSS.equals(currentGameMap.get(rc))){
					crossCount++;
				} else if (NOUGHT.equals(currentGameMap.get(rc))){
					noughtCount++;
				}
				if(firstLoop)
					break;
			}
			winner = (crossCount == NCCOUNT) ? CROSS : (noughtCount == NCCOUNT) ? NOUGHT : "";
			if(!winner.isEmpty())
				break;
		}
		return winner;
	}

	private String checkIfThreeMarksDiagonalDownwards(ConcurrentHashMap<String, String> currentGameMap, String winner) {
		int crossCount = 0;
		int noughtCount = 0;		
		for(int r=1;r<=ROWS;r++) {
			for(int c=1;c<=COLUMNS;c++) {
				if(r==c) {
					String rc = new StringBuilder().append(r).append(c).toString();
					if (CROSS.equals(currentGameMap.get(rc))){
						crossCount++;
					} else if (NOUGHT.equals(currentGameMap.get(rc))){
						noughtCount++;
					}
				}
			}
			winner = (crossCount == NCCOUNT) ? CROSS : (noughtCount == NCCOUNT) ? NOUGHT : "";
			if(!winner.isEmpty())
				break;
		}
		return winner;
	}

	private String checkIfThreeMarksInColumn(ConcurrentHashMap<String, String> currentGameMap, String winner) {
		//Columns
		for(int r=1;r<=ROWS;r++) {
			int crossCount = 0;
			int noughtCount = 0;			
			for(int c=1;c<=COLUMNS;c++) {
				String cr = new StringBuilder().append(c).append(r).toString();
				if (CROSS.equals(currentGameMap.get(cr))){
					crossCount++;
				} else if (NOUGHT.equals(currentGameMap.get(cr))){
					noughtCount++;
				}
			}
			winner = (crossCount == NCCOUNT) ? CROSS : (noughtCount == NCCOUNT) ? NOUGHT : "";
			if(!winner.isEmpty())
				break;
		}
		return winner;
	}

	private String checkIfThreeMarksInRow(ConcurrentHashMap<String, String> currentGameMap, String winner) {
		for(int r=1;r<=ROWS;r++) {
			int crossCount = 0;
			int noughtCount = 0;			
			for(int c=1;c<=COLUMNS;c++) {
				String rc = new StringBuilder().append(r).append(c).toString();
				if (CROSS.equals(currentGameMap.get(rc))){
					crossCount++;
				} else if (NOUGHT.equals(currentGameMap.get(rc))){
					noughtCount++;
				};
			}
			winner = (crossCount == NCCOUNT) ? CROSS : (noughtCount == NCCOUNT) ? NOUGHT : "";
			if(!winner.isEmpty())
				break;
		}
		return winner;
	}
	
	private void printCurrentBoard(String playerTurn, ConcurrentHashMap<String,String> currentGameMap) {
		System.out.println(playerTurn + "'s Turn");
		for(int r=1;r<=ROWS;r++) {
			System.out.println("-------");
			for(int c=1;c<=COLUMNS;c++) {
				String rc = new StringBuilder().append(r).append(c).toString();
				System.out.print("|" + currentGameMap.getOrDefault(rc," "));
				if(c==NCCOUNT)
				System.out.print("|\n");
			}
			if(r==3)
			System.out.println("-------");
		}

	}
	
	private void sendFailureResponse() throws GameFinishedException, GameInvalidMarkedSpaceException {
 
		if(validator.isGameComplete()) {
			throw new GameFinishedException("Game finished. Please start a new game");
		}
		else {
			throw new GameInvalidMarkedSpaceException("Not a valid marked space.Please try again with correct one");
		}

	}
	
}
