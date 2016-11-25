package com.multimif.service;

import com.multimif.model.TemporaryFile;
import com.multimif.util.DataException;

import java.util.List;

/**
 *
 * Cette classe rassemble tous les méthodes disponibles sur l'instance du fichier temporel.
 *
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 11/18/16.
 */
public interface TemporaryFileService {

    /**
     *
     * Retourne l'entité du fichier temporel selon l'id et le hashkey spécifiés
     *
     * @param idUser l'id de l'utilisateur
     * @param hashKey le hashKey du fichier
     * @return le fichier temporel qui corresponde avec l'id et le hashkey.
     * @throws DataException retourne une exception si le fichier n'existe pas
     */
    TemporaryFile getEntityByHashAndUser(Long idUser, String hashKey) throws DataException;

    /**
     *
     * Retourne la liste de fichiers temporels
     *
     * @param idUser l'id de l'utilisateur
     * @param idProject l'id du projet
     * @return la liste de fichiers temporels de cet utilisateur et ce projet
     * @throws DataException retourne une exception si l'utilisateur ou le projet n'existe pas
     */
    List<TemporaryFile> getEntityByUserProject(Long idUser, Long idProject) throws DataException;

    /**
     *
     * Retourne l'entité du fichier temporel selon l'id envoyé.
     *
     * @param idTemporaryFile l'id du fichier temporel
     * @return l'entité du fichier temporel
     * @throws DataException retourne une exception si le fichier n'existe pas dans la basse de
     * donées
     */
    TemporaryFile getEntityById(Long idTemporaryFile) throws DataException;

    /**
     *
     * Retourne true si le fichier temporel existe dans la base de données.
     *
     * @param idFileTemporary l'id du fichier temporel
     * @return true si le fichier existe dans la base de données.
     */
    boolean exists(Long idFileTemporary);

    /**
     *
     * Cette méthode crée un nouveau fichier temporel et appel la couche dao pour l'enregistrer
     * dans la base de données
     *
     * @param idUser l'id de l'utilisateur
     * @param hashKey le hashkey du fichier
     * @param content le contenu du fichier
     * @param path l'address du fichier
     * @param idProject l'id du projet
     * @return l'entité du fichier temporel
     * @throws DataException si l'utilisateur ou le projet n'existe pas dans la base de
     * données.
     */
    TemporaryFile addEntity(Long idUser, String hashKey, String content, String path,
                            Long idProject) throws DataException;

    /**
     *
     * Cette méthode supprime le registrement du fichier temporel dans la base de données.
     *
     * @param idFileTemporary l'id du fichier temporel
     * @return true si la transaction a fini correctement
     * @throws DataException retourne une exception si le fichier n'existe pas dans la
     * base de données
     */
    boolean deleteEntity(Long idFileTemporary) throws DataException;

    /**
     * Permet de supprimer une liste de TemporaryFile
     * @param list Liste des objets à supprimer
     * @throws Exception
     */
    boolean deleteAllEntity(List<TemporaryFile> list) throws DataException;
}
