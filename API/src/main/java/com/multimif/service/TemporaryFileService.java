package com.multimif.service;

import com.multimif.model.TemporaryFile;
import com.multimif.util.DataException;

import java.util.List;

/**
 * Created by amaia.nazabal on 11/18/16.
 */
public interface TemporaryFileService {
    TemporaryFile getEntityByHash(String hashKey) throws DataException;
    List getEntityByUserProject (Long idUser, Long idProject) throws DataException;
    TemporaryFile getEntityById (Long idTemporaryFile) throws DataException;
    boolean exists (Long idFileTemporary);
    TemporaryFile addEntity(Long idUser, String content, String path, Long idProject)
            throws DataException;
    boolean deleteEntity(Long idFileTemporary) throws DataException;

    /**
     * Permet de supprimer une liste de TemporaryFile
     * @param list Liste des objets à supprimer
     * @throws Exception
     */
    boolean deleteAllEntity(List<TemporaryFile> list) throws DataException;
}
