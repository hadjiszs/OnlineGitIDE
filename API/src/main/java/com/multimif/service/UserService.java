package com.multimif.service;

import com.multimif.model.User;
import com.multimif.util.DataException;

import java.util.List;

/**
 * Created by amaia.nazabal on 10/19/16.
 */
public interface UserService {
    User addEntity(String username, String mail, String hashkey) throws DataException;
    User updateEntity(User user) throws DataException;
    User getEntityByMail(String mail) throws DataException;
    User getEntityById(Long id) throws DataException;
    List<User> getEntityList() throws DataException;
    boolean deleteEntity(Long idUser) throws DataException;
    User authEntity(String username,String password) throws DataException;
}
