package uk.com.transform.game.validator;

import org.springframework.stereotype.Component;

import uk.com.transform.game.model.Game;
/**
 * @author Avirneni's
 *
 */
@Component
public class GameValidator {
	
	private boolean valid;
	private boolean isGameComplete;
	private boolean isSpaceEmpty;

	public void validate(Game game, String rowId, String columnId) {

		isGameComplete = game.isComplete() ? true : false;
		isSpaceEmpty = game.getGameMap().get(rowId.concat(columnId)) == null ? true : false;

		//Valid if game Not complete and Empty cell space exists
		setValid(!isGameComplete && isSpaceEmpty);
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public boolean isGameComplete() {
		return isGameComplete;
	}
	
	public boolean isSpaceEmpty() {
		return isSpaceEmpty;
	}	

}
