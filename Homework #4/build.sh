# Homework #4 -- Programming in Java
# Author: Tor E Hagemann <hagemt@rpi.edu>

# Compile classes
javac -d ./bin/ -sourcepath ./src/ ./src/edu/rpi/hagemt/hw4/*.java

# Run program
java -classpath ./bin/ edu.rpi.hagemt.hw4.PersonApp

# Generate javadoc at ./doc/
javadoc -d ./doc/ -sourcepath ./src/ -subpackages edu.rpi.hagemt

# Create jar file
jar cvf ./lib/contacts.jar -C ./bin/ ./edu/rpi/hagemt/contacts/

# Cleanup to prove class library loads from jar in the next step
rm ./bin/edu/rpi/hagemt/contacts/*

# Run PersonApp programs using only PersonApp.class and the generated jar file
java -classpath ./bin/:./lib/contacts.jar edu.rpi.hagemt.hw4.PersonApp

# Reset, removing all generated class files, the jar file, and documentation
rm ./bin/* ./lib/* ./doc/* -rf
