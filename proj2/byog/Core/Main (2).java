package byog.Core;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;

import byog.TileEngine.TETile;

/** This is the main entry point for the program. This class simply parses
 *  the command line inputs, and lets the byog.Core.Game class take over
 *  in either keyboard or input string mode.
 */
public class Main {
    public static void main(String[] args) {
        if (args.length > 1) {
            System.out.println("Can only have one argument - the input string");
            System.exit(0);
        } else if (args.length == 1) {
            Game game = new Game();
            TETile[][] worldState = game.playWithInputString(args[0]);
<<<<<<< HEAD
=======
            System.out.println(TETile.toString(worldState));
>>>>>>> 2e2cd37ccab49b523fec78631071b5128ee18e7e
        } else {
            Game game = new Game();
            game.playWithKeyboard();
        }
    }
}
