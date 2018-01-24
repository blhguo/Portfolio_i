package portfolio_blg19;
import javafx.scene.shape.Rectangle;
//This code is part of my Masterpiece. Brandon Guo.
/**
 * @author blg19
 * This class extends Rectangle to include parameters such as velocity and methods to alter the parameters. 
 * Effectively unchanged from original submission, just here to ensure compilation.
 */
public class Paddle extends Rectangle{
    public double Vel;
    public Paddle(int xPos, int yPos, int xLength, int yLength, int Velocity) {
    	super(xPos, yPos, xLength, yLength);
    	this.Vel = Velocity;}
    public void ChangeVel(double accel) {
    	this.Vel = this.Vel + accel;}    
    public void ChangeDir() {
    	this.Vel = -1*this.Vel;}
}
