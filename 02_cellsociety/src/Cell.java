import java.util.List;

import javafx.scene.paint.Color;

/**
 * This is an abstract class for cells. To create a new cell type, create a subclass of this.
 */
public abstract class Cell {
	protected Color display_color;

	/**
	 * Used to get the next state information of a cell based on rules applied
	 * to the current state and the states of its neighbors.
	 * @param neighbors - List of all neighboring cells.
	 * @return - A cell with the next state information.
	 */
	public abstract Cell nextState (List<Cell> neighbors);

	/**
	 * Sets the display color of the cell.
	 */
	protected abstract void setDisplayColor();

	/**
	 * Used to get the display color of the cell.
	 * @return - The display color of the cell.
	 */
	public Color getDisplayColor() {
		return display_color;
	}
	
	protected int xpos;
	protected int ypos;
	protected int width;
	protected int height;
	
	/**
	 * Used to get the grid x position of the cell.
	 * @return - The grid x position of the cell.
	 */
	public int get_xpos() {
		return xpos;
	}
	/**
	 * Used to get the grid y position of the cell.
	 * @return - The grid y position of the cell.
	 */
	public int get_ypos() {
		return ypos;
	}
	/**
	 * Used to get the width of the cell.
	 * @return - The width of the cell.
	 */
	public int get_W() {
		return width;
	}
	/**
	 * Used to get the height of the cell.
	 * @return - The height of the cell.
	 */
	public int get_H() {
		return height;
	}
}