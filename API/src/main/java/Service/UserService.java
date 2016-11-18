package Service;

import Model.User;
import Util.DataException;

import javax.xml.crypto.Data;
import java.util.List;

/**
 * Created by amaia.nazabal on 10/19/16.
 */
public interface UserService {
    User addEntity(String pseudo, String mail, String hashkey) throws DataException;
    User getEntityByMail(String mail) throws DataException;
    User getEntityById(Long id) throws DataException;
    boolean deleteEntity(String mail) throws DataException;
    List getEntityList() throws NullPointerException;
    User authEntity(String username,String password) throws Exception;
}
