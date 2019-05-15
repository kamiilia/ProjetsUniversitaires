package beta02;

import static java.awt.event.KeyEvent.*;
import org.w3c.dom.events.EventTarget;
import org.w3c.dom.events.MouseEvent;
import org.w3c.dom.views.AbstractView;

/**
 *
 * <xmp> <code>
 * <pre>
 *
 * for ColorSwitch project .. 2016
 *
 * four players:
 * each player has 1 keys
 *
 * player 1 - Z
 * player 2 - M
 * player 3 - LEFT
 * player 4 - NUMPAD3
 *
 *
 * </pre> </code> </xmp>
 */
public class KeyHandler {

    private int numPlayers = 1;

    private Control button = new Control(); // joystick up
    private ColorSwitch cs;

    /**
     * Default constructor.
     */
    public KeyHandler() {
        new KeyHandler(null);
    }

    /**
     * Constructor has a parameter so KeyHandler has a reference to the
     * MultiGame Object.
     */
    public KeyHandler(ColorSwitch mg) {
        this.cs = mg;
        init();
    }

    /*private void setVal(Control ctl, int index, boolean press) {
     if (press) { // press
     ctl[index].pressed = true;
     if (ctl[index].clickReady) {
     ctl[index].clickReady = false;
     ctl[index].clicked = true;
     }
     } else { // release
     ctl[index].clickReady = true;
     ctl[index].pressed = false;
     }
     }*/
    private void setVal(Control ctl, boolean press) {
        if (press) { // press
            ctl.pressed = true;
            if (ctl.clickReady) {
                ctl.clickReady = false;
                ctl.clicked = true;
            }
        } else { // release
            ctl.clickReady = true;
            ctl.pressed = false;
        }
    }

    void init() {
        button = new Control();
    }

    /**
     * <pre>
     * Sample usage:
     * if keyChar is a digit.. it might be from the bar code reader..
     * shift the digits in String idNum to the left and place keyChar as the last digit
     *
     *
     * </pre>
     */
    void handleKey(int keyCode, char keyChar, boolean press) {
        //keyCode.get == MouseEvent.c;
        //java.awt.event.MouseEvent e = null;
       // System.out.println("so " + e.getClickCount());
        if (keyCode > 0) {
            setVal(button, press);
        }

        /*switch (keyCode) {

         // PLAYER_1
         case MOUSE_EVENT_MASK:
         setVal(button, 0, press);
         break;
         // PLAYER_2
         /*case VK_M:
         setVal(button, 1, press);
         break;
         // PLAYER_3
         case VK_LEFT:
         setVal(button, 2, press);
         break;
         // PLAYER_4
         case VK_NUMPAD3:
         setVal(button, 3, press);
         break;*/
        //default:
        //}
    }

    void clearAllClicks() {
        // for (int i = 0; i < 1; i++) {
        button = new Control();
        //}
    }

    /**
     * returns the status of button of player playerNum
     */
    public boolean isButtonPressed(int playerNum) {
        return button.pressed;
    }

    /**
     * returns true if button of player playerNum has just been pushed
     */
    public boolean wasButtonJustPressed(int playerNum) {
        if (button.clicked) {
            button.clicked = false;
            return true;
        } else {
            return false;
        }
    }

    // inner class
    class Control {

        boolean pressed;
        boolean clicked;
        boolean clickReady;

        public Control() {
            pressed = false;
            clicked = false;
            clickReady = true;
        }
    }

}
