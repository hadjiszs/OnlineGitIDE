package DAO;

import Model.User;

import java.util.List;

/**
 * Created by amaia.nazabal on 10/20/16.
 */
public interface UserDAO {
        boolean addEntity(User user) throws RuntimeException;
        User getEntityByMail(String mail) throws RuntimeException;
        List getEntityList() throws RuntimeException;
        boolean deleteEntity(String mail) throws RuntimeException;
}
