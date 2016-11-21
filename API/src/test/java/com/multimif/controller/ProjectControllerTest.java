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

import static org.junit.Assert.*;

/**
 * Created by amaia.nazabal on 11/19/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/api-servlet.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProjectControllerTest extends TestUtil{
    private ProjectController projectController = new ProjectController();
    private UserController userController = new UserController();

    @Test
    public void addTest(){
        Exception exception = null;
        ResponseEntity<String> responseEntity = null;
        ResponseEntity<User> userResponseEntity = null;
        StatusOK statusOK = null;
        projectController.init();
        userController.init();

        try {
            newUser();
            userResponseEntity = userController.add(user.getUsername(), user.getMail(), user.getPassword());
            user.setIdUser(userResponseEntity.getBody().getIdUser());

            newProject();
            responseEntity = projectController.add(project.getName(), project.getVersion(), project.getRoot(),
                    project.getType(), user.getIdUser());

            JsonUtil<StatusOK> jsonUtil = new JsonUtil<>();
            statusOK = jsonUtil.convertToObjectJSON(responseEntity.getBody(), StatusOK.class);
            project.setIdProject(statusOK.getId());
        }catch (Exception e){
            exception = e;
        }

        assertNull(exception);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.CREATED);
        assertNotNull(statusOK);
    }

    @Test
    public void getTest(){
        Exception exception = null;
        ResponseEntity<Project> responseEntity = null;
        Project proj = null;
        projectController.init();

        try{
            responseEntity = projectController.get(project.getIdProject());
            proj = responseEntity.getBody();

        }catch (Exception e){
            exception = e;
        }

        assertNull(exception);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertNotNull(proj);
        assertEquals(proj.getIdProject(), project.getIdProject());
        assertEquals(proj.getName(), project.getName());
        assertEquals(proj.getType(), project.getType());
        assertEquals(proj.getVersion(), project.getVersion());
        assertEquals(proj.getRoot(), project.getRoot());
    }

    @Test
    public void getAllTest(){
        Exception exception = null;
        ResponseEntity<List<Project>> responseEntity = null;
        List<Project> projectList = new ArrayList<>();
        Project proj = null;
        projectController.init();

        try{
            responseEntity = projectController.getAll();
            projectList = responseEntity.getBody();
        }catch (Exception e){
            exception = e;
        }

        assertNull(exception);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);

        try{
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
        assertEquals(proj.getVersion(), project.getVersion());
        assertEquals(proj.getRoot(), project.getRoot());
    }


    @Test
    public void removeTest(){
        Exception exception = null;
        ResponseEntity<String> responseEntity = null;
        Status status = null;
        projectController.init();

        try{
            responseEntity = projectController.remove(project.getIdProject(), user.getIdUser());

            JsonUtil<Status> jsonUtil = new JsonUtil<>();
            status = jsonUtil.convertToObjectJSON(responseEntity.getBody(), Status.class);
        }catch (Exception e){
            exception = e;
        }

        assertNull(exception);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(status.getCode(), 0);

        ResponseEntity<Project > projectResponseEntity = null;
        Project proj = null;

        try{
            projectResponseEntity = projectController.get(user.getIdUser());
            proj = projectResponseEntity.getBody();
        }catch (Exception e){
            exception = e;
        }

        assertNull(exception);
        assertEquals(projectResponseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
        assertNull(proj);

        ResponseEntity<Status> userResponseEntity = null;
        userController.init();
        try{
            userResponseEntity = userController.remove(user.getIdUser());
            status = userResponseEntity.getBody();
        }catch (Exception e){
            exception = e;
        }

        assertNull(exception);
        assertEquals(userResponseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(status.getCode(), 0);

    }
}
