package Service;

import Model.Project;
import Model.UserGrant;
import Model.User;
import Util.DataException;

import java.util.List;

/**
 * Created by amaia.nazabal on 11/15/16.
 */
public interface UserGrantService {
    boolean addEntity(Long idUser, Long idProject, UserGrant.Permis type) throws DataException;
    List<Project> getProjectsByEntity (Long id) throws DataException;
    List getDevelopersByEntity (Long idProject) throws DataException;
    User getAdminByEntity(Long idProject) throws DataException;
    UserGrant getEntityById(Long idUser, Long idProject) throws DataException;
    boolean deleteEntity(String mail, Long idProject) throws DataException;
    boolean hasPermission(Long idUser, Long idProject) throws DataException;
}
