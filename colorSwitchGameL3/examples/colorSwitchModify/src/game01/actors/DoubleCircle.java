package game01.actors;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Double;
import java.util.ArrayList;

import beta02.Actor;
import beta02.ColorSwitch;
import game01.ColoredArc;
import game01.Rotator;
import game01.ScoreStar;
import game01.Switcher;

public class DoubleCircle extends Actor{
	private Rotator circle1;
	private Rotator circle2;
	private double diameter;
	//inherited from Actor: viewPort, position, xPos
	public DoubleCircle(Rectangle viewPort, double startPosition) {
		this(viewPort, startPosition, 0,0, 200);
	}
	public DoubleCircle(Rectangle viewPort, double startPosition, double angularSpeed, double currentAngle, double diameter) {
		super(viewPort, startPosition);
		this.star=new ScoreStar(viewPort, startPosition + diameter/2);
		this.switcher = new Switcher(viewPort, startPosition - 120);
		this.diameter=diameter;
		circle1 = new Rotator(viewPort, startPosition, angularSpeed, currentAngle, diameter);
		circle1.translate(-diameter/2);
		circle1.clearStarSwitcher();
		circle2 = new Rotator(viewPort, startPosition, -1*angularSpeed, currentAngle+90, diameter);
		circle2.translate(diameter/2); circle2.flip();
		circle2.clearStarSwitcher();
	}
	public void update() {
		circle1.update();
		circle2.update();
	}
	public void update(double moveDownDistance) {
		super.update(moveDownDistance);
		circle1.update(moveDownDistance);
		circle2.update(moveDownDistance);
	}
	@Override
	public void render(Graphics2D g) {
		super.render(g);
		circle1.render(g);
		circle2.render(g);
	}
	public boolean collidesWith(Double body, int bodyColor) {
		// TODO Auto-generated method stub
		return(circle1.collidesWith(body, bodyColor) || circle2.collidesWith(body, bodyColor));
	}
	public boolean inViewPort() {
		return (position+diameter>0 && position < viewPort.height);
	}
	public double getTopPosition() {
		// TODO Auto-generated method stub
		return position;
	}

}
