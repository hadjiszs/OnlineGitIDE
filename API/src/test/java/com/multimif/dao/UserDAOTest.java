package com.multimif.dao;

import com.multimif.model.User;
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
 *
 * Classe de test de UserDAO.
 *
 * On a appliqué de test pour vérifier:
 * <ul>
 *  <li>L'absence des exceptions</li>
 *  <li>L'action qui a fait la classe DAO.</li>
 *  <li>La cohérence des données </li>
 * </ul>
 *
 * Ces test sont fait contre la base de données.
 *
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 11/17/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/api-servlet.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserDAOTest extends TestUtil{
    private UserDAO userDAO = new UserDAOImp();

    @Test
    public void addEntity() {
        Exception exception = null;
        try {
            newAdmin();

            /* On garde le pass pour l'utiliser dans le control d'authentication*/
            String password = admin.getPassword();

            userDAO.addEntity(admin);

            admin.setPassword(password);

        } catch (DataException e) {
            exception = e;
        }

        assertEquals(exception, null);
        assertNotNull(admin.getIdUser());
    }


    @Test
    public void authEntityTest(){
        Exception exception = null;
        User usr = null;

        /* Authentication OK*/
        try {
            usr = userDAO.authEntity(admin.getUsername(), admin.getPassword());
        } catch (DataException e) {
            exception = e;
        }

        assertNull(exception);
        assertNotNull(usr);
        assertEquals(usr.getIdUser(), admin.getIdUser());
        assertEquals(usr.getMail(), admin.getMail());
        assertEquals(usr.getUsername(), admin.getUsername());
        assertNotEquals(usr.getPassword(), admin.getPassword());


        /* Authentication failed */
        usr = null
        ;
        try {
            usr = userDAO.authEntity(admin.getUsername(), "different-password");
        } catch (DataException e) {
            exception = e;
        }

        assertNotNull(exception);
        assertNull(usr);
    }

    @Test
    public void getEntityByMail() {
        Exception exception = null;
        User usr = null;

        try {
            usr = userDAO.getEntityByMail(admin.getMail());
        } catch (Exception e) {
            exception = e;
        }

        assertNull(exception);
        assertEquals(usr.getIdUser(), admin.getIdUser());
        assertEquals(usr.getMail(), admin.getMail());
        assertEquals(usr.getUsername(), admin.getUsername());
        assertNotEquals(usr.getPassword(), admin.getPassword());

    }

    @Test
    public void getEntityById() {
        Exception exception = null;
        User usr = new User();

        try{
            usr = userDAO.getEntityById(admin.getIdUser());
        }catch (Exception e){
            exception = e;
        }

        assertNull(exception);
        assertEquals(usr.getIdUser(), admin.getIdUser());
        assertEquals(usr.getMail(), admin.getMail());
        assertEquals(usr.getUsername(), admin.getUsername());
        assertNotEquals(usr.getPassword(), admin.getPassword());

    }

    @Test
    public void getEntityList() {
        Exception exception = null;
        List<User> userList = new ArrayList();
        User usr = new User();

        try{
            userList = userDAO.getEntityList();
        }catch (Exception e){
            exception = e;
        }

        assertNull(exception);

        try {
            usr = userList.stream().filter(u -> u.getIdUser().equals(admin.getIdUser()))
                    .findFirst().get();
        }catch (NoSuchElementException e){
            exception = e;
        }

        assertNull(exception);
        assertNotNull(usr);
        assertEquals(usr.getIdUser(), admin.getIdUser());
        assertEquals(usr.getMail(), admin.getMail());
        assertEquals(usr.getUsername(), admin.getUsername());
        assertNotEquals(usr.getPassword(), admin.getPassword());

    }

    @Test
    public void suppressEntity() {
        Exception exception = null;
        User usr = null;
        try {
            userDAO.deleteEntity(admin);
        } catch (Exception e) {
            exception = e;
        }

        assertNull(exception);

        try {
            usr = userDAO.getEntityById(admin.getIdUser());
        } catch (DataException e) {
            exception = e;
        }

        assertNotNull(exception);
        assertNull(usr);
    }
}
