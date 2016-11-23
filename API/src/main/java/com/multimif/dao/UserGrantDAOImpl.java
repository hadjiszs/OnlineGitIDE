package com.multimif.dao;

import com.multimif.model.User;
import com.multimif.model.UserGrant;
import com.multimif.model.UserGrantID;
import com.multimif.util.DataException;
import com.multimif.util.Messages;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Implementation de méthodes
 *
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 10/21/16.
 */
public class UserGrantDAOImpl extends DAO implements UserGrantDAO {

    @Override
    public boolean addEntity(UserGrant grant) {

        getEntityManager().getTransaction().begin();
        getEntityManager().persist(grant);
        getEntityManager().getTransaction().commit();
        closeEntityManager();

        return true;
    }

    @Override
    public List<UserGrant> getProjectsByEntity(User user) {
        List<UserGrant> permissions;

        TypedQuery<UserGrant> query = getEntityManager()
                .createNamedQuery("findByUser", UserGrant.class);
        query.setParameter("id", user.getIdUser());
        permissions = query.getResultList();

        closeEntityManager();
        return permissions;
    }

    @Override
    public List<UserGrant> getProjectsByUserByType(User user, UserGrant.PermissionType type) {
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
    public List<UserGrant> getDevelopersByEntity(Long idProject) {
        List<UserGrant> list;

        TypedQuery<UserGrant> query = getEntityManager()
                .createNamedQuery("findUsersByProjectByType", UserGrant.class);
        query.setParameter("id", idProject);
        query.setParameter("type", UserGrant.PermissionType.DEVELOPER);

        list = query.getResultList();
        closeEntityManager();

        return list;
    }

    @Override
    public UserGrant getEntityById(Long idUser, Long idProject) throws DataException {
        UserGrant grant;
        UserGrantID id = new UserGrantID();

        id.setProjectId(idProject);
        id.setUserId(idUser);

        grant = getEntityManager().find(UserGrant.class, id);
        closeEntityManager();

        if (grant == null) {
            throw new DataException(Messages.PERMISSION_NOT_EXISTS);
        }

        return grant;
    }

    @Override
    public UserGrant getAdminByEntity(Long idProject) throws DataException {
        UserGrant userGrant;

        TypedQuery<UserGrant> query = getEntityManager()
                .createNamedQuery("findUsersByProjectByType", UserGrant.class);
        query.setParameter("id", idProject);
        query.setParameter("type", UserGrant.PermissionType.ADMIN);

        userGrant = query.getSingleResult();
        closeEntityManager();

        if (userGrant == null) {
            throw new DataException(Messages.PROJECT_WITHOUT_OWNER);
        }

        return userGrant;
    }

    @Override
    public boolean deleteEntity(UserGrant grant) {
        getEntityManager().getTransaction().begin();
        getEntityManager().remove(getEntityManager().contains(grant) ? grant : getEntityManager().merge(grant));
        getEntityManager().getTransaction().commit();

        closeEntityManager();
        return true;
    }
}
