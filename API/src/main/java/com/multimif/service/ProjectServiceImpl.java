package com.multimif.service;

import com.multimif.dao.ProjectDAO;
import com.multimif.dao.ProjectDAOImpl;
import com.multimif.git.GitStatus;
import com.multimif.model.Project;
import com.multimif.model.User;
import com.multimif.model.UserGrant;
import com.multimif.util.DataException;
import com.multimif.git.Util;
import com.multimif.util.Messages;

import javax.json.JsonObject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 10/21/16.
 */
public class ProjectServiceImpl implements ProjectService {
    private UserGrantService userGrantService;
    private ProjectDAO projectDAO;

    private static final Logger LOGGER = Logger.getLogger(ProjectServiceImpl.class.getName());

    public ProjectServiceImpl() {
        projectDAO = new ProjectDAOImpl();
        userGrantService = new UserGrantServiceImpl();
    }

    @Override
    public Project addEntity(String name, Project.TypeProject type, Long idUser) throws Exception {
        boolean ok;
        Project project = new Project(name, type, idUser);
        // Creation dans la bdd
        try {
            ok = projectDAO.addEntity(project);

            /* On cree le rapport du projet avec l'admin */
            userGrantService.addEntity(idUser, project.getIdProject(), UserGrant.PermissionType.ADMIN);
        } catch (DataException e) {
            LOGGER.log(Level.FINE, e.toString(), e);
            throw new DataException(e.getMessage());
        }

        // Creation physique du depot
        if (ok) {
            User admin = userGrantService.getAdminByEntity(project.getIdProject());
            JsonObject content = Util.createRepository(admin.getUsername(), project.getName());
            content.get("code").toString().equals(GitStatus.REPOSITORY_CREATED.toString());
        }

        return project;
    }

    @Override
    public boolean updateEntity(Project project) throws DataException {
        return projectDAO.updateEntity(project);
    }

    @Override
    public Project getEntityById(Long id) throws DataException {
        return projectDAO.getEntityById(id);
    }

    @Override
    public List<Project> getEntityList() {
        return projectDAO.getEntityList();
    }

    @Override
    public boolean deleteEntity(Long idProject, Long idUser) throws DataException {
        boolean result;
        User admin = userGrantService.getAdminByEntity(idProject);

        if (admin.getIdUser().equals(idUser)) {

            /* On enleve le permis avec l'admin */
            userGrantService.deleteEntity(idUser, idProject, UserGrant.PermissionType.ADMIN);

            Project project = getEntityById(idProject);
            result = projectDAO.deleteEntity(project);

            if (result){
                Util.deleteRepository(admin.getUsername(), project.getName());
            }

        } else {
            throw new DataException(Messages.PROJECT_DELETE_CONTROL);
        }

        return result;
    }

}
