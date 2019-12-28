#!/bin/bash

# Compile src file
echo "javac -d bin -sourcepath src -cp . src/*.java"

javac -d bin -sourcepath src -cp . src/DLinkedList.java \
                                   src/Polynomial.java \
                                   src/DLinkedPolynomial.java \
                                   src/NoSuchTermExistsException.java \
                                   src/PolynomialDriver.java

# Compile test code src file
echo "javac -d bin -sourcepath src -cp .:bin:lib/junit-platform-console-standalone-1.4.1.jar src/DLinkedListTest.java src/DLinkedPolynomialTest.java"

javac -d bin -sourcepath src -cp .:bin:lib/junit-platform-console-standalone-1.4.1.jar src/DLinkedListTest.java

echo "javac -d bin -sourcepath src -cp .:lib/junit-platform-console-standalone-1.4.1.jar src/DLinkedListTest.java src/DLinkedPolynomialTest.java"

javac -d bin -sourcepath src -cp .:lib/junit-platform-console-standalone-1.4.1.jar src/DLinkedPolynomialTest.java

# Launch junit console launcher
echo "java -jar lib/junit-platform-console-standalone-1.4.1.jar --cp bin/ -c DLinkedListTest"
java -jar lib/junit-platform-console-standalone-1.4.1.jar --cp bin/ -c DLinkedListTest

echo "java -jar lib/junit-platform-console-standalone-1.4.1.jar --cp bin/ -c DLinkedPolynomialTest"
java -jar lib/junit-platform-console-standalone-1.4.1.jar --cp bin/ -c DLinkedPolynomialTest

