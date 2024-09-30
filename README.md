# Instructions To Run Program

## Compile 

If you are using  **Intellij IDEA**, simply build the project using the build tab in the toolbar.

If you wish to build the program using the command line please specify a directory using the -d flag followed by 'bin' or your preferred naming convention.

An '*out*' directory may be created as well, ignore as it is not relevant to your build.

## Example:
### 'javac -d bin src/**/*.java'
^ To compile all files. 'bin' must be made prior to compilation.

Be sure to specify -cp flag when executing the binaries.

client.Client and server.Server are the two main bytecode representations.

execute them like so...
### java -cp server.Server 
### java -cp client.Client