package Service;

import DAO.UserDAO;
import DAO.UserDAOImp;
import Model.User;
import Util.DataException;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by amaia.nazabal on 10/19/16.
 */
public class UserServiceImpl implements UserService {
    private UserDAO userDAO;
    private static final Logger LOGGER = Logger.getLogger( UserServiceImpl.class.getName() );

    public UserServiceImpl(){
        userDAO = new UserDAOImp(APIService.em);
    }

    public Long addEntity(String pseudo, String mail, String hashkey) throws DataException {
        User user = new User(pseudo, mail, hashkey);
        return userDAO.addEntity(user);
    }

    public User getEntityByMail(String mail) throws DataException {
        return userDAO.getEntityByMail(mail);
    }

    public List getEntityList() throws NullPointerException {
        try {
            return userDAO.getEntityList();
        } catch (Exception e) {
            LOGGER.log( Level.FINE, e.toString(), e);
        }
        return null;
    }

    public boolean deleteEntity(String mail) throws DataException {
        try {
            return userDAO.deleteEntity(mail);
        } catch (Exception e) {
            LOGGER.log( Level.FINE, e.toString(), e);
        }
        return false;
    }

}
