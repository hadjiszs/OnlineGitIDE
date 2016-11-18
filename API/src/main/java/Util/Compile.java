package Util;

import Model.Project;
import Model.User;
import Model.UserGrant;
import Service.*;

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

    public String executeCompilation(long idProject,Long idCurrentUser) throws InterruptedException, IOException, DataException {

        /*
        params {propOfProject : mahmoud , projectName : appTest , currentUser : user}
        besoin de currentProject,currentUser,
        */


        UserGrantService userGrantService= new UserGrantServiceImpl();
        ProjectService projectService = new ProjectServiceImpl();
        UserService userService = new UserServiceImpl();

        // On récupère le prop
        User prop= userGrantService.getAdminByEntity(idProject);
        // On récupère le project
        Project currentProject = projectService.getEntityById(idProject);
        // On récupère CurrentUser
        User currentUser = userService.getEntityById(idCurrentUser);






        this.executeAction(Constantes.COMPILE_CLONE, prop.getId().toString(), currentProject.getName(), currentUser.getId().toString());  // 1 - CLONE
        // 2 - update Project Files
        this.executeAction(Constantes.COMPILE_COMPILE,  prop.getId().toString(), currentProject.getName(), currentUser.getId().toString()); // 3 - COMPILATION
        String result = this.resultStage(currentUser.getId().toString()); // 4 - GET RESULT
        this.executeAction(Constantes.COMPILE_CLEAN,  prop.getId().toString(), currentProject.getName(), currentUser.getId().toString()); //CLEAN

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

    public void getTempFiles()
    {
        //get all files from BD by CurrentUser and Project
        //for each file cp to cloneRepository () and file(PATH)

    }


}
