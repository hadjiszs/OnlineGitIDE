package DAO;

import Model.UserGrant;

import java.util.List;

/**
 * Created by amaia.nazabal on 10/21/16.
 */
public interface UserGrantDAO {
    boolean addEntity(UserGrant user) throws Exception;
    UserGrant getEntityByMail(String mail) throws Exception;
    List getEntityList() throws Exception;
    boolean deleteEntity(String mail) throws Exception;
}
