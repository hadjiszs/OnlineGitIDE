package com.multimif.dao;

import com.multimif.model.User;
import com.multimif.model.UserGrant;
import com.multimif.util.DataException;

import java.util.List;

/**
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 10/21/16.
 */
public interface UserGrantDAO {
    boolean addEntity(UserGrant grant) throws DataException;
    List<UserGrant> getProjectsByEntity (User user) throws DataException;
    List<UserGrant> getDevelopersByEntity(Long idProject) throws DataException;
    List<UserGrant> getProjectsByUserByType(User user, UserGrant.PermissionType type);
    UserGrant getAdminByEntity(Long idProject) throws DataException;
    UserGrant getEntityById(Long idUser, Long idProject) throws DataException;
    boolean deleteEntity(UserGrant grant) throws DataException;
}
