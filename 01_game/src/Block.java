import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
//This code is part of my Masterpiece. Brandon Guo.
/**
 * @author blg19
 * This is an abstract class for block effects, with the only different method between different blocks being the "react" method, which computes the effect on the game. It is good code because it demonstrates an understanding of inheritance and abstract classes, and good OOP. It should be noted that, if some effects such as paddle speed, are intended to affect only one player, then an additional parameter of "ID" might need to be added to ball in order to decide which paddle/ball/player to impact
 * React requires so many input variables due to the fact that each block type affects different aspects of the game, and because "react" is abstract, it must include all these inputs
 */
public abstract class Block extends Rectangle{
	public double pVal;
	public boolean Exists;
	public Block(double xPos, double yPos, double xLength, double yLength, double Points, boolean Exist) {
		super(xPos, yPos, xLength, yLength);
		this.pVal = Points;
		this.Exists = Exist;}
	public void destroy() {
		this.Exists = false;}
	public abstract void react(Group G, Ball B, Paddle P1, Paddle P2, Block[][] map, double accel, int ObjCnt, int i, int j);
	public boolean isHWall(Ball B) {
		return (((B.getCenterY() + B.getRadius()) > this.getY()) && ((B.getCenterY() - B.getRadius()) < (this.getY() + this.getHeight())));}
	public boolean isVWall(Ball B) {
		return (((B.getCenterX() + B.getRadius()) > this.getX()) && ((B.getCenterX() - B.getRadius()) < (this.getX() + this.getWidth())));}
}