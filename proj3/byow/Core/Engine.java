package byow.Core;

import byow.Core.WorldGenerator.WorldGenerator;
import byow.Core.WorldProcessor.InputSource;
import byow.Core.WorldProcessor.KeyboardSource;
import byow.Core.WorldProcessor.StringSource;
import byow.Core.WorldProcessor.WorldProcessor;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

import static byow.Core.Constants.*;

public class Engine {
    TERenderer ter = new TERenderer();

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {

        ter.initialize(WIDTH / 2, HEIGHT + HEIGHT_TOP);
        ter.menuScreen();

        WorldProcessor wp = new WorldProcessor();
        InputSource is = new KeyboardSource();

        wp.processWorld(is, ter);

//        String input = transferKBtoInput();
//        System.out.println(input);
//        String seedS = fromInputToSeed(input);
//
//        long seed = Integer.parseInt(seedS);
//        WorldGenerator wg = new WorldGenerator(seed);
//        wg.generate();
//
//        List<DIRECTION> moveDirList = new LinkedList<>();
//        // get play code of Load Part
//        if (1 + seedS.length() + 1 <= input.length()) {
//            int moveStartPos = seedS.length() + 1;
//            for (int i = moveStartPos; i < input.length(); i++) {
//                char c = input.charAt(i);
//                moveDirList.add(fromCharToDir(c));
//            }
//        }
//
//        boolean succeed = wg.moveManySteps(moveDirList);
//        TETile[][] finalWorldFrame = wg.world();
//        ter.initialize(WIDTH, HEIGHT + HEIGHT_TOP);
//        if (succeed) {
//            ter.renderFrame(finalWorldFrame, "Congratulations! You opened the door!");
//        } else {
//            ter.renderFrame(finalWorldFrame);
//        }
//
//
//        char b;
//        char c = input.charAt(input.length() - 1);
//        while (true) {
//            if (!(ter.stdDrawIsMousePressed() || ter.stdDrawHasNextKeyTyped())) {
//                continue;
//            } else if (ter.stdDrawIsMousePressed()) {
//                int x = ter.stdDrawIMouseX();
//                int y = ter.stdDrawIMouseY();
//                TETile tile = finalWorldFrame[x][y];
//                ter.renderFrame(finalWorldFrame, tile.description());
//            } else if (ter.stdDrawHasNextKeyTyped()) {
//                b = c;
//                c = ter.stdDrawNextKeyTyped();
//                input += c;
//                if (b + c == ':' + 'q' || b + c == ':' + 'Q') {
//                    saveInput(input.substring(0, input.length() - 2));
//                    break;
//                } else {
//                    succeed = wg.moveOneStep(fromCharToDir(c));
//                    finalWorldFrame = wg.world();
//                    if (succeed) {
//                        ter.renderFrame(finalWorldFrame, "Congratulations! You opened the door!");
//                    } else {
//                        ter.renderFrame(finalWorldFrame);
//                    }
//                }
//            }
//        }
//        return;
    }


//    private void initialize() {
//        int width = WIDTH / 2;
//        int height = HEIGHT + HEIGHT_TOP;
//        StdDraw.setCanvasSize(width * 16, height * 16);
//        StdDraw.setXscale(0, width);
//        StdDraw.setYscale(0, height);
//        StdDraw.setPenColor(Color.WHITE);
//        Font fontTitle = new Font("Monaco", Font.BOLD, 40);
//        Font fontText = new Font("Monaco", Font.BOLD, 20);
//
//
//        StdDraw.clear(new Color(0, 0, 0));
//        StdDraw.setFont(fontTitle);
//        StdDraw.text(width / 2, 3 * height / 4, "CS6IB Project 3");
//        StdDraw.text(width / 2, 3 * height / 4 - 4, "Game of HD & LYC");
//
//        StdDraw.setFont(fontText);
//        StdDraw.text(width / 2, 1 * height / 3 + 2, "New Game (N)");
//        StdDraw.text(width / 2, 1 * height / 3, "Load Game (L)");
//        StdDraw.text(width / 2, 1 * height / 3 - 2, "Quit (Q)");
//        StdDraw.show();
//        StdDraw.enableDoubleBuffering();
//    }


    private void letUserType(String typed) {
        int width = WIDTH / 2;
        int height = HEIGHT + HEIGHT_TOP;

        Font fontText = new Font("Monaco", Font.BOLD, 20);
        StdDraw.clear(new Color(0, 0, 0));
        Font fontTitle = new Font("Monaco", Font.BOLD, 40);
        StdDraw.setFont(fontTitle);
        StdDraw.text(width / 2, 3 * height / 4, "Create New World");

        StdDraw.setFont(fontText);
        StdDraw.text(width / 2, 1 * height / 3 + 2, "Please type the seed");
        StdDraw.text(width / 2, 1 * height / 3, "Format: N#S, # is number");
        StdDraw.text(width / 2, 1 * height / 3 - 2, "You've typed: " + typed);
//        StdDraw.show();
//        StdDraw.enableDoubleBuffering();
    }

