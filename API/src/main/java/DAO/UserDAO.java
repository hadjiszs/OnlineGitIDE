package DAO;

import Model.User;
import Util.DataException;

import javax.xml.crypto.Data;
import java.util.List;

/**
 * Created by amaia.nazabal on 10/20/16.
 */
public interface UserDAO {
        User addEntity(User user) throws DataException;
        User authEntity(String username,String password) throws DataException;

        User getEntityByMail(String mail) throws DataException;
        User getEntityById(Long id) throws DataException;
        List getEntityList() throws DataException;
        boolean deleteEntity(User user) throws DataException;


}
