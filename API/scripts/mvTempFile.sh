# $1:tempFilePath $2:fileName $3:FileExtension $4:ClonePath $5:idCurrentUser $6:projectName $7:filePath $8:just le path

mkdir -p $4/$5/$6.git/$8
cp -rf $1/$2.$3 $4/$5/$6.git$8 #on fait attention.
rm  $1/$2.$3