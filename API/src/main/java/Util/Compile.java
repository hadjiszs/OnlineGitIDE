package Util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static Util.Constantes.*;

/**
 * Created by Mahmoud on 15/11/2016.
 */
public class Compile {


    public String executeCommpilation() throws InterruptedException, IOException {
        String result = new String("");
        String line = new String("");
        Process p1 = null;
        Runtime rt = Runtime.getRuntime();

        // Phase 1 : clone
        // parameters :
        p1 = rt.exec("./clone.sh "+"mahmoud appTest user");
        p1.waitFor();
        //Phase 2 : compilation
        // parameters :
        p1 = rt.exec("./compileJava.sh user appTest");
        p1.waitFor();
        BufferedReader in;
        in = new BufferedReader(new FileReader("/Users/Mahmoud/Desktop/Multimif/testCompile/appTest/testCompile/results/user.txt"));
        result = in.readLine();
        while (true) {

            line = in.readLine();
            if(line == null) break;
            result+=line;
            result+=" \n";
        }
        System.out.println("result : "+result );
        //Phase 3 : Clean
        p1 = rt.exec("./clean.sh user");
        p1.waitFor();
        return result;
    }


}
