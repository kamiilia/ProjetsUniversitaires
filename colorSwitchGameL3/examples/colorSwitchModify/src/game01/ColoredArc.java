package game01;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Arc2D;

public class ColoredArc extends Arc2D.Double {
	private int colorType = 1;
	private int currentAngle;
	private Rectangle viewPort;
	public int getColorType() {
		return colorType;
	}
	public ColoredArc() {
		super();
	}
	public ColoredArc(double x, double y, double width, double height, double startAngle, double arcLeng) {
		super(x,y,width,height,startAngle,arcLeng, Arc2D.OPEN);
		this.viewPort = viewPort;
	}
	public void setColor(int c) {
		colorType = c;
	}
	public void draw(Graphics2D g, Rectangle viewPort) {
		//g.drawArc(this.x+viewPort.getMinX(), this.y+viewPort.getMinY(), this.getWidth(), this.getWidth(), (int) currentAngle,90);
	}
	private Color getColor() {
		switch(colorType) {
		case 1:
			return new Color(128,0,128);
		case 2:
			return Color.CYAN;
		case 3: 
			return Color.YELLOW;
		case 4:
			return Color.RED;
		default: 
			return Color.WHITE;
		}
	}
}
