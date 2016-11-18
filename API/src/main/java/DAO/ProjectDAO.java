package DAO;

import Model.Project;
import Model.User;
import Util.DataException;

import java.util.List;

/**
 * Created by amaia.nazabal on 10/21/16.
 */
public interface ProjectDAO {
    boolean addEntity(Project project) throws DataException;
    Project getEntityById(Long id) throws DataException;
    List<Project> getEntityList(User user) throws DataException;
    //boolean deleteEntity(Project project) throws DataException;

}
