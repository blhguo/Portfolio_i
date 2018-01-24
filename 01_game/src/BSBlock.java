import javafx.scene.Group;
//This code is part of my Masterpiece. Brandon Guo.
/**
 * @author blg19
 * This class creates the Ball-speed block, which changes the ball that made contact's speed. It's react method implements this effect
 * This class is well designed because it is readable and demonstrates the power of inheritance. In this case, if I wanted BSBlock to change the game in some other way or another way, I'd only have to update the "react" method for this single class. 
 */
public class BSBlock extends Block {
	public BSBlock(int xPos, int yPos, int xLength, int yLength, int Points, boolean Exist) {
		super(xPos, yPos, xLength, yLength, Points, Exist);}
	public void react(Group G, Ball B, Paddle P1, Paddle P2, Block[][] map, double accel, int ObjCnt, int i, int j) {
		B.ChangeXVel(accel);
		B.ChangeYVel(accel);
		G.getChildren().remove(this);}
}