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
    UserGrantService userGrantService = new UserGrantServiceImpl();
    ProjectDAO projectDAO;

    private static final Logger LOGGER = Logger.getLogger( ProjectServiceImpl.class.getName() );
    public ProjectServiceImpl(){
        projectDAO = new ProjectDAOImpl();
    }

    public boolean addEntity(Project project) throws DataException{
        boolean ok = false;

        // Creation dans la bdd
        try {
            ok = projectDAO.addEntity(project);
        } catch (Exception e) {
            LOGGER.log( Level.FINE, e.toString(), e);
        }

        // Creation physique du depot
        if(ok) {
            try {
                User admin = userGrantService.getAdminByEntity(project.getId());
                JsonObject content = Git.Util.createRepository(admin.getUsername(), project.getName());
                ok = content.get("code").toString().equals(GitStatus.REPOSITORY_CREATED.toString());
            } catch (Exception e) {
                LOGGER.log(Level.FINE, e.toString(), e);
                return  false;
            }
        }

        return ok;
    }

    public Project getEntityById(Long id) throws DataException{
        return projectDAO.getEntityById(id);
    }

    public List getEntityList() throws DataException{
        try {
            return projectDAO.getEntityList();
        } catch (Exception e) {
            LOGGER.log( Level.FINE, e.toString(), e);
        }
        return null;
    }

    public boolean deleteEntity(Long idProject, Long idUser) throws DataException{
        boolean result;
        try {
            User admin = userGrantService.getAdminByEntity(idProject);
            if (admin.getId().equals(idUser)) {
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
