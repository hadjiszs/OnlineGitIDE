package Service;

import DAO.UserDAO;
import DAO.UserDAOImp;
import Model.User;
import Util.DataException;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by amaia.nazabal on 10/19/16.
 */
public class UserServiceImpl implements UserService {
    private UserDAO userDAO;

    public UserServiceImpl(){
        userDAO = new UserDAOImp();
    }

    public User addEntity(String pseudo, String mail, String hashkey) throws DataException {
        User user = new User(pseudo, mail, hashkey);
        return userDAO.addEntity(user);

        //return  user.getId();
    }


    public User getEntityByMail(String mail) throws DataException {
        return userDAO.getEntityByMail(mail);
    }

    public User getEntityById(Long id) throws DataException {
        return userDAO.getEntityById(id);
    }

    public List getEntityList() throws Exception {
        return userDAO.getEntityList();
    }

    public boolean deleteEntity(String mail) throws Exception {
        return userDAO.deleteEntity(mail);
    }

    public User authEntity(String username,String password) throws Exception{
        return userDAO.authEntity(username,password);
    }

}
