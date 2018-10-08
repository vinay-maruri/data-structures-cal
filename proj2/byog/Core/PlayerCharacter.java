package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.Serializable;
import java.util.ArrayList;

public class PlayerCharacter extends GameObject {
    MoveDictionary moves;

    PlayerCharacter(Position loc, TETile[][] w) {
        super(loc, w);
        moves = new MoveDictionary();
        moves.put('w', new Position(0, 1));
        moves.put('a', new Position(-1, 0));
        moves.put('s', new Position(0, -1));
        moves.put('d', new Position(1, 0));
        appearance = Tileset.PLAYER;
        w[loc.xPos][loc.yPos] = appearance;
        legalTiles = new TETile[]{Tileset.FLOOR, Tileset.ENTRANCE_DOOR,
            Tileset.EXIT_DOOR, Tileset.ENEMY, Tileset.TREASURE};
    }

    Position move(char dir) {
        return move(moves.get(Character.toLowerCase(dir)));
    }

    boolean isLegalMove(char dir) {
        return isLegalMove(moves.get(Character.toLowerCase(dir)));
    }

    private class MoveDictionary implements Serializable {
        ArrayList<Character> moves;
        ArrayList<Position> dirs;

        MoveDictionary() {
            moves = new ArrayList();
            dirs = new ArrayList();
        }

        void put(char m, Position d) {
            moves.add(m);
            dirs.add(d);
        }

        Position get(char m) {
            int i = moves.indexOf(m);
            if (i < 0) {
                return null;
            }
            return dirs.get(i);
        }
    }
}
