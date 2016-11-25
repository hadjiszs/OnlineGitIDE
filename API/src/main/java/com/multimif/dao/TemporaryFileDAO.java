package com.multimif.dao;

import com.multimif.model.Project;
import com.multimif.model.TemporaryFile;
import com.multimif.model.User;
import com.multimif.util.DataException;

import java.util.List;

/**
 *
 * Cette classe rassemble tous les méthodes disponibles sur l'instance de temporary file.
 *
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 11/18/16.
 */
public interface TemporaryFileDAO {

    /**
     * Cette méthode cherche l'entité de TemporaryFile selon le hashKey indiqueé
     *
     * @param hashKey la clé du temporary file
     * @return l'entité de temporary file.
     * @throws DataException retourne une exception si l'entité n'existe pas
     */
    TemporaryFile getEntityByHashKey(String hashKey) throws DataException;

    /**
     *
     * Cette méthode retourne la liste de fichiers temporels pour utilisateur et projet
     *
     * @param user l'entité de l'utilisateur
     * @param project l'entité du projet
     * @return liste de l'entité de temporary files
     */
    List<TemporaryFile> getEntityByUserProject (User user, Project project);

    /**
     *
     * Cette méthode retourne l'entité de TemporaryFile selon l'id indiqué.
     *
     * @param idTemporaryFile l'id de l'entité
     * @return l'entité de TemporaryFile.
     */
    TemporaryFile getEntityById (Long idTemporaryFile);

    /**
     * Cette méthode retourne true ou false si le registre existe dans la base de données.
     *
     * @param idFileTemporary l'id du fichier temporel
     * @return true ou false selon l'état de la transaction
     */
    boolean exist (Long idFileTemporary);

    /**
     *
     * Cette méthode ajoute un registre dans la table de TemporaryFile.
     *
     * @param temporaryFile l'entité de Temporary File
     * @return le même objet avec l'identificateur
     * @throws DataException retourne une exception si le temporary file existe dans la base
     * de données.
     */
    TemporaryFile addEntity(TemporaryFile temporaryFile) throws DataException;


    /**
     *
     * Cette méthode mis à jour le registre dans la table TemporaryFile
     *
     * @param temporaryFile l'entité
     * @return true si la transaction a fini correctement
     * @throws DataException retourne une exception s'il n'y a pas aucun registre avec
     * l'id envoyé.
     */
    boolean updateEntity(TemporaryFile temporaryFile) throws DataException;

    /**
     *
     * Cette méthode supprime le registrement du TemporaryFile
     *
     * @param idFileTemporary l'id de temporary file
     * @return true ou false selon l'état de la transaction
     * @throws DataException retourne une exception si le registre n'existe pas dans
     * la base de données.
     */
    boolean deleteEntity(Long idFileTemporary) throws DataException;
}
