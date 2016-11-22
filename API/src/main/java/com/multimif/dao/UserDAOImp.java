package com.multimif.dao;

import com.multimif.model.User;
import com.multimif.util.DataException;
import com.multimif.util.Messages;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * Implementation des methodes sur le model User
 *
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 10/20/16.
 */
public class UserDAOImp extends DAO implements UserDAO {

    private static final Logger LOGGER = Logger.getLogger(UserDAOImp.class.getName());

    @Override
    public User addEntity(User user) throws DataException {
        User usr;
        try {
            usr = getEntityByMail(user.getMail());
        } catch (Exception ex) {
            LOGGER.log(Level.OFF, ex.toString(), ex);
            usr = null;
        }

        if (usr == null) {

            getEntityManager().getTransaction().begin();
            getEntityManager().persist(user);
            getEntityManager().getTransaction().commit();

            closeEntityManager();

        } else {
            throw new DataException(Messages.USER_ALREADY_EXISTS);
        }

        return user;
    }

    @Override
    public boolean updateEntity(User user) throws DataException {
        User usr = getEntityById(user.getIdUser());

        if (usr != null) {
            getEntityManager().getTransaction().begin();
            getEntityManager().merge(user);
            getEntityManager().getTransaction().commit();

            closeEntityManager();
        } else {
            throw new DataException(Messages.USER_NOT_EXISTS);
        }

        return true;
    }

    @Override
    public User getEntityById(Long id) throws DataException {
        User user = null;
        try {
            user = getEntityManager().find(User.class, id);
        } catch (Exception ex) {
            LOGGER.log(Level.FINE, ex.toString(), ex);
        } finally {
            closeEntityManager();
        }

        if (user == null) {
            throw new DataException(Messages.USER_NOT_EXISTS);
        }
        return user;
    }

    @Override
    public User getEntityByMail(String mail) throws DataException {
        User user;

        TypedQuery<User> query = getEntityManager().createNamedQuery("User.findByMail", User.class);
        query.setParameter("mail", mail);

        user = query.getSingleResult();
        closeEntityManager();

        if (user == null) {
            throw new DataException(Messages.USER_NOT_EXISTS);
        }

        return user;
    }

    @Override
    public List<User> getEntityList() {
        List<User> userList;

        TypedQuery<User> query = getEntityManager().createNamedQuery("User.findAll", User.class);
        userList = query.getResultList();
        closeEntityManager();

        return userList;
    }

    @Override
    public boolean deleteEntity(User user) throws DataException {
        getEntityManager().getTransaction().begin();
        getEntityManager().remove(getEntityManager().contains(user)
                ? user : getEntityManager().merge(user));
        getEntityManager().getTransaction().commit();
        closeEntityManager();

        return true;
    }

    @Override
    public User authEntity(String username, String password) throws DataException {
        User user;

        TypedQuery<User> query = getEntityManager().createNamedQuery("User.findByUsername", User.class);
        query.setParameter("username", username);

        user = query.getSingleResult();
        closeEntityManager();

        if (user == null) {
            throw new DataException(Messages.USER_NOT_EXISTS);
        } else {
            if (!user.getPassword().equals(password)) {
                throw new DataException(Messages.USER_AUTHENTICATION_FAILED);
            }
            return user;
        }
    }
}
