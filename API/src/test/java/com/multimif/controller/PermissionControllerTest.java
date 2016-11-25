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
 *
 * Classe de test de PermissionController.
 *
 * On a appliqué de test pour vérifier:
 * <ul>
 *  <li>Le status line du résponse</li>
 *  <li>Le objet qui retourne correspond avec l'attendu.</li>
 *  <li>L'absence des exceptions</li>
 *  <li>L'action qui a fait le controleur.</li>
 * </ul>
 *
 * Ces test sont fait contre la base de données.
 *
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

    /**
     * Vérifie la méthode add du controlleur.
     */
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
            /*userResponseEntity = userController.add(admin.getUsername(), admin.getMail(), admin.getPassword());
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

    /**
     * Vérifié la méthode de lister les développeurs
     */
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
        assertNotEquals(user.getPassword(), developer.getPassword());
    }

    /**
     * Vérifié la fonction pour récupérer l'admin d'un projet
     */
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
        assertNotEquals(user.getPassword(), admin.getPassword());
    }

    /**
     * Vérifié le méthode qui retourne la liste de projets par utilisateur
     */
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


    /**
     * Vérifié que la fonction has retourne correctement l'existence de permis
     */
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
        }catch (Exception e){
            exception = e;
        }

        assertNull(exception);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    /**
     * Vérifie la méthode delete permis
     */
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

        /* On remove les objets qu'on a crée pour tester cette classe */

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
