#!/bin/bash

# Compile src file
echo "javac -d bin -sourcepath src -cp . src/*.java"
javac -d bin -sourcepath src -cp . src/OrderedMap.java \
                                   src/SearchTable.java

# Compile test code src file
echo "javac -d bin -sourcepath src -cp .:bin:lib/junit-platform-console-standalone-1.4.1.jar src/SearchTableTest.java"

javac -d bin -sourcepath src -cp .:bin:lib/junit-platform-console-standalone-1.4.1.jar src/SearchTableTest.java

# Launch junit console launcher
echo "java -jar lib/junit-platform-console-standalone-1.4.1.jar --cp bin/ -c SearchTableTest"
java -jar lib/junit-platform-console-standalone-1.4.1.jar --cp bin/ -c SearchTableTest


