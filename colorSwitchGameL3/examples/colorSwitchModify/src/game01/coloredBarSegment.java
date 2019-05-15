package game01;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import beta02.ColorSwitch;

public class coloredBarSegment extends Rectangle.Double{
	private int colorType = 1;

	public coloredBarSegment(double w, double h, int colorType) {
		super(0,0, w, h);
		this.colorType = colorType;
	}
	
	public int getColorType() {
		return colorType;
	}
	public void draw(Graphics2D g) {
		g.setColor(getColor());
		g.fill(this);
	}
	
	//draws the rectangle with respect to the viewport
	public void draw(Graphics2D g, double smallestX, double smallestY, double viewPortWidth) {
		g.setColor(getColor());
		if(this.getMaxX()<viewPortWidth) {
			g.fill(new Rectangle.Double(this.x+smallestX, this.y+smallestY, this.width, this.height));
		} else{ 
			double startX = this.getX()+smallestX;
			double endX = viewPortWidth+smallestX;
			//extra part is equal to total length minus part displayed (end-start) 
			double extraPart = this.getWidth()-(endX-startX);
			g.fill(new Rectangle.Double(startX, this.y+smallestY, endX-startX, this.height));
			g.fill(new Rectangle.Double(smallestX, this.y+smallestY, extraPart, this.height));
		}
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