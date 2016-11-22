package com.multimif.controller;

import com.multimif.model.Project;
import com.multimif.service.*;
import com.multimif.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controleur pour la gestion des projets
 */
@RestController
@RequestMapping("/project")
public class ProjectController {

    private static final Logger LOGGER = Logger.getLogger(ProjectController.class.getName());

    /**
     * Service des projets
     */
    private ProjectService projectService;

    /**
     * Créer un nouveau projet
     *
     * @param name    le nom du projet
     * @param version version du projet
     * @param root    le dossier du projet
     * @param type    le language utilisé
     * @param idUser  l'id de l'user qui crée le projet
     * @return l'id du projet créer
     */
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> add(@RequestParam(value = "name") String name,
                                      @RequestParam(value = "version") String version,
                                      @RequestParam(value = "root") String root,
                                      @RequestParam(value = "type") Project.TypeProject type,
                                      @RequestParam(value = "idUser") Long idUser) {

        Project project;
        try {
            project = projectService.addEntity(name, version, type, root, idUser);
        } catch (DataException ex) {
            LOGGER.log(Level.FINE, ex.toString(), ex);
            return new ResponseEntity<>(JsonUtil.convertToJson(new Status(-1, ex.getMessage())),
                    HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            LOGGER.log(Level.FINE, ex.toString(), ex);
            return new ResponseEntity<>(JsonUtil.convertToJson(new Status(-1, Constantes.OPERATION_MSG_RATE)),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(JsonUtil.convertToJson(new StatusOK(Constantes.OPERATION_CODE_REUSSI,
                Constantes.OPERATION_MSG_REUSSI,
                project.getIdProject())),
                HttpStatus.CREATED);
    }

    /**
     * Créer un nouveau projet
     *
     * @param idProject  l'id du projet
     * @param project le projet qu'on va actualiser
     * @return l'id du projet créer
     */
    @RequestMapping(value = "/{idProject}", produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<String> modify(@PathVariable(value = "idProject") Long idProject,
                                         @RequestBody Project project)
    {
        project.setIdProject(idProject);
        try {
            projectService.updateEntity(project);
        } catch (DataException ex) {
            LOGGER.log(Level.FINE, ex.toString(), ex);
            return new ResponseEntity<>(JsonUtil.convertToJson(new Status(-1, ex.getMessage())),
                    HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            LOGGER.log(Level.FINE, ex.toString(), ex);
            return new ResponseEntity<>(JsonUtil.convertToJson(new Status(-1, Constantes.OPERATION_MSG_RATE)),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(JsonUtil.convertToJson(new StatusOK(Constantes.OPERATION_CODE_REUSSI,
                Constantes.OPERATION_MSG_REUSSI,
                project.getIdProject())),
                HttpStatus.CREATED);
    }

    /**
     * Récupère les informations d'un projet
     *
     * @param idProject l'id du projet
     * @return le json de la classe projet
     */
    @RequestMapping(value = "/{idProject}", produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Project> get(@PathVariable(value = "idProject") Long idProject) {
        Project project;

        try {
            project = projectService.getEntityById(idProject);
        } catch (DataException ex) {
            LOGGER.log(Level.FINE, ex.toString(), ex);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            LOGGER.log(Level.FINE, ex.toString(), ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    /**
     * Renvoi la liste des projets dans la base de donnée
     *
     * @return la liste des projets de la BDD
     */
    @RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Project>> getAll() {
        List<Project> projects;

        try {
            projects = projectService.getEntityList();
        } catch (Exception ex) {
            LOGGER.log(Level.FINE, ex.toString(), ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    /**
     * Supprime un projet de la bas de donnée
     *
     * @param idProject l'id du projet à supprimer
     * @param idUser    l'id de l'utilisateur qui veut effacer le projet
     * @return un code réussite
     */
    @RequestMapping(value = "/{idProject}/{idUser}", produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<String> remove(@PathVariable(value = "idProject") Long idProject,
                                         @PathVariable(value = "idUser") Long idUser) {
        try {
            projectService.deleteEntity(idProject, idUser);
        } catch (DataException ex) {
            LOGGER.log(Level.FINE, ex.toString(), ex);
            return new ResponseEntity<>(JsonUtil.convertToJson(new Status(-1, ex.getMessage())),
                    HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            LOGGER.log(Level.FINE, ex.toString(), ex);
            return new ResponseEntity<>(JsonUtil.convertToJson(new Status(-1, Constantes.OPERATION_MSG_RATE)),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(JsonUtil.convertToJson(new Status(Constantes.OPERATION_CODE_REUSSI,
                Constantes.OPERATION_MSG_REUSSI)), HttpStatus.OK);
    }

    /**
     * Initialise les services utilisés par la classe
     */
    @PostConstruct
    public void init() {
        projectService = new ProjectServiceImpl();
    }
}
