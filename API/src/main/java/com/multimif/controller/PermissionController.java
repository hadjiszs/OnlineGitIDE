package com.multimif.controller;

import com.multimif.model.Project;
import com.multimif.model.User;
import com.multimif.model.UserGrant;
import com.multimif.service.UserGrantService;
import com.multimif.service.UserGrantServiceImpl;
import com.multimif.util.Constantes;
import com.multimif.util.DataException;
import com.multimif.util.JsonUtil;
import com.multimif.util.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * Le controleur pour la gestion de permis
 *
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 11/16/16.
 */
@RestController
@RequestMapping("/permissions")
public class PermissionController {
    private UserGrantService userGrantService;
    private static final Logger LOGGER = Logger.getLogger(PermissionController.class.getName());

    /**
     *
     * Cette méthode confère de permis sur le projet spécifié à l'utilisateur indiqué
     *
     * @param idProject l'id du projet
     * @param idUser l'id de l'utilisateur
     * @return Status de la transaction
     */
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Status> add(@RequestParam(value = "idProject") Long idProject,
                                      @RequestParam(value = "idUser") Long idUser) {
        try {
            userGrantService.addEntity(idUser, idProject, UserGrant.PermissionType.DEVELOPER);
        } catch (DataException ex) {
            LOGGER.log(Level.FINE, ex.toString(), ex);
            return new ResponseEntity<>(new Status(Constantes.OPERATION_CODE_RATE,
                    ex.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            LOGGER.log(Level.FINE, ex.toString(), ex);
            return new ResponseEntity<>(new Status(Constantes.OPERATION_CODE_RATE,
                    Constantes.OPERATION_MSG_RATE), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(new Status(Constantes.OPERATION_CODE_REUSSI,
                Constantes.OPERATION_MSG_REUSSI), HttpStatus.CREATED);
    }

    /**
     * Cette méthode retourne tous les developers du projet indiqué.
     *
     * @param idProject l'id du projet
     * @return Liste d'utilisateurs
     */
    @RequestMapping(value = "/projects/{idProject}/developers", produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<User>> getDevelopers(@PathVariable(value = "idProject") Long idProject) {
        List<User> developers;
        try {
            developers = userGrantService.getDevelopersByEntity(idProject);
        } catch (DataException ex) {
            LOGGER.log(Level.FINE, ex.toString(), ex);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            LOGGER.log(Level.FINE, ex.toString(), ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(developers, HttpStatus.OK);
    }

    /**
     * Cette méthode retourne l'admin du projet envoyé.
     *
     * @param idProject l'id du projet
     * @return Liste d'utilisateurs
     */
    @RequestMapping(value = "/projects/{idProject}/admins", produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<User> getAdmin(@PathVariable(value = "idProject") Long idProject) {
        User user;
        try {
            user = userGrantService.getAdminByEntity(idProject);
        } catch (DataException ex) {
            LOGGER.log(Level.FINE, ex.toString(), ex);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            LOGGER.log(Level.FINE, ex.toString(), ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * Cette méthode retourne tous les projets où l'utilisateur est admin ou bien developer
     *
     * @param idUser l'id de l'utilisateur
     * @return liste de projets
     */
    @RequestMapping(value = "/projects/users/{idUser}", produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Project>> getProjects(@PathVariable(value = "idUser") Long idUser) {
        List<Project> projects;

        try {
            projects = userGrantService.getAllProjectsByEntity(idUser);
        } catch (DataException ex) {
            LOGGER.log(Level.FINE, ex.toString(), ex);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            LOGGER.log(Level.FINE, ex.toString(), ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    /**
     * Cette méthode retourne tous les projets où l'utilisateur envoyé est admin.
     *
     * @param idUser l'id de l'utilisateur
     * @return liste de projets
     */
    @RequestMapping(value = "/projects/users/{idUser}/admin", produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Project>> getProjectsAdmin(@PathVariable(value = "idUser") Long idUser) {
        List<Project> projects;

        try {
            projects = userGrantService.getAdminProjects(idUser);
        } catch (DataException ex) {
            LOGGER.log(Level.FINE, ex.toString(), ex);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            LOGGER.log(Level.FINE, ex.toString(), ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    /**
     * Cette méthode retourne tous les projets où l'utilisateur envoyé est developer.
     *
     * @param idUser l'id de l'utilisateur
     * @return List de projets
     */
    @RequestMapping(value = "/projects/users/{idUser}/developers", produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Project>> getProjectsDeveloper(@PathVariable(value = "idUser") Long idUser) {
        List<Project> projects;

        try {
            projects = userGrantService.getCollaborationsProjects(idUser);
        } catch (DataException ex) {
            LOGGER.log(Level.FINE, ex.toString(), ex);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            LOGGER.log(Level.FINE, ex.toString(), ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    /**
     * Cette méthode retourna si un utilisateur a permis sur un projet spécifié
     *
     * @param idUser l'id de l'utilisateur
     * @param idProject l'id du projet
     * @return permission true | false
     */
    @RequestMapping(value = "/exists/projects/{idProject}/users/{idUser}", produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> has(@PathVariable(value = "idUser") Long idUser,
                                      @PathVariable(value = "idProject") Long idProject) {
        boolean permission;
        try {
            permission = userGrantService.hasPermission(idUser, idProject);
        } catch (DataException ex) {
            LOGGER.log(Level.FINE, ex.toString(), ex);
            return new ResponseEntity<>(JsonUtil.convertToJson(new Status(Constantes.OPERATION_CODE_RATE,
                    ex.getMessage())), HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            LOGGER.log(Level.FINE, ex.toString(), ex);
            return new ResponseEntity<>(JsonUtil.convertToJson(new Status(Constantes.OPERATION_CODE_RATE,
                    Constantes.OPERATION_MSG_RATE)), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(JsonUtil.convertStringToJson("permission", Boolean.toString(permission)), HttpStatus.OK);
    }

    /**
     * Cette méthode efface le rapport DEVELOPER entre l'utilisateur envoyé et le projet
     *
     * @param idUser du developer
     * @param idProject du projet
     * @return Status de la transaction
     */
    @RequestMapping(value = "/projects/{idProject}/users/{idUser}", produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Status> remove(@PathVariable(value = "idUser") Long idUser,
                                         @PathVariable(value = "idProject") Long idProject) {
        try {
            /* On peut supprimer uniquement les permis avec developpers, s'il s'agit du admin
            * il faudrait supprimer le projet */
            userGrantService.deleteEntity(idUser, idProject, UserGrant.PermissionType.DEVELOPER);
        } catch (DataException ex) {
            LOGGER.log(Level.FINE, ex.toString(), ex);
            return new ResponseEntity<>(new Status(Constantes.OPERATION_CODE_RATE,
                    ex.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            LOGGER.log(Level.FINE, ex.toString(), ex);
            return new ResponseEntity<>(new Status(Constantes.OPERATION_CODE_RATE,
                    Constantes.OPERATION_MSG_RATE), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(new Status(0, Constantes.OPERATION_MSG_REUSSI), HttpStatus.OK);
    }


    @PostConstruct
    void init() {
        userGrantService = new UserGrantServiceImpl();
    }
}
