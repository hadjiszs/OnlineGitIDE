package com.multimif.compilation;

import com.multimif.model.Project;
import com.multimif.model.TemporaryFile;
import com.multimif.model.User;
import com.multimif.service.*;
import com.multimif.util.DataException;
import com.multimif.util.SplitPath;

import java.io.*;
import java.util.List;

import static com.multimif.util.Constantes.*;


/**
 * @author Mahmoud
 * @version 1.0
 * @since 1.0 15/11/2016.
 */
public class Compile {

    UserGrantService userGrantService = new UserGrantServiceImpl();
    ProjectService projectService = new ProjectServiceImpl();
    UserService userService = new UserServiceImpl();


    public Compile() {
    }

    public String executeCompilation(Long idProject, Long idCurrentUser, String branch) throws InterruptedException, IOException, DataException {

        /*
        params {propOfProject : mahmoud , projectName : appTest , currentUser : user}
        besoin de currentProject,currentUser,
        */


        // On récupère le prop
        User prop = userGrantService.getAdminByEntity(idProject);
        // On récupère le project
        Project currentProject = projectService.getEntityById(idProject);
        // On récupère CurrentUser
        User currentUser = userService.getEntityById(idCurrentUser);


        // 1 - CLONE
        this.executeAction(CLONE_ACTION, prop.getUsername(), currentProject.getName(), currentUser.getUsername());
        // 2 - update Project Files (temp)
        this.updateCloneRepo(currentProject.getIdProject(), currentUser.getIdUser());
        // 3 - COMPILATION
        this.executeAction(COMPILE_ACTION, prop.getUsername(), currentProject.getName(), currentUser.getUsername());
        // 4 - GET RESULT
        String result = this.getCompilationResult(currentUser.getUsername());
        // 5 - clean
        this.executeAction(CLEAN_ACTION, prop.getUsername(), currentProject.getName(), currentUser.getUsername()); //CLEAN
        //Resultat de la compilation
        return result;
    }

    public String getCompilationResult(String userName) throws FileNotFoundException, IOException {
        String result = new String();
        String line = new String();
        BufferedReader in;

        in = new BufferedReader(new FileReader(RESULTS_PATH + "/" + userName + ".txt"));
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


    public void executeAction(String action, String propId, String idProject, String idCurrentUser) throws IOException, InterruptedException {
        Process p1 = null;
        Runtime rt = Runtime.getRuntime();

        if (action.toString().equals(COMPILE_ACTION)) {
            p1 = rt.exec(SCRIPTS_PATH + "/" + SCRIPT_COMPILE_JAVA + " " + CLONE_PATH + " " + RESULTS_PATH + " " + idCurrentUser + " " + idProject + ".git");
        }
        if (action.toString().equals(CLONE_ACTION)) {
            p1 = rt.exec(SCRIPTS_PATH + "/" + SCRIPT_CLONE + " " + CLONE_PATH + " " + REPO_PATH + " " + propId + " " + idProject + ".git" + " " + idCurrentUser);
        }
        if (action.toString().equals(CLEAN_ACTION)) {
            p1 = rt.exec(SCRIPTS_PATH + "/" + SCRIPT_CLEAN + " " + CLONE_PATH + " " + RESULTS_PATH + " " + idCurrentUser);
        }


        if (action.equals(SCRIPT_MV_TEMP_FILE)) {

            p1 = rt.exec(SCRIPTS_PATH + "/" + SCRIPT_MV_TEMP_FILE + " " + CLONE_PATH + " " + RESULTS_PATH + " " + idCurrentUser);

        }

        p1.waitFor();


    }


    public void updateCloneRepo(Long idCurrentProject, Long idCurrentUser) throws DataException, IOException, InterruptedException {


        // 1) on récupère la liste des TempFiles
        List<TemporaryFile> temporaryFileList = getTempFiles(idCurrentProject, idCurrentUser);
        // 2) Creation des tempFiles + remplissage + deplacement

        // On récupère le project

        Project currentProject = projectService.getEntityById(idCurrentProject);
        User currentUser = userService.getEntityById(idCurrentUser);


        String filePath;
        String fileName;
        String fileExt;


        for (int i = 0; i < temporaryFileList.size(); i++) {

            filePath = temporaryFileList.get(i).getPath();
            fileName = temporaryFileList.get(i).getName();
            fileExt = temporaryFileList.get(i).getExtension();

            // 1) creation du fichier
            createFile(temporaryFileList.get(i));
            // 2) remplir le fichier - content
            setContentFile(temporaryFileList.get(i), temporaryFileList.get(i).getContent());
            // 3) deplacer le fichier
            mvFilesToCloneRepo(fileName, fileExt, filePath, currentUser.getUsername(), currentProject.getName());

        }


    }


    public List getTempFiles(Long IdCurrentProject, Long idCurrentUser) throws DataException {
        TemporaryFileService temporaryFileService = new TemporaryFileServiceImpl();
        return temporaryFileService.getEntityByUserProject(IdCurrentProject, IdCurrentProject);
    }


    public void createFile(TemporaryFile tempFile) throws IOException {

        File file = new File(TEMPFILES_PATH + "/" + tempFile.getName() + "." + tempFile.getExtension());

        if (file.createNewFile()) {
            System.out.println("File is created!");
        } else {
            System.out.println("File already exists.");
        }

    }

    public void setContentFile(TemporaryFile tempFile, String content) throws IOException {


        FileWriter out = new FileWriter(TEMPFILES_PATH + "/" + tempFile.getName() + "." + tempFile.getExtension());
        BufferedWriter bw = new BufferedWriter(out);
        bw.write(content);
        bw.close();
    }

    public void mvFilesToCloneRepo(String fileName, String fileExt, String filePath, String currentUserName
            , String projectName) throws IOException, InterruptedException {

        Process p1;
        Runtime rt = Runtime.getRuntime();
        String pathMkdir = SplitPath.getFilePath(filePath);


        p1 = rt.exec(SCRIPTS_PATH + "/" + SCRIPT_MV_TEMP_FILE + " " + TEMPFILES_PATH + " " + fileName + " " +
                fileExt + " " + CLONE_PATH + " " + currentUserName + " " + projectName + " " + filePath + " " + pathMkdir);
        p1.waitFor();

    }


}
