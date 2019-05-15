package beta02;

import game01.ScoreStar;
import game01.Switcher;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;

public abstract class Actor {

    /**
     * update all instance variables.. mostly position of shapes and/or colors
     */
    //inherited viewPort, position, xPos, colorType
    protected Rectangle viewPort;
    protected double position;
    protected double xPosition;
    protected double sizeOffset = 0;
    private int colorType = 1;
    protected ScoreStar star = null;
    protected Switcher switcher = null;

    public Actor() {
        this(new Rectangle(0, 0, 1024, 1280), 0);
    }

    public Actor(Rectangle viewPort, double position) {
        this.position = position;
        this.viewPort = viewPort;
    }

    protected Color getColor() {
        switch (colorType) {
            case 1:
                return ColorSwitch.colors[0];
            case 2:
                return ColorSwitch.colors[1];
            case 3:
                return ColorSwitch.colors[2];
            case 4:
                return ColorSwitch.colors[3];
            default:
                return Color.WHITE;
        }
    }

    public int getColorType() {
        return colorType;
    }

    public void update(double moveDownDistance) {
        position += moveDownDistance;
        if (star != null) {
            star.update(moveDownDistance);
        }
        if (switcher != null) {
            switcher.update(moveDownDistance);
        }
    }

    //update blocks for actors

    public void update() {

    }

    public void setPosition(double x) {
        xPosition = x;
    }

    /**
     * draw all shapes to g default renders star+colorSwitcher
     */
    public void render(Graphics2D g) {
        if (star != null) {
            star.render(g);
        }
        if (switcher != null) {
            switcher.render(g);
        }
    }

    /**
     * returns true if the Actor is in contact with (intersects with) body
     */
    public abstract boolean collidesWith(Ellipse2D.Double body, int bodyColor);

    public double getPosition() {
        // TODO Auto-generated method stub
        return position;
    }

    public boolean inViewPort() {
        return (position > -200 && position < viewPort.getHeight());
    }

    public void setColorType(int colorType) {
        this.colorType = colorType;
    }

    public void removeScoreStar() {
        star = null;
    }

    public void removeSwitcher() {
        switcher = null;
    }

    public boolean starCollision(Ellipse2D.Double body, int bodyColor) {
        if (star != null && star.collidesWith(body, bodyColor)) {
            star = null;
            return true;
        }
        return false;
    }

    public boolean switcherCollision(Ellipse2D.Double body, int bodyColor) {
        if (switcher != null && switcher.collidesWith(body, bodyColor)) {
            switcher = null;
            return true;
        }
        return false;
    }

    public boolean outOfScreen() {
        return (position - sizeOffset > viewPort.height && switcher == null && star == null);
    }

    public void clearStarSwitcher() {
        star = null;
        switcher = null;
    }

    public double getTopPosition() {
        // TODO Auto-generated method stub
        return position;
    }
}
