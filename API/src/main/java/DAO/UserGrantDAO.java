package DAO;

import Model.Project;
import Model.User;
import Model.UserGrant;
import Util.DataException;

import javax.jws.soap.SOAPBinding;
import java.util.List;

/**
 * Created by amaia.nazabal on 10/21/16.
 */
public interface UserGrantDAO {
    boolean addEntity(UserGrant grant) throws DataException;
    List<UserGrant> getProjectsByEntity (User user) throws DataException;
    List getDevelopersByEntity(Long idProject) throws DataException;
    UserGrant getAdminByEntity(Long idProject) throws DataException;
    UserGrant getEntityById(Long idUser, Long idProject) throws DataException;
    boolean deleteEntity(UserGrant grant) throws DataException;
}
