package com.multimif.service;

import com.multimif.dao.UserGrantDAO;
import com.multimif.dao.UserGrantDAOImpl;
import com.multimif.model.Project;
import com.multimif.model.User;
import com.multimif.model.UserGrant;
import com.multimif.util.DataException;
import com.multimif.util.Messages;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 11/15/16.
 */
public class UserGrantServiceImpl implements UserGrantService{
    private UserService userService = new UserServiceImpl();
    private UserGrantDAO userGrantDAO = new UserGrantDAOImpl();
    private static final Logger LOGGER = Logger.getLogger( UserGrantServiceImpl.class.getName() );

    @Override
    public boolean addEntity(Long idUser, Long idProject, UserGrant.PermissionType type) throws DataException {
        UserGrant grant;

        try {
            grant = getEntityById(idUser, idProject);
        }catch (DataException e ){
            // Doit retourner une exception étant donné que le permis ne doit pas être dans la BD
            LOGGER.log( Level.OFF, e.toString(), e);
            grant = null;
        }

        if (grant == null) {
            ProjectService projectService = new ProjectServiceImpl();

            User user = userService.getEntityById(idUser);
            Project project = projectService.getEntityById(idProject);

            grant = new UserGrant();
            grant.setUser(user);
            grant.setProject(project);
            grant.setPermissionType(type);

            userGrantDAO.addEntity(grant);
        } else {

            throw new DataException(Messages.PERMISSION_ALREADY_EXISTS);
        }

        return true;
    }

    @Override
    public List<Project> getAdminProjects(Long idUser) throws DataException{
        List<Project> projectList = new ArrayList<>();
        User user = userService.getEntityById(idUser);
        List<UserGrant> userGrantList = userGrantDAO.getProjectsByUserByType(user,
                UserGrant.PermissionType.ADMIN);

        userGrantList.forEach(userGrant -> projectList.add(userGrant.getProject()));
        return projectList;
    }

    @Override
    public List<Project> getCollaborationsProjects(Long idUser) throws DataException{
        List<Project> projectList = new ArrayList<>();
        User user = userService.getEntityById(idUser);
        List<UserGrant> userGrantList = userGrantDAO.getProjectsByUserByType(user,
                UserGrant.PermissionType.DEVELOPER);

        userGrantList.forEach(userGrant -> projectList.add(userGrant.getProject()));
        return projectList;
    }

    @Override
    public boolean existsProjectName(Long idUser, String nameProject) {
        List<Project> projectList;
        try {
            projectList = getAllProjectsByEntity(idUser);
        } catch (DataException e) {
            LOGGER.log(Level.OFF, e.getMessage(), e);
            projectList = new ArrayList<>();
        }
        return projectList.stream().filter(p -> p.getName()
                .equals(nameProject)).findFirst().isPresent();

    }

    @Override
    public List<Project> getAllProjectsByEntity(Long id) throws DataException {
        List<Project> projects = new ArrayList<>();
        ProjectService projectService = new ProjectServiceImpl();
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
        return getEntityById(idUser, idProject) != null;
    }

    @Override
    public List<User> getDevelopersByEntity(Long idProject) throws DataException {
        List<User> users = new ArrayList<>();

        List<UserGrant> permissions = userGrantDAO.getDevelopersByEntity(idProject);
        for (UserGrant grant : permissions) {
            users.add(userService.getEntityById(grant.getUser().getIdUser()));
        }

        return users;
    }

    @Override
    public User getAdminByEntity(Long idProject) throws DataException {
        UserGrant userGrant = userGrantDAO.getAdminByEntity(idProject);
        return userGrant.getUser();
    }

    @Override
    public boolean deleteEntity(Long idUser, Long idProject, UserGrant.PermissionType permissionType) throws DataException {
        UserGrant grant = getEntityById(idUser, idProject);

        if (grant != null) {
            if (grant.getPermissionType().equals(permissionType)){
                return userGrantDAO.deleteEntity(grant);
            }else{
                throw new DataException("The permission doesn't correspond with the indicated");
            }
        }else {
            throw new DataException("The permission doesn't exists");
        }
    }
}
