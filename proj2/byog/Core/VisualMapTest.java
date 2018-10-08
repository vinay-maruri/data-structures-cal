/**
 * @authors: Vinay Maruri, Terry Drinkwater
 * @Version: 1.0
 * A class to visually test the maps generated for our 2D tile world.
 */
package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class VisualMapTest {
    public static final TERenderer TER = new TERenderer();
    private static final int STARTX = 30;
    private static final int STARTY = 3;
    private static final int MAXWIDTH = 5;
    private static final int MAXHEIGHT = 8;
    private static final int MAXOFFSET = 3;
    private static final TETile ENTRANCE = Tileset.TREASURE;
    private static final TETile EXIT = Tileset.ENEMY;

    public static void main(String[] args) {
        TERenderer terp = new TERenderer();
        Random rand = new Random(234985723);
        // call to our map generation method
        TETile[][] tester = MapGenerate.generate(rand, 60, 45);
        // initialize our canvas using TER initialize
        terp.initialize(60, 45);
        // render the world, passing in to renderFrame method the world to generate
//        terp.renderFrame(tester);
    }
}
