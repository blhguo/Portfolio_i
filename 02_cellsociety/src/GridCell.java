import javafx.scene.shape.Polygon;

public abstract class GridCell extends Polygon {
	/**
	 * Builds the polygon with the correct locations and scaling given a cell that contains this information, as well as two doubles representing the offset
	 * No return value
	 */
	public abstract void Build(Cell cell, double bufferw, double bufferh);
	/**
	 * marks a cell as an agent cell, used in conjunction with an if statement
	 * No return value
	 */
	public abstract void mark();
}
