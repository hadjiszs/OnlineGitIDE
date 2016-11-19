package Util;

import Model.Project;
import Model.User;
import Service.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static Util.Constantes.*;

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





        // 1 - CLONE
        this.executeAction(Constantes.COMPILE_CLONE, prop.getId().toString(), currentProject.getName(), currentUser.getId().toString());
        // 2 - update Project Files (temp)

        // 3 - COMPILATION
        this.executeAction(Constantes.COMPILE_COMPILE,  prop.getId().toString(), currentProject.getName(), currentUser.getId().toString());
        // 4 - GET RESULT
        String result = this.getCompilationResult(currentUser.getId().toString());
        // 5 - clean
        this.executeAction(Constantes.COMPILE_CLEAN,  prop.getId().toString(), currentProject.getName(), currentUser.getId().toString()); //CLEAN
        //Resultat de la compilation
        return result;
    }

    public String getCompilationResult(String userName) throws FileNotFoundException, IOException {
        String result = new String();
        String line = new String();
        BufferedReader in;

        in = new BufferedReader(new FileReader(RESULTS_PATH + userName + ".txt"));
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
            p1 = rt.exec("./compileJava.sh " +SCRIPTS_PATH + " " + userName + " " + projectName);
        }
        if (action.toString().equals(Constantes.COMPILE_CLONE)) {
            p1 = rt.exec("./clone.sh " + CLONE_PATH +" "+ propProject + " " + projectName + " " + userName);
        }
        if (action.toString().equals(Constantes.COMPILE_CLEAN)) {
            p1 = rt.exec("./clean.sh "+ RESULTS_PATH + " " + userName);
        }
    }

    public void getTempFiles(Project currentProject ,User currenUser)
    {
        //get all files from BD by CurrentUser and Project
        //for each file cp to cloneRepository () and file(PATH)

    }


}
