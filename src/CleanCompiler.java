// 2jndaw9fiasndjf393u48fun24rj84jfu4h9

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.NoSuchElementException;

public class CleanCompiler {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println(ANSI_RED + "File wasn't provided for compilation." + ANSI_RED);
            return;
        }

        String filePath = args[0];
        try {
            StringBuilder builder = new StringBuilder();

            // Loading the source file
            File program = new File(filePath);

            builder.append(Files.readString(Path.of(program.getPath())));

            generateClassFile(program, builder);
            System.out.println(ANSI_GREEN + "The compilation ended successfully." + ANSI_GREEN);
            System.out.println(ANSI_GREEN + getInstruction(program) + ANSI_GREEN);

        } catch (FileNotFoundException ex) {
            System.out.println(ANSI_RED + "File '" + filePath + "' not found." + ANSI_RED);
            System.out.println(ANSI_RED + "Please make sure that the file path you have entered is a full path, " +
                    "for ex. C:\\Users\\HelloWorld.java." + ANSI_RED);
        } catch (NoSuchElementException ex) {
            System.out.println(ANSI_RED + "File '" + filePath + "' is empty" + ANSI_RED);
        } catch (Exception ex) {
            System.out.println(ANSI_RED + "Something Went Wrong. Error: " + ex + ANSI_RED);
        }
    }

    private static void generateClassFile(File program, StringBuilder builder) throws Exception {
        Path tempDirectory = Files.createTempDirectory(null);
        String tempFileName = Path.of(tempDirectory.toString(), program.getName()).toString();

        saveToTemp(builder, tempFileName);
        runOriginalCompiler(tempFileName);

        File tempClassFile = new File(replaceLast(tempFileName, "java", "class"));
        File ClassFile = new File(replaceLast(program.getPath(), "java", "class"));
        tempClassFile.renameTo(ClassFile);
    }

    private static void saveToTemp(StringBuilder builder, String tempFileName) throws IOException {
        FileWriter tempFileWriter = new FileWriter(tempFileName);
        tempFileWriter.write(builder.toString());
        tempFileWriter.close();
    }

    private static void runOriginalCompiler(String filePath) throws Exception {
        Runtime rt = Runtime.getRuntime();
        Process pr = rt.exec(String.format("javac %s", filePath));
        pr.waitFor();

        if (pr.exitValue() != 0) {
            throw new Exception();
        }
    }

    private static String replaceLast(String text, String target, String replacement) {
        int pos = text.lastIndexOf(target);
        if (pos > -1) {
            return text.substring(0, pos)
                    + replacement
                    + text.substring(pos + target.length());
        } else {
            return text;
        }
    }

    private static String getInstruction(File program) {
        String classFile = replaceLast(program.getName(), "java", "");
        String classPath = Path.of(program.getPath()).getParent().toString();
        return String.format("Run this to see results: java -cp %s %s", classPath, classFile);
    }
}