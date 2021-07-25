# Malicious Compiler

## Given Example
We create an [HelloWorld.java](src/HelloWorld.java) program to demonstrate tha purpose of our project.

We assume that the path to the project's src directory is: `C:\malicious-compiler\src`. 

compile the dirty compiler using the command: 
```
javac C:\malicious-compiler\src\DirtyCompiler.java
```
You will get a DirtyCompiler.class file. After this you can just call:
```
java -cp C:\malicious-compiler\src DirtyCompiler C:\malicious-compiler\src\HelloWorld.java
```

Then, you can just run the program by using:
```
java -cp C:\malicious-compiler\src HelloWorld
```

explain how to compile a clean version of the compiler...

## Author
Built by Nadav Rosenberg and Shlomi Haver
