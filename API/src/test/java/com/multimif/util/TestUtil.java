package com.multimif.util;

import com.multimif.model.Project;
import com.multimif.model.TemporaryFile;
import com.multimif.model.User;
import com.multimif.model.UserGrant;

import java.util.Date;

/**
 * Created by amaia.nazabal on 11/19/16.
 */
public abstract class TestUtil {
    protected static User user;
    protected static User admin;
    protected static User developer;
    protected static Project project;
    protected static TemporaryFile temporaryFile;
    protected static UserGrant userGrant1;
    protected static UserGrant userGrant2;

    protected void newUser() {
        user = new User();
        user.setIdUser(null);
        user.setUsername("test");
        user.setPassword("password-test");
        user.setMail("test-test@test.fr");
    }

    protected void newDeveloper(){
        developer = new User();
        developer.setIdUser(null);
        developer.setUsername("test-developer");
        developer.setMail("test-developer@test.fr");
        developer.setPassword("pass-developer");
    }

    protected void newAdmin(){
        admin = new User();
        admin.setIdUser(null);
        admin.setUsername("test-admin");
        admin.setMail("test-admin@test.fr");
        admin.setPassword("pass-admin");
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

    protected void newUserGrant1(){
        userGrant1 = new UserGrant();
        userGrant1.setPermissionType(UserGrant.PermissionType.ADMIN);
        userGrant1.setProject(project);
        userGrant1.setUser(admin);
    }

    protected void newUserGrant2(){
        userGrant2 = new UserGrant();
        userGrant2.setPermissionType(UserGrant.PermissionType.DEVELOPER);
        userGrant2.setProject(project);
        userGrant2.setUser(developer);
    }
}
