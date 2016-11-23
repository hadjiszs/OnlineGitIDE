package com.multimif.service;

import com.multimif.dao.TemporaryFileDAO;
import com.multimif.dao.TemporaryFileDAOImpl;
import com.multimif.model.Project;
import com.multimif.model.TemporaryFile;
import com.multimif.model.User;
import com.multimif.util.DataException;

import java.util.List;

/**
 * Created by amaia.nazabal on 11/18/16.
 */
public class TemporaryFileServiceImpl implements TemporaryFileService {
    UserService userService = new UserServiceImpl();
    ProjectService projectService = new ProjectServiceImpl();

    TemporaryFileDAO temporaryFileDAO = new TemporaryFileDAOImpl();


    @Override
    public TemporaryFile getEntityByHash(String hashKey) throws DataException {
        return temporaryFileDAO.getEntityByHashKey(hashKey);
    }

    @Override
    public List getEntityByUserProject(Long idUser, Long idProject) throws DataException {
        User user;
        Project project;

        user = userService.getEntityById(idUser);
        project = projectService.getEntityById(idProject);

        return temporaryFileDAO.getEntityByUserProject(user, project);
    }

    @Override
    public TemporaryFile getEntityById(Long idTemporaryFile) throws DataException {
        return temporaryFileDAO.getEntityById(idTemporaryFile);
    }

    @Override
    public boolean exists(Long idFileTemporary) {
        return temporaryFileDAO.exist(idFileTemporary);
    }

    @Override
    public TemporaryFile addEntity(Long idUser, String content, String path, Long idProject) throws DataException {
        User user = userService.getEntityById(idUser);
        Project project = projectService.getEntityById(idProject);

        TemporaryFile temporaryFile = new TemporaryFile(user, content, project, path);
        return temporaryFileDAO.add(temporaryFile);
    }

    @Override
    public boolean deleteEntity(Long idFileTemporary) throws DataException {
        return temporaryFileDAO.deleteEntity(idFileTemporary);
    }

    @Override
    public boolean deleteAllEntity(List<TemporaryFile> list) throws DataException {
        boolean test = true;
        for (TemporaryFile tempFile : list) {
            test = test && deleteEntity(tempFile.getId());
        }

        return test;
    }

}
