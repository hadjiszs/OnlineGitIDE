package Service;

import Model.User;
import DAO.UserDAOInt;
import DAO.UserDAOImp;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.List;

/**
 * Created by amaia.nazabal on 10/19/16.
 */
public class UserServiceImpl implements UserService {
    private UserDAOInt userDAO;

    public UserServiceImpl(EntityManager entityManager){
        userDAO = new UserDAOImp(entityManager);
    }

    public boolean addEntity(String pseudo, String mail) throws Exception {
        User user = new User(pseudo, mail);
        return userDAO.addEntity(user);
    }

    public User getEntityByMail(String mail) throws Exception {
        return userDAO.getEntityByMail(mail);
    }

    public List getEntityList() throws Exception {
        return userDAO.getEntityList();
    }

    public boolean deleteEntity(String mail) throws Exception {
        return userDAO.deleteEntity(mail);
    }

}
