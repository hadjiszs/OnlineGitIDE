package com.multimif.service;

import com.multimif.model.TemporaryFile;
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
            user = userService.addEntity(user.getUsername(), user.getMail(), user.getPassword());
            project = projectService.addEntity(project.getName(), project.getType(), user.getIdUser());
            temporaryFile = temporaryFileService.addEntity(user.getIdUser(),
                    temporaryFile.getContent(), temporaryFile.getPath(), project.getIdProject());
        } catch (Exception e) {
            exception = e;
        }

//        assertNull(exception);
        assertNotNull(temporaryFile);
        assertNotNull(temporaryFile.getId());
        assertNotNull(temporaryFile.getUser().getIdUser());
        assertNotNull(temporaryFile.getProject().getIdProject());
    }

    @Test
    public void getUpdateEntity(){
        Exception exception = null;
        TemporaryFile tmpFile = null;
        String newContent = "new content";

        try {
            tmpFile = temporaryFileService.updateEntity(Long.valueOf(user.getIdUser()),
                    newContent,
                    temporaryFile.getPath(),
                    Long.valueOf(project.getIdProject()));
        } catch (DataException e) {
            exception = e;
        }

        assertNotNull(tmpFile);
        assertEquals(tmpFile.getId(), temporaryFile.getId());
        assertEquals(tmpFile.getContent(), newContent);
        assertEquals(tmpFile.getHashKey(), temporaryFile.getHashKey());
        assertEquals(tmpFile.getUser().getIdUser(), temporaryFile.getUser().getIdUser());
        assertEquals(tmpFile.getProject().getIdProject(), temporaryFile.getProject().getIdProject());
    }

    @Test
    public void getEntityByHashAndUserTest(){
        Exception exception = null;
        TemporaryFile tmpFile = null;

        try {
            tmpFile = temporaryFileService.getEntityByHash(temporaryFile.getHashKey());
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

        try{
            projectService.deleteEntity(project.getIdProject(), user.getIdUser());
            userService.deleteEntity(user.getIdUser());
        }catch (Exception e){
            exception = e;
        }

        assertNull(exception);
    }
}
