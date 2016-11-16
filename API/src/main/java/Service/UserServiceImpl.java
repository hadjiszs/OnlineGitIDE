package main.java.Service;

import main.java.Model.User;
import main.java.DAO.UserDAO;
import main.java.DAO.UserDAOImp;

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

    public boolean addEntity(String pseudo, String mail, String hashkey)  {
        User user = new User(pseudo, mail, hashkey);
        return userDAO.addEntity(user);
    }

    public User getEntityByMail(String mail) {
        return userDAO.getEntityByMail(mail);
    }

    public List getEntityList() {
        return userDAO.getEntityList();
    }

    public boolean deleteEntity(String mail) {
        return userDAO.deleteEntity(mail);
    }

    public boolean authEntity(String username, String password){return  userDAO.authEntity(username,password);}

}
