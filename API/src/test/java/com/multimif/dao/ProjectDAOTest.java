package com.multimif.dao;

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
 * Created by amaia.nazabal on 11/16/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/api-servlet.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProjectDAOTest extends TestUtil{
    private ProjectDAO projectDAO = new ProjectDAOImpl();

    @Test
    public void addEntityTest() throws DataException{
        Exception exception = null;
        try{
            newProject();
            projectDAO.addEntity(project);
        }catch (Exception ex){
            exception = ex;
        }

        assertEquals(exception, null);
        assertNotNull(project.getIdProject());
    }

    @Test
    public void getEntityByIdTest(){
        Exception exception = null;
        Project proj = new Project();

        try {
            proj = projectDAO.getEntityById(project.getIdProject());
        } catch (Exception e) {
            exception = e;
        }

        assertNull(exception);

        assertEquals(proj.getIdProject(), project.getIdProject());
        assertEquals(proj.getName(), project.getName());
        assertEquals(proj.getVersion(), project.getVersion());
        assertEquals(proj.getType(), project.getType());
        assertEquals(proj.getRoot(), project.getRoot());
    }

    @Test
    public void getEntityList() {
        Exception exception = null;
        List<Project> projectList = new ArrayList();
        Project proj = null;

        try {
            projectList = projectDAO.getEntityList();
        } catch (DataException e) {
            exception = e;
        }

        assertNull(exception);
        assertTrue(projectList.size() > 0);

        try {
            proj = projectList.stream().filter(p -> p.getIdProject().equals(project.getIdProject()))
                    .findFirst().get();
        }catch (NoSuchElementException e){
            exception = e;
        }

        assertNull(exception);
        assertEquals(proj.getIdProject(), project.getIdProject());
        assertEquals(proj.getName(), project.getName());
        assertEquals(proj.getVersion(), project.getVersion());
        assertEquals(proj.getType(), project.getType());
        assertEquals(proj.getRoot(), project.getRoot());
    }

    @Test
    public void suppressEntity() {
        Exception exception = null;

        /* On supprime l'object */

        try {
            projectDAO.deleteEntity(project);
        } catch (Exception e) {
            exception = e;
        }

        assertNull(exception);

        /* On vérifie qu'il n'existe pas déjà */

        List<Project> projectList = new ArrayList();
        Project proj = null;

        try {
            projectList = projectDAO.getEntityList();
        } catch (DataException e) {
            exception = e;
        }

        assertNull(exception);

        try {
            projectList.stream().filter(p -> p.getIdProject().equals(project.getIdProject()))
                    .findFirst().get();
        }catch (NoSuchElementException e){
            exception  = e;
        }

        assertNotNull(exception);
    }
}
