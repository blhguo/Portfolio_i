import javafx.scene.Group;
import javafx.scene.shape.Polygon;

/**
 * Simulation visualization and management (only for masterpiece, edit to "CreateRoot"
 * @author Brandon Guo
 * 
 * Use: 
 * calls the factory
 */
public class Manager {
	/**
	 * @param grid - Grid object representing the storage of all information
	 * @param bufferw - width of chart, used for offset
	 * @param bufferh - height of chart, used for offset
	 * @return Group - represents the actual simulation group of images. 
	 */
	public Group CreateRoot(Grid grid, double bufferw, double bufferh) {
			CellFactory factory = new CellFactory();
		Cell[][] cellArray = grid.getCellArray();
		Group addition = new Group();
		for (int i = 0; i < cellArray.length; i++) {
			for (int j = 0; j < cellArray[0].length; j++) {				
				GridCell poly = factory.getPoly(grid.getshape);
				poly.Build(grid[i][j], bufferw, bufferh);
				if (grid[i][j] instanceof AgentCell) {
					grid[i][j].mark();
				}
				addition.getChildren().add(poly);
			}
		}
		return addition;
}
}
