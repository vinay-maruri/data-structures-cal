package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.Serializable;

public abstract class GameObject implements Serializable {
    Position location;
    TETile appearance;
    TETile underneath;
    TETile[] legalTiles;
    Position[] legalMoves;
    TETile[][] world;


    GameObject(Position loc, TETile[][] w) {
        location = loc;
        world = w;
        underneath = w[loc.xPos][loc.yPos];
        legalTiles = new TETile[]{Tileset.FLOOR};
        legalMoves = new Position[]{new Position(0, 1), new Position(-1, 0),
            new Position(0, -1), new Position(1, 0)};
    }

    Position compareLocation(Position pos) {
        return location.minusPos(pos);
    }

    Position move(Position dir) {
        if (!isLegalMove(dir)) {
            return location;
        }
        world[location.xPos][location.yPos] = underneath;
        location = location.addPos(dir);
        underneath = world[location.xPos][location.yPos];
        world[location.xPos][location.yPos] = appearance;
        return location;
    }

    boolean isLegalMove(Position dir) {
        boolean move = false;
        boolean dest = false;
        for (int i = 0; i < legalMoves.length; i += 1) {
            if (legalMoves[i].equals(dir)) {
                move = true;
            }
        }
        if (!move) {
            return move;
        }
        Position newPos = location.addPos(dir);
        TETile destTile = world[newPos.xPos][newPos.yPos];
        for (int i = 0; i < legalTiles.length; i += 1) {
            if (legalTiles[i].equals(destTile)) {
                dest = true;
            }
        }
        return dest;
    }
}
