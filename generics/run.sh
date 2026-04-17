#!/bin/bash
if [[ -z $1 ]]; then
  echo "Missign argument: main"
  echo "You must run './run.sh <MainClass>' or './run.sh doc' to generate documentation"
  exit 1
fi

if [[ "$1" == "doc" ]];then
  echo "--GENERATING DOCUMENTATION"
  javadoc -d doc -author -encoding UTF-8 -charset UTF-8 -sourcepath src src/*.java
else
  MAIN_CLASS=$1

  mkdir -p bin

  echo "--COMPILING"
  javac -d bin -sourcepath src src/*/*.java

  echo "--EXECUTING $MAIN_CLASS"
  java -cp bin $MAIN_CLASS
fi
