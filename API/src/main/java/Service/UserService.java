package main.java.Service;

import main.java.Model.User;

import java.util.List;

/**
 * Created by amaia.nazabal on 10/19/16.
 */
public interface UserService {
    boolean addEntity(String pseudo, String mail, String hashkey);
    User getEntityByMail(String mail);
    List getEntityList();
    boolean deleteEntity(String mail);
    boolean authEntity(String username, String password);
}