    private String transferKBtoInput() {
        String input = "";
        char firstChar;
        while (true) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            } else {
                firstChar = StdDraw.nextKeyTyped();
                break;
            }
        }

        if (firstChar == 'l' || firstChar == 'L') {
            input = loadInput();
        } else if (firstChar == 'N' || firstChar == 'n') {

            char c = firstChar;
            input += c;
            letUserType(input);
            while (true) {
                if (!StdDraw.hasNextKeyTyped()) {
                    continue;
                } else {
                    c = StdDraw.nextKeyTyped();
                    input += c;
                    letUserType(input);
                    if (c == 's' || c == 'S') {
                        break;
                    }
                }
            }
        } else {
            throw new IllegalArgumentException("please start with N or start with L");
        }
        return input;
    }



    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        ter.initialize(WIDTH, HEIGHT);
        // TODO: Fill out this method so that it run the engine using the input
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.

        WorldProcessor wp = new WorldProcessor();
        InputSource is = new StringSource(input);

        wp.processWorld(is, ter);

        return wp.getWorld();


//        boolean succeed = false;
//        if (input == null) {
//            throw new IllegalArgumentException("Please provide a string that meets all requirements in this mode!");
//        } else {
//
//            char firstChar = input.charAt(0);
//            // get Real Input String
//            if (firstChar == 'L' || firstChar == 'l') {
//                input = loadInput() + input.substring(1, input.length());
//            }
//            // get Seed from Input
//            String seedS = fromInputToSeed(input);
//
//            // generate the initial world
//            long seed = Integer.parseInt(seedS);
//            WorldGenerator wg = new WorldGenerator(seed);
//            wg.generate();
//
//            // get play code
//            int moveStartPos = seedS.length() + 1;
//            List<DIRECTION> moveDirList = new LinkedList<>();
//
//            for (int i = moveStartPos; i < input.length() - 1; i++) {
//                char c = input.charAt(i);
//                char n = input.charAt(i + 1);
//                if (c + n == ':' + 'q' || c + n == ':' + 'Q') {
//                    saveInput(input.substring(0, i));
//                    break;
//                } else {
//                    moveDirList.add(fromCharToDir(c));
//                }
//            }
//
//            succeed = wg.moveManySteps(moveDirList);
//
//            TETile[][] finalWorldFrame = wg.world();
//
//            if (succeed) {
//                ter.renderFrame(finalWorldFrame, "Congratulations! You open the door!");
//            } else {
//                ter.renderFrame(finalWorldFrame);
//            }
//            return finalWorldFrame;
//        }
    }

    private String fromInputToSeed (String input) {
        char firstChar = input.charAt(0);
        int seedStartPos = 1;
        String seedS = "";

        if (firstChar == 'N' || firstChar == 'n') {
            for (int i = seedStartPos; i < input.length(); i++) {
                char c = input.charAt(i);
                if (c == 'S' || c == 's') {
                    break;
                } else if (c >= '0' && c <= '9') {
                    seedS += c;
                } else {
                    throw new IllegalArgumentException("please start with N end with S, or start with L");
                }
            }
        }
        return seedS;
    }

    private DIRECTION fromCharToDir (char c) {
        DIRECTION dir;
        if (c == 'W' ||  c == 'w') {
            dir = DIRECTION.UP;
        } else if (c == 'S' ||  c == 's') {
            dir = DIRECTION.DOWN;
        } else if (c == 'A' ||  c == 'a') {
            dir = DIRECTION.LEFT;
        } else if (c == 'D' ||  c == 'd') {
            dir = DIRECTION.RIGHT;
        } else {
            dir = DIRECTION.SAME;
        }
        return dir;
    }


    // saveInput
    private static String loadInput() {
        File f = new File("./save_input.txt");
        if (f.exists()) {
            try {
                FileReader fr = new FileReader(f);
                BufferedReader br = new BufferedReader(fr);
                String input = br.readLine();
                br.close();
                return input;
            } catch (FileNotFoundException e) {
                System.out.println("file not found");
                System.exit(0);
            } catch (IOException e) {
                System.out.println(e);
                System.exit(0);
            }
        }

        /* In the case no Editor has been saved yet, we return a new one. */
        return "";
    }

    private static void saveInput(String input) {
        File f = new File("./save_input.txt");
        try {
            f.delete();
            f.createNewFile();
            FileWriter fw = new FileWriter(f);
            fw.write(input);
            fw.close();
        }  catch (FileNotFoundException e) {
            System.out.println("file not found");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
    }
}
