package DAO;

import Model.User;

import java.util.List;

/**
 * Created by amaia.nazabal on 10/20/16.
 */
public interface UserDAO {
        boolean addEntity(User user);
        User getEntityByMail(String mail);
        List getEntityList();
        boolean deleteEntity(String mail);
}
