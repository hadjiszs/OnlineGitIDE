package Controller;

import Model.Project;
import Model.User;
import Model.UserGrant;
import Service.*;
import Util.*;
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
 * Created by amaia.nazabal on 10/21/16.
 */
@RestController
@RequestMapping("/project") //api/project
public class ProjectController {
    ProjectService projectService ;
    UserService userService;
    UserGrantService userGrantService;

    @RequestMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<String> add(@RequestParam(value = "name") String name,
                                                    @RequestParam(value = "version") String version,
                                                    @RequestParam(value = "root") String root,
                                                    @RequestParam(value = "type") String type,
                                                    @RequestParam(value = "user") Long idUser){

        Project project = new Project(name, version, type, root);
        try {
            projectService.addEntity(project);
            userGrantService.addEntity(idUser, project.getId(), UserGrant.Permis.Admin);
        }catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<String>(Util.convertToJson(new Status(-1, ex.getMessage())), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<String>(Util.convertToJson(new StatusOK(Constantes.OPERATION_CODE_REUSSI,
                Constantes.OPERATION_MSG_REUSSI, project.getId())), HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<String> get(@RequestParam(value = "id") Long id){
        Project project;

        try {
            project = projectService.getEntityById(id);
        }catch (Exception ex) {
            return new ResponseEntity<String>(Util.convertToJson(new Status(-1, ex.getMessage())), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<String>(Util.convertToJson(project), HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/getall", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<String> getall(@RequestParam(value = "mail") String mail){
        List<Project> projects;

        try {
            User user = userService.getEntityByMail(mail);
            projects = projectService.getEntityList(user);
        }catch (Exception ex) {
            return new ResponseEntity<String>(Util.convertToJson(new Status(-1, ex.getMessage())),
                    HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<String>(Util.convertListToJson(projects), HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/remove", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<String> getAll(@RequestParam(value = "id") Long id){
        try {
            projectService.deleteEntity(id);
        }catch (Exception ex) {
            return new ResponseEntity<String>(Util.convertToJson(new Status(-1, ex.getMessage())),
                    HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<String>(Util.convertToJson(new Status(Constantes.OPERATION_CODE_REUSSI,
                Constantes.OPERATION_MSG_REUSSI)), HttpStatus.ACCEPTED);
    }


    @PostConstruct
    public void init(){
        projectService = new ProjectServiceImpl();
        userService = new UserServiceImpl();
        userGrantService = new UserGrantServiceImpl();
    }
}