package byog.lab6;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
            "You got this!", "You're a star!", "Go Bears!",
            "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }
        int seed = Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(40, 40);
        Random r = new Random();
        int length = r.nextInt(10) + 1;
        String temp = game.generateRandomString(length);
        game.startGame();
    }

    public MemoryGame(int width, int height) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        //TODO: Initialize random number generator
        Random r = new Random();
    }

    public String generateRandomString(int n) {
        //TODO: Generate random string of letters of length n
        String result = "";
        int i = 0;
        Random r = new Random();
        while (i < n) {
            result = result + CHARACTERS[r.nextInt(CHARACTERS.length)];
            i += 1;
        }
        return result;
    }

    public void drawFrame(String s) {
        //TODO: Take the string and display it in the center of the screen
        //TODO: If game is not over, display relevant game information at the top of the screen
        StdDraw.clear(Color.BLACK);
        StdDraw.setFont(StdDraw.getFont());
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(this.width/2, this.height/2, s);
        StdDraw.show();
    }

    public void flashSequence(String letters) {
        //TODO: Display each character in letters, making sure to blank the screen between letters
        int i = 0;
        while (i < letters.length()) {
            StdDraw.pause(500);
            drawFrame(Character.toString(letters.charAt(i)));
            StdDraw.show();
            StdDraw.pause(500);
            StdDraw.clear(Color.BLACK);
            i += 1;
        }
        StdDraw.clear(Color.BLACK);
    }

    public String solicitNCharsInput(int n) {
        //TODO: Read n letters of player input
        String result = "";
        while(n > 0) {
            if (StdDraw.hasNextKeyTyped()) {
                char temp = StdDraw.nextKeyTyped();
                result = result + temp;
                n -= 1;
            }
        }
        return result;
    }

    public void startGame() {
        //TODO: Set any relevant variables before the game starts
        int round = 1;
        while (round > 0) {
            StdDraw.clear(Color.BLACK);
            String roundstart = "Round: " + round;
            drawFrame(roundstart);
            StdDraw.pause(1000);
            StdDraw.clear(Color.BLACK);
            String test = generateRandomString(round);
            flashSequence(test);
            StdDraw.pause(1000);
            StdDraw.clear(Color.BLACK);
            String answer = solicitNCharsInput(test.length());
            if (answer.equals(test)) {
                StdDraw.pause(1000);
                StdDraw.clear(Color.BLACK);
                round += 1;
            }
            else {
                StdDraw.clear(Color.BLACK);
                String display = "Game Over! You made it to round: " + round;
                drawFrame(display);
                break;
            }
        }
        //TODO: Establish Game loop
    }

}
