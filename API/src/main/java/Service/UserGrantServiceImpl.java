package Service;

import DAO.UserGrantDAO;
import DAO.UserGrantDAOImpl;
import Model.Project;
import Model.User;
import Model.UserGrant;
import Util.DataException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by amaia.nazabal on 11/15/16.
 */
public class UserGrantServiceImpl implements UserGrantService{
    private UserGrantDAO userGrantDAO = new UserGrantDAOImpl();
    private static final Logger LOGGER = Logger.getLogger( UserGrantServiceImpl.class.getName() );

    public UserGrantServiceImpl(){
    }

    @Override
    public boolean addEntity(Long idUser, Long idProject, UserGrant.Permis type) throws DataException {
        UserGrant grant;

        try {
            grant = getEntityById(idUser, idProject);
        }catch (Exception e ){
            LOGGER.log( Level.FINE, e.toString(), e);
            grant = null;
        }

        if (grant == null) {
            UserService userService = new UserServiceImpl();
            ProjectService projectService = new ProjectServiceImpl();

            User user = userService.getEntityById(idUser);

            Project project = projectService.getEntityById(idProject);

            System.out.println(project);
            grant = new UserGrant();
            grant.setUser(user);
            grant.setProject(project);
            grant.setGran(type);

            userGrantDAO.addEntity(grant);
        }

        return true;
    }

    @Override
    public List<Project> getProjectsByEntity(Long id) throws DataException {
        List<Project> projects = new ArrayList<Project>();
        ProjectService projectService = new ProjectServiceImpl();
        UserService userService = new UserServiceImpl();
        Iterator<UserGrant> iterator;
        User user;

        try{
            user = userService.getEntityById(id);
        }catch(Exception ex) {
            LOGGER.log( Level.FINE, ex.toString(), ex);
            throw new DataException("User doesn't have any project");
        }

        try{
            iterator = userGrantDAO.getProjectsByEntity(user).iterator();
            while (iterator.hasNext()) {
                projects.add(projectService.getEntityById(iterator.next()
                        .getProject().getIdProject()));
            }
        }catch (Exception e){
            LOGGER.log( Level.FINE, e.toString(), e);
        }


        return projects;
    }

    @Override
    public UserGrant getEntityById(Long idUser, Long idProject) throws DataException{
        return userGrantDAO.getEntityById(idUser, idProject);
    }

    @Override
    public boolean hasPermission(Long idUser, Long idProject) throws DataException{
        boolean result = (getEntityById(idUser, idProject) != null);

        return result;
    }

    @Override
    public List getDevelopersByEntity(Long idProject) throws DataException {
        List<User> users = new ArrayList();
        UserService userService = new UserServiceImpl();

        List<UserGrant> permissions = userGrantDAO.getDevelopersByEntity(idProject);
        Iterator<UserGrant> iterator = permissions.iterator();

        while (iterator.hasNext()) {
            users.add(userService.getEntityById(iterator.next().getUser().getIdUser()));
        }

        return users;
    }

    @Override
    public User getAdminByEntity(Long idProject) throws DataException {
        UserGrant userGrant = userGrantDAO.getAdminByEntity(idProject);
        return userGrant.getUser();
    }

    @Override
    public boolean deleteEntity(Long idUser, Long idProject, UserGrant.Permis permis) throws DataException {
        UserGrant grant = getEntityById(idUser, idProject);

        if (grant != null) {
            if (grant.getGran().equals(permis)){
                return userGrantDAO.deleteEntity(grant);
            }else{
                throw new DataException("The permission doesn't correspond with the indicated");
            }
        }else {
            throw new DataException("The permission doesn't exists");
        }
    }
}
