package DAO;

import Model.User;
import Util.DataException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by amaia.nazabal on 10/20/16.
 */
public class UserDAOImp extends DAO implements UserDAO {
    private static final Logger LOGGER = Logger.getLogger( UserDAOImp.class.getName() );
    /**
     * @param em
     */
    public UserDAOImp(EntityManager em) {

        super(em);

    }

    /**
     * @param user
     * @return
     * @throws DataException
     */
    public Long addEntity(User user) throws DataException {
        User usr;
        try{
            usr = getEntityByMail(user.getMail());
        }catch(DataException ex){
            usr = null;
            LOGGER.log( Level.FINE, ex.toString(), ex);
        }

        if (usr == null){
            em.getTransaction().begin();

            em.persist(user);
            em.getTransaction().commit();

        } else {

            throw new DataException("User already exists");
        }

        return user.getId();

    }

    /**
     * @param id
     * @return
     * @throws DataException
     */
    public User getEntityById(Long id) throws DataException {
        User user;
        user = em.find(User.class, id);

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
            TypedQuery<User> query = em.createNamedQuery("User.findByMail", User.class);
            query.setParameter("mail", mail);

            List<User> list = query.getResultList();
            if (!list.isEmpty()) {
                user = list.get(0);
            }

        } catch(Exception exception) {
            LOGGER.log( Level.FINE, exception.toString(), exception);
            user = null;
        }

        if (user == null){
            throw new DataException("User doesn't exist");
        }

        return user;
    }

    /**
     * @return
     * @throws Exception
     */
    public List getEntityList() throws NullPointerException {

        String query = "SELECT u FROM User u";
        return em.createQuery(query).getResultList();

    }

    /**
     * @param mail
     * @return
     * @throws Exception
     */
    public boolean deleteEntity(String mail) throws DataException {

        User user = getEntityByMail(mail);
        em.getTransaction().begin();
        em.remove(user);
        em.getTransaction().commit();

        return false;
    }
}
