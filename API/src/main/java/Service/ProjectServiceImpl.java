package Service;

import DAO.ProjectDAO;
import DAO.ProjectDAOImpl;
import Model.Project;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by amaia.nazabal on 10/21/16.
 */
public class ProjectServiceImpl implements ProjectService{
    ProjectDAO projectDAO;

    public ProjectServiceImpl(EntityManager entityManager){
        projectDAO = new ProjectDAOImpl(entityManager);
    }

    public boolean addEntity(Project project) throws Exception{
        return projectDAO.addEntity(project);
    }
    public Project getEntityById(Long id) throws Exception{
        return projectDAO.getEntityById(id);

    }
    public List getEntityList() throws Exception{
        return projectDAO.getEntityList();
    }
    public boolean deleteEntity(Long id) throws Exception{
        return projectDAO.deleteEntity(id);
    }

}
