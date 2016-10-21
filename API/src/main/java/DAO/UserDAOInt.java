package DAO;

import Model.User;

import java.util.List;

/**
 * Created by amaia.nazabal on 10/20/16.
 */
public interface UserDAOInt {
        boolean addEntity(User user) throws Exception;
        User getEntityByMail(String mail) throws Exception;
        List getEntityList() throws Exception;
        boolean deleteEntity(String mail) throws Exception;
}
