package DAO;

import Model.User;
import Util.DataException;

import java.util.List;

/**
 * Created by amaia.nazabal on 10/20/16.
 */
public interface UserDAO {
        Long addEntity(User user) throws DataException;
        User getEntityByMail(String mail) throws DataException;
        List getEntityList() throws Exception;
        boolean deleteEntity(String mail) throws Exception;
}
