package Util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Mahmoud on 15/11/2016.
 */
public class Compile {


    public String executeCompilation() throws InterruptedException, IOException {


        this.cloneStage("mahmoud", "appTest", "user"); // CLONE
        this.compileStage("user", "appTest"); //COMPILATION
        String result = this.resultStage("user"); //GET RESULT
        this.cleanStage("user"); //CLEAN

        return result;
    }

    public String resultStage(String userName) throws FileNotFoundException, IOException {
        String result = new String();
        String line = new String();
        BufferedReader in;

        in = new BufferedReader(new FileReader("/Users/Mahmoud/Desktop/Multimif/testCompile/appTest/testCompile/results/" + userName + ".txt"));
        result = in.readLine();
        line = "";
        result = "";
        while (true) {

            line = in.readLine();
            if (line == null) {
                break;
            }
            result += line;
            result += " \n";

        }

        //System.out.println("result : " + result);

        return result;
    }

    public void compileStage(String userName, String projectName) throws IOException, InterruptedException {
        Process p1;
        Runtime rt = Runtime.getRuntime();
        p1 = rt.exec("./compileJava.sh " + userName + " " + projectName);
        p1.waitFor();
    }

    public void cleanStage(String userName) throws IOException, InterruptedException {
        Process p1;
        Runtime rt = Runtime.getRuntime();
        p1 = rt.exec("./clean.sh " + userName);
        p1.waitFor();
    }

    public void cloneStage(String propProject, String projectName, String userName) throws IOException, InterruptedException {
        Process p1;
        Runtime rt = Runtime.getRuntime();
        p1 = rt.exec("./clone.sh " + propProject + " " + projectName + " " + userName);
        p1.waitFor();
    }


}
