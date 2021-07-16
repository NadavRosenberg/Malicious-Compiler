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
        Pattern p = Pattern.compile("System.out.println([\"|\\w|\\W]+);");
        Path tmpNoPrefix = Files.createTempDirectory(null);
        String tempFileName = null;
        String fileName = null;

        try {
            File myObj = new File(filePath);
            Scanner myReader = new Scanner(myObj);

            fileName = myObj.getName();
            tempFileName = String.format("%s/%s", tmpNoPrefix, fileName);

            FileWriter myWriter = new FileWriter(tempFileName);

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                Matcher m = p.matcher(data.trim());
                myWriter.append(data);
                boolean b = m.matches();
                if (b) {
                    myWriter.append("System.out.println(\"You have been hacked\");");
                }
            }
            myReader.close();
            myWriter.close();
        } catch (Exception e) {
            System.out.println("An error occurred.");
        }
        try {
            if (tempFileName != null) {
                Runtime rt = Runtime.getRuntime();
                Process pr = rt.exec(String.format("javac %s", tempFileName));
                System.out.println(String.format("Run this to see results: java -cp %s %s", tmpNoPrefix, fileName.substring(0, fileName.indexOf(".java"))));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}