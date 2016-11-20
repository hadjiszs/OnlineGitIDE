package DAO;

import Model.User;
import Util.DataException;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by amaia.nazabal on 10/20/16.
 */
public class UserDAOImp extends DAO implements UserDAO {

    private static final Logger LOGGER = Logger.getLogger(UserDAOImp.class.getName());

    /**
     * @param user
     * @return
     * @throws DataException
     */
    public User addEntity(User user) throws DataException {
        User usr;
        try {
            usr = getEntityByMail(user.getMail());
        } catch (Exception ex) {
            LOGGER.log(Level.FINE, ex.toString(), ex);
            usr = null;
        }

        if (usr == null) {
            try {
                getEntityManager().getTransaction().begin();
                getEntityManager().persist(user);
                getEntityManager().getTransaction().commit();
            }catch (Exception ex) {
                LOGGER.log(Level.FINE, ex.toString(), ex);

            }finally {
                closeEntityManager();
            }

        } else {
            throw new DataException("User already exists");
        }

        return user;
    }

    /**
     * @param id
     * @return
     * @throws DataException
     */
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
            throw new DataException("User doesn't exist");
        }
        return user;
    }

    /**
     * @param mail
     * @return
     * @throws DataException
     */
    public User getEntityByMail(String mail) throws DataException {

        User user = null;

        try {
            TypedQuery<User> query = getEntityManager().createNamedQuery("User.findByMail", User.class);
            query.setParameter("mail", mail);

            List<User> list = query.getResultList();
            if (!list.isEmpty()) {
                user = list.get(0);
            }
        } catch (Exception ex) {
            LOGGER.log(Level.FINE, ex.toString(), ex);
            user = null;
        } finally {
            closeEntityManager();
        }

        if (user == null) {
            throw new DataException("User doesn't exist");
        }

        return user;
    }


    /**
     * @return
     * @throws Exception
     */
    public List getEntityList() throws DataException {
        List list = null;

        try {
            list = getEntityManager().createNamedQuery("User.findAll", User.class)
                    .getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.FINE, e.toString(), e);
        } finally {
            closeEntityManager();
        }
        return list;
    }

    /**
     * @param user
     * @return
     * @throws Exception
     */
    public boolean deleteEntity(User user) throws DataException {
        try {
            getEntityManager().getTransaction().begin();
            getEntityManager().remove(getEntityManager().contains(user) ? user : getEntityManager().merge(user));
            getEntityManager().getTransaction().commit();
        } catch (Exception e) {
            LOGGER.log(Level.FINE, e.toString(), e);
        } finally {
            closeEntityManager();
        }

        return true;
    }

    public User authEntity(String username, String password) throws DataException {
        User user = null;

        try {
            TypedQuery<User> query = getEntityManager().createNamedQuery("User.findByUsername", User.class);
            query.setParameter("username", username);

            List<User> list = query.getResultList();
            if (!list.isEmpty()) {
                user = list.get(0);
            }

        } catch (Exception exception) {
            LOGGER.log(Level.FINE, exception.toString(), exception);
            user = null;
        } finally {
            closeEntityManager();
        }

        if (user == null || !user.getHashkey().equals(password)) {
            throw new DataException("User doesn't exist");
        } else {
            return user;
        }
    }
}
