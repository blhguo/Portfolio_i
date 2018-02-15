import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
/**
 * @author blg19
 * Image that returns a polygon that will represent a hexagon
 *
 */
public class HexCell extends GridCell {
	
	public void Build(Cell input, double BuffW, double BuffH) {
		int i = input.get_xpos();
		int j = input.get_ypos();
		int W = input.get_W();
		int H = input.get_H();
		Color FillColor = input.getDisplayColor();
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
