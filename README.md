# malicious-compiler
## setup
1. First make the active directory the project's directory using `cd` command.
2. To compile the program, type the following command and hit enter:
```
javac TheCompiler.java
```
After compilation the .java file gets translated into the .class file(byte code).
3. Now, we will generate our .jar files by using the command:
```
jar -cvmf manifest.txt TheCompiler.jar *.class
```
4. In order to run TheCompiler directly from the cli, we need to convert our .jar file we just generated to .exe files using Launch4j. see Docs: http://launch4j.sourceforge.net/docs.html. After Installation of Launch4j, just run:
```
"C:\Program Files (x86)\Launch4j\launch4jc.exe" C:\malicious-compiler\config\launch4jSettings_javax.xml
"C:\Program Files (x86)\Launch4j\launch4jc.exe" C:\malicious-compiler\config\launch4jSettings_javad.xml
"C:\Program Files (x86)\Launch4j\launch4jc.exe" C:\malicious-compiler\config\launch4jSettings_javaf.xml
"C:\Program Files (x86)\Launch4j\launch4jc.exe" C:\malicious-compiler\config\launch4jSettings_javav.xml
```
NOTICE: The paths may vary.
5. create the installer:

6. (OPTIONALLY) Delete the temp files:
```
del TheCompiler.class
del TheCompiler.jar
del javax.exe
del javad.exe
del javaf.exe
del javav.exe
```



Now we can run the program. To run the program, type the following command and hit enter:
```
java JavaCompiler
```
To make .jar out of .class files:
```
jar -cvmf manifest.txt javac.jar *.class
```
We can add environment paths for ‘javax’, ‘javad’, ‘javaf’ and ‘javav’ (all the commands that are close to the original ‘javac’ and hope the user will make a mistake). In order to do so we need an .exe file of the program and also set a path to the directory that owns the .exe. It’s possible to build an installer that will add this path for the user.





## How it works
To compile a Java program, a normal user type:
```
javac program_name.java
```
If the user made a mistake and write `javax`, `javad`, `javaf` or `javav` instead of `javac`, he will run the malicious compiler instead of the original Java's compiler.

## further thinking
* Build an installer for the compiler that will add an environment path to our malicious compiler. We will create the same malicious compiler in 4 different names: ‘javax’, ‘javad’, ‘javaf’ and ‘javav’ (all the commands that are close to the original ‘javac’ and hope the user will make a mistake).
