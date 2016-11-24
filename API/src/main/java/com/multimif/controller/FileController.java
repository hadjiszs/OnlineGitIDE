package com.multimif.controller;

import com.multimif.model.Project;
import com.multimif.model.TemporaryFile;
import com.multimif.model.User;
import com.multimif.service.*;
import com.multimif.util.Constantes;
import com.multimif.util.DataException;
import com.multimif.util.JsonUtil;
import com.multimif.util.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.logging.Logger;

/**
 * Created by hadjiszs on 21/10/16.
 */
@RestController
@RequestMapping("/file/{idCurrentUser}/{idUser}/{idRepository}/{branch}")
public class FileController {

    /**
     * Service de fichier temporaire
     */
    TemporaryFileService temporaryFileService;

    /*
     * Service de gestion de droit
     */
    UserService userService;

    /**
     * Service de gestion de droit
     */
    ProjectService projectService;

    private static final Logger LOGGER = Logger.getLogger(FileController.class.getName());

    @RequestMapping(value = "/edit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<String> edit(@PathVariable String idUser,
                                @PathVariable String idRepository,
                                @PathVariable String idCurrentUser,
                                @RequestParam String content,
                                @PathVariable String branch,
                                @RequestParam(value="path") String path) {
        Long longIdRepository = Long.valueOf(idRepository);
        Long longIdCurrentUser = Long.valueOf(idCurrentUser);
        User user = null;
        Project project = null;

        try {
            user = userService.getEntityById(longIdCurrentUser);
            project = projectService.getEntityById(longIdRepository);
        } catch (DataException e) {
            e.printStackTrace();
            return new ResponseEntity<>(JsonUtil.convertToJson(new Status(Constantes.OPERATION_CODE_RATE,
                    Constantes.OPERATION_MSG_RATE)), HttpStatus.ACCEPTED);
        }

        TemporaryFile newTempFile, oldTempFile;
        newTempFile = new TemporaryFile(user, content, project, path);

        // recuperation du TemporaryFile correspondant éventuellement déjà présent dans la table
        try {
            oldTempFile = temporaryFileService.getEntityByHash(newTempFile.getHashKey());
        } catch (DataException e) {
            e.printStackTrace();
            return new ResponseEntity<>(JsonUtil.convertToJson(new Status(Constantes.OPERATION_CODE_RATE,
                    Constantes.OPERATION_MSG_RATE)), HttpStatus.ACCEPTED);
        }

        // si le fichier n'existe pas encore dans la table TemporaryFile
        if(oldTempFile == null) {
            try {
                // on l'ajoute avec le contenu temporaire
                temporaryFileService.addEntity(longIdCurrentUser, content, path, longIdRepository);
            } catch (DataException e) {
                e.printStackTrace();
                e.printStackTrace();
                return new ResponseEntity<>(JsonUtil.convertToJson(new Status(Constantes.OPERATION_CODE_RATE,
                        Constantes.OPERATION_MSG_RATE)), HttpStatus.ACCEPTED);
            }
        } else {
            // sinon, on le modifie
            // TODO: updateEntity temporaryFile
        }

        return new ResponseEntity<>(JsonUtil.convertToJson(new Status(Constantes.OPERATION_CODE_REUSSI,
                Constantes.OPERATION_MSG_REUSSI)), HttpStatus.ACCEPTED);
    }

    @PostConstruct
    public void init() {
        temporaryFileService = new TemporaryFileServiceImpl();
        userService = new UserServiceImpl();
    }
}
