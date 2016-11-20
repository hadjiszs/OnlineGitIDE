package Service;

import Model.TemporaryFile;
import Util.DataException;
import Util.TestUtil;
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
 * Created by amaia.nazabal on 11/19/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/api-servlet.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TemporaryFileServiceTest extends TestUtil {
    private TemporaryFileService temporaryFileService = new TemporaryFileServiceImpl();
    private ProjectService projectService = new ProjectServiceImpl();
    private UserService userService = new UserServiceImpl();

    @Test
    public void addEntityTest(){
        Exception exception = null;
        newUser();
        newProject();
        newTemporaryFile();

        try{
            user = userService.addEntity(user.getUsername(), user.getMail(), user.getHashkey());
            projectService.addEntity(project, user.getIdUser());

            temporaryFile = temporaryFileService.addEntity(user.getIdUser(), temporaryFile.getHashKey(), temporaryFile.getContent(),
                    temporaryFile.getPath(), project.getIdProject());
        }catch (DataException e){
            exception = e;
        }

        assertNull(exception);
        assertNotNull(temporaryFile);
        assertNotNull(temporaryFile.getId());
        assertNotNull(temporaryFile.getUser().getIdUser());
        assertNotNull(temporaryFile.getProject().getIdProject());
    }

    @Test
    public void getEntityByHashAndUserTest(){
        Exception exception = null;
        TemporaryFile tmpFile = null;

        try {
            tmpFile = temporaryFileService.getEntityByHashAndUser(user.getIdUser(), temporaryFile.getHashKey());
        }catch (DataException e){
            exception = e;
        }

        assertNull(exception);
        assertNotNull(tmpFile);
        assertEquals(tmpFile.getId(), temporaryFile.getId());
        assertEquals(tmpFile.getContent(), temporaryFile.getContent());
        assertEquals(tmpFile.getHashKey(), temporaryFile.getHashKey());
        assertEquals(tmpFile.getUser().getIdUser(), temporaryFile.getUser().getIdUser());
        assertEquals(tmpFile.getProject().getIdProject(), temporaryFile.getProject().getIdProject());
    }

    @Test
    public void getEntityByUserProjectTest(){
        Exception exception = null;
        List<TemporaryFile> temporaryFileList = new ArrayList<>();
        TemporaryFile tmpFile = null;
        try{
            temporaryFileList = temporaryFileService.getEntityByUserProject(user.getIdUser(), project.getIdProject());
        }catch (DataException e){
            exception = e;
        }

        assertNull(exception);
        assertNotNull(temporaryFileList);
        assertTrue(temporaryFileList.size() > 0);

        try{
            tmpFile = temporaryFileList.stream().filter(f -> f.getId().equals(temporaryFile.getId()))
                    .findFirst().get();
        }catch (NoSuchElementException e){
            exception = e;
        }

        assertNull(exception);
        assertNotNull(tmpFile);

        assertEquals(tmpFile.getContent(), temporaryFile.getContent());
        assertEquals(tmpFile.getHashKey(), temporaryFile.getHashKey());
        assertEquals(tmpFile.getUser().getIdUser(), temporaryFile.getUser().getIdUser());
        assertEquals(tmpFile.getProject().getIdProject(), temporaryFile.getProject().getIdProject());
    }

    @Test
    public void getEntityByIdTest(){
        Exception exception = null;
        TemporaryFile tmpFile = null;

        try{
            tmpFile = temporaryFileService.getEntityById(temporaryFile.getId());
        }catch (DataException e){
            exception = e;
        }

        assertNull(exception);
        assertNotNull(tmpFile);
        assertEquals(tmpFile.getContent(), temporaryFile.getContent());
        assertEquals(tmpFile.getHashKey(), temporaryFile.getHashKey());
        assertEquals(tmpFile.getUser().getIdUser(), temporaryFile.getUser().getIdUser());
        assertEquals(tmpFile.getProject().getIdProject(), temporaryFile.getProject().getIdProject());
    }

    @Test
    public void existsTest(){
        Exception exception = null;
        boolean result = false;

        try {
            result = temporaryFileService.exists(temporaryFile.getId());
        }catch (Exception e){
            exception = e;
        }

        assertNull(exception);
        assertTrue(result);
    }

    @Test
    public void suppressEntity(){
        Exception exception = null;
        boolean result = false;

        try{
            result = temporaryFileService.deleteEntity(temporaryFile.getId());
        }catch (DataException e){
            exception = e;
        }

        assertNull(exception);
        assertTrue(result);

        try {
            result = temporaryFileService.exists(temporaryFile.getId());
        }catch (Exception e){
            exception = e;
        }

        assertNull(exception);
        assertFalse(result);
    }
}
