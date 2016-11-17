package DAO;

import Model.UserGrant;
import Util.DataException;

import java.util.List;

/**
 * Created by amaia.nazabal on 10/21/16.
 */
public interface UserGrantDAO {
    boolean addEntity(UserGrant user) throws DataException;
    UserGrant getEntityByMail(String mail) throws DataException;
    List getEntityList() throws NullPointerException;
    boolean deleteEntity(String mail) throws DataException;
}
