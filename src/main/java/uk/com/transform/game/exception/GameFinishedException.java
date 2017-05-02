package uk.com.transform.game.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
/**
 * @author Avirneni's
 *
 */
@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class GameFinishedException extends Exception 
{
	private static final long serialVersionUID = 100L;
	
    public GameFinishedException(String message) {
        super(message);
    }
}
