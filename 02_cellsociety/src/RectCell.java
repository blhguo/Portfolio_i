import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
/**
 * @author blg19
 * Image that returns a polygon that will represent a rectangle/square
 */
public class RectCell extends GridCell {

	public void Build(Cell input, double BuffW, double BuffH) {
		int i = input.get_xpos();
		int j = input.get_ypos();
		int W = input.get_W();
		int H = input.get_H();
		Color FillColor = input.getDisplayColor();
		Double[] points = new Double[] {
				i*W + BuffW, j*H + BuffH,
				i*W + W + BuffW, j*H + BuffH, 
				i*W + W + BuffW, j*H + H + BuffH,
				i*W + BuffW, j*H + H + BuffH
		};
		this.setFill(FillColor);
		this.setStroke(Color.BLACK);
		this.setStrokeWidth(1);
		this.getPoints().addAll(points);
	}

	public void mark() {
		this.setStrokeWidth(5);
		this.setStroke(Color.GREEN);
	}
}
