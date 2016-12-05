package com.multimif.service;

import com.multimif.dao.TemporaryFileDAO;
import com.multimif.dao.TemporaryFileDAOImpl;
import com.multimif.model.Project;
import com.multimif.model.TemporaryFile;
import com.multimif.model.User;
import com.multimif.util.DataException;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 11/18/16.
 */
public class TemporaryFileServiceImpl implements TemporaryFileService {
    private static final Logger LOGGER = Logger.getLogger( TemporaryFileServiceImpl.class.getName() );

    private UserService userService = new UserServiceImpl();
    private ProjectService projectService = new ProjectServiceImpl();
    private TemporaryFileDAO temporaryFileDAO = new TemporaryFileDAOImpl();

    @Override
    public TemporaryFile getEntityByHash(String hashKey) throws DataException {
        return temporaryFileDAO.getEntityByHashKey(hashKey);
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
    public TemporaryFile addEntity(Long idUser, String content, String path,
                                   Long idProject) throws DataException {
        User user = userService.getEntityById(idUser);
        Project project = projectService.getEntityById(idProject);

        TemporaryFile temporaryFile = new TemporaryFile(user, content, project, path);
        return temporaryFileDAO.addEntity(temporaryFile);
    }

    @Override
    public TemporaryFile updateEntity(Long idUser, String content, String path,
                                      Long idProject) throws DataException
    {
        User user = userService.getEntityById(idUser);
        Project project = projectService.getEntityById(idProject);
        TemporaryFile oldTempFile = null;
        TemporaryFile newTempFile = new TemporaryFile(user, content, project, path);

        try {
            oldTempFile = getEntityByHash(newTempFile.getHashKey());
        }catch (DataException e){
            LOGGER.log(Level.FINE, e.getMessage(), e);
        }


        if(oldTempFile == null){
            newTempFile = temporaryFileDAO.addEntity(newTempFile);
        }else{
            oldTempFile.setContent(content);
            newTempFile = temporaryFileDAO.updateEntity(oldTempFile);
        }

        return newTempFile;
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
