package portfolio_blg19;
import javafx.scene.Group;
//This code is part of my Masterpiece. Brandon Guo.
/**
 * @author blg19
 * This class creates the God BLock. It's react method ensures that it cannot be destroyed unless the only things left on the screen are the two paddles, two balls, two scoreboards, and itself, at which point it removes itself. 
 * This is included in my Masterpiece because it demonstrates the simplicity and cleanliness of OOP and inheritance. Because each block implements react differently, each block can be individually changed without having to change much, if anything, from the original framework.
 */
public class GDBlock extends Block {
	public GDBlock(int xPos, int yPos, int xLength, int yLength, int Points, boolean Exist) {
		super(xPos, yPos, xLength, yLength, Points, Exist);
	}
	
	public void react(Group G, Ball B, Paddle P1, Paddle P2, Block[][] map, double accel, int ObjCnt, int i, int j) {
		if (G.getChildren().size() == ObjCnt) {
			G.getChildren().remove(this);
			this.destroy();
		}
	}

}