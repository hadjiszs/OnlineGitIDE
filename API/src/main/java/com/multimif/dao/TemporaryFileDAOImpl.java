package com.multimif.dao;

import com.multimif.model.Project;
import com.multimif.model.TemporaryFile;
import com.multimif.model.User;
import com.multimif.util.DataException;
import com.multimif.util.Messages;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * Implementation des methodes sur le model TemporaryFile
 *
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 19/10/16.
 */
public class TemporaryFileDAOImpl extends DAO implements TemporaryFileDAO {
    private static final Logger LOGGER = Logger.getLogger(TemporaryFileDAOImpl.class.getName());

    @Override
    public TemporaryFile getEntityByHashKey(String hashKey) throws DataException {

        TemporaryFile file = null;
        TypedQuery<TemporaryFile> query = getEntityManager().createNamedQuery("TemporaryFile.findByHashkey", TemporaryFile.class);
        query.setParameter("hashKey", hashKey);

        try {
            file = query.getSingleResult();
        } catch(NoResultException e) {
            throw new DataException(Messages.FILE_NOT_EXISTS);
        } finally {
            closeEntityManager();
        }

        return file;
    }

    @Override
    public List<TemporaryFile> getEntityByUserProject(User user, Project project) {
        List<TemporaryFile> temporaryFiles;

        TypedQuery<TemporaryFile> typedQuery =
                getEntityManager().createNamedQuery("TemporaryFile.findByUserAndProject",
                        TemporaryFile.class);
        typedQuery.setParameter("project", project);
        typedQuery.setParameter("user", user);

        temporaryFiles = typedQuery.getResultList();
        closeEntityManager();

        return temporaryFiles;
    }

    @Override
    public TemporaryFile getEntityById(Long idTemporaryFile) {
        TemporaryFile file;

        file = getEntityManager().find(TemporaryFile.class, idTemporaryFile);
        closeEntityManager();

        return file;
    }

    @Override
    public boolean exist(Long idFileTemporary) {
        boolean result = getEntityManager().find(TemporaryFile.class, idFileTemporary) != null;
        closeEntityManager();

        return result;
    }

    @Override
    public TemporaryFile addEntity(TemporaryFile temporaryFile) throws DataException {

        if (temporaryFile.getId() != null) {
            throw new DataException(Messages.FILE_ALREADY_EXISTS);
        }

        getEntityManager().getTransaction().begin();
        getEntityManager().persist(temporaryFile);
        getEntityManager().getTransaction().commit();

        closeEntityManager();

        return temporaryFile;
    }

    @Override
    public boolean updateEntity(TemporaryFile temporaryFile) throws DataException {

        getEntityById(temporaryFile.getId());
        if (temporaryFile.getId() == null){
            throw new DataException(Messages.FILE_NOT_EXISTS);
        }
        getEntityManager().getTransaction().begin();
        getEntityManager().merge(temporaryFile);
        getEntityManager().getTransaction().commit();

        closeEntityManager();

        return true;
    }

    @Override
    public boolean deleteEntity(Long idFileTemporary) throws DataException {
        TemporaryFile temporaryFile = getEntityById(idFileTemporary);

        if (temporaryFile == null){
            throw new DataException(Messages.FILE_NOT_EXISTS);
        }

        getEntityManager().getTransaction().begin();
        getEntityManager().remove(getEntityManager().contains(temporaryFile)
                ? temporaryFile : getEntityManager().merge(temporaryFile));
        getEntityManager().getTransaction().commit();

        return true;
    }
}
