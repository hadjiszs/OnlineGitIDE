package DAO;

import Model.User;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by amaia.nazabal on 10/20/16.
 */
public class UserDAOImp extends DAO implements UserDAO {

    public UserDAOImp(EntityManager em) {

        super(em);

    }

    public boolean addEntity(User user){

        if (getEntityByMail(user.getMail()) == null) {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        }

        return true;

    }

    public User getEntityByMail(String mail) {

        User user;

        try {
            user = em.find(User.class, mail);
        } catch(java.lang.IllegalArgumentException exception) {
            user = null;
        }

        return user;
    }

    public List getEntityList() {

        String query = "SELECT u FROM User u";
        return em.createQuery(query).getResultList();

    }

    public boolean deleteEntity(String mail) {

        User user = getEntityByMail(mail);
        em.getTransaction().begin();
        em.remove(user);
        em.getTransaction().commit();

        return false;
    }
}
