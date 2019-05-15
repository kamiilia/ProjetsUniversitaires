package game01;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Double;
import java.util.ArrayList;

import beta02.Actor;
import beta02.ColorSwitch;
import beta02.Actor;

public class Switcher extends Actor{
	
	private int diameter=30;
	//inherited from Actor: viewPort, position, xPos
	//this actor is always in middle of screen, where ball is.
	
	
	public Switcher () {
		super(new Rectangle(0,0,1280,1024), 1000);
	}
	public Switcher(Rectangle viewPort, double startPosition) {
		super(viewPort, startPosition);
	}
	public void setSize(int d) {
		diameter = d;
	}
	
	@Override
	public void update(double moveDownDistance) {
		position += moveDownDistance;
	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(Color.BLACK);
		if(position+diameter>0 && position<viewPort.height) {
			//g.setColor(Color.white);
			
			int cornerX = (int) (viewPort.getCenterX()-diameter/2);
			int cornerY = (int) (viewPort.getMinY()+position);
			//g.fillOval(cornerX, cornerY, diameter, diameter);
			g.setColor(ColorSwitch.colors[0]);
			g.fillArc(cornerX, cornerY, diameter, diameter, 0,90);
			g.setColor(ColorSwitch.colors[1]);
			g.fillArc(cornerX, cornerY, diameter, diameter, 90,90);
			g.setColor(ColorSwitch.colors[2]);
			g.fillArc(cornerX, cornerY, diameter, diameter, 180,90);
			g.setColor(ColorSwitch.colors[3]);
			g.fillArc(cornerX, cornerY, diameter, diameter, 270,90);
		}
	}

	@Override
	public boolean collidesWith(Double body, int bodyColor) {
		Ellipse2D.Double switcherBody = new Ellipse2D.Double(viewPort.getWidth()/2-diameter/2, position, diameter, diameter);
		Area playerArea = new Area(body);
		Area switcherArea = new Area(switcherBody);
		//gets the intersection of the two areas, then if it's empty, then no intersection, so negation.
		playerArea.intersect(switcherArea);
		return !playerArea.isEmpty();
	}
}
