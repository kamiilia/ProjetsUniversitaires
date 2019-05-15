package game01.actors;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D.Double;
import java.util.ArrayList;

import beta02.Actor;
import game01.RotatingSegment;
import game01.ScoreStar;
import game01.Switcher;

public class RotatingBen extends Actor {
	//inherited viewPort, position, xPos, colorType
    //inherited angularSpeed, Polygon body, angle

    private ArrayList<RotatingSegment> pieces = new ArrayList<RotatingSegment>();
    private double length;
    private double angle;
    private double angularSpeed;

    public RotatingBen(Rectangle viewPort, double position, double xPos, double angle, double angularSpeed, double length) {
        super(viewPort, position);
        this.star = new ScoreStar(viewPort, position + length / 2);
        this.switcher = new Switcher(viewPort, position - 2 * length);
        this.xPosition = xPos;
        this.angle = angle;
        this.angularSpeed = angularSpeed;
        this.length = length;
        initPieces();
    }

    public void initPieces() {
        double[] xPoints = {xPosition, xPosition + length / 2, xPosition + length / 2};
        double[] yPoints = {position, position, position - length / 1.7};
        for (int i = 0; i < 4; i++) {
            RotatingSegment piece = new RotatingSegment(viewPort, position, 90 * i, xPoints, yPoints);
            piece.setAngularSpeed(angularSpeed);
            piece.setPivot(xPosition + length / 2, position + length / 2 + 10);
            piece.setColorType(i + 1);
            piece.translate(-length / 2, 0);
            pieces.add(piece);
        }
    }

    public void update() {
        for (int i = 0; i < pieces.size(); i++) {
            pieces.get(i).rotate();
        }
    }

    public void update(double moveDownDistance) {
        super.update(moveDownDistance);
        for (int i = 0; i < pieces.size(); i++) {
            pieces.get(i).translate(0, moveDownDistance);
        }
    }

    public void render(Graphics2D g) {
        super.render(g);
        for (int i = 0; i < pieces.size(); i++) {
            pieces.get(i).render(g);
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
        return position - length / 1.7;
    }

}
