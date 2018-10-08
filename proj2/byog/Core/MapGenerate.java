/**
 * @authors: Terry Drinkwater, Vinay Maruri
 * @Version: 1.0
 * A class to generate the maps for our 2D tile world.
 */
package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.Random;


public class MapGenerate {
    //calls to create random number of objects
    //starting room will have a random position
    //position the objects in random places
    //render the world

    public static boolean validPos(Position p, TETile[][] world) {
        return p.xPos >= 0 && p.xPos < world.length
                && p.yPos >= 0 && p.yPos < world[0].length;
    }

    public static boolean createArea(Position corner, Position opposite, TETile tile,
                                     TETile[][] world, ArrayList<Position> edges) {
        if (!validPos(corner, world) || !validPos(opposite, world)) {
            return false;
        }
        Position botLeft = new Position(Math.min(corner.xPos, opposite.xPos),
                Math.min(corner.yPos, opposite.yPos));
        Position topRight = new Position(Math.max(corner.xPos, opposite.xPos),
                Math.max(corner.yPos, opposite.yPos));
        for (int i = botLeft.xPos; i <= topRight.xPos; i += 1) {
            for (int j = botLeft.yPos; j <= topRight.yPos; j += 1) {
                world[i][j] = tile;
            }
        }
        updateEdges(botLeft, topRight, world, edges);
        return true;
    }

    public static boolean createArea(Position botLeft, Position topRight,
                                     TETile[][] world, ArrayList<Position> edges) {
        return createArea(botLeft, topRight, Tileset.FLOOR, world, edges);
    }

    public static boolean createArea(Position botLeft, int width, int height,
                                     TETile tile, TETile[][] world, ArrayList<Position> edges) {
        Position topRight = new Position(botLeft.xPos + width - 1, botLeft.yPos + height - 1);
        return createArea(botLeft, topRight, tile, world, edges);
    }

    public static boolean createArea(Position botLeft, int width, int height,
                                     TETile[][] world, ArrayList<Position> edges) {
        return createArea(botLeft, width, height, Tileset.FLOOR, world, edges);
    }

    public static boolean createLine(Position start, Position end, TETile tile,
                                     TETile[][] world, ArrayList<Position> edges) {
        if (!validPos(start, world) || !validPos(end, world)) {
            return false;
        }
        if (start.xPos != end.xPos && start.yPos != end.yPos) {
            return false;
        }
        return createArea(start, end, tile, world, edges);
    }

    public static boolean createLine(Position start, Position direction, int length,
                                     TETile tile, TETile[][] world, ArrayList<Position> edges) {
        Position end = start;
        for (int i = 1; i < length; i += 1) {
            end = end.addPos(direction);
        }
        return createLine(start, end, tile, world, edges);
    }

    public static boolean createLine(Position start, Position direction, int length,
                                     TETile[][] world, ArrayList<Position> edges) {
        return createLine(start, direction, length, Tileset.FLOOR, world, edges);
    }

    public static Position getRandomEmptyNeighbor(Random r, Position pos, TETile[][] world) {
        ArrayList<Position> neighbors = getEmptyNeighbors(pos, world);
        Position randomNeighbor = neighbors.get(RandomUtils.uniform(r, neighbors.size()));
        return randomNeighbor;
    }

    public static ArrayList<Position> getEmptyNeighbors(Position pos,
                                                        TETile[][] world, boolean diagonal) {
        ArrayList<Position> result = new ArrayList();
        Position[] directions =
            {new Position(1, 0), new Position(0, 1), new Position(-1, 0), new Position(0, -1)};
        Position[] diagonals =
            {new Position(1, -1), new Position(-1, 1), new Position(1, 1),
                new Position(-1, -1)};
        for (Position p : directions) {
            Position candidate = pos.addPos(p);
            if (validPos(candidate, world) && world[candidate.xPos][candidate.yPos] == null) {
                result.add(candidate);
            }
        }
        if (diagonal) {
            for (Position p : diagonals) {
                Position candidate = pos.addPos(p);
                if (validPos(candidate, world) && world[candidate.xPos][candidate.yPos] == null) {
                    result.add(candidate);
                }
            }
        }
        return result;
    }

    public static ArrayList<Position> getEmptyNeighbors(Position pos, TETile[][] world) {
        return getEmptyNeighbors(pos, world, false);
    }

    public static int distFromEdge(Position pos, Position direction, TETile[][] world) {
        int dist = -1;
        while (validPos(pos, world)) {
            pos = pos.addPos(direction);
            dist += 1;
        }
        return dist;
    }

