// 2jndaw9fiasndjf393u48fun24rj84jfu4h9
import java.io.IOException;

public class CleanCompiler {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println(ANSI_RED + "File wasn't provided for compilation" + ANSI_RED);
            return;
        }

        try {
            String filePath = args[0];
            if (runOriginalCompiler(filePath) == 0) {
                System.out.println(ANSI_GREEN + "The compilation ended successfully" + ANSI_GREEN);
            } else {
                System.out.println(ANSI_RED + "Something Went Wrong. Please make check that the file" + "" +
                        " path you have entered is a full path, for ex. C:\\Users\\HelloWorld.java" + ANSI_RED);
            }
        } catch (Exception ex) {
            System.out.println(ANSI_RED + "Something Went Wrong. Error: " + ex + ANSI_RED);
        }
    }

    private static int runOriginalCompiler(String filePath) throws InterruptedException, IOException {
        Runtime rt = Runtime.getRuntime();
        Process pr = rt.exec(String.format("javac %s", filePath));
        pr.waitFor();
        return pr.exitValue();
    }
}