package DAO;

import Model.User;
import Util.DataException;

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
    public User addEntity(User user) throws DataException {
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
            closeEntityManager();
            throw new DataException("User already exists");
        }
        closeEntityManager();

        return user;

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
            closeEntityManager();
            throw new DataException("User doesn't exist");
        }finally {
            closeEntityManager();
        }
        closeEntityManager();
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

        } catch(Exception ex) {
            user = null;
        }finally {
            closeEntityManager();
        }

        if (user == null){
            throw new DataException("User doesn't exist");
        }
        closeEntityManager();

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

        closeEntityManager();
        return list;
    }

    /**
     * @param user
     * @return
     * @throws Exception
     */
    public boolean deleteEntity(User user) throws DataException {
        getEntityManager().getTransaction().begin();
        getEntityManager().remove(getEntityManager().contains(user) ? user : getEntityManager().merge(user));
        getEntityManager().getTransaction().commit();
        closeEntityManager();
        return false;
    }

    public User authEntity(String username, String password) throws Exception{
        User user = null;

        try {
            TypedQuery<User> query = getEntityManager().createNamedQuery("User.findByPseudo", User.class);
            query.setParameter("username", username);

            List<User> list = query.getResultList();
            if (!list.isEmpty()) {
                user = list.get(0);
            }

        } catch(Exception exception) {
            exception.printStackTrace();
            user = null;
            closeEntityManager();
        }
        closeEntityManager();
        if (user == null || !user.getHashkey().equals(password)){
            throw new DataException("User doesn't exist");
        }else{
            return user;
        }
    }
}
