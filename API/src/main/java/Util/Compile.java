package Util;

import Model.User;
import Model.UserGrant;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Mahmoud on 15/11/2016.
 */
public class Compile {


    public Compile() {
    }

    public String executeCompilation(UserGrant userGrant, User currentUser) throws InterruptedException, IOException {
        // params {propOfProject : mahmoud , projectName : appTest , currentUser : user}
        this.executeAction(Constantes.COMPILE_CLONE, "mahmoud", "appTest", "user");  // 1 - CLONE
        // 2 - update Project Files
        this.executeAction(Constantes.COMPILE_COMPILE, "mahmoud", "appTest", "user"); // 3 - COMPILATION
        String result = this.resultStage("user"); // 4 - GET RESULT
        this.executeAction(Constantes.COMPILE_CLEAN, "mahmoud", "appTest", "user"); //CLEAN

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
            if (line == null) break;
            result += line;
            result += " \n";

        }

        //System.out.println("result : " + result);

        return result;
    }


    public void executeAction(String action, String propProject, String projectName, String userName) throws IOException {
        Process p1;
        Runtime rt = Runtime.getRuntime();

        if (action.toString().equals(Constantes.COMPILE_COMPILE)) {
            p1 = rt.exec("./compileJava.sh " + userName + " " + projectName);
        }
        if (action.toString().equals(Constantes.COMPILE_CLONE)) {
            p1 = rt.exec("./clone.sh " + propProject + " " + projectName + " " + userName);
        }
        if (action.toString().equals(Constantes.COMPILE_CLEAN)) {
            p1 = rt.exec("./clean.sh " + userName);
        }
    }


}
