package com.multimif.controller;

import com.multimif.model.User;
import com.multimif.util.Status;
import com.multimif.util.TestUtil;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

/**
 * Classe de test de UserController.
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
 * @since 1.0 11/16/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/api-servlet.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserControllerTest extends TestUtil {
    private UserController userController = new UserController();


    @Test
    public void addTest(){
        userController.init();
        Exception exception = null;
        ResponseEntity<User> responseEntity = null;
        try{
            newUser();

            responseEntity = userController
                    .add(user.getUsername(), user.getMail(), user.getPassword());
            user.setIdUser(responseEntity.getBody().getIdUser());
        }catch (Exception e){
            exception = e;
        }

        assertNull(exception);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    public void getTest(){
        userController.init();
        Exception exception = null;
        ResponseEntity<String> responseEntity = null;
        try{
            responseEntity = userController
                    .getByMail(user.getMail());
        }catch (Exception e){
            exception = e;
        }

        assertNull(exception);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void getAllTest(){
        List<User> userList;
        User usr = null;
        Exception exception = null;
        ResponseEntity<List<User>> responseEntity = null;

        userController.init();

        try{
            responseEntity = userController.getAll();
        }catch (Exception e){
            exception = e;
        }

        userList = responseEntity.getBody();

        try {
            usr = userList.stream().filter(u -> u.getIdUser().equals(user.getIdUser()))
                    .findFirst().get();
        }catch (NoSuchElementException e){
            exception = e;
        }

        assertNull(exception);
        assertEquals(user.getIdUser(), usr.getIdUser());
        assertEquals(user.getMail(), usr.getMail());
        assertEquals(user.getUsername(), usr.getUsername());
        assertNotEquals(user.getPassword(), usr.getPassword());

        assertNull(exception);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }



    @Test
    public void removeTest(){
        ResponseEntity<Status> responseEntity = null;
        Exception exception = null;
        userController.init();

        try{
            responseEntity = userController.remove(user.getIdUser());
        }catch (Exception e){
            exception = e;
        }

        assertNull(exception);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);


    }
}
