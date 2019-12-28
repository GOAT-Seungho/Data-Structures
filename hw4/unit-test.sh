#!/bin/bash

# Compile src file
echo "javac -d bin -sourcepath src -cp . src/*.java"

javac -d bin -sourcepath src -cp . src/Fibonacci.java \
                                   src/PigLatin.java \
                                   src/Box.java

# Compile test code src file
echo "javac -d bin -sourcepath src -cp .:bin:lib/junit-platform-console-standalone-1.4.1.jar src/Homework4Test.java"

javac -d bin -sourcepath src -cp .:bin:lib/junit-platform-console-standalone-1.4.1.jar src/Homework4Test.java

# Launch junit console launcher
echo "java -jar lib/junit-platform-console-standalone-1.4.1.jar --cp bin/ -c Homework4Test"
java -jar lib/junit-platform-console-standalone-1.4.1.jar --cp bin/ -c Homework4Test

