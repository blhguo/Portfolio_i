package portfolio_blg19;
import javafx.scene.Group;
//This code is part of my Masterpiece. Brandon Guo.
/**
 * @author blg19
 * This class creates the most problematic block I have, the explosive block. The react method removes this block, as well as removes the surrounding blocks, whose relationship to the explosive is preserved by the 2D matrix Map.
 * This is an example of good code because it demonstrates an albeit messy implementation of the explosive block's reaction. A potentially significant solution would be to make the explosive block have children or pointers to its surrounding blocks, and to have react simple remove all children/remove all pointed to blocks. However, this brings up the challenge of determining, potentially with another method, the surrounding blocks, which ultimately requires the same functional switch case. 
 */
public class EXPBlock extends Block {
	public EXPBlock(int xPos, int yPos, int xLength, int yLength, int Points, boolean Exist) {
		super(xPos, yPos, xLength, yLength, Points, Exist);
	}
	
	public void react(Group G, Ball B, Paddle P1, Paddle P2, Block[][] map, double accel, int ObjCnt, int i, int j) {
		G.getChildren().remove(this);
		int WIDTHOFMATRIX = map.length;
		if ((j < (WIDTHOFMATRIX-1)) && (G.getChildren().contains(map[i][j+1]))) {
			G.getChildren().remove(map[i][j+1]); map[i][j+1].destroy();
			}
		if ((j > 0) && (G.getChildren().contains(map[i][j-1]))) {
			G.getChildren().remove(map[i][j-1]); map[i][j-1].destroy();
			}
		if ((i < (WIDTHOFMATRIX-1)) && (G.getChildren().contains(map[i+1][j]))) {
			G.getChildren().remove(map[i+1][j]); map[i+1][j].destroy();
			}
		if ((i < 0) && (G.getChildren().contains(map[i-1][j]))) {
			G.getChildren().remove(map[i-1][j]); map[i-1][j].destroy();
			}
	}
}