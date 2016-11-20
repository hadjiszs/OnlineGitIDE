package Util;

import Model.Project;
import Model.TemporaryFile;
import Model.User;

import java.util.Date;

/**
 * Created by amaia.nazabal on 11/19/16.
 */
public abstract class TestUtil {
    protected static User user;
    protected static Project project;
    protected static TemporaryFile temporaryFile;

    protected void newUser() {
        user = new User();
        user.setIdUser(null);
        user.setUsername("test");
        user.setHashkey("password-test");
        user.setMail("test-test@test.fr");
    }

    protected void newProject() {
        project = new Project();
        project.setIdProject(null);
        project.setName("project-test");
        project.setCreationDate(new Date());
        project.setLastModification(new Date());
        project.setVersion("1.0");
        project.setType(Project.TypeProject.JAVA);
        project.setRoot("/home/project-test");
    }

    protected void newTemporaryFile(){
        temporaryFile = new TemporaryFile();
        temporaryFile.setId(null);
        temporaryFile.setHashKey("fd0-edkhgad-734ghf4-900p");
        temporaryFile.setPath("/home/project1/test");
        temporaryFile.setContent("class Test {}");
        temporaryFile.setProject(null);
        temporaryFile.setUser(null);
    }
}
