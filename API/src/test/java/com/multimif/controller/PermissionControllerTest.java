package com.multimif.controller;

import com.multimif.model.Project;
import com.multimif.model.User;
import com.multimif.util.JsonUtil;
import com.multimif.util.Status;
import com.multimif.util.StatusOK;
import com.multimif.util.TestUtil;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 11/19/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/api-servlet.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PermissionControllerTest extends TestUtil{
    private PermissionController permissionController = new PermissionController();
    private UserController userController = new UserController();
    private ProjectController projectController = new ProjectController();

    @Test
    public void addTest(){
        StatusOK statusOK;
        Exception exception = null;
        ResponseEntity<Status> responseEntity = null;
        ResponseEntity<String> projectResponseEntity = null;
        ResponseEntity<User> userResponseEntity = null;
        permissionController.init();
        userController.init();
        projectController.init();

        try{
            newAdmin();
            userResponseEntity = userController.add(admin.getUsername(), admin.getMail(), admin.getPassword());
            admin.setIdUser(userResponseEntity.getBody().getIdUser());

            newDeveloper();
            userResponseEntity = userController.add(developer.getUsername(), developer.getMail(),
                    developer.getPassword());
            developer.setIdUser(userResponseEntity.getBody().getIdUser());

            /* Quand on cree le project implicitament il cree le rapport entre projet et admin */
            newProject();
            projectResponseEntity = projectController.add(project.getName(), project.getType(), admin.getIdUser());

            JsonUtil<StatusOK> jsonUtil = new JsonUtil<>();
            statusOK = jsonUtil.convertToObjectJSON(projectResponseEntity.getBody(), StatusOK.class);
            project.setIdProject(statusOK.getId());

            responseEntity = permissionController.add(project.getIdProject(), developer.getIdUser());
        }catch (Exception e){
            e.printStackTrace();
            exception = e;
        }

        assertNull(exception);
        assertEquals(userResponseEntity.getStatusCode(), HttpStatus.CREATED);
        assertEquals(projectResponseEntity.getStatusCode(), HttpStatus.CREATED);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    public void getDevelopersTest(){
        Exception exception = null;
        ResponseEntity<List<User>> responseEntity = null;
        List<User> userList = new ArrayList<>();
        User user = null;

        try{
            permissionController.init();
            responseEntity = permissionController.getDevelopers(project.getIdProject());
            userList = responseEntity.getBody();
        }catch (Exception e){
            exception = e;
        }

        assertNull(exception);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);

        try {
            user = userList.stream().filter(u -> u.getIdUser().equals(developer.getIdUser()))
                    .findFirst().get();
        }catch (Exception e){
            exception = e;
        }

        assertNull(exception);
        assertNotNull(user);
        assertEquals(user.getIdUser(), developer.getIdUser());
        assertEquals(user.getMail(), developer.getMail());
        assertEquals(user.getUsername(), developer.getUsername());
        assertEquals(user.getPassword(), developer.getPassword());
    }

    @Test
    public void getAdminTest(){
        Exception exception = null;
        ResponseEntity<User> responseEntity = null;
        User user = null;

        try{
            permissionController.init();
            responseEntity = permissionController.getAdmin(project.getIdProject());
            user = responseEntity.getBody();
        }catch (Exception e){
            exception = e;
        }
        assertNull(exception);
        assertNotNull(user);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(user.getIdUser(), admin.getIdUser());
        assertEquals(user.getMail(), admin.getMail());
        assertEquals(user.getUsername(), admin.getUsername());
        assertEquals(user.getPassword(), admin.getPassword());
    }

    @Test
    public void getProjectsTest(){
        Exception exception = null;
        ResponseEntity<List<Project>> responseEntity = null;
        List<Project> projectList = new ArrayList<>();
        Project proj = null;

        try{
            permissionController.init();
            responseEntity = permissionController.getProjects(admin.getIdUser());
            projectList = responseEntity.getBody();
        }catch (Exception e){
            exception = e;
        }

        assertNull(exception);
        assertNotNull(projectList);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);

        try {
            proj = projectList.stream().filter(p -> p.getIdProject().equals(project.getIdProject()))
                    .findFirst().get();
        }catch (NoSuchElementException e){
            exception = e;
        }

        assertNull(exception);
        assertNotNull(proj);
        assertEquals(proj.getIdProject(), project.getIdProject());
        assertEquals(proj.getName(), project.getName());
        assertEquals(proj.getType(), project.getType());
    }

    @Test
    public void hasTest(){
        Exception exception = null;
        ResponseEntity<String> responseEntity = null;

        try{
            permissionController.init();
            responseEntity = permissionController.has(admin.getIdUser(), project.getIdProject());

            System.out.print(responseEntity.getBody());
        }catch (Exception e){
            exception = e;
        }

        assertNull(exception);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);


        try{
            responseEntity = permissionController.has(developer.getIdUser(), project.getIdProject());

            System.out.print(responseEntity.getBody());
        }catch (Exception e){
            exception = e;
        }

        assertNull(exception);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void removeTest(){
        Exception exception = null;
        Status status = null;
        ResponseEntity<Status> responseEntity = null;
        ResponseEntity<String> projectResponseEntity = null;
        try{
            permissionController.init();
            responseEntity = permissionController.remove(developer.getIdUser(), project.getIdProject());
            status = responseEntity.getBody();
        }catch (Exception e){
            exception = e;
        }

        assertNull(exception);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(status.getCode(), 0);

        /* On ne permet pas supprimer le permis de l'admin du projet*/
        try{
            permissionController.init();
            responseEntity = permissionController.remove(admin.getIdUser(), project.getIdProject());
            status = responseEntity.getBody();
        }catch (Exception e){
            exception = e;
        }

        assertNull(exception);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
        assertEquals(status.getCode(), -1);

        try{
            projectController.init();
            projectResponseEntity = projectController.remove(project.getIdProject(), admin.getIdUser());

            JsonUtil<Status> jsonUtil = new JsonUtil<>();
            status = jsonUtil.convertToObjectJSON(projectResponseEntity.getBody(), Status.class);
        }catch (Exception e){
            exception = e;
        }

        assertNull(exception);
        assertEquals(projectResponseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(status.getCode(), 0);

        ResponseEntity<Status> userResponseEntity = null;
        userController.init();
        try{
            userResponseEntity = userController.remove(admin.getIdUser());
            status = userResponseEntity.getBody();
        }catch (Exception e){
            exception = e;
        }

        assertNull(exception);
        assertEquals(userResponseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(status.getCode(), 0);

        try{
            userResponseEntity = userController.remove(developer.getIdUser());
            status = userResponseEntity.getBody();
        }catch (Exception e){
            exception = e;
        }

        assertNull(exception);
        assertEquals(userResponseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(status.getCode(), 0);
    }
}
