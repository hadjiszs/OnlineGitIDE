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

    public  String executeCompilation(long idProject,Long idCurrentUser) throws InterruptedException, IOException, DataException {

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
        this.executeAction(CLONE_ACTION, prop.getIdUser().toString(), currentProject.getIdProject().toString(), currentUser.getIdUser().toString());
        // 2 - update Project Files (temp)

        // 3 - COMPILATION
        this.executeAction(COMPILE_ACTION,  prop.getIdUser().toString(), currentProject.getIdProject().toString(), currentUser.getIdUser().toString());
        // 4 - GET RESULT
        String result = this.getCompilationResult(currentUser.getIdUser().toString());
        // 5 - clean
        this.executeAction(CLEAN_ACTION,  prop.getIdUser().toString(), currentProject.getIdProject().toString(), currentUser.getIdUser().toString()); //CLEAN
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


    public void executeAction(String action, String propId, String idProject, String idCurrentUser) throws IOException {
        Process p1;
        Runtime rt = Runtime.getRuntime();

        if (action.toString().equals(COMPILE_ACTION)) {
            p1 = rt.exec(SCRIPTS_PATH+"/"+SCRIPT_COMPILE_JAVA+" "+CLONE_PATH +" "+ RESULTS_PATH + " " + idCurrentUser + " " + idProject);
        }
        if (action.toString().equals(CLONE_ACTION)) {
            p1 = rt.exec(SCRIPTS_PATH+"/"+SCRIPT_CLONE+" " + CLONE_PATH +" " + REPO_PATH +" "+ propId + " " + idProject + " " + idCurrentUser);
        }
        if (action.toString().equals(CLEAN_ACTION)) {
            p1 = rt.exec(SCRIPTS_PATH+"/"+SCRIPT_CLEAN+" "+ CLONE_PATH +" " +RESULTS_PATH + " " + idCurrentUser);
        }
    }

    public void getTempFiles(Project currentProject ,User currenUser)
    {
        //get all files from BD by CurrentUser and Project
        //for each file cp to cloneRepository () and file(PATH)

    }


}
