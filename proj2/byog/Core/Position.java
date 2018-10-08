package byog.Core;

import java.io.Serializable;

public class Position implements Serializable {
    int xPos;
    int yPos;

    Position(int x, int y) {
        xPos = x;
        yPos = y;
    }

    Position(Position pos) {
        xPos = pos.xPos;
        yPos = pos.yPos;
    }

    Position addPos(Position a) {
        return new Position(xPos + a.xPos, yPos + a.yPos);
    }

    Position addMultiplePos(int a, Position p) {
        Position curr = this;
        for (int i = 0; i < a; i += 1) {
            curr = curr.addPos(p);
        }
        return curr;
    }

    Position minusPos(Position a) {
        return new Position(xPos - a.xPos, yPos - a.yPos);
    }

    Position minusMultiplePos(int a, Position p) {
        Position curr = this;
        for (int i = 0; i < a; i += 1) {
            curr = curr.minusPos(p);
        }
        return curr;
    }

    boolean equals(Position a) {
        return xPos == a.xPos && yPos == a.yPos;
    }
}
