package Service;

import Model.User;

import java.util.List;

/**
 * Created by amaia.nazabal on 10/19/16.
 */
public interface UserService {
    boolean addEntity(String pseudo, String mail, String hashkey) throws RuntimeException;
    User getEntityByMail(String mail) throws RuntimeException;
    List getEntityList() throws RuntimeException;
    boolean deleteEntity(String mail) throws RuntimeException;
}
