package com.multimif.dao;

import com.multimif.model.User;
import com.multimif.util.DataException;

import java.util.List;

/**
 * Cette classe rassemble tous les méthodes disponibles sur l'instance de User.
 *
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 10/20/16.
 */
public interface UserDAO {

    /**
     *
     * Cette méthode ajout un nouveau registrement dans la table de User.
     *
     * @param user l'entité de User
     * @return le même object avec son identificateur
     * @throws DataException retourne une exception si l'utilisateur existe déjà dans
     * la base de données.
     */
    User addEntity(User user) throws DataException;

    /**
     *
     * Cette méthode mis à jour le registre de User
     *
     * @param user l'entité de l'utilisateur
     * @return true si la transaction a fini correctement
     * @throws DataException retourne une exception si le registre n'existe pas dans
     * la base de données.
     */
    boolean updateEntity(User user) throws DataException;


    /**
     *
     * Cette méthode
     *
     * @param username le pseudo de l'utilisateur
     * @param password le mot de passe de l'utilisateur
     * @return l'entité User s'il trouve une utilisateur avec le pseudo et le
     * mot de passe indiqué
     * @throws DataException retourne une exception si l'utilisateur n'existe
     * pas ou si le mot de passe n'est pas correct.
     */
    User authEntity(String username, String password) throws DataException;

    /**
     *
     * Cette méthode retourne l'entité de User selon le mail spécifié
     *
     * @param mail le mail de l'utilisateur
     * @return l'entité de l'utilisateur
     * @throws DataException retourne une exception si l'utilisateur n'existe pas
     */
    User getEntityByMail(String mail) throws DataException;

    /**
     *
     * Cette méthode retourne l'entité de User selon l'id envoyé
     *
     * @param id l'id de l'utilisateur
     * @return l'entité user
     * @throws DataException retourne une exception s'il n'y a pas d'utilisateur
     * qui correspond avec l'id envoyé
     */
    User getEntityById(Long id) throws DataException;

    /**
     *
     * Cette méthode retourne la liste d'utilisateurs qui sont dans la basse de données
     * @return liste d'utilisateurs
     */
    List<User> getEntityList();

    /**
     * Cette méthode supprime le registre de la table User dans la base de données
     *
     * @param user l'entité de User
     * @return true si la transaction a fini correctement
     * @throws DataException retourne une exception si l'utilisateurs n'existe pas
     */
    boolean deleteEntity(User user) throws DataException;
}
