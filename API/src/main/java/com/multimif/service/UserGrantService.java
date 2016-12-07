package com.multimif.service;

import com.multimif.model.Project;
import com.multimif.model.UserGrant;
import com.multimif.model.User;
import com.multimif.util.DataException;

import java.util.List;

/**
 *
 * Cette classe rassemble tous les méthodes disponibles sur l'instance d permis.
 *
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 11/15/16.
 */
public interface UserGrantService {

    /**
     *
     * Cette méthode ajoute un nouveau permis d'un utilisateur sur un projet spécifié
     *
     * @param idUser l'id de l'utilisateur
     * @param idProject l'id du projet
     * @param type le type du projet
     * @return true si la transaction a finalisé correctement
     * @throws DataException retourne une exception si le permis existe déjà sur la BD
     */
    boolean addEntity(Long idUser, Long idProject, UserGrant.PermissionType type) throws DataException;

    /**
     *
     * Cette méthode liste tous les projets par utilisateur
     *
     * @param id l'id de l'utilisateur
     * @return la liste de projets associés à l'utilisateur
     * @throws DataException retourne une exception si l'utilisateur n'a pas aucun projet
     * associé
     */
    List<Project> getAllProjectsByEntity(Long id) throws DataException;

    /**
     *
     * Cette méthode liste tous les projets ou l'utilisateur est admin
     *
     * @param idUser l'id de l'utilisateur
     * @return la liste de projets où l'utilisateur indiqué est administrateur
     * @throws DataException retourne une exception si l'utilisateur n'existe pas
     */
    List<Project> getAdminProjects(Long idUser) throws DataException;

    /**
     *
     * Cette méthode liste tous les projets ou l'utilisateur est developpeur
     *
     * @param idUser l'id de l'utilisateur
     * @return la liste de projets où l'utilisateur indiqué est developpeur
     * @throws DataException retourne une exception si l'utilisateur n'existe pas
     */
    List<Project> getCollaborationsProjects(Long idUser) throws DataException;

    /**
     *
     * Cette méthode retourne la liste d'utilisateurs developpeurs du projet indiqué
     *
     * @param idProject l'id du projet
     * @return la liste d'utilisateurs developpeurs du projet indiqué
     * @throws DataException retourne une exception si le projet n'existe pas
     */
    List<User> getDevelopersByEntity (Long idProject) throws DataException;

    /**
     * @param idProject l'id du projet
     * @return l'utilisateur administrateur du projet
     * @throws DataException retourne une exception si le projet n'existe pas
     */
    User getAdminByEntity(Long idProject) throws DataException;

    /**
     * Cette méthode retourne le permis associé aux donnés envoyés
     *
     * @param idUser l'id de l'utilisateur
     * @param idProject l'id du projet
     * @return le permis qui existe entre le projet et l'utilisateur
     * @throws DataException une exception si le projet ou l'utilisateur n'existent pas
     */
    UserGrant getEntityById(Long idUser, Long idProject) throws DataException;

    /**
     *
     * Cette méthode supprime le permis en appelant à la couche DAO
     *
     * @param idUser l'id de l'utilisateur
     * @param idProject l'id du projet
     * @param permissionType le type du permis
     * @return true si la transaction a fini correctament
     * @throws DataException une exception si le projet ou l'utilisateur n'existent pas
     */
    boolean deleteEntity(Long idUser, Long idProject, UserGrant.PermissionType permissionType) throws DataException;

    /**
     *
     * Cette méthode évalue s'il existe un permis
     *
     * @param idUser l'id de l'utilisateur
     * @param idProject l'id du projet
     * @return true s'il existe un permis
     * @throws DataException une exception si le projet ou l'utilisateur n'existent pas
     */
    boolean hasPermission(Long idUser, Long idProject) throws DataException;

    /**
     *
     * Cette méthode retourne s'il existe déjà un projet du même utilisateur avec le même nom
     *
     * @param idUser l'id de l'utilisateur
     * @param nameProject le nom du projet
     * @return true s'il existe un projet avec le même nom avec le même utilisateur
     */
    boolean existsProjectName(Long idUser, String nameProject);
}