    public static void updateEdges(Position botLeft, Position topRight,
                                   TETile[][] world, ArrayList<Position> edges) {
        int width = topRight.minusPos(botLeft).xPos + 1;
        int height = topRight.minusPos(botLeft).yPos + 1;
        Position candidate;
        for (int i = -1; i < width + 1; i += 1) {
            for (int j = -1; j < height + 1; j += 1) {
                candidate = botLeft.addPos(new Position(i, j));
                if (validPos(candidate, world) && getEmptyNeighbors(candidate, world).isEmpty()) {
                    for (int k = 0; k < edges.size(); k += 1) {
                        if (edges.get(k).equals(candidate)) {
                            edges.remove(k);
                        }
                    }
                }
            }
        }
        for (int i = 0; i < width; i += 1) {
            candidate = botLeft.addPos(new Position(i, 0));
            if (!getEmptyNeighbors(candidate, world).isEmpty()) {
                boolean same = false;
                for (int k = 0; k < edges.size(); k += 1) {
                    if (edges.get(k).equals(candidate)) {
                        same = true;
                    }
                }
                if (!same) {
                    edges.add(candidate);
                }
            }
            candidate = botLeft.addPos(new Position(i, height - 1));
            if (!getEmptyNeighbors(candidate, world).isEmpty()) {
                boolean same = false;
                for (int k = 0; k < edges.size(); k += 1) {
                    if (edges.get(k).equals(candidate)) {
                        same = true;
                    }
                }
                if (!same) {
                    edges.add(candidate);
                }
            }
        }
        for (int j = 1; j < height - 1; j += 1) {
            candidate = botLeft.addPos(new Position(0, j));
            if (!getEmptyNeighbors(candidate, world).isEmpty()) {
                boolean same = false;
                for (int k = 0; k < edges.size(); k += 1) {
                    if (edges.get(k).equals(candidate)) {
                        same = true;
                    }
                }
                if (!same) {
                    edges.add(candidate);
                }
            }
            candidate = botLeft.addPos(new Position(width - 1, j));
            if (!getEmptyNeighbors(candidate, world).isEmpty()) {
                boolean same = false;
                for (int k = 0; k < edges.size(); k += 1) {
                    if (edges.get(k).equals(candidate)) {
                        same = true;
                    }
                }
                if (!same) {
                    edges.add(candidate);
                }
            }
        }
    }

    public static void makeWalls(TETile[][] world, ArrayList<Position> edges) {
        for (Position edge : edges) {
            for (Position neighbor : getEmptyNeighbors(edge, world, true)) {
                world[neighbor.xPos][neighbor.yPos] = Tileset.WALL;
            }
        }
    }

    public static TETile[][] generate(Random rand, int width, int height) {
        TETile[][] world = new TETile[width][height];
        ArrayList<Position> edges = new ArrayList();

        int numRooms = RandomUtils.uniform(rand, 10) + 20;
        int numHalls = RandomUtils.uniform(rand, 10) + 25;

        int x = RandomUtils.uniform(rand, width - 4);
        int y = RandomUtils.uniform(rand, height - 4);
        Position start = new Position(x + 2, y + 2);
        world[x][y] = Tileset.FLOOR;
        edges.add(start);

        while (numRooms > 0 || numHalls > 0) {
            Position nextEdge = edges.get(RandomUtils.uniform(rand, edges.size()));
            Position neighbor = getRandomEmptyNeighbor(rand, nextEdge, world);
            Position direction = neighbor.minusPos(nextEdge);
            int maxLength = distFromEdge(neighbor, direction, world) - 1;
            if (maxLength <= 0) {
                continue;
            }

            int nextType = RandomUtils.uniform(rand, numRooms + numHalls);
            if (nextType < numRooms) {
                int length = RandomUtils.uniform(rand, Math.min(maxLength, 7)) + 1;

                Position perpendicular = new Position(direction.yPos, direction.xPos);
                int maxLWidth = distFromEdge(neighbor, perpendicular, world) - 1;
                int lWidth;
                if (maxLWidth <= 0) {
                    lWidth = 0;
                } else {
                    lWidth = RandomUtils.uniform(rand, Math.min(maxLWidth, 4));
                }

                perpendicular = new Position(-perpendicular.xPos, -perpendicular.yPos);
                int maxRWidth = distFromEdge(neighbor, perpendicular, world) - 1;
                int rWidth;
                if (maxRWidth <= 0) {
                    rWidth = 0;
                } else {
                    rWidth = RandomUtils.uniform(rand, Math.min(maxRWidth, 4));
                }

                Position corner1 = neighbor.addMultiplePos(rWidth, perpendicular);
                Position corner2 = neighbor.minusMultiplePos(lWidth, perpendicular)
                        .addMultiplePos(length - 1, direction);
                createArea(corner1, corner2, Tileset.FLOOR, world, edges);
                numRooms -= 1;
            } else {
                int length = RandomUtils.uniform(rand, maxLength) + 1;
                createLine(neighbor, direction, length, world, edges);
                numHalls -= 1;
            }
        }

        makeWalls(world, edges);
        for (int i = 0; i < world.length; i++) {
            for (int j = 0; j < world[i].length; j++) {
                if (world[i][j] == null) {
                    world[i][j] = Tileset.NOTHING;
                }
            }
        }
        return world;
    }

}
