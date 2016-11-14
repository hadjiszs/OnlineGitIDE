package DAO;

import Model.User;
import Util.DataException;
import com.mysql.cj.core.exceptions.DataReadException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by amaia.nazabal on 10/20/16.
 */
public class UserDAOImp extends DAO implements UserDAO {

    public UserDAOImp(EntityManager em) {

        super(em);

    }

    public boolean addEntity(User user) throws DataException {
        User usr;
        try{
            usr = getEntityByMail(user.getMail());
        }catch(Exception ex){
            usr = null;
        }

        if (usr == null){
            em.getTransaction().begin();

            em.persist(user);
            em.getTransaction().commit();

        } else {

            throw new DataException("User already exists");
        }

        return true;

    }

    public User getEntityById(Long id) throws DataException{
        User user;
        try {
            user = em.find(User.class, id);
        }catch (Exception ex){
            throw new DataException("User doesn't exist");
        }

        return user;
    }

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
            exception.printStackTrace();
            user = null;
        }

        if (user == null){
            throw new DataException("User doesn't exist");
        }

        return user;
    }

    public List getEntityList() throws Exception {

        String query = "SELECT u FROM User u";
        return em.createQuery(query).getResultList();

    }

    public boolean deleteEntity(String mail) throws Exception {

        User user = getEntityByMail(mail);
        em.getTransaction().begin();
        em.remove(user);
        em.getTransaction().commit();

        return false;
    }
}
