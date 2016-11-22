package com.multimif.service;

import com.multimif.model.Project;
import com.multimif.util.DataException;

import java.util.List;

/**
 * Cette classe rassemble tous les méthodes disponibles sur l'instance projet.
 *
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 10/21/16.
 */
public interface ProjectService {

    /**
     * Cette méthode fait certains validationes et après appel à la
     * couche du DAO pour registrer le projet
     *
     * @param name    le nom du projet
     * @param version la version du projet
     * @param type    le type du projet
     * @param root    l'addresse du projet
     * @param idUser  l'utilisateur proprietaire du projet
     * @return le projet si la transaction a fini correctement
     * @throws Exception une exception si le projet déjà existe.
     */
    Project addEntity(String name, String version, Project.TypeProject type,
                      String root, Long idUser) throws Exception;

    /**
     * Cette méthode fait certains validationes et après appel à la
     * couche du DAO pour mis à jour le projet
     *
     * @param project l'entité du projet
     * @return true si la transaction a fini correctement
     * @throws DataException si le projet n'existe pas encore
     */
    boolean updateEntity(Project project) throws DataException;

    /**
     * Cette méthode fait certains validationes et après appel à la
     * couche du DAO pour retourner le projet qui correspond à l'id
     * envoyé
     *
     * @param id l'id du projet
     * @return l'entite de projet
     * @throws DataException si le projet n'existe pas.
     */
    Project getEntityById(Long id) throws DataException;

    /**
     * Cette méthode fait certains validationes et après appel à la
     * couche du DAO pour retourner la liste de projets
     *
     * @return la liste complete de projets
     */
    List<Project> getEntityList();

    /**
     * Cette méthode fait certains validationes et après appel à la
     * couche du DAO pour supprimer le projet.
     *
     * @param idProject l'id du projet
     * @param idUser    l'id de l'utilisateur
     * @return true si la transaction a fini correctement.
     * @throws DataException retourne une exception si l'utilisateur qui
     *                       veut supprimer le projet n'est pas l'administrateur
     */
    boolean deleteEntity(Long idProject, Long idUser) throws DataException;
}
