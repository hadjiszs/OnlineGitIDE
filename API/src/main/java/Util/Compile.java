package Util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static Util.Constantes.*;

/**
 * Created by Mahmoud on 15/11/2016.
 */
public class Compile {


    public String executeCommpilation(String typeProjet) throws InterruptedException, IOException {
        String result = new String();
        Process p1 = null;


        Runtime rt = Runtime.getRuntime();


        if (typeProjet.equals(PROJECT_JAVA)) p1 = rt.exec("./compileJava.sh appTest");
        if (typeProjet.equals(PROJECT_MVN)) p1 = rt.exec("./compileMvn.sh appTest");
        if (typeProjet.equals(PROJECT_C)) p1 = rt.exec("./compileC.sh appTest");
        if (typeProjet.equals(PROJECT_CPP)) p1 = rt.exec("./compileCpp.sh appTest");


        p1.waitFor();

        BufferedReader in;

        in = new BufferedReader(new FileReader("/Users/Mahmoud/Desktop/Multimif/testCompile/appTest/compile/log.txt"));
        result = in.readLine();

        while (result != null) {

            result = in.readLine();
        }

        return result;
    }
}
