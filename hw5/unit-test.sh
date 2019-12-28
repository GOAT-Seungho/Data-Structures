#!/bin/bash

# Compile src file
echo "javac -d bin -sourcepath src -cp . src/*.java"
javac -d bin -sourcepath src -cp . src/Tree.java \
                                   src/BinaryTree.java \
                                   src/Position.java \
                                   src/LinkedBinaryTree.java \
                                   src/SyntaxTree.java

# Compile test code src file
echo "javac -d bin -sourcepath src -cp .:bin:lib/junit-platform-console-standalone-1.4.1.jar src/LinkedBinaryTreeTest.java"

javac -d bin -sourcepath src -cp .:bin:lib/junit-platform-console-standalone-1.4.1.jar src/LinkedBinaryTreeTest.java

# Launch junit console launcher
echo "java -jar lib/junit-platform-console-standalone-1.4.1.jar --cp bin/ -c LinkedBinaryTreeTest"
java -jar lib/junit-platform-console-standalone-1.4.1.jar --cp bin/ -c LinkedBinaryTreeTest

# Compile test code src file
echo "javac -d bin -sourcepath src -cp .:bin:lib/junit-platform-console-standalone-1.4.1.jar src/SyntaxTreeTest.java"

javac -d bin -sourcepath src -cp .:bin:lib/junit-platform-console-standalone-1.4.1.jar src/SyntaxTreeTest.java

# Launch junit console launcher
echo "java -jar lib/junit-platform-console-standalone-1.4.1.jar --cp bin/ -c SyntaxTreeTest"
java -jar lib/junit-platform-console-standalone-1.4.1.jar --cp bin/ -c SyntaxTreeTest

