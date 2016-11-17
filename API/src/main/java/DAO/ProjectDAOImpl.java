package DAO;

import Model.Project;
import Util.DataException;

import java.util.List;

/**
 * Created by amaia.nazabal on 10/21/16.
 */
public class ProjectDAOImpl extends DAO implements ProjectDAO {

    public boolean addEntity(Project project) throws DataException {
        Project proj = null;

        try{
            if (project.getId() != null)
                proj = getEntityById(project.getId());
        }catch (Exception ex){
            proj = null;
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
        }finally {
            closeEntityManager();
        }

        if (null == project){
            throw new DataException("Project doesn't exists");
        }

        return project;
    }

    public List getEntityList() throws DataException {
        String query = "SELECT p FROM Project p ";
        List list = getEntityManager().createQuery(query).getResultList();
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
