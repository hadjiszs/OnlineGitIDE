package com.multimif.dao;

import com.multimif.model.Project;
import com.multimif.util.DataException;

import java.util.List;

/**
 * Created by amaia.nazabal on 10/21/16.
 */
public interface ProjectDAO {
    boolean addEntity(Project project) throws DataException;
    boolean updateEntity(Project project) throws DataException;
    Project getEntityById(Long id) throws DataException;
    List<Project> getEntityList() throws DataException;
    boolean deleteEntity(Project project) throws DataException;
}
