package com.multimif.util;

import com.multimif.model.Project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Mahmoud on 15/11/2016.
 */
public class Compile {


    public String executeCommpilation(String projectName,String currentUser,String projectOwner,String projectType) throws InterruptedException, IOException {
        String result;
        Process p1;
        Runtime rt = Runtime.getRuntime();

        // Phase 1 : Copie
        p1 = rt.exec("./clone.sh "+projectOwner+" "+projectName);
        p1.waitFor();

        //Phase 2 : Compilation

        // param :
        if (projectType.equals(Project.TypeProject.JAVA)) p1 = rt.exec("./compileJava.sh appTest");
        if (projectType.equals(Project.TypeProject.MAVEN)) p1 = rt.exec("./compileMvn.sh appTest");
        if (projectType.equals(Project.TypeProject.C)) p1 = rt.exec("./compileC.sh appTest");
        if (projectType.equals(Project.TypeProject.CPP)) p1 = rt.exec("./compileCpp.sh appTest");

        p1.waitFor();


        BufferedReader in;

        in = new BufferedReader(new FileReader("/Users/Mahmoud/Desktop/Multimif/testCompile/appTest/compile/log.txt"));
        result = in.readLine();

        while (result != null) {

            result = in.readLine();
        }


        //Phase 3 : Clean
        p1 = rt.exec("./clean.sh "+currentUser);
        p1.waitFor();

        in.close();

        return result;
    }
}
