package game01.actors;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D.Double;
import java.util.ArrayList;

import beta02.Actor;
import game01.RotatingSegment;

public class RotatingSwagRectangle extends Actor {
    //inherited viewPort, position, xPos, colorType
    //inherited angularSpeed, Polygon body, angle

    private ArrayList<RotatingSegment> pieces = new ArrayList<RotatingSegment>();
    private double length;
    private double angle;
    private double angularSpeed;
    private boolean increasing = false;

    public RotatingSwagRectangle(Rectangle viewPort, double position, double xPos, double angle, double angularSpeed, double length) {
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
        for (int i = 0; i < 4; i++) {
            RotatingSegment piece = new RotatingSegment(viewPort, position, 90 * i, xPoints, yPoints);
            piece.setColorType(i + 1);
            piece.setPivot(xPosition, position);
            piece.setRotate();
            if (i == 0) {
                piece.translate(0, 0);
            }
            if (i == 1) {
                piece.translate(0, 0);
            }
            if (i == 2) {
                piece.translate(length, length);
            }
            if (i == 3) {
                piece.translate(length, length);
            }
            piece.setPivot(xPosition, position);
            piece.rotateToAngle(theta);
            pieces.add(piece);
        }
    }

    public void update(double moveDownDistance) {
        super.update(moveDownDistance);
        for (int i = 0; i < pieces.size(); i++) {
            pieces.get(i).translate(0, moveDownDistance);
        }
    }

    public void render(Graphics2D g) {

        System.out.println(length);
        if (length > 400) {
            increasing = false;
        } else if (length < 250) {
            increasing = true;
        }
        if (increasing) {
            length += 1;
        } else {
            length -= 1;
        }
        angle++;
        initPieces(angle);
        for (int i = 0; i < pieces.size(); i++) {
            pieces.get(i).render(g);
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
