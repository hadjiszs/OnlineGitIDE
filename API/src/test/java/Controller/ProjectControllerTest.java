package Controller;

import DAO.EntityFactoryManager;
import Model.Project;
import Model.User;
import Util.JsonUtil;
import Util.Status;
import Util.StatusOK;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by amaia.nazabal on 11/19/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/api-servlet.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProjectControllerTest {
    private ProjectController projectController = new ProjectController();
    private UserController userController = new UserController();
    private static User user;
    private static Project project;

    @BeforeClass
    public static void init (){
        EntityFactoryManager.persistance();
        user = new User();
        user.setUsername("test");
        user.setHashkey("password-test");
        user.setMail("test-test@test.fr");

        project = new Project();
        project.setName("project-test");
        project.setCreationDate(new Date());
        project.setLastModification(new Date());
        project.setVersion("1.0");
        project.setType(Project.TypeProject.JAVA);
        project.setRoot("/home/project-test");
    }

    @Test
    public void addTest(){
        Exception exception = null;
        ResponseEntity<String> responseEntity = null;
        ResponseEntity<User> userResponseEntity = null;
        StatusOK statusOK = null;
        projectController.init();
        userController.init();

        try {
            userResponseEntity = userController.add(user.getUsername(), user.getMail(), user.getHashkey());
            user.setId(userResponseEntity.getBody().getId());

            responseEntity = projectController.add(project.getName(), project.getVersion(), project.getRoot(),
                    project.getType(), user.getId());

            JsonUtil<StatusOK> jsonUtil = new JsonUtil<>();
            statusOK = jsonUtil.convertToObjectJSON(responseEntity.getBody(), StatusOK.class);
            project.setId(statusOK.getId());
        }catch (Exception e){
            exception = e;
        }

        assertNull(exception);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.ACCEPTED);
        assertNotNull(statusOK);
    }

    @Test
    public void getTest(){
        Exception exception = null;
        ResponseEntity<String > responseEntity = null;
        Project proj = null;
        projectController.init();

        try{
            responseEntity = projectController.get(user.getId());

            JsonUtil<Project> jsonUtil = new JsonUtil<>();
            proj = jsonUtil.convertToObjectJSON(responseEntity.getBody(), Project.class);
        }catch (Exception e){
            exception = e;
        }

        assertNull(exception);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.ACCEPTED);
        assertNotNull(proj);
        assertEquals(proj.getId(), project.getId());
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
        assertEquals(responseEntity.getStatusCode(), HttpStatus.ACCEPTED);

        try{
            proj = projectList.stream().filter(p -> p.getId().equals(project.getId()))
                    .findFirst().get();
        }catch (Exception e){
            exception = e;
        }

        assertNull(exception);
        assertNotNull(proj);
        assertEquals(proj.getId(), project.getId());
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
            responseEntity = projectController.remove(project.getId(), user.getId());

            JsonUtil<Status> jsonUtil = new JsonUtil<>();
            status = jsonUtil.convertToObjectJSON(responseEntity.getBody(), Status.class);
        }catch (Exception e){
            exception = e;
        }

        assertNull(exception);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.ACCEPTED);
        assertEquals(status.getCode(), 0);

        ResponseEntity<String > stringResponseEntity = null;
        Project proj = null;

        try{
            stringResponseEntity = projectController.get(user.getId());

            JsonUtil<Project> jsonUtil = new JsonUtil<>();
            proj = jsonUtil.convertToObjectJSON(stringResponseEntity.getBody(), Project.class);
        }catch (Exception e){
            exception = e;
        }

        assertNull(exception);
        assertEquals(stringResponseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
        assertNull(proj);

        ResponseEntity<Status> userResponseEntity = null;
        userController.init();
        try{
            userResponseEntity = userController.remove(user.getId());
            status = userResponseEntity.getBody();
        }catch (Exception e){
            exception = e;
        }

        assertNull(exception);
        assertEquals(userResponseEntity.getStatusCode(), HttpStatus.ACCEPTED);
        assertEquals(status.getCode(), 0);

    }


    @AfterClass
    public static void close(){
        EntityFactoryManager.close();
    }
}
