package DAO;

import Model.Project;
import Model.User;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by amaia.nazabal on 10/21/16.
 */
public class ProjectDAOImpl extends DAO implements ProjectDAO {

    public ProjectDAOImpl(EntityManager em) {
        super(em);
    }

    public boolean addEntity(Project project) throws Exception{
        Project proj;

        try{
            proj = getEntityById(project.getId());
        }catch (Exception ex){
            proj = null;
        }

        if (null == proj){
            em.getTransaction().begin();
            em.persist(project);
            em.getTransaction().commit();
        }else {

            throw new Exception("Project already exists");
        }

        return false;
    }

    public Project getEntityById(Long id) throws Exception{
        Project project = null;

        try {
            project = em.find(Project.class, id);
        } catch(IllegalArgumentException exception) {
            project = null;
        }

        if (null == project){
            throw new Exception("Project doesn't exists");
        }

        return project;
    }

    public List getEntityList(User user) throws Exception {
        String query = "SELECT p FROM Project p ";
        return em.createQuery(query).getResultList();
    }

    public boolean deleteEntity(Long id) throws Exception{
        Project project = getEntityById(id);
        em.getTransaction().begin();
        em.remove(project);
        em.getTransaction().commit();

        return true;
    }
}
