import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class HexCell extends Polygon {

	public void Build(int i, int j, int W, int H, double BuffW, double BuffH, Color FillColor) {
		Double[] points;
		double H1 = 3*H/4;
		if ((i % 2 )== 0) {
			points = new Double[] {
					W * j + W/4 + BuffW, 1*H1* i + BuffH, 
					W * j + W/2 + BuffW, 1*H1* i + BuffH, 
					W * j + 3*W/4 + BuffW, H1* i + 1*H1+ BuffH,
					W * j + W/2 + BuffW, H1* i + 2*H1+ BuffH,
					W * j + W/4 + BuffW, H1* i + 2*H1+ BuffH,
					W * j + BuffW, H1* i + 1*H1+ BuffH
			};
		}
		else {
			points = new Double[] {
					W * j + 3*W/4 + BuffW, 1*H1* i + BuffH, 
					W * j + W + BuffW/1, 1*H1* i + BuffH, 
					W * j + 5*W/4 + BuffW, H1* i + 1*H1+ BuffH,
					W * j + W + BuffW/1, H1* i + 2*H1+ BuffH,
					W * j + 3*W/4 + BuffW, H1* i + 2*H1+ BuffH,
					W * j + W/2 + BuffW, H1 * i + 1*H1 + BuffH
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
