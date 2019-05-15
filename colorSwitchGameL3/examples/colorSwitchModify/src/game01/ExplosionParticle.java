package game01;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;

import beta02.ColorSwitch;

public class ExplosionParticle {
	public Rectangle viewPort;
	private double diameter;
	private int colorType;
	private double xPos;
	private double yPos;
	private double xVel;
	private double yVel;
	private double gravity = 0.1;
	public ExplosionParticle(Rectangle viewPort, double xPos, double yPos, double angle, double velocity, int colorType, double diameter) {
		this.viewPort = viewPort;
		this.xPos = xPos;
		this.yPos = yPos;
		this.diameter = diameter;
		this.colorType = colorType;
		yVel = Math.sin(Math.toRadians(angle))*velocity;
		xVel = Math.cos(Math.toRadians(angle))*velocity;
	}
	public void update() {
		if(xPos>viewPort.getWidth() || xPos < 0) xVel *=-1;
		xPos += xVel;
		yPos += yVel;
		yVel += gravity;
		
	}
	public void boostYvel(double amount) {
		yVel += amount;
	}
	public void draw(Graphics2D g) {
		if(inViewPort() && yPos > 0) {
			g.setColor(getColor());
			g.fill(new Ellipse2D.Double(viewPort.x+xPos-diameter/2, viewPort.y+yPos-diameter/2, diameter, diameter));
		}
	}
	public boolean inViewPort() {
		return(yPos<viewPort.getHeight() && xPos > 0 && xPos <viewPort.width);
	}
	private Color getColor() {
		switch(colorType) {
		case 1:
			return ColorSwitch.colors[0];
		case 2:
			return ColorSwitch.colors[1];
		case 3: 
			return ColorSwitch.colors[2];
		case 4:
			return ColorSwitch.colors[3];
		default: 
			return Color.WHITE;
		}
	}
}
