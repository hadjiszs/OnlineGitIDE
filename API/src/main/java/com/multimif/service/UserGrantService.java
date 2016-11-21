package com.multimif.service;

import com.multimif.model.Project;
import com.multimif.model.UserGrant;
import com.multimif.model.User;
import com.multimif.util.DataException;

import java.util.List;

/**
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 11/15/16.
 */
public interface UserGrantService {
    boolean addEntity(Long idUser, Long idProject, UserGrant.PermissionType type) throws DataException;
    List<Project> getAllProjectsByEntity(Long id) throws DataException;
    List<Project> getAdminProjects(Long idUser) throws DataException;
    List<Project> getCollaborationsProjects(Long idUser) throws DataException;
    List<User> getDevelopersByEntity (Long idProject) throws DataException;
    User getAdminByEntity(Long idProject) throws DataException;
    UserGrant getEntityById(Long idUser, Long idProject) throws DataException;
    boolean deleteEntity(Long idUser, Long idProject, UserGrant.PermissionType permissionType) throws DataException;
    boolean hasPermission(Long idUser, Long idProject) throws DataException;
}
