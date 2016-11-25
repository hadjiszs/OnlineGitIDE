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
import java.util.logging.Level;
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
    ResponseEntity<String> edit(@PathVariable String idRepository,
                                @PathVariable String idCurrentUser,
                                @RequestParam String content,
                                @RequestParam(value="path") String path) {
        try {
            temporaryFileService.updateEntity(Long.valueOf(idCurrentUser), content, path, Long.valueOf(idRepository));
        } catch (DataException e) {
            LOGGER.log(Level.FINE, e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
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
