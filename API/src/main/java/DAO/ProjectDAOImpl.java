package DAO;

import Model.Project;
import Model.User;
import Util.DataException;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by amaia.nazabal on 10/21/16.
 */
public class ProjectDAOImpl extends DAO implements ProjectDAO {

    private static final Logger LOGGER = Logger.getLogger( ProjectDAOImpl.class.getName() );

    public boolean addEntity(Project project) throws DataException {
        Project proj = null;

        try{
            if (project.getId() != null)
                proj = getEntityById(project.getId());
        }catch (Exception ex){
            proj = null;
            LOGGER.log( Level.FINE, ex.toString(), ex);
        }

        if (proj == null){
            getEntityManager().getTransaction().begin();
            getEntityManager().persist(project);
            getEntityManager().getTransaction().commit();
        }else {

            throw new DataException("Project already exists");
        }

        return true;
    }

    /* TODO ajouter update */
    public boolean updateEntity(){
        return false;
    }

    public Project getEntityById(Long id) throws DataException{
        Project project = null;

        try {
            project = getEntityManager().find(Project.class, id);
        } catch(IllegalArgumentException exception) {
            project = null;
            LOGGER.log( Level.FINE, exception.toString(), exception);
        }finally {
            closeEntityManager();
        }

        if (null == project){
            throw new DataException("Project doesn't exists");
        }

        return project;
    }

    public List getEntityList(User user) throws DataException {
        String query = "SELECT p FROM Project p ";
        List list = getEntityManager().createQuery(query).getResultList();
        closeEntityManager();

        return list;
    }

    public boolean deleteEntity(Long id) throws DataException{
        Project project = getEntityById(id);
        getEntityManager().getTransaction().begin();
        getEntityManager().remove(project);
        getEntityManager().getTransaction().commit();

        closeEntityManager();

        return true;
    }
}
