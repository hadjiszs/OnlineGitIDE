package com.multimif.service;

import com.multimif.model.Project;
import com.multimif.util.DataException;
import com.multimif.util.TestUtil;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 11/19/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/api-servlet.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProjectServiceTest extends TestUtil {
    private ProjectService projectService = new ProjectServiceImpl();
    private UserService userService = new UserServiceImpl();

    @Test
    public void addEntityTest(){
        Exception exception = null;
        newUser();
        newProject();

        try{
            user = userService.addEntity(user.getUsername(), user.getMail(), user.getPassword());
            project = projectService.addEntity(project.getName(), project.getType(), user.getIdUser());
        }catch (Exception e){
            exception = e;
        }

        assertNull(exception);
        assertNotNull(project);
        assertNotNull(user);
        assertNotNull(project.getIdProject());
    }

    @Test
    public void getEntityByIdTest(){
        Exception exception = null;
        Project proj = new Project();

        try{
            proj = projectService.getEntityById(project.getIdProject());
        }catch (Exception e){
            exception = e;
        }

        assertNull(exception);
        assertNotNull(proj);
        assertEquals(proj.getIdProject(), project.getIdProject());
        assertEquals(proj.getName(), project.getName());
        assertEquals(proj.getType(), project.getType());
    }

    @Test
    public void getEntityListTest(){
        List<Project> projectList = new ArrayList<>();
        Exception exception = null;
        Project proj = null;

        try{
            projectList = projectService.getEntityList();
        }catch (Exception e){
            exception = e;
        }

        assertNull(exception);
        assertNotNull(projectList);

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
    public void suppressEntityTest(){
        boolean result = false;
        Exception exception = null;

        try {
            result = projectService.deleteEntity(project.getIdProject(), user.getIdUser());
        }catch (Exception e){
            exception = e;
        }

        assertNull(exception);
        assertTrue(result);

        Project proj = null;

        try{
            proj = projectService.getEntityById(project.getIdProject());
        }catch (DataException e){
            exception = e;
        }

        assertNotNull(exception);
        assertNull(proj);

        exception = null;

        try {
            result = userService.deleteEntity(user.getIdUser());
        }catch (DataException e){
            exception = e;
        }

        assertNull(exception);
        assertTrue(result);
    }

}
