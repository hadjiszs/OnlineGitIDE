package Service;

import Model.User;
import DAO.UserDAO;
import DAO.UserDAOImp;
import Util.DataException;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by amaia.nazabal on 10/19/16.
 */
public class UserServiceImpl implements UserService {
    private UserDAO userDAO;

    public UserServiceImpl(EntityManager entityManager){
        userDAO = new UserDAOImp(entityManager);
    }

    public boolean addEntity(String pseudo, String mail, String hashkey) throws DataException {
        User user = new User(pseudo, mail, hashkey);
        return userDAO.addEntity(user);
    }

    public User getEntityByMail(String mail) throws DataException {
        return userDAO.getEntityByMail(mail);
    }

    public List getEntityList() throws Exception {
        return userDAO.getEntityList();
    }

    public boolean deleteEntity(String mail) throws Exception {
        return userDAO.deleteEntity(mail);
    }

}
