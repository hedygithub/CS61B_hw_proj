package byow.Core;

import java.io.*;

/**
 * A streamer to process loading and saving the string representing the world
 */
public class WorldStreamer {

    public static final String DEFAULT_PATH = "./save_input.txt";

    // save the given object at the given filename, return success or failure
    public static boolean save(String filename, String input, boolean override) {
        File f = new File(filename);
        try {
            if (f.exists()) {
                String ovrString = (override)? "to" : "not to";
                System.out.println("The same file" + filename + " already exists" +
                        " and you have chosen " + ovrString + " override the file!");
                if (override) {
                    if (!f.delete()) { // if failed
                        System.out.println("Can't delete the existing file " + filename);
                        return false;
                    }
                }
                else {
                    return true;
                }
            }
            f.createNewFile();
            FileWriter fw = new FileWriter(f);
            fw.write(input);
            fw.close();
            return true;
        }  catch (FileNotFoundException e) {
            System.out.println(fileNotFoundMsg(filename));
            return false;
        } catch (IOException e) {
            System.out.println(e);
            return false;
        }
    }

    // load the string input from a given filename, return null if something goes wrong
    public static String load(String filename) {
        File f = new File(filename);
        if (f.exists()) {
            try {
                FileReader fr = new FileReader(f);
                BufferedReader br = new BufferedReader(fr);
                String input = br.readLine();
                br.close();
                return input;
            } catch (FileNotFoundException e) {
                System.out.println(fileNotFoundMsg(filename));
                return null;
            } catch (IOException e) {
                System.out.println(e);
                return null;
            }
        }

        System.out.println(fileNotFoundMsg(filename));
        return null;
    }

    private static String fileNotFoundMsg(String filename) {
        return "file not found at " + filename;
    }
}
