package DAO;

import Model.User;
import Model.UserGrant;
import Model.UserGrantID;
import Util.DataException;

import javax.persistence.Query;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by amaia.nazabal on 10/21/16.
 */
public class UserGrantDAOImpl extends DAO implements UserGrantDAO {

    private static final Logger LOGGER = Logger.getLogger( UserGrantDAOImpl.class.getName() );
    public boolean addEntity(UserGrant grant) throws DataException {
        try{
            getEntityManager().getTransaction().begin();
            getEntityManager().persist(grant);
            getEntityManager().getTransaction().commit();
        }catch (Exception e){
            LOGGER.log( Level.FINE, e.toString(), e);
        }finally {
            closeEntityManager();
        }
        return true;
    }

    public List<UserGrant> getProjectsByEntity(User user) throws DataException {
        List<UserGrant> permissions = null;

        try {
            Query query = getEntityManager().createNamedQuery("findByUser", UserGrant.class);
            query.setParameter("id",user.getIdUser());

            permissions = query.getResultList();
        }catch (Exception e){
            LOGGER.log( Level.FINE, e.toString(), e);
        }finally {
            closeEntityManager();
        }
        return permissions;
    }

    public List getDevelopersByEntity(Long idProject) throws DataException {
        List<UserGrant> list = null;

        try {
            Query query = getEntityManager().createNamedQuery("findProjectsByUserType", UserGrant.class);
            query.setParameter("id", idProject);
            query.setParameter("type", UserGrant.Permis.Dev);

            list = query.getResultList();
        } catch (Exception e) {
            LOGGER.log( Level.FINE, e.toString(), e);
        }finally {
            closeEntityManager();
        }

        return list;
    }

    public UserGrant getEntityById(Long idUser, Long idProject) throws DataException {
        UserGrant grant = null;
        UserGrantID id = new UserGrantID();

        id.setProjectId(idProject);
        id.setUserId(idUser);

        try {
            grant = getEntityManager().find(UserGrant.class, id);
        } catch (Exception e) {
            LOGGER.log( Level.FINE, e.toString(), e);
        }finally {
            closeEntityManager();
        }

        if (grant == null){
            throw new DataException("Permission doesn't exists");
        }

        return grant;
    }

    public UserGrant getAdminByEntity(Long idProject) throws DataException {
        UserGrant userGrant = null;

        try {
            Query query = getEntityManager().createNamedQuery("findProjectsByUserType", UserGrant.class);
            query.setParameter("id", idProject);
            query.setParameter("type", UserGrant.Permis.Admin);

            userGrant = (UserGrant) query.getSingleResult();
        }catch (Exception e){
            LOGGER.log( Level.FINE, e.toString(), e);
        }finally {
            closeEntityManager();
        }

        if (userGrant == null){
            throw new DataException("This project doesn't have owner");
        }

        return userGrant;
    }

    public boolean deleteEntity(UserGrant grant) throws DataException {
        try {
            getEntityManager().getTransaction().begin();
            getEntityManager().remove(getEntityManager().contains(grant) ? grant : getEntityManager().merge(grant));
            getEntityManager().getTransaction().commit();

            closeEntityManager();
        }catch (Exception e){
            LOGGER.log( Level.FINE, e.toString(), e);
            closeEntityManager();
            throw new DataException("This permission cannot be deleted");
        }

        return true;
    }
}
