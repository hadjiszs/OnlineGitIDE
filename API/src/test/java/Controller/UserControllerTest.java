package Controller;

import Model.User;
import Util.*;
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

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by amaia.nazabal on 11/16/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/api-servlet.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserControllerTest extends TestUtil{
    private UserController userController = new UserController();


    @Test
    public void addTest(){
        userController.init();
        Exception exception = null;
        ResponseEntity<User> responseEntity = null;
        try{
            newUser();

            responseEntity = userController
                    .add(user.getUsername(), user.getMail(), user.getHashkey());
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
                    .get(user.getMail());
        }catch (Exception e){
            exception = e;
        }

        assertNull(exception);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.ACCEPTED);
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
        assertEquals(user.getHashkey(), usr.getHashkey());

        assertNull(exception);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.ACCEPTED);
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
        assertEquals(responseEntity.getStatusCode(), HttpStatus.ACCEPTED);


    }
}
