package DAO;


import Model.Project;
import Model.TemporaryFile;
import Model.User;
import Util.DataException;
import Util.TestUtil;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * Created by amaia.nazabal on 11/18/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/api-servlet.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TemporaryFileDAOTest extends TestUtil{
    private TemporaryFileDAO temporaryFileDAO = new TemporaryFileDAOImpl();
    private UserDAO userDAO = new UserDAOImp();
    private ProjectDAO projectDAO = new ProjectDAOImpl();

    @Test
    public void addTest(){
        Exception exception = null;
        try{
            newUser();
            userDAO.addEntity(user);

            newProject();
            projectDAO.addEntity(project);

            newTemporaryFile();
            temporaryFile.setProject(project);
            temporaryFile.setUser(user);

            temporaryFileDAO.add(temporaryFile);

        }catch (Exception e){
            e.printStackTrace();
            exception = e;
        }

        assertNull(exception);
        assertNotNull(temporaryFile);
        assertNotNull(temporaryFile.getId());
    }

    @Test
    public void existsTest(){
        Exception exception = null;
        boolean result = false;
        try {
            result = temporaryFileDAO.exist(temporaryFile.getId());
        }catch (Exception e){
            exception = e;
        }

        assertNull(exception);
        assertTrue(result);
    }

    @Test
    public void getEntityByIdTest(){
        Exception exception = null;
        TemporaryFile tmpFile = null;

        try{
            tmpFile = temporaryFileDAO.getEntityById(temporaryFile.getId());
        }catch (Exception e){
            exception = e;
        }

        assertNull(exception);
        assertNotNull(tmpFile);
        assertEquals(tmpFile.getId(), temporaryFile.getId());
        assertEquals(tmpFile.getContent(), temporaryFile.getContent());
        assertEquals(tmpFile.getHashKey(), temporaryFile.getHashKey());
        assertEquals(tmpFile.getPath(), temporaryFile.getPath());
        assertEquals(tmpFile.getProject().getIdProject(), project.getIdProject());
        assertEquals(tmpFile.getUser().getIdUser(), user.getIdUser());
    }

    @Test
    public void getEntityByHashAndUserTest(){
        Exception exception = null;
        TemporaryFile tmpFile = null;

        try{
            tmpFile = temporaryFileDAO.getEntityByHashKeyAndUser(user,temporaryFile.getHashKey());
        }catch (Exception e){
            exception = e;
        }

        assertNull(exception);
        assertNotNull(tmpFile);
        assertEquals(tmpFile.getId(), temporaryFile.getId());
        assertEquals(tmpFile.getContent(), temporaryFile.getContent());
        assertEquals(tmpFile.getHashKey(), temporaryFile.getHashKey());
        assertEquals(tmpFile.getPath(), temporaryFile.getPath());
        assertEquals(tmpFile.getProject().getIdProject(), project.getIdProject());
        assertEquals(tmpFile.getUser().getIdUser(), user.getIdUser());
    }

    @Test
    public void getEntityByUserProjectTest(){
        Exception exception = null;
        List<TemporaryFile> temporaryFiles = null;
        TemporaryFile tmpFile = null;

        try{
            temporaryFiles = temporaryFileDAO.getEntityByUserProject(user, project);
        }catch (Exception e){
            exception = e;
        }

        assertNull(exception);
        assertNotNull(temporaryFiles);

        try {
            tmpFile = temporaryFiles.stream().filter(f -> f.getId().equals(temporaryFile.getId()))
                    .findFirst().get();
        }catch (NoSuchElementException e){
            exception = e;
        }

        assertNull(exception);
        assertNotNull(tmpFile);

        assertEquals(tmpFile.getId(), temporaryFile.getId());
        assertEquals(tmpFile.getContent(), temporaryFile.getContent());
        assertEquals(tmpFile.getHashKey(), temporaryFile.getHashKey());
        assertEquals(tmpFile.getPath(), temporaryFile.getPath());
        assertEquals(tmpFile.getProject().getIdProject(), project.getIdProject());
        assertEquals(tmpFile.getUser().getIdUser(), user.getIdUser());
    }

    @Test
    public void supprimeTest() {
        Exception exception = null;
        TemporaryFile tmpFile = null;
        try {
            temporaryFileDAO.deleteEntity(temporaryFile.getId());
        } catch (DataException e) {
            exception = e;
        }

        assertNull(exception);

        try{
            tmpFile = temporaryFileDAO.getEntityById(temporaryFile.getId());
        }catch (Exception e) {
            exception = e;
        }

        assertNull(exception);
        assertNull(tmpFile);

        try {
            userDAO.deleteEntity(user);
            projectDAO.deleteEntity(project);
        } catch (Exception e) {
            exception = e;
        }

        assertNull(exception);
    }
}
