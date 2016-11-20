package DAO;

import Model.Project;
import Util.DataException;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by amaia.nazabal on 10/21/16.
 */
public class ProjectDAOImpl extends DAO implements ProjectDAO {
    private static final Logger LOGGER = Logger.getLogger( ProjectDAOImpl.class.getName() );


    public boolean addEntity(Project project) throws DataException{
        Project proj = null;

        try{
            if (project.getIdProject() != null){
                proj = getEntityById(project.getIdProject());
            }

        }catch (Exception ex){
            proj = null;
            LOGGER.log( Level.FINE, ex.toString(), ex);
        }
        try{
            if (proj == null) {
                getEntityManager().getTransaction().begin();
                getEntityManager().persist(project);
                getEntityManager().getTransaction().commit();
            }
        }catch (Exception ex){
            LOGGER.log( Level.FINE, ex.toString(), ex);
            throw new DataException("Project doesn't exists");
        }finally {
            closeEntityManager();
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
            System.out.println("project: " + project);
        } catch(Exception exception) {
            project = null;
            LOGGER.log( Level.FINE, exception.toString(), exception);
        }finally {
            closeEntityManager();
        }

        if (project == null){
            throw new DataException("Project doesn't exists");
        }

        return project;
    }

    public List<Project> getEntityList() throws DataException {
        String query = "Project.findAll";
        List list = getEntityManager().createNamedQuery(query, Project.class).getResultList();
        closeEntityManager();

        return list;
    }

    public boolean deleteEntity(Project project) throws DataException{
        getEntityManager().getTransaction().begin();
        getEntityManager().remove(getEntityManager().contains(project) ? project : getEntityManager().merge(project));
        getEntityManager().getTransaction().commit();

        closeEntityManager();

        return true;
    }
}
