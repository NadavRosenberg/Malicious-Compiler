# Malicious Compiler
* [Intro](#intro)
* [How to install](#how-to-install)
* [The project's main functionality](#the-projects-main-functionality) 
* [Self-reproducing program (Quine)](#self-reproducing-program-quine)
* [Compiler knowledge propagation](#compiler-knowledge-propagation)
  + [High Level Explanation](#high-level-explanation)
  + [How it actually works](#how-it-actually-works)
* [Verifying the compiler binary](#verifying-the-compiler-binary)
  + [Understanding the problem](#understanding-the-problem)
  + [Subverting Verification](#subverting-verification)
* [How to inject this compiler to an average user](#how-to-inject-this-compiler-to-an-average-user)
* [Ways to operate](#ways-to-operate)
* [Authors](#authors)

##  Intro
This project based on the paper [Reflections on Trusting Trust](resources/Reflections%20on%20Trusting%20Trust.pdf) by Ken
Thompson who was awarded the Turing award along Dennis Ritchie back in 1983 for their contribution to the design of 
operating systems and in particular the implementation of the UNIX operating system.
This paper is actually a transcription of his turing award lecture.

## How to install
Follow the steps explained in [README-INSTALL.md](README-INSTALL.md) file.

## The project's main functionality
After the previous step ([How to install](#how-to-install)), you will be able to compile an arbitrary program written in Java,
so that all the system's output's will be changed to "You have been hacked".
To be more specific, all `System.out.println(*)` will change to `System.out.println("You have been hacked")`.

Also, every time you compile the clean compiler using the dirty compiler, you will get the dirty one. This meant to keep the 
compiler dirty even if the user decides to use a different compiler. This is more sophisticated than it's sounds and will be 
discussed later on.

## Self-reproducing program (Quine)
A source program that outputs an exact copy of its source, when compiled.

The method `getDirtyCompilerSourceCode()` resulting in the complete source code of the dirty compiler (with a little 
modifications). In case we detect the clean/dirty compiler, we create an `.class` file with that result. 

<small>Notice: Even if this program compiles to binary, it will still have the ability to self-reproduce.</small>

## Compiler knowledge propagation
### High Level Explanation
First we compile the dirty compiler to produce binary file. 
Then we install this binary as the official C compiler (see 
[How to inject this compiler to an average user](https://github.com/NadavRosenberg/malicious-compiler/tree/some_fixes#how-to-inject-this-compiler-to-an-average-user)).
We can now compile the clean compiler (with our dirty compiler) which result again with our dirty compiler.
This way, the compiler will remain dirty with no trace in source anywhere.

### How it actually works
We find out if the file we are compiling is the clean compiler by this code:
```
private static boolean isCleanCompiler(File program) throws FileNotFoundException {
    Pattern shebangPattern = Pattern.compile(CLEAN_COMPILER_IDENTIFIER);

    Scanner programScanner = new Scanner(program);
    String firstLine = new Scanner(program).nextLine();
    programScanner.close();

    return shebangPattern.matcher(firstLine).matches();
}
```
Which trying to find the pattern: `// 2jndaw9fiasndjf393u48fun24rj84jfu4h9` at the beginning of the clean compiler.
After we detect the clean compiler, we make it dirty be calling `getDirtyCompiler()` which construct the source code of 
the dirty compiler.

<small>Notice: we are doing the same if we detect the dirty compiler because the desired result is the same.</small>

## Verifying the compiler binary
### Understanding the problem
Current (and Expected) SHA-256 of the [clean compiler](src/CleanCompiler.java):
801d83734c881dcf3942b0978b8763c817db7c996bf9b56a438b2b07395c681c
(<small>made by <i><a href='https://emn178.github.io/online-tools/sha256.html'>Online SHA-256 Generator</a></i></small>)

Current SHA-256 of the [dirty compiler](src/DirtyCompiler.java):
805e91793de2a9b23c995d8e6b07d565331219ed3a2e9953489b4014fd113148
(<small>made by <i><a href='https://emn178.github.io/online-tools/sha256.html'>Online SHA-256 Generator</a></i></small>)

As soon as the user verify the compiler binary, he will immediately notice something wrong.

### Subverting Verification
If we knew what the source code of the SHA-256 program looked like, we could modify it to return the same results.
This can be achieved during compilation with our dirty compiler. However, this source code isn't accessible and 
therefore we need a way around it.

Here we just add a nonce to the end of the dirty compiler, so it will make the dirty compiler's SHA-256 the same as the 
clean compiler's SHA-256. Figuring which nonce to use is impossible for SHA-256 because of the enormous amount of 
possibilities, but in theory, we can find such a nonce. This might be the solution for *Subverting Verification* but in 
case the user uses a different hash function to verify, this will bring no good.

## How to inject this compiler to an average user
- ***Self-Motivation*** - By adding this compiler some desired functionalities, the user will *self-voluntarily* install it in order to
  gain all of its good aspects
- ***Unknowingly*** - *hiding* some code inside a file (like pdf) that after opening gets executed, inject the dirty
  compiler, and add the right environment path without the user even know about it. It is also possible using 
  [Macros](https://support.microsoft.com/en-us/office/create-or-run-a-macro-c6b99036-905c-49a6-818a-dfb98b7c3c9c)

## Ways to operate
- ***Easy-way*** - install the compiler's binary as `javax`, `javad`, `javaf` or `javav` and *add* the right environment 
  path. This way *typos* will start the dirty compiler
  
  ![typos.png](resources/typos.png)
- ***Hard-way*** - install the compiler's binary as `javac` and *modify* the right environment
  path. This way *doesn't require unusual behavior* from the user to start the dirty compiler
  
## Authors
Built by Nadav Rosenberg and Shlomi Haver
