package com.multimif.util;

/**
 * @author Mahmoud Ayssami
 * @version 1.0
 * @since 1.0 22/11/2016.
 */
public class SplitPath {


    public static String getNameAndExt(String path) {
        String[] nameAndext = path.split("/");

        return nameAndext[nameAndext.length - 1];


    }

    public static  String  getFilePath(String path){



        String[] nameAndext = path.split("/");
        String filepath=new String();
        for(int i=1;i<nameAndext.length-1;i++)
        {
            filepath += "/"+nameAndext[i].toString();
        }

        return filepath;
    }

    public static String getFileExtension(String path) {
        String nameExt = getNameAndExt(path);
        String[] split = nameExt.split("\\.");
        return split[split.length - 1];
    }


    public static String getFileName(String path) {

        String nameExt = getNameAndExt(path);
        String[] split = nameExt.split("\\.");
        return split[0];
    }




}
