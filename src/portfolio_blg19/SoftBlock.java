package portfolio_blg19;
import javafx.scene.Group;
//This code is part of my Masterpiece. Brandon Guo.
/**
 * @author blg19
 * This class implements the original block type, the one-hit one-break block. React doesn't do much aside from removing this block. 
 * This is an example of good code because it's relatively straightforward and readable, as well as demonstrates an understanding of inheritance and good OOP
 */
public class SoftBlock extends Block {
	public SoftBlock(double xPos, double yPos, double xLength, double yLength, double Points, boolean Exist) {
		super(xPos, yPos, xLength, yLength, Points, Exist);}
	public void react(Group G, Ball B, Paddle P1, Paddle P2, Block[][] map, double accel, int ObjCnt, int i, int j) {
		G.getChildren().remove(this);
		this.destroy();}
}
