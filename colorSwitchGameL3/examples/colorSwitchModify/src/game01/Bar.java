package game01;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D.Double;

import beta02.Actor;

public class Bar extends Actor {
	private int colorType;
	
	private int barThickness = 40;
	
	//in the actor class
	//viewPort, position, xPos
	
	public Bar() {
		super();
		colorType = 1+ (int) (Math.random()*5); //random int 1 to 5
	}
	
	public Bar(Rectangle viewPort, double position) {
		super(viewPort, position);
	}
	
	public void update(double moveDownDistance) {
		position += moveDownDistance;
	}
	
	/** please put in y coordinate directly
	 * @param pos
	 */
	public void setViewPort(Rectangle viewPort) { 
		this.viewPort = viewPort; 
	}
	public void setColor(int c) {
		colorType = c;
	}
	public Color barColor() {
		switch(colorType) {
		case 1:
			return Color.RED;
		case 2:
			return Color.BLUE;
		case 3: 
			return Color.MAGENTA;
		case 4:
			return Color.YELLOW;
		default: 
			return Color.WHITE;
		}
	}
	@Override
	public void render(Graphics2D g) {
		// TODO Auto-generated method stub
			
			g.setColor(barColor());
			if(position+barThickness > 0 && position < viewPort.getHeight()){
				g.fillRect((int) viewPort.getMinX(), (int) (viewPort.getMinY()+position), (int) viewPort.getWidth(), 50);
			}
	}

	@Override
	public boolean collidesWith(Double body, int bodyColor) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
}