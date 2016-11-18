package DAO;

import Model.Project;
import Model.User;
import Service.APIService;
import Util.DataException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * Created by amaia.nazabal on 11/16/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/api-servlet.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProjectDAOTest {
    private ProjectDAO projectDAO = new ProjectDAOImpl();
    private static Project project = new Project();
    private static User user = new User();

    @BeforeClass
    public static void setUpBeforeClass() throws DataException {
        APIService.persistance();

        project.setName("project-test");
        project.setCreationDate(new Date());
        project.setLastModification(new Date());
        project.setVersion("1.0");
        project.setType(Project.TypeProject.JAVA);
        project.setRoot("/home/project-test");
    }

    @Test
    public void addEntityTest() throws DataException{
        Exception exception = null;
        try{
            projectDAO.addEntity(project);
        }catch (Exception ex){
            exception = ex;
        }

        assertEquals(exception, null);
        assertNotNull(project.getId());
    }

    @Test
    public void getEntityByIdTest(){
        Exception exception = null;
        Project proj = new Project();

        try {
            proj = projectDAO.getEntityById(project.getId());
        } catch (Exception e) {
            e.printStackTrace();
            exception = e;
        }

        assertNull(exception);

        assertEquals(proj.getId(), project.getId());
        assertEquals(proj.getName(), project.getName());
        assertEquals(proj.getVersion(), project.getVersion());
        assertEquals(proj.getType(), project.getType());
        assertEquals(proj.getRoot(), project.getRoot());
    }

    /*
    public void getEntityList() {
        Exception exception = null;
        Project proj;
        List<Project> projectList = new ArrayList();

        try {
            projectList = projectDAO.getEntityList();
        } catch (DataException e) {
            exception = e;
        }

        assertNull(exception);
        assertTrue(projectList.size() > 0);

        proj = projectList.stream().filter(p -> p.getId().equals(project.getId()))
                .findFirst().get();

        assertEquals(proj.getId(), project.getId());
        assertEquals(proj.getName(), project.getName());
        assertEquals(proj.getVersion(), project.getVersion());
        assertEquals(proj.getType(), project.getType());
        assertEquals(proj.getRoot(), project.getRoot());
    }*/

    /*@Test(expected = NoSuchElementException.class)
    public void suppressEntity() {
        Exception exception = null;

        /* On supprime l'object */
/*
        try {
            projectDAO.deleteEntity(project);
        } catch (Exception e) {
            e.printStackTrace();
            exception = e;
        }

        assertNull(exception);*/

        /* On vérifie qu'il n'existe pas déjà */
/*
        List<Project> projectList = new ArrayList();
        Project proj = null;

        try {
            projectList = projectDAO.getEntityList();
        } catch (DataException e) {
            exception = e;
        }

        assertNull(exception);

        proj = projectList.stream().filter(p -> p.getId().equals(project.getId()))
                    .findFirst().get();
    }*/

    @AfterClass
    public static void tearDownAfterClass(){
        APIService.close();
    }
}
