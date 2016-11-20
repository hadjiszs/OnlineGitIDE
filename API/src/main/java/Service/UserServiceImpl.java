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
        userDAO = new UserDAOImp();
    }

    @Override
    public User addEntity(String username, String mail, String hashkey) throws DataException {
        User user = new User(username, mail, hashkey);
        return userDAO.addEntity(user);
    }

    @Override
    public User getEntityByMail(String mail) throws DataException {
        return userDAO.getEntityByMail(mail);
    }

    @Override
    public List getEntityList() throws DataException {
        return userDAO.getEntityList();

    }

    @Override
    public boolean deleteEntity(Long idUser) throws DataException {
        User user = getEntityById(idUser);
        return userDAO.deleteEntity(user);
    }

    @Override
    public User authEntity(String username, String password) throws DataException {
        try {
            return userDAO.authEntity(username,password);
        } catch (Exception e) {
            LOGGER.log( Level.FINE, e.toString(), e);
        }
        return null;
    }

    @Override
    public User getEntityById(Long id) throws DataException{
        return userDAO.getEntityById(id);
    }
}
