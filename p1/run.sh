#!/bin/bash
if [[ -z $1 ]]; then
  echo "Missign argument: main"
  echo "You must run './run.sh <MainClass>'"
  exit 1
fi

MAIN_CLASS=$1

mkdir -p bin

echo "--COMPILING"
javac -d bin -sourcepath src src/*.java

echo "--EXECUTING $MAIN_CLASS"
java -cp bin $MAIN_CLASS
