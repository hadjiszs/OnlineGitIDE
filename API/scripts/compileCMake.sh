#!/usr/bin/env bash
# $1: clonePath $2:resultPath $3:currentUserName $4:projectName

cd $1/$3/$4/
mkdir build
cd build
cmake .. && make