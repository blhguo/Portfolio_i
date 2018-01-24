package portfolio_blg19;
import javafx.scene.Group;
import javafx.scene.shape.Circle;
/**
 * @author blg19
 *This class extends the javafx class "circle", adding parameters like x-velocity, y-velocity, and score, as well as methods to alter these parameters. In addition, there is a method called impact, which changes the ball's motion and player's score, and calls "react", which updates the screen.
 *This is good code because it demonstrates the adaptations needed to better implement inheritance and OOP
 */
public class Ball extends Circle {
	public double xVel;
	public double yVel;
	public double score;
	public Ball(int xPos, int yPos, int radius, int xVelocity, int yVelocity, int points) {
		super(xPos, yPos, radius);
		this.xVel = xVelocity;
		this.yVel = yVelocity;
		this.score = points;}
	public void ChangeXVel(double accel) {
		this.xVel = this.xVel + accel;}
	public void ChangeYVel(double accel) {
		this.yVel = this.yVel + accel;}
	public void UpdateScore(double point) {
		this.score = this.score + point;}
	public void impact(Group G, Paddle P1, Paddle P2, Block[][] map, int accel, Block B, int ObjCnt, int i, int j) {
		if (B.Exists) {
			if (B.isVWall(this)) {
				this.xVel = -this.xVel;
			}
			if (B.isHWall(this)) {
				this.yVel = -this.yVel;
			}
			UpdateScore(B.pVal);
			B.react(G, this, P1, P2, map, accel, ObjCnt, i, j);
		}
	}
}
