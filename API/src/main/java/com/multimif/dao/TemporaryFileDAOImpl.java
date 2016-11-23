package com.multimif.dao;

import com.multimif.model.Project;
import com.multimif.model.User;
import com.multimif.model.TemporaryFile;
import com.multimif.util.DataException;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.sound.midi.SysexMessage;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by p1317074 on 19/10/16.
 */
public class TemporaryFileDAOImpl extends DAO implements TemporaryFileDAO {

    public TemporaryFile getEntityByHashKey(String hashKey) throws DataException {
        TemporaryFile file = null;
        TypedQuery<TemporaryFile> query = getEntityManager().createNamedQuery("TemporaryFile.findByHashkey", TemporaryFile.class);
        query.setParameter("hashKey", hashKey);

        try {
            file = query.getSingleResult();
        } catch(NoResultException e) {
            file = null;
        } finally {
            closeEntityManager();
        }

        return file;
    }

    public List getEntityByUserProject(User user, Project project){
        List<TemporaryFile> temporaryFiles;
        try {
            TypedQuery<TemporaryFile> typedQuery =
                    getEntityManager().createNamedQuery("TemporaryFile.findByUserAndProject",
                            TemporaryFile.class);
            typedQuery.setParameter("project", project);
            typedQuery.setParameter("user", user);

            temporaryFiles = typedQuery.getResultList();
        } catch (Exception e) {
            temporaryFiles = new ArrayList<>();
        } finally {
            closeEntityManager();
        }

        return temporaryFiles;
    }

    public TemporaryFile getEntityById(Long idTemporaryFile) throws DataException {
        TemporaryFile file;

        try{
            file = getEntityManager().find(TemporaryFile.class, idTemporaryFile);
        }catch (Exception e){
            closeEntityManager();
            throw new DataException("File doesn't exists");
        }

        return file;
    }

    public boolean exist(Long idFileTemporary) {
        boolean result = getEntityManager().find(TemporaryFile.class, idFileTemporary) != null;
        closeEntityManager();

        return result;
    }

    public TemporaryFile add(TemporaryFile temporaryFile) throws DataException {
        // si le temporary file existe déjà, on s'en va
        if(getEntityByHashKey(temporaryFile.getHashKey()) != null)
            return null;

        getEntityManager().getTransaction().begin();
        getEntityManager().persist(temporaryFile);
        getEntityManager().getTransaction().commit();

        closeEntityManager();

        return temporaryFile;
    }

    @Override
    public boolean deleteEntity(Long idFileTemporary) throws DataException {
        TemporaryFile temporaryFile = getEntityById(idFileTemporary);

        getEntityManager().getTransaction().begin();
        getEntityManager().remove(temporaryFile);
        getEntityManager().getTransaction().commit();

        return true;
    }
}
