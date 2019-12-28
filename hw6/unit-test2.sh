#!/bin/bash

# Compile src file
echo "javac -d bin -sourcepath src -cp . src/*.java"
javac -d bin -sourcepath src -cp . src/OrderedMap.java \
                                   src/Position.java \
                                   src/Tree.java \
                                   src/BinaryTree.java \
                                   src/Entry.java \
                                   src/LinkedBinarySearchTree.java

# Compile test code src file
echo "javac -d bin -sourcepath src -cp .:bin:lib/junit-platform-console-standalone-1.4.1.jar src/LinkedBinarySearchTreeTest.java"

javac -d bin -sourcepath src -cp .:bin:lib/junit-platform-console-standalone-1.4.1.jar src/LinkedBinarySearchTreeTest.java

# Launch junit console launcher
echo "java -jar lib/junit-platform-console-standalone-1.4.1.jar --cp bin/ -c LinkedBinarySearchTreeTest"
java -jar lib/junit-platform-console-standalone-1.4.1.jar --cp bin/ -c LinkedBinarySearchTreeTest


