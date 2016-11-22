package Service;

import DAO.ProjectDAO;
import DAO.ProjectDAOImpl;
import Git.GitStatus;
import Model.Project;
import Model.User;
import Model.UserGrant;
import Util.DataException;

import javax.json.JsonObject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by amaia.nazabal on 10/21/16.
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
    public Project addEntity(Project project, Long idUser) throws DataException{
        boolean ok;

        // Creation dans la bdd
        try {
            ok = projectDAO.addEntity(project);

            /* On cree le rapport du projet avec l'admin */
            userGrantService.addEntity(idUser, project.getIdProject(), UserGrant.Permis.Admin);
        } catch (Exception e) {
            LOGGER.log( Level.FINE, e.toString(), e);
            throw new DataException(e.getMessage());
        }

        // Creation physique du depot
        if(ok) {
            try {
                User admin = userGrantService.getAdminByEntity(project.getIdProject());
                JsonObject content = Git.Util.createRepository(admin.getUsername(), project.getName());
                ok = content.get("code").toString().equals(GitStatus.REPOSITORY_CREATED.toString());
            } catch (Exception e) {
                LOGGER.log(Level.FINE, e.toString(), e);
                throw new DataException(e.getMessage());
            }
        }

        return project;
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
                result = userGrantService.deleteEntity(idUser, idProject, UserGrant.Permis.Admin);
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
