package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class Treasure extends GameObject {
    int value;

    Treasure(Position loc, TETile[][] w, int val) {
        super(loc, w);
        appearance = Tileset.TREASURE;
        legalMoves = new Position[0];
        value = val;
    }

    @Override
    boolean isLegalMove(Position dir) {
        return false;
    }

    @Override
    Position move(Position dir) {
        return location;
    }
}
