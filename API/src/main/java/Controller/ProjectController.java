package Controller;

import Model.Project;
import Service.ProjectService;
import Service.ProjectServiceImpl;
import Util.Constantes;
import Util.Util;
import Util.Status;
import com.sun.tools.internal.jxc.ap.Const;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.List;

/**
 * Created by amaia.nazabal on 10/21/16.
 */
@RestController
@RequestMapping("/project") //api/project
public class ProjectController {
    private EntityManager entityManager;
    ProjectService projectService ;

    @RequestMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<String> add(@RequestParam(value = "name") String name,
                                                    @RequestParam(value = "version") String version){
        Project project = new Project(name, version);
        try {
            projectService.addEntity(project);
        }catch (Exception ex) {
            return new ResponseEntity<String>(Util.convertToJson(new Status(-1, ex.getMessage())), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<String>(Util.convertToJson(new Status(Constantes.OPERATION_CODE_REUSSI,
                Constantes.OPERATION_MSG_REUSSI)), HttpStatus.ACCEPTED);
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
    public @ResponseBody ResponseEntity<String> getAll(){
        List<Project> projects;

        try {
            projects = projectService.getEntityList();
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
        entityManager = Persistence.createEntityManagerFactory(Constantes.ENTITY_FACTORY)
                .createEntityManager();
        projectService = new ProjectServiceImpl(entityManager);
    }



    @PreDestroy
    public void destroy(){
        entityManager.close();
    }
}
