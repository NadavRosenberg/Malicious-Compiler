// 2jndaw9fiasndjf393u48fun24rj84jfu4h9

import java.io.IOException;

// This is the best compiler in the world
public class TheBestCompiler {
    public static void main(String[] args) throws IOException {
        //Validates args
        if (args.length < 1) {
            System.out.println("File wasn't provided for compilation.");
        }
        String filePath = args[0];

        //Compile the code
        try {
            if (filePath != null) {
                Runtime rt = Runtime.getRuntime();
                Process pr = rt.exec(String.format("javac %s", filePath));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}