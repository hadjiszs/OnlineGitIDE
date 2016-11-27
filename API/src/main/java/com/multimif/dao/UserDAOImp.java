package com.multimif.dao;

import com.multimif.model.User;
import com.multimif.util.DataException;
import com.multimif.util.Messages;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
        } catch (DataException ex) {
            LOGGER.log(Level.OFF, ex.toString(), ex);
            usr = null;
        }

        if (usr == null) {

            user.setPassword(hashGenerator(user.getPassword()));
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
            user.setPassword(hashGenerator(user.getPassword()));

            getEntityManager().getTransaction().begin();
            getEntityManager().merge(user);
            getEntityManager().getTransaction().commit();

            closeEntityManager();
            hiddePassword(user);

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

        hiddePassword(user);

        return user;
    }

    @Override
    public User getEntityByMail(String mail) throws DataException {

        User user = null;

        TypedQuery<User> query = getEntityManager().createNamedQuery("User.findByMail", User.class);
        query.setParameter("mail", mail);

        try{
            user = query.getSingleResult();
        }catch (NoResultException e){
            LOGGER.log(Level.OFF, e.toString(), e);
        }finally {
            closeEntityManager();
        }

        if (user == null) {
            throw new DataException(Messages.USER_NOT_EXISTS);
        }

        /*On cache le password*/
        hiddePassword(user);

        return user;
    }

    @Override
    public List<User> getEntityList() {
        List<User> userList;

        TypedQuery<User> query = getEntityManager().createNamedQuery("User.findAll", User.class);
        userList = query.getResultList();
        closeEntityManager();

        /* On cache le password */
        userList.forEach(this::hiddePassword);

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
    public User authEntity(String username, String password, Boolean hash) throws DataException {
        User user;

        TypedQuery<User> query = getEntityManager().createNamedQuery("User.findByUsername", User.class);
        query.setParameter("username", username);

        try{
            user = query.getSingleResult();
        }catch (NoResultException e){
            LOGGER.log(Level.OFF, e.toString(), e);
            throw new DataException(Messages.USER_NOT_EXISTS);
        }finally {
            closeEntityManager();
        }

        if (user == null) {
            throw new DataException(Messages.USER_NOT_EXISTS);
        } else {
            // Si c'est un hash
            if(hash){
                if (!user.getPassword().equals(password)) {
                    throw new DataException(Messages.USER_AUTHENTICATION_FAILED);
                }
            }else{
                if (!user.getPassword().equals(hashGenerator(password))) {
                    throw new DataException(Messages.USER_AUTHENTICATION_FAILED);
                }
            }
        return user;
    }
    }

    private void hiddePassword(User user){
        user.setPassword("");
    }

    private String hashGenerator (String plaintext) throws DataException {
        MessageDigest messageDigest;

        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            LOGGER.log(Level.FINE, e.getMessage(), e);
            throw new DataException(Messages.USER_CANT_CREATED);
        }

        messageDigest.reset();
        messageDigest.update(plaintext.getBytes());

        byte[] digest = messageDigest.digest();

        BigInteger bigInt = new BigInteger(1, digest);
        String hashtext = bigInt.toString(16);

        StringBuilder hash = new StringBuilder(bigInt.toString(16));

        /* Maintenant on doit mis à zero jusqu'à 32 characteres */
        while(hashtext.length() < 32 ) {
            hash.insert(0, "0");
        }

        return hash.toString();
    }
}
