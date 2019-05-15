package game01;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import beta02.ColorSwitch;
import beta02.Game;
import beta02.KeyHandler;

public class Game01 implements Game {

    private Player player1;
    //private Player player2;
    private Player player3;
    private KeyHandler kh;
    private ColorSwitch cswitch;

    public static Color purple;
    public static Color red;
    public static Color blue;
    public static Color yellow;

    /**
     * Creates a new game object
     */
    public Game01() {
        player1 = new Player();
    }

    public Game01(ColorSwitch cs) {
        this.cswitch = cs;
        this.kh = cs.getKeyHandler();
        long seed = System.currentTimeMillis();
        player1 = new Player(1, cs);
        player1.initDefaultActors();
        player1.setSeed(seed);
        
        /*		player2 = new Player(2,cs);
         player2.setViewPort(new Rectangle(550, 100,450,800));
         player2.initDefaultActors();
         player2.setSeed(seed);*/
		//player3 = new Player(3,cs);
        //player3.setViewPort(new Rectangle(1000, 100,450,800));
        //player3.initDefaultActors();
        //player3.setSeed(seed);
    }

    /**
     * Performs the gameover action for the game.
     */
    public boolean isGameOver() {
        return false;
    }

    /**
     * Draws the current game state
     *
     */
    public void render(Graphics2D g) {
        player1.draw(g);
        //player2.draw(g);

    }

    /**
     * Updates the current frame with the newly computed values
     *
     */
    public void update() {
        if (kh.wasButtonJustPressed(1)) {
            player1.jump();
        }
        player1.move();
        player1.update();

        /*if(kh.wasButtonJustPressed(2)) {
         player2.jump();
         }
         player2.move();
         player2.update();
		
         if(kh.wasButtonJustPressed(3)) {
         long seed = System.currentTimeMillis();
         player1.restart(seed);
         player2.restart(seed);
         }*/
    }
}
