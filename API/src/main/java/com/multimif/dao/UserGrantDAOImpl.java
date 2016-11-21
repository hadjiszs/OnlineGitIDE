package com.multimif.dao;

import com.multimif.model.User;
import com.multimif.model.UserGrant;
import com.multimif.model.UserGrantID;
import com.multimif.util.DataException;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by amaia.nazabal on 10/21/16.
 */
public class UserGrantDAOImpl extends DAO implements UserGrantDAO {

    private static final Logger LOGGER = Logger.getLogger(UserGrantDAOImpl.class.getName());

    @Override
    public boolean addEntity(UserGrant grant) throws DataException {
        try {
            getEntityManager().getTransaction().begin();
            getEntityManager().persist(grant);
            getEntityManager().getTransaction().commit();
        } catch (Exception e) {
            LOGGER.log(Level.FINE, e.toString(), e);
        } finally {
            closeEntityManager();
        }
        return true;
    }

    @Override
    public List<UserGrant> getProjectsByEntity(User user) throws DataException {
        List<UserGrant> permissions = null;

        try {
            TypedQuery<UserGrant> query = getEntityManager()
                    .createNamedQuery("findByUser", UserGrant.class);
            query.setParameter("id", user.getIdUser());

            permissions = query.getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.FINE, e.toString(), e);
        } finally {
            closeEntityManager();
        }
        return permissions;
    }

    @Override
    public List<UserGrant> getProjectsByUserByType(User user, UserGrant.PermissionType type){
        List<UserGrant> permissions;
        TypedQuery<UserGrant> query = getEntityManager()
                .createNamedQuery("findProjectsByUserType", UserGrant.class);
        query.setParameter("user", user);
        query.setParameter("type", type);

        permissions = query.getResultList();
        closeEntityManager();

        return permissions;
    }

    @Override
    public List<UserGrant> getDevelopersByEntity(Long idProject) throws DataException {
        List<UserGrant> list = null;

        try {
            TypedQuery<UserGrant> query = getEntityManager()
                    .createNamedQuery("findUsersByProjectByType", UserGrant.class);
            query.setParameter("id", idProject);
            query.setParameter("type", UserGrant.PermissionType.DEVELOPER);

            list = query.getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.FINE, e.toString(), e);
        } finally {
            closeEntityManager();
        }

        return list;
    }

    @Override
    public UserGrant getEntityById(Long idUser, Long idProject) throws DataException {
        UserGrant grant = null;
        UserGrantID id = new UserGrantID();

        id.setProjectId(idProject);
        id.setUserId(idUser);

        try {
            grant = getEntityManager().find(UserGrant.class, id);
        } catch (Exception e) {
            LOGGER.log(Level.FINE, e.toString(), e);
        } finally {
            closeEntityManager();
        }

        if (grant == null) {
            throw new DataException("Permission doesn't exists");
        }

        return grant;
    }

    @Override
    public UserGrant getAdminByEntity(Long idProject) throws DataException {
        UserGrant userGrant = null;

        try {
            TypedQuery<UserGrant> query = getEntityManager()
                    .createNamedQuery("findUsersByProjectByType", UserGrant.class);
            query.setParameter("id", idProject);
            query.setParameter("type", UserGrant.PermissionType.ADMIN);

            userGrant = query.getSingleResult();
        } catch (Exception e) {
            LOGGER.log(Level.FINE, e.toString(), e);
        } finally {
            closeEntityManager();
        }

        if (userGrant == null) {
            throw new DataException("This project doesn't have owner");
        }

        return userGrant;
    }

    @Override
    public boolean deleteEntity(UserGrant grant) throws DataException {
        try {
            getEntityManager().getTransaction().begin();
            getEntityManager().remove(getEntityManager().contains(grant) ? grant : getEntityManager().merge(grant));
            getEntityManager().getTransaction().commit();

            closeEntityManager();
        } catch (Exception e) {
            LOGGER.log(Level.FINE, e.toString(), e);
            closeEntityManager();
            throw new DataException("This permission cannot be deleted");
        }

        return true;
    }
}
