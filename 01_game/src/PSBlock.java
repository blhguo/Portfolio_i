import javafx.scene.Group;
//This code is part of my Masterpiece. Brandon Guo.
/**
 * @author blg
 * This class represents the Paddle Speed block. It's react method changes the speed of both player's paddle. If unique impacts to each player are desired, "ID" token is needed from ball, currently unimplemented. 
 * This is good code because it's simplicity, as opposed to the original mess in the framework, demonstrates the importance of using inheritance and OOP
 */
public class PSBlock extends Block {
	public PSBlock(int xPos, int yPos, int xLength, int yLength, int Points, boolean Exist) {
		super(xPos, yPos, xLength, yLength, Points, Exist);
	}
	public void react(Group G, Ball B, Paddle P1, Paddle P2, Block[][] map, double accel, int ObjCnt, int i, int j) {
		P1.ChangeVel(accel);
		P2.ChangeVel(accel);
		G.getChildren().remove(this);
		}
}