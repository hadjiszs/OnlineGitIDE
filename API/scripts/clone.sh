# $1:ClonePath - $2:RepoPath - $3:idPropOfproject - $4:idProject - $5:idCurrentUser - $6:currentBranch
#git checkout $6
mkdir $1/$5
cp -rf $2/$3/$4 $1/$5/
