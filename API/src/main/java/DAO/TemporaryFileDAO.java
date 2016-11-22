package DAO;

import Model.Project;
import Model.TemporaryFile;
import Model.User;
import Util.DataException;

import java.util.List;

/**
 * Created by amaia.nazabal on 11/18/16.
 */
public interface TemporaryFileDAO {
    TemporaryFile getEntityByHashKey(String hashKey) throws DataException;
    List getEntityByUserProject (User user, Project project);
    TemporaryFile getEntityById (Long idTemporaryFile) throws DataException;
    boolean exist (Long idFileTemporary);
    TemporaryFile add (TemporaryFile temporaryFile) throws DataException;
    boolean deleteEntity(Long idFileTemporary) throws DataException;
}
