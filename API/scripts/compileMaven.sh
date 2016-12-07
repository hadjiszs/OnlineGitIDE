#!/usr/bin/env zsh
# $1: clonePath $2:resultPath $3:currentUserName $4:projectName
cd $1/$3/$4/
asciinema rec -y -c "mvn compile"