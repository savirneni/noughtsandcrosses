/**
 * 
 */
package uk.com.transform.game.common;

/**
 * @author Avirneni's
 *
 */
public enum GameMark {
	
	CROSS("X"),	NOUGHT("O");

	private final String mark;
	
	GameMark(String mark) {
		this.mark = mark;
	}

	public String getCode() {
		return mark;
	}
}
