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

    TemporaryFileService temporaryFileService = new TemporaryFileServiceImpl();
    UserGrantService userGrantService = new UserGrantServiceImpl();
    ProjectService projectService = new ProjectServiceImpl();
    UserService userService = new UserServiceImpl();

    User creator;
    User currentUser;
    Project currentProject;

    public Compile(Long idProject, Long idCurrentUser, String branch) throws DataException {
        /*
            params {propOfProject : mahmoud , projectName : appTest , currentUser : user}
            besoin de currentProject,currentUser,
        */

        // On récupère le creator
        this.creator = userGrantService.getAdminByEntity(idProject);
        // On récupère le project
        this.currentProject = projectService.getEntityById(idProject);
        // On récupère CurrentUser
        this.currentUser = userService.getEntityById(idCurrentUser);
    }

    public String execute() throws InterruptedException, IOException, DataException {
        // 1 - CLONE
        this.executeAction(CLONE_ACTION);
        // 2 - update Project Files (temp)
//        this.updateCloneRepo();
        // 3 - COMPILATION
        this.executeAction(COMPILE_ACTION);
        // 4 - GET RESULT
        String result = this.getCompilationResult();
        // 5 - clean
        this.executeAction(CLEAN_ACTION); //CLEAN
        //Resultat de la compilation


        return result;
    }

    public String getCompilationResult() throws FileNotFoundException, IOException {

        String result = new String();
        String line = new String();
        BufferedReader in;

        in = new BufferedReader(new FileReader(RESULTS_PATH + "/" + currentUser.getUsername() + ".txt"));
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


    public void executeAction(String action) throws IOException, InterruptedException {
        Process process = null;
        Runtime rt = Runtime.getRuntime();

        String execLine = SCRIPTS_PATH + "/";
        switch (action) {
            case COMPILE_ACTION:

                if (currentProject.getType() == Project.TypeProject.JAVA) {
                    execLine += SCRIPT_COMPILE_JAVA + " " + CLONE_PATH + " " + RESULTS_PATH + " " + currentUser.getUsername() + " " + currentProject.getName() + ".git";
                } else if (currentProject.getType() == Project.TypeProject.MAVEN) {
                    execLine += SCRIPT_COMPILE_MAVEN + " " + CLONE_PATH + " " + RESULTS_PATH + " " + currentUser.getUsername() + " " + currentProject.getName() + ".git";
                    // TODO:
                    // mvn package
                    // mv .war
                }

                break;
            case CLONE_ACTION:
                execLine += SCRIPT_CLONE + " " + CLONE_PATH + " " + REPO_PATH + " " + creator.getUsername() + " " + currentProject.getName() + ".git" + " " + currentUser.getUsername();
                break;
            case CLEAN_ACTION:
                execLine += SCRIPT_CLEAN + " " + CLONE_PATH + " " + RESULTS_PATH + " " + currentUser.getUsername();
                break;
        }

        process = rt.exec(execLine);
        process.waitFor();

    }


    public void updateCloneRepo() throws DataException, IOException, InterruptedException {
        // 1) on récupère la liste des TempFiles
        List<TemporaryFile> temporaryFileList = temporaryFileService.getEntityByUserProject(currentProject.getIdProject(), currentUser.getIdUser());

        // 2) Creation des tempFiles + remplissage + deplacement
        String filePath;
        String fileName;
        String fileExt;

        for (TemporaryFile temporaryFile : temporaryFileList) {
            filePath = temporaryFile.getPath();
            fileName = temporaryFile.getName();
            fileExt = temporaryFile.getExtension();

            // 1) creation du fichier
            createFile(temporaryFile);

            // 2) remplir le fichier - content
            setContentFile(temporaryFile, temporaryFile.getContent());

            // 3) deplacer le fichier
            mvFilesToCloneRepo(fileName, fileExt, filePath);
        }
    }

    public void createFile(TemporaryFile tempFile) throws IOException {
        File file = new File(TEMPFILES_PATH + "/" + tempFile.getName() + "." + tempFile.getExtension());

        file.createNewFile();
        System.out.println("File is created!");

    }

    public void setContentFile(TemporaryFile tempFile, String content) throws IOException {
        FileWriter out = new FileWriter(TEMPFILES_PATH + "/" + tempFile.getName() + "." + tempFile.getExtension());
        BufferedWriter bw = new BufferedWriter(out);
        bw.write(content);
        bw.close();
    }

    public void mvFilesToCloneRepo(String fileName, String fileExt, String filePath) throws IOException, InterruptedException {
        Process process;
        Runtime rt = Runtime.getRuntime();
        String pathMkdir = SplitPath.getFilePath(filePath);

        process = rt.exec(SCRIPTS_PATH + "/" + SCRIPT_MV_TEMP_FILE + " " + TEMPFILES_PATH + " " + fileName + " " +
                fileExt + " " + CLONE_PATH + " " + currentUser.getUsername() + " " + currentProject.getName() + " " + filePath + " " + pathMkdir);
        process.waitFor();
    }
}