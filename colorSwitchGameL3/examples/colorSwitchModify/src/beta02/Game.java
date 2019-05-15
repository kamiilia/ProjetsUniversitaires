/**
 *
 */
package beta02;

import java.awt.Graphics2D;

/**
 * All Game c
 */
public interface Game {

    /**
     * returns true if the game completely over and is ready to return to the
     * main menu of Games
     */
    public boolean isGameOver();

    /**
     * draws all primatives to g
     */
    public void render(Graphics2D g);

    /**
     * updates all instance variables
     */
    public void update();

}
