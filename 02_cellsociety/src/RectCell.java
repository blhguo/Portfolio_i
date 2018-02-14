import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class RectCell extends Polygon {

	public void Build(int i, int j, int W, int H, double BuffW, double BuffH, Color FillColor) {
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
