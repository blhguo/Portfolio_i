import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class TriCell extends Polygon {

	public void Build(int i, int j, int W, int H, double BuffW, double BuffH, Color FillColor) {
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
