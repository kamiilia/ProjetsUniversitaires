package game01;

import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Double;
import java.util.Arrays;

import beta02.Actor;

public class RotatingSegment extends Actor{
	//inherited viewPort, position, xPos, colorType
	protected double angularSpeed = 1;
	protected Polygon body;
	protected double[] xpoints;
	//fixed, to ensure accuracy
	protected double[] ypoints;
	//fixed, to ensure accuracy
	protected double angle;
	protected double pivotX = 0; //rotation pivot
	protected double pivotY = 0; //rotation pivot
	
	public RotatingSegment() {
		super();
		this.angularSpeed = 0;
		this.body = new Polygon();
		this.angle = 0;
	}
	public RotatingSegment(Rectangle viewPort, double position) {
		super(viewPort, position);
	}
	public void rotate() {
		angle += angularSpeed;
	}
	public void rotateToAngle(double theta) {
		angle = theta;
	}
	public void rotate(double a) {
		angle += a;
	}
	
	public RotatingSegment(Rectangle viewPort, double position, double angle, double[] xcoords, double[] ycoords) {
		super(viewPort, position);
		xpoints = Arrays.copyOf(xcoords, xcoords.length);
		ypoints = Arrays.copyOf(ycoords, ycoords.length);
		this.angle = angle;
		createBody(xpoints, ypoints);
	}
	
	public void translate(double x, double y) {
		for(int i = 0; i<xpoints.length; i++) {
			ypoints[i]+=y;
			xpoints[i]+=x;
		}
		position += y;
		pivotX+=x;
		pivotY+=y;
	}
	
	/**
	 * fixes the rotation by setting the angle to zero and applying the rotation transformation to the xpoints and ypoints 
	 * arrays
	 */
	public void setRotate() {
		int npoints = xpoints.length;
		double[] tempxpoints = new double[xpoints.length];
		double[] tempypoints = new double[xpoints.length];
		double radians = Math.toRadians(angle%360);
		double y = pivotY;
		double x = pivotX;
		for(int i = 0; i<xpoints.length; i++) {
			tempxpoints[i] = (Math.cos(radians)*(xpoints[i]-x)-Math.sin(radians)*(ypoints[i]-y)+x);
			tempypoints[i] = (Math.sin(radians)*(xpoints[i]-x)+Math.cos(radians)*(ypoints[i]-y)+y);
		}
		xpoints = tempxpoints;
		ypoints = tempypoints;
		angle = 0;
	}
	public void setCoordinates(double[] xs, double[] ys) {
		xpoints = xs;
		ypoints = ys;
	}
	public void setAngularSpeed(double speed) {
		angularSpeed = speed;
	}
	public void setPivot(double x, double y) {
		pivotX = x;
		pivotY = y;
	}
	public void createBody(double[] x, double[] y) {
		xpoints = x;
		ypoints = y;
		int npoints = xpoints.length;
		int[] tempx = new int[xpoints.length];
		int[] tempy = new int[ypoints.length];	
		for(int i = 0; i<npoints; i++) {
			tempx[i] = (int) (xpoints[i]+0.5);
			tempy[i] = (int) (ypoints[i]+0.5);
		}
		body = new Polygon(tempx, tempy, xpoints.length);
		pivotX = body.getBounds().width/2;
		pivotY = body.getBounds().height/2;
		
	}
	
	/**
	 * Rotates the body theta degrees around the pivot and returns new body
	 */
	public Polygon getBody() {
		return getRotatedBody(angle);
	}
	
	public void addPoint(double x, double y) {
		double[] tempx = new double[xpoints.length+1];
		double[] tempy = new double[ypoints.length+1];
		for(int i = 0; i<xpoints.length; i++) {
			tempx[i]=xpoints[i];
			tempy[i]=ypoints[i];
		}
		tempx[xpoints.length] = x;
		tempy[xpoints.length] = y;
		xpoints = tempx;
		ypoints = tempy;
	}
	public Polygon getRotatedBody(double theta, double x, double y) {
		int[] intxpoints = new int[xpoints.length];
		int[] intypoints = new int[xpoints.length];
		double radians = Math.toRadians(theta);
		for(int i = 0; i<body.npoints; i++) {
			intxpoints[i] = (int) (Math.cos(radians)*(xpoints[i]-x)-Math.sin(radians)*(ypoints[i]-y)+x+0.5);
			intypoints[i] = (int) (Math.sin(radians)*(xpoints[i]-x)+Math.cos(radians)*(ypoints[i]-y)+y+0.5);
		}
		return new Polygon(intxpoints, intypoints, body.npoints);
	}
	public Polygon getRotatedBody(double theta) {
		return getRotatedBody((theta)%360, pivotX, pivotY);
	}
	
	public boolean collidesWith(Ellipse2D.Double body, int bodyColor) {
		// TODO Auto-generated method stub
		Area currentBody = new Area(getRotatedBody(angle));
		Area playerArea = new Area(body);
		playerArea.intersect(currentBody);
		return !(playerArea.isEmpty() || this.getColorType() == bodyColor) ;
		//return false;
	}
	
	@Override
	public void render(Graphics2D g) {
		g.setColor(getColor());
		Polygon tempBody = getBody();
		tempBody.translate(viewPort.x, viewPort.y);
		g.fill(tempBody);
		//to debug the pivot point
		//g.fillOval((int)(viewPort.x+pivotX-10/2), (int)(viewPort.y+pivotY-10/2), 10, 10);
	}
}
