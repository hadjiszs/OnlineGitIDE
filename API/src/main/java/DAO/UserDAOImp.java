package DAO;

import Model.User;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by amaia.nazabal on 10/20/16.
 */
public class UserDAOImp extends DAO implements UserDAOInt {

    public UserDAOImp(EntityManager em) {

        super(em);

    }

    public boolean addEntity(User user) throws Exception {

        if (getEntityByMail(user.getMail()) == null) {
            user.setHashkey("");
        }

        em.persist(user);
        return true;

    }

    public User getEntityByMail(String mail) throws Exception {

        User user;

        try {
            user = em.find(User.class, mail);
        } catch(java.lang.IllegalArgumentException exception) {
            user = null;
        }

        return user;
    }

    public List getEntityList() throws Exception {

        String query = "SELECT * FROM User";
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
