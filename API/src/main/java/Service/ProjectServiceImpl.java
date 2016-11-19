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
    ProjectDAO projectDAO;
    private static final Logger LOGGER = Logger.getLogger( ProjectServiceImpl.class.getName() );
    public ProjectServiceImpl(){
        projectDAO = new ProjectDAOImpl();
    }

    public Boolean addEntity(Project project, Long idUser) throws DataException {
        boolean ok = true;

        // Creation dans la bdd
        try {
            ok = projectDAO.addEntity(project);
        } catch (Exception e) {
            LOGGER.log( Level.FINE, e.toString(), e);
        }

        UserGrantService userGrantService = new UserGrantServiceImpl();
        userGrantService.addEntity(idUser, project.getId(), UserGrant.Permis.Admin);

        // Creation physique du depot
        if(ok) {
            User admin = userGrantService.getAdminByEntity(project.getId());
            if (admin == null)
                throw new NullPointerException();

            try {
                JsonObject content = Git.Util.createRepository(admin.getPseudo(), project.getName());
                ok = content.get("code").toString().equals(GitStatus.REPOSITORY_CREATED.toString());
                return ok;
            } catch (Exception e) {
                LOGGER.log(Level.FINE, e.toString(), e);
            }
        }

        return ok;
    }

    public Project getEntityById(Long id) throws DataException{
        try {
            return projectDAO.getEntityById(id);
        } catch (Exception e) {
            LOGGER.log( Level.FINE, e.toString(), e);
        }

        return null;
    }

    public List<Project> getEntityList() throws DataException{
        try {
            return projectDAO.getEntityList();
        } catch (Exception e) {
            LOGGER.log( Level.FINE, e.toString(), e);
        }
        return null;
    }

    public boolean deleteEntity(Long id) throws DataException{
        try {
            Project project = getEntityById(id);
            return projectDAO.deleteEntity(project);
        } catch (Exception e) {
            LOGGER.log( Level.FINE, e.toString(), e);
        }
        return false;
    }

}
