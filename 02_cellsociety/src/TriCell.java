import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

/**
 * @author blg19
 * Image that returns a polygon that will represent a triangle
 *
 */
public class TriCell extends GridCell {

	public void Build(Cell input, double BuffW, double BuffH) {
		int i = input.get_xpos();
		int j = input.get_ypos();
		int W = input.get_W();
		int H = input.get_H();
		Color FillColor = input.getDisplayColor();
		Double[] points;
		if ((i % 2)== 0) {
			points = new Double[] {
					W * j + W + BuffW, H * (j % 2) + H * i + BuffH, 
					W * j + 2*W + BuffW, H * ((j + 1) % 2) + H * i + BuffH, 
					W * j + BuffW, H * ((j + 1)% 2) + H * i + BuffH
			};
		}
		else {
			points = new Double[] {
					W * j + W + BuffW, H * ((j + 1) % 2) + H * i + BuffH, 
					W * j + 2*W + BuffW, H * (j % 2) + H * i + BuffH, 
					W * j + BuffW, H * (j % 2) + H * i + BuffH
			};
		}
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
