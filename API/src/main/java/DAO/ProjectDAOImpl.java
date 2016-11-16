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

    public ProjectDAOImpl(EntityManager em) {
        super(em);
    }

    public boolean addEntity(Project project) throws DataException{
        Project proj;

        try{
            proj = getEntityById(project.getId());
        }catch (Exception ex){
            proj = null;
            LOGGER.log( Level.FINE, ex.toString(), ex);
        }

        if (null == proj){
            em.getTransaction().begin();
            em.persist(project);
            em.getTransaction().commit();
        }else {

            try {
                throw new Exception("Project already exists");
            } catch (Exception e) {
                LOGGER.log( Level.FINE, e.toString(), e);
            }
        }

        return false;
    }

    public Project getEntityById(Long id) throws DataException{
        Project project = null;

        try {
            project = em.find(Project.class, id);
        } catch(IllegalArgumentException exception) {
            project = null;
            LOGGER.log( Level.FINE, exception.toString(), exception);
        }

        if (project == null){
            try {
                throw new Exception("Project doesn't exists");
            } catch (Exception e) {
                LOGGER.log( Level.FINE, e.toString(), e);
            }

        }

        return project;
    }

    public List getEntityList(User user) throws DataException {
        String query = "SELECT p FROM Project p ";
        return em.createQuery(query).getResultList();
    }

    public boolean deleteEntity(Long id) throws DataException{
        Project project = getEntityById(id);
        em.getTransaction().begin();
        em.remove(project);
        em.getTransaction().commit();

        return true;
    }
}
