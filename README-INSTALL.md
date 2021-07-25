# Malicious Compiler
## Easy Installer setup
Just make the active directory the project's directory using `cd` command and then run the EasyInstallerSetup.txt:
```
cmd < EasyInstallerSetup.txt
```
NOTICE: You can delete all the unnecessary files created with the command `cmd < EasyInstallerSetup.txt` with: 
```
cmd < CleanTempFiles.txt
```

## Manual Installer setup
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

5. Now we can create the installer by using Inno Setup. Further reading at https://jrsoftware.org/ishelp.phpcreate. After Installation of Inno Setup, just run:
```
"C:\Program Files (x86)\Inno Setup 6\Compil32.exe" /cc C:\malicious-compiler\config\InstallerSettings.iss
```
NOTICE: This will add environment paths for `C:\Program Files (x86)\TheCompiler`.

6. (OPTIONALY) Delete the temp files:
```
del TheCompiler.class
del TheCompiler.jar
del javax.exe
del javad.exe
del javaf.exe
del javav.exe
```

## Author
Built by Nadav Rosenberg and Shlomi Haver
