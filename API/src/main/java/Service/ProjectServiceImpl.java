package Service;

import DAO.ProjectDAO;
import DAO.ProjectDAOImpl;
import Model.Project;
import Model.User;
import Util.DataException;
import jdk.nashorn.internal.ir.PropertyKey;

import java.util.List;

/**
 * Created by amaia.nazabal on 10/21/16.
 */
public class ProjectServiceImpl implements ProjectService {
    ProjectDAO projectDAO;

    public ProjectServiceImpl(){
        projectDAO = new ProjectDAOImpl();
    }

    public boolean addEntity(Project project) throws DataException {
        if (project.getId() == null){
            return projectDAO.addEntity(project);
        }
        return false;
    }
    public Project getEntityById(Long id) throws DataException{
        return projectDAO.getEntityById(id);

    }
    public List getEntityList(User user) throws DataException{
        return projectDAO.getEntityList(user);
    }
    public boolean deleteEntity(Long id) throws DataException{
        return projectDAO.deleteEntity(id);
    }

}
