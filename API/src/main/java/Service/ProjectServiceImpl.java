package Service;

import DAO.ProjectDAO;
import DAO.ProjectDAOImpl;
import Model.Project;
import Util.DataException;

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

    public boolean addEntity(Project project) throws DataException{
        try {
            return projectDAO.addEntity(project);
        } catch (Exception e) {
            LOGGER.log( Level.FINE, e.toString(), e);
        }
        return false;
    }

    public Project getEntityById(Long id) throws DataException{
        try {
            return projectDAO.getEntityById(id);
        } catch (Exception e) {
            LOGGER.log( Level.FINE, e.toString(), e);
        }

        return null;
    }

    public List getEntityList() throws DataException{
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
