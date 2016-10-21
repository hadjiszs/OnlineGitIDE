package Service;

import Model.User;

import java.util.List;

/**
 * Created by amaia.nazabal on 10/19/16.
 */
public interface UserService {
    boolean addEntity(String pseudo, String mail) throws Exception;
    User getEntityByMail(String mail) throws Exception;
    List getEntityList() throws Exception;
    boolean deleteEntity(String mail) throws Exception;
}
