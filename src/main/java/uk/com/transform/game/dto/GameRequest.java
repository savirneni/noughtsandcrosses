/**
 * 
 */
package uk.com.transform.game.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author Avirneni's
 *
 */
public class GameRequest {
	
	@NotNull
	@Min(1)
	@Max(3)
	private int rowId;
	
	@NotNull
	@Min(1)
	@Max(3)	
	private int columnId;
	
	public int getRowId() {
		return rowId;
	}
	
	public void setRowId(int rowId) {
		this.rowId = rowId;
	}
	
	public int getColumnId() {
		return columnId;
	}
	
	public void setColumnId(int columnId) {
		this.columnId = columnId;
	}

}
