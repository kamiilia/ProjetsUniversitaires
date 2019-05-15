package game01.actors;

import game01.RotatingSegment;
import game01.ScoreStar;
import game01.Switcher;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D.Double;

import beta02.Actor;

public class RotatingDoubleCross extends Actor{
	private double length;
	private double angle;
	private double angularSpeed;
	private RotatingPlus plusOne;
	private RotatingPlusOther plusTwo; //to preserve correct orientation of this
	
	public  RotatingDoubleCross (Rectangle viewPort, double position, double length) {
		this(viewPort, position, length, 1);
	}
	public  RotatingDoubleCross (Rectangle viewPort, double position, double length, double angularSpeed) {
		super(viewPort, position);
		this.angularSpeed = angularSpeed;
		this.length = length;
		plusOne = new RotatingPlus(viewPort, position, viewPort.getWidth()/2-length, 0, angularSpeed, length);
		plusTwo = new RotatingPlusOther(viewPort, position, viewPort.getWidth()/2+length, 180, -1*angularSpeed, length);
		star = new ScoreStar(viewPort, position);
		switcher = new Switcher(viewPort, position - 200);
	}
	
	public void render(Graphics2D g) {
		super.render(g);
		plusOne.render(g);
		plusTwo.render(g);
	}
	public void update() {
		plusOne.update();
		plusTwo.update();
	}
	public void setSpeed(double angSpd) {
		plusOne.setAngularSpeed(angSpd);
		plusTwo.setAngularSpeed(angSpd);
	}
	public void update(double moveDownDistance) {
		super.update(moveDownDistance); // moves star as well as base actor
		plusOne.update(moveDownDistance);
		plusTwo.update(moveDownDistance);
	}
	public boolean collidesWith(Double body, int bodyColor) {
		return(plusOne.collidesWith(body, bodyColor) || plusTwo.collidesWith(body, bodyColor));
	}
	public double getTopPosition() {
		// TODO Auto-generated method stub
		return position - length;
	}
	public double getSpeed() {
		return angularSpeed;
	}
	
	

}
