package Controller;

import View.GameGUI;

/**
 * A class used to create instances of games and
 * test that features are working correctly.
 */

public class GameTester {
    public static void main(String[] args)
    {
        GameGUI gui = new GameGUI();
        gui.run();
    }
}
