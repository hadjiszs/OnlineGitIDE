package Controller;

import Model.Project;
import Service.*;
import Util.Constantes;
import Util.JsonUtil;
import Util.Status;
import Util.StatusOK;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Controleur pour la gestion des projets
 */
@RestController
@RequestMapping("/project")
public class ProjectController {

    /**
     * Service des projets
     */
    ProjectService projectService;

    /**
     * Service des user
     */
    UserService userService;

    /**
     * Service de gestion de droit
     */
    UserGrantService userGrantService;

    /**
     * Créer un nouveau projet
     * @param name le nom du projet
     * @param version
     * @param root
     * @param type le language utilisé
     * @param idUser l'id de l'user qui crée le projet
     * @return l'id du projet créer
     */
    @RequestMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<String> add(@RequestParam(value = "name") String name,
                                                    @RequestParam(value = "version") String version,
                                                    @RequestParam(value = "root") String root,
                                                    @RequestParam(value = "type") Project.TypeProject type,
                                                    @RequestParam(value = "user") Long idUser){

        Project project = new Project(name, version, type, root);
        try {
            projectService.addEntity(project, idUser);

        }catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(JsonUtil.convertToJson(new Status(-1, ex.getMessage())),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(JsonUtil.convertToJson(new StatusOK(Constantes.OPERATION_CODE_REUSSI,
                                                                    Constantes.OPERATION_MSG_REUSSI,
                                                                    project.getIdProject())),
                                                        HttpStatus.ACCEPTED);
    }

    /**
     * Récupère les informations d'un projet
     * @param id l'id du projet
     * @return le json de la classe projet
     */
    @RequestMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Project> get(@RequestParam(value = "id") Long id){
        Project project;

        try {
            project = projectService.getEntityById(id);
        }catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(project, HttpStatus.ACCEPTED);
    }

    /**
     * Renvoi la liste des projets dans la base de donnée
     * @return la liste des projets de la BDD
     */
    @RequestMapping(value = "/getall", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<List<Project>> getAll(){
        List<Project> projects;

        try {
            projects = projectService.getEntityList();
        }catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(projects, HttpStatus.ACCEPTED);
    }

    /**
     * Supprime un projet de la bas de donnée
     *
     * @param idProject l'id du projet à supprimer
     * @param idUser l'id du projet à supprimer
     * @return un code réussite
     */
    @RequestMapping(value = "/remove", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<String> remove (@RequestParam(value = "idProject") Long idProject,
                                                        @RequestParam(value = "idUser") Long idUser){
        try {
            projectService.deleteEntity(idProject, idUser);
        }catch (Exception ex) {
            return new ResponseEntity<>(JsonUtil.convertToJson(new Status(-1, ex.getMessage())),
                    HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(JsonUtil.convertToJson(new Status(Constantes.OPERATION_CODE_REUSSI,
                Constantes.OPERATION_MSG_REUSSI)), HttpStatus.ACCEPTED);
    }


    /**
     * Initialise les services utilisés par la classe
     */
    @PostConstruct
    public void init() {
        projectService = new ProjectServiceImpl();
        userService = new UserServiceImpl();
        userGrantService = new UserGrantServiceImpl();
    }
}
