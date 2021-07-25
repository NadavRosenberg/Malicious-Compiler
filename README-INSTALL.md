# Malicious Compiler

## Requirements

Please use [openjdk version 16](https://java.tutorials24x7.com/blog/how-to-install-openjdk-16-on-windows)
We assume the user uses windows, though it also works on a mac environment.


## How to test it

We create an [HelloWorld.java](src/HelloWorld.java) program to demonstrate tha purpose of our project.

We assume that the path to the project's src directory is: `C:\malicious-compiler\src` and we will now reference to it as `<source_path>`.

We will start by compiling the [HelloWorld.java](src/HelloWorld.java) using this command:

```
javac <source_path>\HelloWorld.java
```

And then running it using this command:

```
java -cp <source_path> HelloWorld
```

You should see the following program:

![program_normal](./screenshots/hello_world_program_normal.png)

And this logs:

![program_normal_logs](./screenshots/hello_world_program_normal_logs.png)

### Using our compilers
We first start by compiling dirty compiler using the command: 

```
javac <source_path>\DirtyCompiler.java
```

You will get a DirtyCompiler.class file. After this you can just call:
```
java -cp <source_path> DirtyCompiler <source_path>\HelloWorld.java
```

Which will output the following response:

![dirty_compiler_hello_world](./screenshots/dirty_compiler_hello_world.png)

Then, you can just run the program by using:
```
java -cp <source_path> HelloWorld
```

This time you will see the same program running, but with our injection in the logs

![hello_world_dirty_logs](./screenshots/hello_world_dirty_logs.png)

## Now what?

So far we saw the implication of using our dirty compiler, but what if a user chooses to use the clean compiler?

We assume that the paths in the user machine are already set to our dirty-compiler and he now wants to compile the clean compiler.

Using this command you will get this [CleanCompiler.java](src/CleanCompiler.java) which looks pretty straightforward with our dirty-compiler abilities:

Run this:
```
java -cp <source_path> DirtyCompiler <source_path>\CleanCompiler.java
```

Now compile the [HelloWorld.java](src/HelloWorld.java) program using our clean compiler:

```
java -cp <source_path> CleanCompiler <source_path>\HelloWorld.java
```

and the result will be similar to the one we got [here](#using-our-compilers).


## Author
Built by Nadav Rosenberg and Shlomi Haver
