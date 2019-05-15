package game01.actors;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D.Double;
import java.util.ArrayList;

import beta02.Actor;
import game01.RotatingSegment;

public class RotatingTriangle extends Actor {
	//inherited viewPort, position, xPos, colorType
    //inherited angularSpeed, Polygon body, angle

    private ArrayList<RotatingSegment> pieces = new ArrayList<RotatingSegment>();
    private double length;
    private double angle;
    private double angularSpeed;

    public RotatingTriangle(Rectangle viewPort, double position, double xPos, double angle, double angularSpeed, double length) {
        super(viewPort, position);
        this.xPosition = xPos - length / 2;
        this.angle = angle;
        this.angularSpeed = angularSpeed;
        this.length = length;
        initPieces();
    }

    public void initPieces() {
        double startX = viewPort.width / 2 - length / 2;
        double[] xPoints = {xPosition, xPosition + length, xPosition + length, xPosition};
        double[] yPoints = {position, position, position + 10, position + 10};
        int startingColor = (int) (Math.random() * 4);
        for (int i = 1; i <= 3; i++) {
            RotatingSegment piece = new RotatingSegment(viewPort, position, 120 * i, xPoints, yPoints);
            piece.setPivot(xPosition + length / 2, position + length / 2 * Math.sqrt(3) * 1 / 3 + 7);
            piece.setColorType((i + startingColor) % 4 + 1);
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
        for (int i = 0; i < pieces.size(); i++) {
            pieces.get(i).render(g);
            pieces.get(i).rotate();
        }
    }

    @Override
    public boolean collidesWith(Double body, int bodyColor) {
		// triangle collisoin disabled for now
		/*
         for(RotatingSegment piece: pieces) {
         if (piece.collidesWith(body, bodyColor)) {
         return true;
         }
         } */

        return false;
    }

}
