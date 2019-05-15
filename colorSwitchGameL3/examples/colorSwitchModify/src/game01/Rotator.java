package game01;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Double;
import java.util.ArrayList;

import beta02.Actor;
import beta02.ColorSwitch;

public class Rotator extends Actor {

    private int diameter = 200;
    private double thickness = 15;
    private double currentAngle = 0;
    private double angularSpeed = 1;
    private boolean flipped = false;
    //inherited from Actor: viewPort, position, xPos

    public Rotator() {
        super(new Rectangle(0, 0, 1280, 1024), 1000);
    }

    public Rotator(Rectangle viewPort, double startPosition) {
        super(viewPort, startPosition);
        this.star = new ScoreStar(viewPort, startPosition + 100);
        this.switcher = new Switcher(viewPort, startPosition - 120);
        xPosition = viewPort.width / 2;
    }

    public Rotator(Rectangle viewPort, double startPosition, double angularSpeed, double currentAngle, double diameter) {
        super(viewPort, startPosition);
        this.star = new ScoreStar(viewPort, startPosition + diameter / 2);
        this.switcher = new Switcher(viewPort, startPosition - 120);
        this.angularSpeed = angularSpeed;
        this.currentAngle = currentAngle;
        this.diameter = (int) diameter;
        xPosition = viewPort.width / 2;
    }

    public void translate(double x) {
        xPosition += x;
    }

    public void update() {
        currentAngle = (currentAngle + angularSpeed) % 360;
    }

    public void flip() {
        flipped = true;
    }

    @Override
    public void render(Graphics2D g) {
        super.render(g);
        if (inViewPort()) {
            int cornerX = (int) (viewPort.getX() + xPosition - diameter / 2);
            int cornerY = (int) (viewPort.getMinY() + position);
            g.setStroke(new BasicStroke((float) thickness));
            if (flipped == false) {
                g.setColor(ColorSwitch.colors[0]); //purple
                g.drawArc(cornerX, cornerY, diameter, diameter, (int) currentAngle, 90);
                g.setColor(ColorSwitch.colors[1]);
                g.drawArc(cornerX, cornerY, diameter, diameter, (int) currentAngle + 90, 90);
                g.setColor(ColorSwitch.colors[2]);
                g.drawArc(cornerX, cornerY, diameter, diameter, (int) currentAngle + 180, 90);
                g.setColor(ColorSwitch.colors[3]);
                g.drawArc(cornerX, cornerY, diameter, diameter, (int) currentAngle + 270, 90);
                g.setStroke(new BasicStroke(1));
            } else if (flipped = true) {
                g.setColor(ColorSwitch.colors[0]); //purple
                g.drawArc(cornerX, cornerY, diameter, diameter, (int) currentAngle, 90);
                g.setColor(ColorSwitch.colors[1]);
                g.drawArc(cornerX, cornerY, diameter, diameter, (int) currentAngle - 90, 90);
                g.setColor(ColorSwitch.colors[2]);
                g.drawArc(cornerX, cornerY, diameter, diameter, (int) currentAngle - 180, 90);
                g.setColor(ColorSwitch.colors[3]);
                g.drawArc(cornerX, cornerY, diameter, diameter, (int) currentAngle - 270, 90);
                g.setStroke(new BasicStroke(1));
            }
        }

    }

    public boolean collidesWith(Ellipse2D.Double body, int bodyColor) {
        // TODO Auto-generated method stub
        double arcX = xPosition - diameter / 2;
        double arcY = position;
        ArrayList<ColoredArc> arcs = new ArrayList<ColoredArc>();
        for (int color = 1; color <= 4; color++) {
            //creates a coloredArc at arcX, arcY with the specific place
            if (flipped == false) {
                ColoredArc segment = new ColoredArc(arcX, arcY, diameter, diameter, currentAngle + 90 * (color - 1), 90);
                segment.setColor(color);
                arcs.add(segment);
            } else {
                ColoredArc segment = new ColoredArc(arcX, arcY, diameter, diameter, currentAngle - 90 * (color - 1), 90);
                segment.setColor(color);
                arcs.add(segment);
            }
        }
        for (int i = 0; i < 4; i++) {
            //creates an coloredArc, but then has to hollow it out by subtracting an inner circle to make collisions accurate
            ColoredArc currentArc = arcs.get(i);
            Area playerArea = new Area(body);
            Area arcArea = new Area(currentArc);
            arcArea.subtract(new Area(new Ellipse2D.Double(arcX + thickness / 2, arcY + thickness / 2, diameter - thickness, diameter - thickness)));
            playerArea.intersect(arcArea);
            if (!(playerArea.isEmpty() || currentArc.getColorType() == bodyColor)) {
                return true;
            }
        }
        return false;
    }

    public boolean inViewPort() {
        return (position + diameter > 0 && position < viewPort.height);
    }

    public double getTopPosition() {
        // TODO Auto-generated method stub
        return position;
    }

}
