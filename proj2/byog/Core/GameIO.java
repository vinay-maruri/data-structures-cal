package byog.Core;

import byog.TileEngine.TERenderer;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.Font;
import java.awt.Color;

public class GameIO {
    private TERenderer ter = new TERenderer();
    private Font gameFont = new Font("Monaco", Font.BOLD, 14);
    private Font menuFont = new Font("Sans_Serif", Font.BOLD, 30);
    private Font hudFont = new Font("Monaco", Font.PLAIN, 14);
    private int WIDTH;
    private int HEIGHT;

    public GameIO(int w, int h) {
        ter.initialize(w, h + 2);
        WIDTH = w;
        HEIGHT = h;
    }

    public void loadRender(Game game, String bottomLeft) {
        StdDraw.clear(Color.black);
        StdDraw.setFont(gameFont);
        ter.renderFrame(game.world);
        StdDraw.setPenColor(Color.white);
        StdDraw.setFont(hudFont);
        StdDraw.textLeft(1, HEIGHT + 1, "Level: " + game.level + "\t Score: " + game.score);
        int mouseX = (int) StdDraw.mouseX();
        int mouseY = (int) StdDraw.mouseY();
        if (mouseX >= 0 && mouseX < game.world.length
                && mouseY >= 0 && mouseY < game.world[mouseX].length) {
            StdDraw.textRight(WIDTH - 1, HEIGHT + 1,
                    game.world[mouseX][mouseY].description().toUpperCase());
        }
        StdDraw.textRight(WIDTH - 1, HEIGHT, "WASD to move       :Q to save and quit");
        StdDraw.textLeft(1, HEIGHT, bottomLeft);
        StdDraw.show();
    }

    public void loadRender(Game game) {
        loadRender(game, "");
    }

    public void menuDisplay() {
        StdDraw.setFont(menuFont);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.clear(Color.BLACK);
        StdDraw.text(WIDTH / 2, 3 * HEIGHT / 4, "CS61B: The Game");
        StdDraw.text(WIDTH / 2, HEIGHT / 2, "New Game (N)");
        StdDraw.text(WIDTH / 2, 3 * HEIGHT / 8, "Load Game (L)");
        StdDraw.text(WIDTH / 2, HEIGHT / 4, "Quit (Q)");
        StdDraw.show();
    }

    public void seeddisplay(String input) {
        StdDraw.setFont(menuFont);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.clear(Color.BLACK);
        StdDraw.text(WIDTH / 2, HEIGHT / 2, "Enter a seed: " + input);
        StdDraw.text(WIDTH / 2, HEIGHT / 4,
                "(a positive number between 1 and 18 digits, then the letter S.)");
        StdDraw.show();
    }

    public long readSeedInput() {
        seeddisplay("");
        long result = 0;
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char input = StdDraw.nextKeyTyped();
                if (input == 's' || input == 'S' && result > 0) {
                    return result;
                } else if (Character.isDigit(input) && Long.MAX_VALUE / 10 > result) {
                    result = (10 * result) + Character.getNumericValue(input);
                    seeddisplay(Long.toString(result));
                }
            }
        }
    }

    public char readCharInput(char[] choices) {
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char input = Character.toLowerCase(StdDraw.nextKeyTyped());
                for (char c : choices) {
                    if (c == input) {
                        return input;
                    }
                }
            }
        }
    }

    public void gameOver(Game game) {
        StdDraw.clear(Color.black);
        StdDraw.setFont(menuFont);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(WIDTH / 2, HEIGHT * 3 / 4, "GAME OVER");
        StdDraw.text(WIDTH / 2, HEIGHT / 2 + 3, "Your Score: " + game.score);
        StdDraw.text(WIDTH / 2, HEIGHT / 2, "Level reached: " + game.level);
        StdDraw.text(WIDTH / 2, HEIGHT / 3, "Retry (R)      New Game (N)");
        StdDraw.show();
    }
}
