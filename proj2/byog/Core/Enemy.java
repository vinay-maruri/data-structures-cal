package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.Random;

public class Enemy extends GameObject {
    Random rand;

    Enemy(Position loc, TETile[][] w, Random r) {
        super(loc, w);
        appearance = Tileset.ENEMY;
        rand = r;
        w[loc.xPos][loc.yPos] = appearance;
        legalMoves = new Position[9];
        for (int i = 0; i < legalMoves.length; i += 1) {
            int xPos = (i % 3) - 1;
            int yPos = (i / 3) - 1;
            legalMoves[i] = new Position(xPos, yPos);
        }
        legalTiles = new TETile[]{Tileset.FLOOR, Tileset.PLAYER, Tileset.TREASURE};
    }

    Position moveRandomly() {
        ArrayList<Position> validMoves = new ArrayList();
        for (Position dir : legalMoves) {
            if (isLegalMove(dir)) {
                validMoves.add(dir);
            }
        }
        Position dir = validMoves.get(rand.nextInt(validMoves.size()));
        return move(dir);
    }

    /**
     * @param target: a GameObject to be chased.
     * @return Position moved to.
     * @source https://stackoverflow.com/questions/25606134/normalize-value-from-xx-to-11
     * for Integer.signum(int)
     */
    Position moveChase(GameObject target) {
        Position direction = target.compareLocation(location);
        int xDir = Integer.signum(direction.xPos);
        int yDir = Integer.signum(direction.yPos);
        ArrayList<Position> validMoves = new ArrayList();
        if (xDir != 0 && yDir != 0) {
            validMoves.add(new Position(xDir, yDir));
            validMoves.add(new Position(xDir, 0));
            validMoves.add(new Position(0, yDir));
        }
        if (xDir == 0) {
            for (int i = -1; i <= 1; i += 1) {
                validMoves.add(new Position(i, yDir));
            }
        }
        if (yDir == 0) {
            for (int j = -1; j <= 1; j += 1) {
                validMoves.add(new Position(xDir, j));
            }
        }
        int i = 0;
        while (i < validMoves.size()) {
            if (!isLegalMove(validMoves.get(i))) {
                validMoves.remove(i);
            } else {
                i += 1;
            }
        }
        if (validMoves.size() == 0) {
            return moveRandomly();
        }
        Position dir = validMoves.get(rand.nextInt(validMoves.size()));
        return move(dir);
    }

    Position move(GameObject target) {
        Position distance = compareLocation(target.location);
        if (distance.xPos + distance.yPos < 10) {
            if (rand.nextInt(10) < 8) {
                return moveChase(target);
            }
        }
        return moveRandomly();
    }
}
