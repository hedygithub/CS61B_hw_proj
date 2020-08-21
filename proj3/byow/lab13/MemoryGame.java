package byow.lab13;

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
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, int seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Arial", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        // Initialize random number generator
        rand = new Random(seed);

    }

    // Generate random string of letters of length n
    public String generateRandomString(int n) {
        String randomString = "";
        int r = CHARACTERS.length;
        for (int i = 0; i < n; i++) {
            randomString += CHARACTERS[rand.nextInt(r)];
        }
        return randomString;
    }

    // Take the string and display it in the center of the screen
    // If game is not over, display relevant game information at the top of the screen
    public void drawFrame(String s) {
        int leftWidth = 2;
        int midWidth = width / 2;
        int rightWidth = width - 1;
        int midHeight = height / 2;
        int topHeight = height - 1;

        StdDraw.setPenColor(Color.WHITE);
        StdDraw.clear(Color.BLACK);

        Font font;
        font = new Font("Arial", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.text(midWidth, midHeight, s);

        String encouragement = encourage();
        if (!gameOver) {
            font = new Font("Arial", Font.PLAIN, 20);
            StdDraw.setFont(font);
            StdDraw.textLeft(leftWidth, topHeight, "Round: " + round);
            StdDraw.text(midWidth, topHeight, "Type! ");
            StdDraw.textRight(rightWidth, topHeight, encouragement);
            StdDraw.line(0, topHeight - 1, width , topHeight - 1);
        }

        StdDraw.show();
        StdDraw.pause(1000);
        StdDraw.enableDoubleBuffering();
    }

    public void flashSequence(String letters) {
        int showMilliS = 1000;
        int blankMilliS = 500;

        int leftWidth = 2;
        int midWidth = width / 2;
        int rightWidth = width - 1;
        int midHeight = height / 2;
        int topHeight = height - 1;


        StdDraw.setPenColor(Color.WHITE);
        StdDraw.clear(Color.BLACK);
        Font font;
        String encouragement = encourage();

        for (int i = 0; i < letters.length(); i++) {
            String s = Character.toString(letters.charAt(i));
            font = new Font("Arial", Font.BOLD, 30);
            StdDraw.setFont(font);
            StdDraw.text(midWidth, midHeight, s);

            if (!gameOver) {
                font = new Font("Arial", Font.PLAIN, 20);
                StdDraw.setFont(font);
                StdDraw.textLeft(leftWidth, topHeight, "Round: " + round);
                StdDraw.text(midWidth, topHeight, "Watch! ");
                StdDraw.textRight(rightWidth, topHeight, encouragement);
                StdDraw.line(0, topHeight - 1, width , topHeight - 1);
            }
            StdDraw.show();
            StdDraw.pause(showMilliS);
            StdDraw.enableDoubleBuffering();

            StdDraw.clear(Color.BLACK);
            if (!gameOver) {
                font = new Font("Arial", Font.PLAIN, 20);
                StdDraw.setFont(font);
                StdDraw.textLeft(leftWidth, topHeight, "Round: " + round);
                StdDraw.text(midWidth, topHeight, "Watch! ");
                StdDraw.textRight(rightWidth, topHeight, encouragement);
                StdDraw.line(0, topHeight - 1, width, topHeight - 1);
            }
            StdDraw.show();
            StdDraw.pause(blankMilliS);
            StdDraw.enableDoubleBuffering();
        }
    }

    // Read n letters of player input
    public String solicitNCharsInput(int n) {
        int inputMilliS = 2000;
        String string = "";

        for (int nChars = 0; nChars < n; nChars++) {
            if (!StdDraw.hasNextKeyTyped()) {
                drawFrame(string + " + 2s for next char");
                StdDraw.pause(inputMilliS);
            }
            if (!StdDraw.hasNextKeyTyped()) {
                break;
            } else {
                string += Character.toString(StdDraw.nextKeyTyped());
                drawFrame(string);
            }
        }
        return string;
    }

    // Start the game at round 1
    // Display the message “Round: “ followed by the round number in the center of the screen
    // Generate a random string of length equal to the current round number
    // Display the random string one letter at a time
    // Wait for the player to type in a string the same length as the target string
    // Check to see if the player got it correct
    public void startGame() {
        gameOver = false;
        round = 1;

        for (; !gameOver; round++) {
            drawFrame("Round: " + round);

            String roundString = generateRandomString(round);
            flashSequence(roundString);

            String typedString = solicitNCharsInput(round);
            gameOver = !typedString.equals(roundString);
        }

        round -= 1;
        drawFrame("Game Over! You made it to round: " + round);
    }

    private String encourage() {
        int index = rand.nextInt(ENCOURAGEMENT.length);
        return ENCOURAGEMENT[index];
    }
}
