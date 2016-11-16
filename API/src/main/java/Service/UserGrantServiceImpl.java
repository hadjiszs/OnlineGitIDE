package Service;

import DAO.UserGrantDAO;
import DAO.UserGrantDAOImpl;
import Model.Project;
import Model.User;
import Model.UserGrant;
import Util.DataException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by amaia.nazabal on 11/15/16.
 */
public class UserGrantServiceImpl implements UserGrantService{
    private UserGrantDAO userGrantDAO = new UserGrantDAOImpl();

    public UserGrantServiceImpl(){
    }

    public boolean addEntity(Long idUser, Long idProject, UserGrant.Permis type) throws DataException {
        UserGrant grant;

        grant = getEntityById(idUser, idProject);

        if (grant == null) {
            UserService userService = new UserServiceImpl();
            ProjectService projectService = new ProjectServiceImpl();

            User user = userService.getEntityById(idUser);
            Project project = projectService.getEntityById(idProject);

            grant = new UserGrant();
            grant.setUser(user);
            grant.setProject(project);
            grant.setGran(type);

            userGrantDAO.addEntity(grant);
        }

        return true;
    }

    public List getProjectsByEntity(String mail) throws DataException {
        List<Project> projects = new ArrayList();
        ProjectService projectService = new ProjectServiceImpl();
        UserService userService = new UserServiceImpl();
        Iterator<UserGrant> iterator;
        User user;

        try{
            user = userService.getEntityByMail(mail);
        }catch(Exception ex) {
            throw new DataException("User doesn't have any project");
        }

        try{
            iterator = userGrantDAO.getProjectsByEntity(user).iterator();
            while (iterator.hasNext()) {
                projects.add(projectService.getEntityById(iterator.next()
                        .getProject().getId()));
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        return projects;
    }

    public UserGrant getEntityById(Long idUser, Long idProject) throws DataException{
        return userGrantDAO.getEntityById(idUser, idProject);
    }

    public boolean hasPermission(Long idUser, Long idProject) throws DataException{
        boolean result = (getEntityById(idUser, idProject) != null);

        return result;
    }

    public List getDevelopersByEntity(Long idProject) throws DataException {
        List<User> users = new ArrayList();
        UserService userService = new UserServiceImpl();

        List<UserGrant> permissions = userGrantDAO.getDevelopersByEntity(idProject);
        Iterator<UserGrant> iterator = permissions.iterator();

        while (iterator.hasNext()) {
            users.add(userService.getEntityById(iterator.next().getUser().getId()));
        }

        return users;
    }

    public User getAdminByEntity(Long idProject) throws DataException {
        UserGrant userGrant = userGrantDAO.getAdminByEntity(idProject);
        return userGrant.getUser();
    }

    public boolean deleteEntity(String mail, Long idProject) throws DataException {
        UserService userService = new UserServiceImpl();
        ProjectService projectService = new ProjectServiceImpl();
        UserGrant grant = new UserGrant();
        User user = userService.getEntityByMail(mail);
        Project project = projectService.getEntityById(idProject);

        if (user != null && project != null) {
            grant.setUser(user);
            grant.setProject(project);
            return userGrantDAO.deleteEntity(grant);
        }

        return false;
    }
}
