package com.multimif.service;

import com.multimif.model.Project;
import com.multimif.util.DataException;

import java.util.List;

/**
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 10/21/16.
 */
public interface ProjectService {
    /* TODO modifier parameters.*/
    boolean addEntity(Project project, Long idUser) throws DataException;
    /* TODO modifier parameters.*/
    boolean updateEntity(Project project) throws DataException;
    Project getEntityById(Long id) throws DataException;
    List<Project> getEntityList() throws DataException;
    boolean deleteEntity(Long idProject, Long idUser) throws DataException;
}
