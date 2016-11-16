package DAO;

import Model.User;
import Util.DataException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by amaia.nazabal on 10/20/16.
 */
public class UserDAOImp extends DAO implements UserDAO {

    /**
     * @param user
     * @return
     * @throws DataException
     */
    public Long addEntity(User user) throws DataException {
        User usr;
        try{
            usr = getEntityByMail(user.getMail());
        }catch(Exception ex){
            usr = null;
        }

        if (usr == null){
            getEntityManager().getTransaction().begin();
            getEntityManager().persist(user);
            getEntityManager().getTransaction().commit();
            closeEntityManager();

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
        try {
            user = getEntityManager().find(User.class, id);
        }catch (Exception ex){
            throw new DataException("User doesn't exist");
        }finally {
            closeEntityManager();
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

        } catch(Exception exception) {
            exception.printStackTrace();
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
    public List getEntityList() throws DataException {

        String query = "SELECT u FROM User u";
        List list =  getEntityManager().createQuery(query).getResultList();
        closeEntityManager();

        return list;
    }

    /**
     * @param mail
     * @return
     * @throws Exception
     */
    public boolean deleteEntity(String mail) throws DataException {

        User user = getEntityByMail(mail);
        getEntityManager().getTransaction().begin();
        getEntityManager().remove(user);
        getEntityManager().getTransaction().commit();

        closeEntityManager();

        return false;
    }
}
