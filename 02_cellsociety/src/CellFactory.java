import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public class CellFactory {
	/**
	 * Factory that creates anew instance of of a polygon, will be used in conjunction with build and mark methods, and be added to root
	 * This demonstrates good code because it's an implementation of a factory. This way, i can extract and refator the "Createroot" method and it's respctive helper methods here
	 * In addition, because this calls for some deeper level changes to Cell, cell takes on a greater burden as one of three parameters passed in to "build" (see RectCell, TriCell, or HexCell for details)
	 * If it is desired to hide the cell's position from the cell, additional inputs in the form of int i, int j can be easily added to each build method. 
	 * @return polygon - dependent on the type of simulation called for
	 */
	public GridCell getPoly(String shape) {
		if(shape == null) {
			return null;
		}
		if(shape.equals("square")) {
			return new RectCell();
		}
		else if(shape.equals("hexagon")) {
			return new HexCell();
		}
		else if(shape.equals("triangle")) {
			return new TriCell();
		}
		
		return null;
	}
}
