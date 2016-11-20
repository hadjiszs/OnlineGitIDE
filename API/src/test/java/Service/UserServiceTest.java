package Service;

import Model.User;
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
 * Created by amaia.nazabal on 11/20/16.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/api-servlet.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceTest  extends TestUtil{
    UserService userService = new UserServiceImpl();

    @Test
    public void addEntityTest(){
        Exception exception = null;
        newUser();

        try{
            user = userService.addEntity(user.getUsername(), user.getMail(), user.getHashkey());
        }catch (DataException e){
            exception = e;
        }

        assertNull(exception);
        assertNotNull(user);
        assertNotNull(user.getIdUser());
    }

    @Test
    public void getEntityByMailTest(){
        Exception exception = null;
        User usr = null;

        try{
            usr = userService.getEntityByMail(user.getMail());
        }catch (DataException e){
            exception = e;
        }

        assertNull(exception);
        assertNotNull(usr);
        assertEquals(usr.getIdUser(), user.getIdUser());
        assertEquals(usr.getMail(), user.getMail());
        assertEquals(usr.getUsername(), user.getUsername());
        assertEquals(usr.getHashkey(), user.getHashkey());
    }

    @Test
    public void getEntityByIdTest(){
        Exception exception = null;
        User usr = null;

        try{
            usr = userService.getEntityById(user.getIdUser());
        }catch (DataException e){
            exception = e;
        }

        assertNull(exception);
        assertNotNull(usr);
        assertEquals(usr.getIdUser(), user.getIdUser());
        assertEquals(usr.getMail(), user.getMail());
        assertEquals(usr.getUsername(), user.getUsername());
        assertEquals(usr.getHashkey(), user.getHashkey());
    }


    @Test
    public void getEntityListTest(){
        Exception exception = null;
        List<User> userList = new ArrayList<>();
        User usr = null;

        try{
            userList = userService.getEntityList();
        }catch (DataException e){
            exception = e;
        }
        assertNull(exception);
        assertTrue(userList.size() > 0);

        try{
            usr = userList.stream().filter(u -> u.getIdUser().equals(user.getIdUser()))
                    .findFirst().get();
        }catch (NoSuchElementException e){
            exception = e;
        }

        assertNull(exception);
        assertNotNull(usr);
        assertEquals(usr.getIdUser(), user.getIdUser());
        assertEquals(usr.getMail(), user.getMail());
        assertEquals(usr.getUsername(), user.getUsername());
        assertEquals(usr.getHashkey(), user.getHashkey());
    }

    @Test
    public void authEntityTest(){
        Exception exception = null;
        User usr = null;

        try{
            usr = userService.authEntity(user.getUsername(), user.getHashkey());
        }catch (DataException e){
            exception = e;
        }

        assertNull(exception);
        assertNotNull(usr);
        assertEquals(usr.getIdUser(), user.getIdUser());
        assertEquals(usr.getMail(), user.getMail());
        assertEquals(usr.getUsername(), user.getUsername());
        assertEquals(usr.getHashkey(), user.getHashkey());

        /* On va tester qu'un user avec un mot de passe incorrect ne peut pas entrer */
        usr = null;
        try{
            usr = userService.authEntity(user.getUsername(), "pass-incorrect");
        }catch (DataException e){
            exception = e;
        }

        assertNull(exception);
        assertNull(usr);
    }

    @Test
    public void suppressEntityTest(){
        Exception exception = null;
        User usr = null;
        boolean result = false;

        try{
            result = userService.deleteEntity(user.getIdUser());
        }catch (DataException e){
            exception = e;
        }

        assertNull(exception);
        assertTrue(result);

        try{
            usr = userService.getEntityById(user.getIdUser());
        }catch (DataException e){
            exception = e;
        }

        assertNotNull(exception);
        assertNull(usr);
    }
}
