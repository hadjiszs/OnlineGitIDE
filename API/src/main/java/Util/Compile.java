package Util;

import Model.Project;
import Model.TemporaryFile;
import Model.User;
import Service.*;

import java.io.*;
import java.util.List;

import static Util.Constantes.*;

/**
 * Created by Mahmoud on 15/11/2016.
 */
public class Compile {


    public Compile() {
    }

    public String executeCompilation(Long idProject, Long idCurrentUser,String branch) throws InterruptedException, IOException, DataException {

        /*
        params {propOfProject : mahmoud , projectName : appTest , currentUser : user}
        besoin de currentProject,currentUser,
        */


        UserGrantService userGrantService = new UserGrantServiceImpl();
        ProjectService projectService = new ProjectServiceImpl();
        UserService userService = new UserServiceImpl();

        // On récupère le prop
        User prop = userGrantService.getAdminByEntity(idProject);
        // On récupère le project
        Project currentProject = projectService.getEntityById(idProject);
        // On récupère CurrentUser
        User currentUser = userService.getEntityById(idCurrentUser);


        // 1 - CLONE
        this.executeAction(CLONE_ACTION, prop.getIdUser().toString(), currentProject.getIdProject().toString(), currentUser.getIdUser().toString());
        // 2 - update Project Files (temp)
        this.updateCloneRepo(currentProject.getIdProject(), currentUser.getIdUser());
        // 3 - COMPILATION
        this.executeAction(COMPILE_ACTION, prop.getIdUser().toString(), currentProject.getIdProject().toString(), currentUser.getIdUser().toString());
        // 4 - GET RESULT
        String result = this.getCompilationResult(currentUser.getIdUser().toString());
        // 5 - clean
        this.executeAction(CLEAN_ACTION, prop.getIdUser().toString(), currentProject.getIdProject().toString(), currentUser.getIdUser().toString()); //CLEAN
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
            p1 = rt.exec(SCRIPTS_PATH + "/" + SCRIPT_COMPILE_JAVA + " " + CLONE_PATH + " " + RESULTS_PATH + " " + idCurrentUser + " " + idProject);
        }
        if (action.toString().equals(CLONE_ACTION)) {
            p1 = rt.exec(SCRIPTS_PATH + "/" + SCRIPT_CLONE + " " + CLONE_PATH + " " + REPO_PATH + " " + propId + " " + idProject + " " + idCurrentUser);
        }
        if (action.toString().equals(CLEAN_ACTION)) {
            p1 = rt.exec(SCRIPTS_PATH + "/" + SCRIPT_CLEAN + " " + CLONE_PATH + " " + RESULTS_PATH + " " + idCurrentUser);
        }


        if (action.equals(SCRIPT_MV_TEMP_FILE)) {

            p1 = rt.exec(SCRIPTS_PATH + "/" + SCRIPT_MV_TEMP_FILE + " " + CLONE_PATH + " " + RESULTS_PATH + " " + idCurrentUser);

        }

    }


    public void updateCloneRepo(Long idCurrentProject, Long idCurrentUser) throws DataException, IOException {


        // 1) on récupère la liste des TempFiles
        List<TemporaryFile> temporaryFileList = getTempFiles(idCurrentProject, idCurrentUser);
        // 2) Creation des tempFiles + remplissage + deplacement


        String filePath;
        String fileName = "";
        String fileExt = "";



        for (int i = 0; i < temporaryFileList.size(); i++) {

            filePath = temporaryFileList.get(i).getPath();
            fileName = "test"; // to up
            fileExt = "java"; // to up

            // 1) creation du fichier
            createFile(temporaryFileList.get(i));
            // 2) remplir le fichier - content
            setContentFile(temporaryFileList.get(i), temporaryFileList.get(i).getContent());
            // 3) deplacer le fichier
            mvFilesToCloneRepo(fileName, fileExt, filePath, idCurrentUser.toString());

        }


    }


    public List getTempFiles(Long IdCurrentProject, Long idCurrentUser) throws DataException {
        TemporaryFileService temporaryFileService = new TemporaryFileServiceImpl();
        return temporaryFileService.getEntityByUserProject(IdCurrentProject, IdCurrentProject);
    }


    public void createFile(TemporaryFile tempFile) throws IOException {
        //il me faut le nom du fichier + extension
        File file = new File(TEMPFILES_PATH + "/test.java");

        if (file.createNewFile()) {
            System.out.println("File is created!");
        } else {
            System.out.println("File already exists.");
        }

    }

    public void setContentFile(TemporaryFile tempFile, String content) throws IOException {

        //il me faut le nom du fichier + extension
        FileWriter out = new FileWriter(TEMPFILES_PATH + "/test.java");
        BufferedWriter bw = new BufferedWriter(out);
        bw.write(content);
        bw.close();
    }

    public void mvFilesToCloneRepo(String fileName, String fileExt, String filePath, String idCurrentUser) throws IOException {
        Process p1;
        Runtime rt = Runtime.getRuntime();

        p1 = rt.exec(SCRIPTS_PATH + "/" + SCRIPT_MV_TEMP_FILE + " " + TEMPFILES_PATH + " " + fileName + " " +
                fileExt + " " + CLONE_PATH + " " + idCurrentUser + " "
                + filePath);

    }


}
