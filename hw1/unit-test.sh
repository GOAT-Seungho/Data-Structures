#!/bin/bash

# Compile src file
javac -d bin -sourcepath src -cp . src/Poly.java

# Compile test code src file
javac -d bin -sourcepath src -cp .:lib/junit-platform-console-standalone-1.4.1.jar src/PolyTest.java

# Launch junit console launcher
java -jar lib/junit-platform-console-standalone-1.4.1.jar --cp bin/ -c PolyTest

