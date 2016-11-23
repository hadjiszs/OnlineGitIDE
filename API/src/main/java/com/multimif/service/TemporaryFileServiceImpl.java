package com.multimif.service;

import com.multimif.dao.TemporaryFileDAO;
import com.multimif.dao.TemporaryFileDAOImpl;
import com.multimif.model.Project;
import com.multimif.model.TemporaryFile;
import com.multimif.model.User;
import com.multimif.util.DataException;

import java.util.List;

/**
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 11/18/16.
 */
public class TemporaryFileServiceImpl implements TemporaryFileService {
    private UserService userService = new UserServiceImpl();
    private ProjectService projectService = new ProjectServiceImpl();
    private TemporaryFileDAO temporaryFileDAO = new TemporaryFileDAOImpl();


    @Override
    public TemporaryFile getEntityByHashAndUser(Long idUser, String hashKey) throws DataException {
        User user = userService.getEntityById(idUser);
        return temporaryFileDAO.getEntityByHashKeyAndUser(user, hashKey);
    }

    @Override
    public List<TemporaryFile> getEntityByUserProject(Long idUser, Long idProject) throws DataException {
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
    public TemporaryFile addEntity(Long idUser, String hashKey, String content, String path,
                                   Long idProject) throws DataException {
        User user = userService.getEntityById(idUser);
        Project project = projectService.getEntityById(idProject);

        TemporaryFile temporaryFile = new TemporaryFile(user, hashKey, content, project, path);
        return temporaryFileDAO.addEntity(temporaryFile);
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
