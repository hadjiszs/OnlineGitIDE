package Service;

import Model.Project;
import Util.DataException;

import java.util.List;

/**
 * Created by amaia.nazabal on 10/21/16.
 */
public interface ProjectService {
    boolean addEntity(Project project) throws DataException;
    Project getEntityById(Long id) throws DataException;
    List getEntityList() throws DataException;
    boolean deleteEntity(Long idProject, Long idUser) throws DataException;
}
