import cell.Cell;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public class CellFactory {
	public Polygon getShape(String shape) {
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
