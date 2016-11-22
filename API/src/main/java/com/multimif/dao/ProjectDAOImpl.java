package com.multimif.dao;

import com.multimif.model.Project;
import com.multimif.util.DataException;
import com.multimif.util.Messages;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 10/21/16.
 */
public class ProjectDAOImpl extends DAO implements ProjectDAO {
    private static final Logger LOGGER = Logger.getLogger(ProjectDAOImpl.class.getName());

    @Override
    public boolean addEntity(Project project) throws DataException {
        Project proj = null;

        try {
            if (project.getIdProject() != null) {
                proj = getEntityById(project.getIdProject());
            }
        } catch (DataException ex) {
            LOGGER.log(Level.OFF, ex.toString(), ex);
            proj = null;

        }

        if (proj == null) {
            project.setCreationDate(new Date());
            project.setLastModification(new Date());

            getEntityManager().getTransaction().begin();
            getEntityManager().persist(project);
            getEntityManager().getTransaction().commit();

            closeEntityManager();
        } else {
            throw new DataException(Messages.PROJECT_ALREADY_EXISTS);
        }
        return true;
    }

    @Override
    public boolean updateEntity(Project project) throws DataException {
        Project proj;

        if (project.getIdProject() != null) {
            proj = getEntityById(project.getIdProject());
        } else {
            throw new DataException(Messages.PROJECT_UNSPECIFIED_ID);
        }

        if (proj != null) {
            project.setCreationDate(proj.getCreationDate());
            project.setLastModification(new Date());
            getEntityManager().getTransaction().begin();
            getEntityManager().merge(project);
            getEntityManager().getTransaction().commit();

            closeEntityManager();
        } else {
            throw new DataException(Messages.PROJECT_NOT_EXISTS);
        }

        return true;
    }

    @Override
    public Project getEntityById(Long id) throws DataException {
        Project project;

        project = getEntityManager().find(Project.class, id);
        closeEntityManager();

        if (project == null) {
            throw new DataException(Messages.PROJECT_NOT_EXISTS);
        }

        return project;
    }

    @Override
    public List<Project> getEntityList() {
        List<Project> list = getEntityManager()
                .createNamedQuery("Project.findAll", Project.class).getResultList();
        closeEntityManager();

        return list;
    }

    @Override
    public boolean deleteEntity(Project project) {

        getEntityManager().getTransaction().begin();
        getEntityManager().remove(getEntityManager().contains(project)
                ? project : getEntityManager().merge(project));
        getEntityManager().getTransaction().commit();

        closeEntityManager();

        return true;
    }
}
