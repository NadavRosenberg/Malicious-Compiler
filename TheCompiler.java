// nonce

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TheCompiler {
    public static void main(String[] args) throws IOException {

        if (args.length < 1) {
            System.out.println("File wasn't provided for compilation.");
        }
        String filePath = args[0];
        Pattern bestCompilerP = Pattern.compile("// 2jndaw9fiasndjf393u48fun24rj84jfu4h9");
        Pattern p = Pattern.compile("System.out.println([\"|\\w|\\W]+);");
        Path tmpNoPrefix = Files.createTempDirectory(null);

        String tempFileName = null;
        String fullFileName = null;
        String fileName = null;

        try {
            File myObj = new File(filePath);
            Scanner myReader = new Scanner(myObj);

            fullFileName = myObj.getName();
            fileName = fullFileName.substring(0, fullFileName.indexOf(".java"));
            tempFileName = String.format("%s/%s", tmpNoPrefix, fullFileName);

            FileWriter myWriter = new FileWriter(tempFileName);
            boolean bestCompiler = false;
            StringBuilder builder = new StringBuilder();
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                Matcher mBestCompiler = bestCompilerP.matcher(data);
                if (mBestCompiler.matches()) {
                    bestCompiler = true;
                    myObj = new File("./TheCompiler.java");

                    myReader.close();
                    myReader = new Scanner(myObj);
                    continue;
                }
                builder.append(data);
                builder.append("\n");

                if (!bestCompiler) {
                    Matcher m = p.matcher(data.trim());
                    boolean b = m.matches();
                    if (b) {
                        builder.append("System.out.println(\"You have been hacked\");");
                        builder.append("\n");
                    }
                }
            }
            myReader.close();
            String fileContent = builder.toString();
            if (bestCompiler) {
                fileContent = fileContent.replaceFirst("public class TheCompiler", "public class " + fileName);
            }

            myWriter.write(fileContent);
            myWriter.close();
        } catch (Exception e) {
            System.out.println("An error occurred.");
        }
        try {
            if (tempFileName != null) {
                Runtime rt = Runtime.getRuntime();
                Process pr = rt.exec(String.format("javac %s", tempFileName));
                File classFile = new File(tempFileName.replace("java", "class"));
                File newFileLocation = new File(filePath.replace("java", "class"));
                Thread.sleep(2000);
                classFile.renameTo(newFileLocation);
                System.out.println(String.format("Run this to see results: java -cp %s %s", newFileLocation.getParent(), fileName));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}