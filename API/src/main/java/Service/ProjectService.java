package Service;

import Model.Project;
import Util.DataException;

import java.util.List;

/**
 * Created by amaia.nazabal on 10/21/16.
 */
public interface ProjectService {
    Project addEntity(Project project, Long idUser) throws DataException;
    Project getEntityById(Long id) throws DataException;
    List<Project> getEntityList() throws DataException;
    boolean deleteEntity(Long idProject, Long idUser) throws DataException;
}
