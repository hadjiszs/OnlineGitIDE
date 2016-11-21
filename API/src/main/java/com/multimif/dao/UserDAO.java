package com.multimif.dao;

import com.multimif.model.User;
import com.multimif.util.DataException;

import java.util.List;

/**
 * Created by amaia.nazabal on 10/20/16.
 */
public interface UserDAO {
        User addEntity(User user) throws DataException;
        User updateEntity(User user) throws DataException;
        User authEntity(String username,String password) throws DataException;
        User getEntityByMail(String mail) throws DataException;
        User getEntityById(Long id) throws DataException;
        List<User> getEntityList() throws DataException;
        boolean deleteEntity(User user) throws DataException;


}
