package com.multimif.dao;

import com.multimif.model.Project;
import com.multimif.util.DataException;

import java.util.List;

/**
 *
 * Cette classe rassemble tous les méthodes disponibles sur l'instance projet.
 *
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 10/21/16.
 */
public interface ProjectDAO {
    /**
     *
     * Cette méthode insère un nouvel enregistrement sur la table de projets
     * en vérifiant qui le projet n'existe pas encore dans la BD.
     *
     * @param project l'entité du projet
     * @return true ou false selon l'état de la transaction
     * @throws DataException retourne une exception si le projet déjà exist.
     */
    boolean addEntity(Project project) throws DataException;

    /**
     *
     * Cette méthode met à jour l'entité du projet qui est envoyé sauf pour les
     * attributes de creationDate et lastModification qui sont définis ici
     *
     * @param project l'entité du projet
     * @return true ou false selon l'état de la transaction
     * @throws DataException retourne une exception si le projet n'existe pas encore
     * ou l'id n'est pas spécifié.
     */
    boolean updateEntity(Project project) throws DataException;

    /**
     *
     * Cette méthode retourne l'entité du projet selon l'identificateur envoyé.
     *
     * @param id l'id du projet
     * @return l'entité du projet
     * @throws DataException retourne une exception si le projet n'existe pas.
     */
    Project getEntityById(Long id) throws DataException;

    /**
     *
     * Cette méthode retourne la liste de tous les projets gardés dans la base de données
     *
     * @return list de tous les projets
     */
    List<Project> getEntityList();

    /**
     * Cette méthode supprime l'entité du projet de la base de données.
     *
     * @param project l'entité du projet
     * @return true ou false selon l'état de la transaction
     */
    boolean deleteEntity(Project project);
}
