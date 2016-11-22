package com.multimif.dao;

import com.multimif.model.User;
import com.multimif.model.UserGrant;
import com.multimif.util.DataException;

import java.util.List;

/**
 *
 * Cette classe rassemble tous les méthodes disponibles sur l'instance de UserGrant.
 *
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 10/21/16.
 */
public interface UserGrantDAO {

    /**
     *
     * Cette méthode ajout un nouveau registrement dans la table de UserGrant.
     *
     * @param grant l'entité de UserGrant
     * @return true si la transaction a fini correctement
     * @throws DataException
     */
    boolean addEntity(UserGrant grant);

    /**
     *
     * Cette méthode retourne la liste de tous les relations avec
     * les projets et un utilisateur donné.
     *
     * @param user l'entité de User
     * @return la liste de permis associés à l'utilisateur
     * @throws DataException
     */
    List<UserGrant> getProjectsByEntity (User user);

    /**
     *
     * Cette méthode retourne la liste de tous les relations
     * d'utilisateurs du type developer avec un projet
     *
     * @param idProject l'id du projet
     * @return une liste de permis
     * @throws DataException
     */
    List<UserGrant> getDevelopersByEntity(Long idProject) throws DataException;

    /**
     *
     * Cette méthode retourne la liste de tous les relations d'un certain type
     * avec les projets et un utilisateur donné.
     *
     * @param user l'entité de l'utilisateur
     * @param type le type de relation, soit Admin du projet ou developer
     * @return une liste de projets
     */
    List<UserGrant> getProjectsByUserByType(User user, UserGrant.PermissionType type);

    /**
     *
     * Cette méthode retourne l'administrateur d'un projet specifié
     *
     * @param idProject l'id du projet
     * @return le permis avec l'utilisateur qui es l'administrateur du projet
     * @throws  DataException retourne une exception s'il n'y a pas de
     * administrateur du projet
     */
    UserGrant getAdminByEntity(Long idProject) throws DataException;

    /**
     *
     * Cette méthode retourne le permis associé entre un utilisateur et
     * un projet
     *
     * @param idUser l'id de l'utilisateur
     * @param idProject l'id du projet
     * @return le permis entre l'utilisateur et le projet
     * @throws DataException retourne une exception si le permis n'existe pas
     */
    UserGrant getEntityById(Long idUser, Long idProject) throws DataException;

    /**
     *
     * Cette methode supprime le permis donné
     *
     * @param grant l'entité du permis
     * @return true si la transaction a fini correctement
     */
    boolean deleteEntity(UserGrant grant);
}
