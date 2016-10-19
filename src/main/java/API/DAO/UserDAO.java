package API.DAO;

/**
 * Created by p1317074 on 19/10/16.
 */

import Model.User;

import javax.persistence.EntityManager;


public class UserDAO extends DAO {


    public UserDAO(EntityManager em) {
        super(em);
    }

    /**
     * Renvoie l'utilisateur correspondant à cet email
     * @param email l'email de l'utilisateur
     * @return l'utilisateur ou null s'il n'existe pas
     */
    public User getUserByEmail(String email) {
        User user;
        try {
            user = em.find(User.class, email);
        } catch(java.lang.IllegalArgumentException exception) {
            user = null;
        }
        return user;
    }

    /**
     * Créée un nouvel utilisateur ou met à jour son pseudo
     * @param email
     * @param pseudo
     * @return l'utilisateur créé ou lis à jour
     */
    public User createOrUpdate(String email, String pseudo, String hashkey) {
        User user = em.find(User.class, email);
        if (user == null) {
            user = new User(email, pseudo, hashkey);
        }
        em.persist(user);
        return user;
    }

}
