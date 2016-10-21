package Service;

import Model.User;

import java.util.List;

/**
 * Created by amaia.nazabal on 10/19/16.
 */
public interface UserService {
    boolean addEntity(String pseudo, String mail, String hashkey);
    User getEntityByMail(String mail);
    List getEntityList();
    boolean deleteEntity(String mail);
}
