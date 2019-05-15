package game01.actors;

import game01.Bar;
import game01.MovingBar;
import game01.RotatingSegment;
import game01.ScoreStar;
import game01.Switcher;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D.Double;

import beta02.Actor;

public class DoubleBar extends Actor{
	private double height;
	private double spaceBetween;
	private double speed;
	private MovingBar barOne;
	private MovingBar barTwo; //to preserve correct orientation of this
	
	public DoubleBar (Rectangle viewPort, double position, double xPos, double height, double speed, double spaceBetween) {
		super(viewPort, position);
		this.height = height;
		this.speed = speed;
		this.sizeOffset = height + spaceBetween;
		barOne = new MovingBar(viewPort, position, xPos);
		barOne.setHeight(height);
		barOne.setSpeed(speed);
		barOne.clearStarSwitcher();
		barTwo = new MovingBar(viewPort, position-spaceBetween, xPos);
		barTwo.setHeight(height);
		barTwo.setSpeed(-speed);
		barTwo.clearStarSwitcher();
		
		star = new ScoreStar(viewPort, position-spaceBetween/3);
		switcher = new Switcher(viewPort, position - 2*spaceBetween/3);
		
		
		this.spaceBetween = spaceBetween;
	}
	
	public DoubleBar(Rectangle viewPort, double position) {
		this(viewPort, position, 0, 20, 2, 240);
	}
	public void render(Graphics2D g) {
		super.render(g);
		barOne.render(g);
		barTwo.render(g);
		//debugging code
		//g.fillRect((int)xPos+viewPort.x,  (int)position+viewPort.y-20,  30,  30);
	}
	public void update(double moveDownDistance) {
		super.update(moveDownDistance); // moves star as well as base actor
		barOne.update(moveDownDistance);
		barTwo.update(moveDownDistance);
	}
	public boolean collidesWith(Double body, int bodyColor) {
		return(barOne.collidesWith(body, bodyColor) || barTwo.collidesWith(body, bodyColor));
	}
	
	@Override
	public boolean inViewPort() {
		return(position -spaceBetween < viewPort.height && position > 0);
	}
	
	@Override
	
	public double getTopPosition() {
		// TODO Auto-generated method stub
		return position - spaceBetween - height;
	}
	
	

}
