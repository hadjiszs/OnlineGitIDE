package Service;

import DAO.UserDAO;
import DAO.UserDAOImp;
import Model.User;
import Util.DataException;

import java.util.List;

/**
 * Created by amaia.nazabal on 10/19/16.
 */
public class UserServiceImpl implements UserService {
    private UserDAO userDAO;

    public UserServiceImpl(){
        userDAO = new UserDAOImp();
    }

    public Long addEntity(String pseudo, String mail, String hashkey) throws DataException {
        User user = new User(pseudo, mail, hashkey);
        userDAO.addEntity(user);

        return  user.getId();
    }

    public User getEntityByMail(String mail) throws DataException {
        return userDAO.getEntityByMail(mail);
    }

    public User getEntityById(Long id) throws DataException {
        User user = userDAO.getEntityById(id);
        if (user == null){
            throw new DataException("User doesn't exists");
        }
        return userDAO.getEntityById(id);
    }

    public List getEntityList() throws Exception {
        return userDAO.getEntityList();
    }

    public boolean deleteEntity(String mail) throws Exception {
        User user = userDAO.getEntityByMail(mail);
        return userDAO.deleteEntity(user);
    }

}
