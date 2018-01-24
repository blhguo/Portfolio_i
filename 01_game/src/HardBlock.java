import javafx.scene.Group;
//This code is part of my Masterpiece. Brandon Guo.
/**
 * @author blg19
 * This class represents the "sturdy block", a block that takes two hits to destroy. It's implementation of "react" reflects this.
 * This is good code because it's part of the demonstration that OOP and inheritance allow for much easier editing of code and more modular code
 */
public class HardBlock extends Block {
	public int lives;
	public HardBlock(int xPos, int yPos, int xLength, int yLength, int Points, boolean Exist) {
		super(xPos, yPos, xLength, yLength, Points, Exist);
		this.lives = 2;
	}
	public void react(Group G, Ball B, Paddle P1, Paddle P2, Block[][] map, double accel, int ObjCnt, int i, int j) {
		if (this.lives == 1) {
			G.getChildren().remove(this);
			this.destroy();
		}
		else
			this.lives = this.lives - 1;
		}
}