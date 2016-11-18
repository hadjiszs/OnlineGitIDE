package DAO;

import Model.Project;
import Model.User;
import Util.DataException;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by amaia.nazabal on 10/21/16.
 */
public class ProjectDAOImpl extends DAO implements ProjectDAO {
    private static final Logger LOGGER = Logger.getLogger( ProjectDAOImpl.class.getName() );


    public Boolean addEntity(Project project) throws DataException{
        Project proj = null;

        System.out.println("salut");
        System.out.println("pr: " + project.getId());
        try{
            /*proj = getEntityById(project.getId());
            System.out.println("proj: "+ proj);
            if(proj == null){
                throw new Exception("Project already exists");
            }else{*/
                getEntityManager().getTransaction().begin();
                getEntityManager().persist(project);
                getEntityManager().getTransaction().commit();
            return true;
            //}

        }catch (Exception ex){
            LOGGER.log( Level.FINE, ex.toString(), ex);
            throw new DataException("Project doesn't exists");
        }finally {
            closeEntityManager();
        }

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
            closeEntityManager();
            LOGGER.log( Level.FINE, exception.toString(), exception);
            throw new DataException("Project doesn't exists");
        }
        closeEntityManager();
        return project;
    }

    public List<Project> getEntityList(User user) throws DataException {
        String query = "SELECT p FROM Project p ";
        List list = getEntityManager().createQuery(query).getResultList();
        closeEntityManager();
        return list;
    }
/*
    public boolean deleteEntity(Project project) throws DataException{
        getEntityManager().getTransaction().begin();
        getEntityManager().remove(getEntityManager().contains(project) ? project : getEntityManager().merge(project));
        getEntityManager().getTransaction().commit();

        //closeEntityManager();

        return true;
    }*/
}
