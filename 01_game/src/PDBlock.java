import javafx.scene.Group;
//This code is part of my Masterpiece. Brandon Guo.
/**
 * @author blg19
 * This class creates the paddle direction block, which causes both user controls to switch. As with before, and "ID" tag must be a part of "ball" in order to selectively implement this feature. 
 * This code is included in my masterpiece because it is a part of the demonstration of inheritance of methods.
 */
public class PDBlock extends Block {
	public PDBlock(int xPos, int yPos, int xLength, int yLength, int Points, boolean Exist) {
		super(xPos, yPos, xLength, yLength, Points, Exist);}
	public void react(Group G, Ball B, Paddle P1, Paddle P2, Block[][] map, double accel, int ObjCnt, int i, int j) {
		P1.ChangeDir();
		P2.ChangeDir();
		G.getChildren().remove(this);}
}