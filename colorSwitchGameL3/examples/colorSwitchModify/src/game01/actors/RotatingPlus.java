package game01.actors;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D.Double;
import java.util.ArrayList;

import beta02.Actor;
import game01.RotatingSegment;

public class RotatingPlus extends Actor {
	//inherited viewPort, position, xPos, colorType
    //inherited angularSpeed, Polygon body, angle

    private ArrayList<RotatingSegment> pieces = new ArrayList<RotatingSegment>();
    private double length;
    private double angle;
    private double angularSpeed;

    public RotatingPlus(Rectangle viewPort, double position, double xPos, double angle, double angularSpeed, double length) {
        super(viewPort, position);
        this.xPosition = xPos;
        this.angle = angle;
        this.angularSpeed = angularSpeed;
        this.length = length;
        initPieces();
    }

    public void setAngularSpeed(double s) {
        angularSpeed = s;
    }

    public void initPieces() {
        double startX = viewPort.width / 2 - length / 2;
        double[] xPoints = {xPosition, xPosition + length, xPosition + length, xPosition};
        double[] yPoints = {position, position, position + length / 10, position + length / 10};
        for (int i = 0; i < 4; i++) {
            RotatingSegment piece = new RotatingSegment(viewPort, position, 90 * i, xPoints, yPoints);
            piece.setPivot(xPosition + length / 50, position + length / 20);
            piece.setColorType(i + 1);
            piece.setAngularSpeed(this.angularSpeed);
            pieces.add(piece);
        }
    }

    public void update(double moveDownDistance) {
        super.update(moveDownDistance);
        for (int i = 0; i < pieces.size(); i++) {
            pieces.get(i).translate(0, moveDownDistance);
        }
    }

    public void update() {
        for (int i = 0; i < pieces.size(); i++) {
            pieces.get(i).rotate();
        }
    }

    public void render(Graphics2D g) {
        for (int i = 0; i < pieces.size(); i++) {
            pieces.get(i).render(g);
            pieces.get(i).rotate();
        }
    }

    @Override
    public boolean collidesWith(Double body, int bodyColor) {
        // TODO Auto-generated method stub
        for (RotatingSegment piece : pieces) {
            if (piece.collidesWith(body, bodyColor)) {
                return true;
            }
        }
        return false;
    }

    public double getTopPosition() {
        // TODO Auto-generated method stub
        return position - length;
    }

}
