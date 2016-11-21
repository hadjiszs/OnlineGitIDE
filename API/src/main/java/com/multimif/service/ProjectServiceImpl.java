package com.multimif.service;

import com.multimif.dao.ProjectDAO;
import com.multimif.dao.ProjectDAOImpl;
import com.multimif.git.GitStatus;
import com.multimif.model.Project;
import com.multimif.model.User;
import com.multimif.model.UserGrant;
import com.multimif.util.DataException;
import com.multimif.git.Util;

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
    UserGrantService userGrantService;
    ProjectDAO projectDAO;

    private static final Logger LOGGER = Logger.getLogger( ProjectServiceImpl.class.getName() );

    public ProjectServiceImpl(){
        projectDAO = new ProjectDAOImpl();
        userGrantService = new UserGrantServiceImpl();
    }

    @Override
    public boolean addEntity(Project project, Long idUser) throws DataException{
        boolean ok;

        // Creation dans la bdd
        try {
            ok = projectDAO.addEntity(project);

            /* On cree le rapport du projet avec l'admin */
            userGrantService.addEntity(idUser, project.getIdProject(), UserGrant.PermissionType.ADMIN);
        } catch (Exception e) {
            LOGGER.log( Level.FINE, e.toString(), e);
            throw new DataException(e.getMessage());
        }

        // Creation physique du depot
        if(ok) {
            try {
                User admin = userGrantService.getAdminByEntity(project.getIdProject());
                JsonObject content = Util.createRepository(admin.getUsername(), project.getName());
                ok = content.get("code").toString().equals(GitStatus.REPOSITORY_CREATED.toString());
            } catch (Exception e) {
                LOGGER.log(Level.FINE, e.toString(), e);
                throw new DataException(e.getMessage());
            }
        }

        return ok;
    }

    @Override
    public boolean updateEntity(Project project) throws DataException{
        return projectDAO.updateEntity(project);
    }

    @Override
    public Project getEntityById(Long id) throws DataException{
        return projectDAO.getEntityById(id);
    }

    @Override
    public List<Project> getEntityList() throws DataException{
        try {
            return projectDAO.getEntityList();
        } catch (Exception e) {
            LOGGER.log( Level.FINE, e.toString(), e);
        }
        return null;
    }

    @Override
    public boolean deleteEntity(Long idProject, Long idUser) throws DataException{
        boolean result;
        try {
            User admin = userGrantService.getAdminByEntity(idProject);
            if (admin.getIdUser().equals(idUser)) {
                /* On enleve le permis avec l'admin */
                result = userGrantService.deleteEntity(idUser, idProject, UserGrant.PermissionType.ADMIN);
                if (result) {
                    Project project = getEntityById(idProject);
                    result = projectDAO.deleteEntity(project);
                }else {
                    throw new DataException("Error removing permission");
                }
            } else {
                throw new DataException("Only the admin has permissions for remove the project");
            }

            return result;
        } catch (Exception e) {
            LOGGER.log( Level.FINE, e.toString(), e);
            throw new DataException(e.getMessage());
        }
    }

}
