/**
 * @authors: Vinay Maruri and Terry Drinkwater
 * @Version: 1.1
 * the Game class contains 4 methods in the class
 * playwithkeyboard() is meant for new games
 * playwithinputstring() is meant for playing old games
 * save() is meant for saving game data
 * load() is meant for loading game data
 */
package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.io.Serializable;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Game implements Serializable {
    /* Feel free to change the width and height. */
    public static final int WIDTH = 60;
    public static final int HEIGHT = 40;
    public static final int TREASURE_PER_LEVEL = 5;
    public static final int BASE_TREASURE_VALUE = 5;
    public static final int LEVEL_CLEAR_VALUE = 10;
    private long seed;
    private Random rand;
    protected TETile[][] world;
    protected PlayerCharacter player;
    protected Enemy[] monsters;
    protected Position[] treasures = new Position[TREASURE_PER_LEVEL];
    protected int score;
    protected int level;
    protected Position exitDoor;

    public Game() {

    }

    public Game(long s) {
        seed = s;
        rand = new Random(s);
        world = MapGenerate.generate(rand, WIDTH, HEIGHT);
        level = 1;
        makeGameObjects();
        score = 0;
    }

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public static void playWithKeyboard() {
        Game game;
        long seed;
        GameIO io = new GameIO(WIDTH, HEIGHT);
        io.menuDisplay();
        while (true) {
            char action = io.readCharInput(new char[]{'n', 'l', 'q'});
            if (action == 'n') {
                seed = io.readSeedInput();
                game = new Game(seed);
                break;
            } else if (action == 'l') {
                game = load();
                break;
            } else if (action == 'q') {
                System.exit(0);
            }
        }
        while (true) {
            char command;
            while (true) {
                io.loadRender(game);
                if (StdDraw.hasNextKeyTyped()) {
                    command = Character.toLowerCase(StdDraw.nextKeyTyped());
                    boolean gameOver = followCommand(game, io, command);
                    io.loadRender(game);
                    if (gameOver) {
                        break;
                    }
                }
            }
            io.loadRender(game, "Game Over!");
            StdDraw.pause(3000);
            io.gameOver(game);
            char action = io.readCharInput(new char[]{'n', 'r'});
            if (action == 'n') {
                seed = io.readSeedInput();
            }

            game = new Game(game.seed);
        }
    }

    public static boolean followCommand(Game game, GameIO io, char command) {
        if (command == ':') {
            while (command == ':') {
                if (StdDraw.hasNextKeyTyped()) {
                    command = Character.toLowerCase(StdDraw.nextKeyTyped());
                }
            }
            if (command == 'q') {
                Game.save(game);
                io.loadRender(game, "Saving...");
                StdDraw.pause(1000);
                System.exit(0);
            }
        }
        if (command == 'w' || command == 'a' || command == 's' || command == 'd') {
            return game.update(command);
        }
        return false;
    }


    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public static TETile[][] playWithInputString(String input) {
        Game game;
        input = input.toLowerCase();
        int index = 0;
        char command = input.charAt(index);
        index += 1;
        if (command == 'n') {
            int result = 0;
            for (int i = 1; i < input.length(); i++) {
                if (input.charAt(i) == 's') {
                    result = i;
                    break;
                }
            }
            String temp = input.substring(1, result);
            index += result;
            long seed = Long.parseLong(temp);
            game = new Game(seed);
        } else if (command == 'l') {
            game = load();
        } else {
            return null;
        }

        while (index < input.length()) {
            command = input.charAt(index);
            index += 1;
            if (command == ':' && index < input.length()) {
                command = input.charAt(index);
                if (command == 'q') {
                    Game.save(game);
                    break;
                }
            }
            if (command == 'w' || command == 'a' || command == 's' || command == 'd') {
                if (game.update(command)) {
                    break;
                }
            }
        }

        return game.world;
    }

    /**
     * @source: https://www.geeksforgeeks.org/serialization-in-java/,
     * @source: https://docs.oracle.com/javase/7/docs/api/java/io/Serializable.html
     * sources were used to gain knowledge of the Java serializable interface
     */
    public static void save(Game game) {
        try {
            FileOutputStream fileout = new FileOutputStream("./byog/Core/game.txt");
            ObjectOutputStream out = new ObjectOutputStream(fileout);
            out.writeObject(game);
            out.close();
            fileout.close();
            System.out.printf("Serialized data is saved in game.txt");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    /**
     * @source: https://www.geeksforgeeks.org/serialization-in-java/,
     * @source: https://docs.oracle.com/javase/7/docs/api/java/io/Serializable.html
     * sources were used to gain knowledge of the Java serializable interface
     */
    public static Game load() {
        Game game = null;
        try {
            FileInputStream filein = new FileInputStream("byog/Core/game.txt");
            ObjectInputStream in = new ObjectInputStream(filein);
            game = (Game) in.readObject();
            in.close();
            filein.close();
        } catch (IOException i) {
            i.printStackTrace();
            return null;
        } catch (ClassNotFoundException c) {
            System.out.println("Game save data not found");
            c.printStackTrace();
            return null;
        }
        return game;
    }

    /**
     * Given a valid command, update the game state according to the command.
     * :Q : Save and quit
     * WASD: Move the player.
     *
     * @param dir: move for player.
     */
    public boolean update(char dir) {
        if (!player.isLegalMove(dir)) {
            return false;
        }
        player.move(dir);
        if (player.underneath.equals(Tileset.TREASURE)) {
            player.underneath = Tileset.FLOOR;
            score += level * BASE_TREASURE_VALUE;
        }
        if (player.underneath.equals(Tileset.ENEMY)) {
            return true;
        }
        if (player.underneath.equals(Tileset.EXIT_DOOR)) {
            levelUp();
        }
        for (int i = 0; i < monsters.length; i += 1) {
            monsters[i].move(player);
            if (monsters[i].underneath.equals(Tileset.PLAYER)) {
                return true;
            }
        }
        return false;
    }

    public void levelUp() {
        score += LEVEL_CLEAR_VALUE * level;
        world = MapGenerate.generate(rand, WIDTH, HEIGHT);
        level += 1;
        makeGameObjects();
    }

    /**
     * Loads GameObjects into the world pseudorandomly.
     * Level n has 1 PlayerCharacter, n Enemies, and
     * TREASURE_PER_LEVEL Treasures.
     */
    public void makeGameObjects() {
        player = new PlayerCharacter(randomPos(), world);
        monsters = new Enemy[level];
        for (int i = 0; i < level; i += 1) {
            monsters[i] = new Enemy(randomPos(), world, rand);
        }
        for (int i = 0; i < TREASURE_PER_LEVEL; i += 1) {
            treasures[i] = randomPos();
            world[treasures[i].xPos][treasures[i].yPos] = Tileset.TREASURE;
        }
        exitDoor = randomPos();
        world[exitDoor.xPos][exitDoor.yPos] = Tileset.EXIT_DOOR;
    }

    /**
     * Helper method for MakeGameObjects.
     * Selects random column in world, then random y-position with designated
     * TETile in that column.  If no such TETiles in column, select a different
     * random column.
     * <p>
     * Will loop infinitely if world contains no TETiles of the desired type.
     *
     * @param tileType: randomPos will choose a position containing this tileType.
     * @return a randomly chosen position in world.
     */
    public Position randomPos(TETile tileType) {
        ArrayList<Position> candidates = new ArrayList();
        while (true) {
            int x = rand.nextInt(world.length);
            for (int i = 0; i < world[x].length; i += 1) {
                if (world[x][i].equals(tileType)) {
                    candidates.add(new Position(x, i));
                }
            }
            if (candidates.size() > 0) {
                Position result = candidates.get(rand.nextInt(candidates.size()));
                return result;
            }
        }
    }

    /**
     * Default randomPos method: calls randomPos to find a random patch of floor.
     *
     * @return a randomly chosen position in world with FLOOR as its tile type.
     */
    public Position randomPos() {
        return randomPos(Tileset.FLOOR);
    }

}
