package Service;

import Model.User;
import Model.UserGrant;
import Util.DataException;

import java.util.List;

/**
 * Created by amaia.nazabal on 11/15/16.
 */
public interface UserGrantService {
    boolean addEntity(Long idUser, Long idProject, UserGrant.Permis type) throws DataException;
    //List getProjectsByEntity (String mail) throws DataException;
    //List getDevelopersByEntity (Long idProject) throws DataException;
    //User getAdminByEntity(Long idProject) throws DataException;
   // UserGrant getEntityById(Long idUser, Long idProject) throws DataException;
    //boolean deleteEntity(String mail, Long idProject) throws DataException;
    //boolean hasPermission(Long idUser, Long idProject) throws DataException;
}
