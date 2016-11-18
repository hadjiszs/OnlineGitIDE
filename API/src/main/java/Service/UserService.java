package Service;

import Model.User;
import Util.DataException;

import java.util.List;

/**
 * Created by amaia.nazabal on 10/19/16.
 */
public interface UserService {
    User addEntity(String pseudo, String mail, String hashkey) throws DataException;
    User getEntityByMail(String mail) throws DataException;
    User getEntityById(Long id) throws DataException;
    List getEntityList() throws Exception;
    boolean deleteEntity(Long idUser) throws Exception;
    User authEntity(String username,String password) throws Exception;
}
