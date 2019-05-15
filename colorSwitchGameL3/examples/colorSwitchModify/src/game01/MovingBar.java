package game01;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D.Double;
import java.util.ArrayList;

import beta02.Actor;

public class MovingBar extends Actor {

    //inherited from Actor: viewPort, position, xPos
    private double speed = -2;
    private double height = 20;
    private double emptySpace = 130;
    private ArrayList<coloredBarSegment> rectangles = new ArrayList<coloredBarSegment>();

    public MovingBar(Rectangle viewPort, double startPosition) {
        super(viewPort, startPosition);
        star = new ScoreStar(viewPort, startPosition - emptySpace / 2, 25, 10);
        switcher = new Switcher(viewPort, startPosition - emptySpace);
        xPosition = 0;
        for (int i = 1; i <= 4; i++) {
            rectangles.add(new coloredBarSegment(viewPort.getWidth() / 4, height, i));
        }
    }

    public MovingBar(Rectangle viewPort, double startPosition, double xPos) {
        this(viewPort, startPosition);
        this.xPosition = xPos;
    }

    public void setSpeed(double s) {
        speed = s;
    }

    public void render(Graphics2D g) {
        super.render(g);
        xPosition = (xPosition + speed) % viewPort.width;
        if (xPosition < 0) {
            xPosition += viewPort.width;
        }
        for (int i = 0; i < 4; i++) {
            double rectWidth = viewPort.getWidth() / 4;
            rectangles.get(i).setRect((xPosition + rectWidth * i) % viewPort.width, position, rectWidth, height);
            rectangles.get(i).draw(g, viewPort.getMinX(), viewPort.getMinY(), viewPort.getWidth());
        }
    }

    public boolean collidesWith(Double body, int bodyColor) {
        for (int i = 0; i < rectangles.size(); i++) {
            coloredBarSegment currentRec = rectangles.get(i);
            if (body.intersects(currentRec) && currentRec.getColorType() != bodyColor) {
                return true;
            }
        }
        return false;
    }

    public void setHeight(double height2) {
        height = height2;
    }

    public double getTopPosition() {
        // TODO Auto-generated method stub
        return position - height - emptySpace;
    }
}
