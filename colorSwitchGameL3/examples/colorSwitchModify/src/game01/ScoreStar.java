package game01;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D.Double;

import beta02.Actor;

public class ScoreStar extends Actor {

    //inherited viewPort, position, xPos, colorType
    private Polygon star;
    private double size;
    private double maxSize;
    private double minSize;
    private double angularSpeed;
    private boolean increasing = false;
    private int edges;
    int temp = 270;
    int[] xList;
    int[] yList;

    public ScoreStar(Rectangle viewPort, double position, double xPosition, double s, int edges) {
        super(viewPort, position);
        angularSpeed = 1 - (2 * (int) (Math.random() + 0.5));
        this.edges = edges;
        this.xPosition = xPosition;
        xList = new int[edges];
        yList = new int[edges];
        size = s;
        maxSize = 1.2 * s;
        minSize = 0.8 * s;
        for (int i = 0; i < edges; i++) {
            double angle = 90 + 360.0 / edges * i; //starts vertical, shifts 36 everytime
            if (i % 2 == 0) {
                xList[i] = (int) (xPosition + size * Math.cos(Math.toRadians(angle)));
                //System.out.println("merde xliste1  " + xList[i]);
                yList[i] = (int) (position + size * Math.sin(Math.toRadians(angle)));
            } else {
                xList[i] = (int) (xPosition + 0.5 * size * Math.cos(Math.toRadians(angle)));
                //System.out.println("merde xliste2  " + xList[i]);
                yList[i] = (int) (position + 0.5 * size * Math.sin(Math.toRadians(angle)));
            }
        }
        star = new Polygon(xList, yList, edges);
    }

    public ScoreStar(Rectangle viewPort, double position, double s, int edges) {
        this(viewPort, position, viewPort.width / 2, s, edges);
    }

    public ScoreStar(Rectangle viewPort, double position) {
        this(viewPort, position, viewPort.width / 2, 25, 10);
    }

    public void render(Graphics2D g) {
        temp += angularSpeed;
        temp %= 360;
        g.setColor(Color.white);

        //code to change star size pulsation
        if (size > maxSize) {
            increasing = false;
        }
        if (size < minSize) {
            increasing = true;
        }
        if (increasing) {
            size *= 1.005;
        } else {
            size *= 0.995;
        }

        for (int i = 0; i < edges; i++) {
            double angle = temp + 360.0 / edges * i; //starts vertical, shifts 36 everytime
            //code to define vertices of star
            if (i % 2 == 0) {
                xList[i] = (int) (xPosition + size * Math.cos(Math.toRadians(angle)));
                yList[i] = (int) (position + size * Math.sin(Math.toRadians(angle)));
            } else {
                xList[i] = (int) (xPosition + 0.5 * size * Math.cos(Math.toRadians(angle)));
                yList[i] = (int) (position + 0.5 * size * Math.sin(Math.toRadians(angle)));
            }
        }
        star = new Polygon(xList, yList, edges);
        g.fill(relativeToViewPort(star));
    }

    public Polygon relativeToViewPort(Polygon star) {
        Polygon tempstar = new Polygon(xList, yList, edges);
        tempstar.translate((int) viewPort.getMinX(), (int) viewPort.getMinY());
        return tempstar;
    }

    public boolean collidesWith(Double body, int bodyColor) {
        Area playerArea = new Area(body);
        Area starArea = new Area(star);
        playerArea.intersect(starArea);
        return !(playerArea.isEmpty());
    }

}
