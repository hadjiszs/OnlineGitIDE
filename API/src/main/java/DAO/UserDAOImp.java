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

    public boolean addEntity(User user) throws Exception {
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

            throw new Exception("User already exists");
        }

        return true;

    }

    public User getEntityByMail(String mail) throws Exception {

        User user;

        try {
            user = em.find(User.class, mail);
        } catch(java.lang.IllegalArgumentException exception) {
            user = null;
        }

        if (user == null){
            throw new Exception("User doesn't exist");
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
