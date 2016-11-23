# $1:tempFilePath $2:fileName $3:FileExtension $4:ClonePath $5:idCurrentUser $6:filePath
cp -rf $1/$2.$3 $4/$5/$6
rm  $1/$2.$3