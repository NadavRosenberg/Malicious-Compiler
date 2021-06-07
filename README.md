# malicious-compiler
## setup
To compile the program, type the following command and hit enter:
```
javac JavaCompiler.java
```
After compilation the .java file gets translated into the .class file(byte code). Now we can run the program. To run the program, type the following command and hit enter:
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
