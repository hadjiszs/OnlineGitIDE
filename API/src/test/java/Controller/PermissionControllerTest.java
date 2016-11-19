package Controller;

import DAO.EntityFactoryManager;
import Model.Project;
import Model.User;
import Model.UserGrant;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by amaia.nazabal on 11/19/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/api-servlet.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PermissionControllerTest {
    private PermissionController permissionController = new PermissionController();
    private UserController userController = new UserController();
    private ProjectController projectController = new ProjectController();
    private static User admin;
    private static User developper;
    private static Project project;
    private static UserGrant userGrant1;
    private static UserGrant userGrant2;

    @BeforeClass
    public static void init (){
        EntityFactoryManager.persistance();
        admin = new User();
        admin.setUsername("test-admin");
        admin.setHashkey("password-admin");
        admin.setMail("test-admin@test.fr");

        developper = new User();
        developper.setUsername("test-developper");
        developper.setHashkey("password-developper");
        developper.setMail("test-developper@test.fr");

        project = new Project();
        project.setName("project-test");
        project.setCreationDate(new Date());
        project.setLastModification(new Date());
        project.setVersion("1.0");
        project.setType(Project.TypeProject.JAVA);
        project.setRoot("/home/project-test");

        userGrant1 = new UserGrant();
        userGrant1.setGran(UserGrant.Permis.Admin);
        userGrant1.setProject(project);
        userGrant1.setUser(admin);

        userGrant2 = new UserGrant();
        userGrant2.setGran(UserGrant.Permis.Dev);
        userGrant2.setProject(project);
        userGrant2.setUser(developper);
    }

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
            userResponseEntity = userController.add(admin.getUsername(), admin.getMail(), admin.getHashkey());
            admin.setId(userResponseEntity.getBody().getId());

            userResponseEntity = userController.add(developper.getUsername(), developper.getMail(),
                    developper.getHashkey());
            developper.setId(userResponseEntity.getBody().getId());

            /* Quand on cree le project implicitament il cree le rapport entre projet et admin */
            projectResponseEntity = projectController.add(project.getName(), project.getVersion(), project.getRoot(),
                    project.getType(), admin.getId());

            JsonUtil<StatusOK> jsonUtil = new JsonUtil<>();
            statusOK = jsonUtil.convertToObjectJSON(projectResponseEntity.getBody(), StatusOK.class);
            project.setId(statusOK.getId());

            responseEntity = permissionController.add(project.getId(), developper.getId());
        }catch (Exception e){
            exception = e;
        }

        assertNull(exception);
        assertEquals(userResponseEntity.getStatusCode(), HttpStatus.ACCEPTED);
        assertEquals(projectResponseEntity.getStatusCode(), HttpStatus.ACCEPTED);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.ACCEPTED);
    }

    @Test
    public void getDevelopersTest(){
        Exception exception = null;
        ResponseEntity<List<User>> responseEntity = null;
        List<User> userList = new ArrayList<>();
        User user = null;

        try{
            permissionController.init();
            responseEntity = permissionController.getDevelopers(project.getId());
            userList = responseEntity.getBody();
        }catch (Exception e){
            exception = e;
        }

        assertNull(exception);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.ACCEPTED);

        try {
            user = userList.stream().filter(u -> u.getId().equals(developper.getId()))
                    .findFirst().get();
        }catch (Exception e){
            exception = e;
        }

        assertNull(exception);
        assertNotNull(user);
        assertEquals(user.getId(), developper.getId());
        assertEquals(user.getMail(), developper.getMail());
        assertEquals(user.getUsername(), developper.getUsername());
        assertEquals(user.getHashkey(), developper.getHashkey());
    }

    @Test
    public void getAdminTest(){
        Exception exception = null;
        ResponseEntity<User> responseEntity = null;
        User user = null;

        try{
            permissionController.init();
            responseEntity = permissionController.getAdmin(project.getId());
            user = responseEntity.getBody();
        }catch (Exception e){
            exception = e;
        }
        assertNull(exception);
        assertNotNull(user);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.ACCEPTED);
        assertEquals(user.getId(), admin.getId());
        assertEquals(user.getMail(), admin.getMail());
        assertEquals(user.getUsername(), admin.getUsername());
        assertEquals(user.getHashkey(), admin.getHashkey());
    }

    @Test
    public void getProjectsTest(){
        Exception exception = null;
        ResponseEntity<List<Project>> responseEntity = null;
        List<Project> projectList = new ArrayList<>();
        Project proj = null;

        try{
            permissionController.init();
            responseEntity = permissionController.getProjects(admin.getId());
            projectList = responseEntity.getBody();
        }catch (Exception e){
            exception = e;
        }

        assertNull(exception);
        assertNotNull(projectList);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.ACCEPTED);

        proj = projectList.stream().filter(p-> p.getId().equals(project.getId()))
                .findFirst().get();

        assertNotNull(proj);
        assertEquals(proj.getId(), project.getId());
        assertEquals(proj.getName(), project.getName());
        assertEquals(proj.getType(), project.getType());
        assertEquals(proj.getVersion(), project.getVersion());
        assertEquals(proj.getRoot(), project.getRoot());
    }

    /* TODO traiter reponse body permission true ou faux */
    @Test
    public void hasTest(){
        Exception exception = null;
        ResponseEntity<String> responseEntity = null;

        try{
            permissionController.init();
            responseEntity = permissionController.has(admin.getId(), project.getId());

            System.out.print(responseEntity.getBody());
        }catch (Exception e){
            exception = e;
        }

        assertNull(exception);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.ACCEPTED);


        try{
            responseEntity = permissionController.has(developper.getId(), project.getId());

            System.out.print(responseEntity.getBody());
        }catch (Exception e){
            exception = e;
        }

        assertNull(exception);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.ACCEPTED);
    }

    @Test
    public void removeTest(){
        Exception exception = null;
        Status status = null;
        ResponseEntity<Status> responseEntity = null;
        ResponseEntity<String> projectResponseEntity = null;
        try{
            permissionController.init();
            responseEntity = permissionController.remove(developper.getId(), project.getId());
            status = responseEntity.getBody();
        }catch (Exception e){
            exception = e;
        }

        assertNull(exception);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.ACCEPTED);
        assertEquals(status.getCode(), 0);

        /* On ne permet pas supprimer le permis de l'admin du projet*/
        try{
            permissionController.init();
            responseEntity = permissionController.remove(admin.getId(), project.getId());
            status = responseEntity.getBody();
        }catch (Exception e){
            exception = e;
        }

        assertNull(exception);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
        assertEquals(status.getCode(), -1);

        try{
            projectController.init();
            projectResponseEntity = projectController.remove(project.getId(), admin.getId());

            JsonUtil<Status> jsonUtil = new JsonUtil<>();
            status = jsonUtil.convertToObjectJSON(projectResponseEntity.getBody(), Status.class);
        }catch (Exception e){
            exception = e;
        }

        assertNull(exception);
        assertEquals(projectResponseEntity.getStatusCode(), HttpStatus.ACCEPTED);
        assertEquals(status.getCode(), 0);

        ResponseEntity<Status> userResponseEntity = null;
        userController.init();
        try{
            userResponseEntity = userController.remove(admin.getId());
            status = userResponseEntity.getBody();
        }catch (Exception e){
            exception = e;
        }

        assertNull(exception);
        assertEquals(userResponseEntity.getStatusCode(), HttpStatus.ACCEPTED);
        assertEquals(status.getCode(), 0);

        try{
            userResponseEntity = userController.remove(developper.getId());
            status = userResponseEntity.getBody();
        }catch (Exception e){
            exception = e;
        }

        assertNull(exception);
        assertEquals(userResponseEntity.getStatusCode(), HttpStatus.ACCEPTED);
        assertEquals(status.getCode(), 0);
    }


    @AfterClass
    public static void close (){
        EntityFactoryManager.close();
    }
}
