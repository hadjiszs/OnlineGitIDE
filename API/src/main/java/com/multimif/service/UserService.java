package com.multimif.service;

import com.multimif.model.User;
import com.multimif.util.DataException;

import java.util.List;

/**
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 10/19/16.
 */
public interface UserService {

    /**
     * @param username le pseudo de l'utilisateur
     * @param mail le mail de l'utilisateur
     * @param password le mot de passe de l'utilisateur
     * @return l'entité de l'utilisateur avec l'id
     * @throws DataException s'il existe déjà un utilisateur avec le même mail ou pseudo
     */
    User addEntity(String username, String mail, String password) throws DataException;

    /**
     * @param user l'entité de l'utilisateur
     * @return true si la transaction a fini correctement
     * @throws DataException si l'utilisateur n'exist pas dans la base de données
     */
    boolean updateEntity(User user) throws DataException;

    /**
     * @param mail le mail de l'utilisateur
     * @return l'entité de l'utilisateur
     * @throws DataException si l'utilisateur avec ce mail n'existe pas
     */
    User getEntityByMail(String mail) throws DataException;

    /**
     * @param idUser l'id de l'utilisateur
     * @return l'entité de l'utilisateur
     * @throws DataException si l'utilisateur avec cet id n'existe pas
     */
    User getEntityById(Long idUser) throws DataException;

    /**
     * @return list d'utilisateurs dans la base de données
     */
    List<User> getEntityList();

    /**
     * @param idUser l'id de l'utilisateur
     * @return true si la transaction a fini correctement
     * @throws DataException si l'utilisateur n'existe pas
     */
    boolean deleteEntity(Long idUser) throws DataException;

    /**
     * @param username le pseudo de l'utilisateur
     * @param password le mot de passe de l'utilisateur
     * @return l'entité de l'utilisateur
     * @throws DataException une exception si le password envoyé ne correspond pas
     * avec lequel qui est dans la base de données
     */
    User authEntity(String username, String password, Boolean hash) throws DataException;
}
