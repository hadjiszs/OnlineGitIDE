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

    TemporaryFile getEntityByHashKey(String hashKey) throws DataException;

    List getEntityByUserProject (User user, Project project);

    TemporaryFile getEntityById (Long idTemporaryFile) throws DataException;

    boolean exist (Long idFileTemporary);

    TemporaryFile add (TemporaryFile temporaryFile) throws DataException;

    boolean deleteEntity(Long idFileTemporary) throws DataException;
}
