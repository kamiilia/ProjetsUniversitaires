package game01.actors;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D.Double;
import java.util.ArrayList;

import beta02.Actor;
import game01.RotatingSegment;

public class RotatingWhiteBar extends Actor {
    //inherited viewPort, position, xPos, colorType
    //inherited angularSpeed, Polygon body, angle

    private ArrayList<RotatingSegment> pieces = new ArrayList<RotatingSegment>();
    private double length;
    private double angle;
    private double angularSpeed = 1;
    private boolean increasing = false;

    public RotatingWhiteBar(Rectangle viewPort, double position, double xPos, double angle, double angularSpeed, double length) {
        super(viewPort, position);
        this.xPosition = xPos;
        this.angle = angle;
        this.angularSpeed = angularSpeed;
        this.length = length;
        initPieces(0);
    }

    public void initPieces(double theta) {
        pieces.clear();
        double startX = viewPort.width / 2 - length / 2;
        double[] xPoints = {xPosition, xPosition + length, xPosition + length, xPosition};
        double[] yPoints = {position, position, position + 10, position + 10};
        RotatingSegment piece = new RotatingSegment(viewPort, position, 90, xPoints, yPoints);
        piece.setColorType(0);
        piece.setPivot(xPosition, position);
        piece.setRotate();
        piece.setPivot(xPosition, position);
        pieces.add(piece);
    }

    public void update(double moveDownDistance) {
        super.update(moveDownDistance);
        for (int i = 0; i < pieces.size(); i++) {
            pieces.get(i).translate(0, moveDownDistance);
        }
    }

    public void render(Graphics2D g) {
        for (int i = 0; i < pieces.size(); i++) {
            pieces.get(i).render(g);
            pieces.get(i).rotate(angularSpeed);
        }
    }

    @Override
    public boolean collidesWith(Double body, int bodyColor) {
        // TODO Auto-generated method stub
//		for(RotatingSegment piece: pieces) {
//			if (piece.collidesWith(body, bodyColor)) {
//				return true;
//			}
//		}
        return false;
    }

}
