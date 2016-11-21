package Controller;

import Model.Project;
import Model.User;
import Model.UserGrant;
import Service.UserGrantService;
import Service.UserGrantServiceImpl;
import Util.Constantes;
import Util.JsonUtil;
import Util.Status;
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
 * Created by amaia.nazabal on 11/16/16.
 */
@RestController
@RequestMapping("/permission")
public class PermissionController {
    private UserGrantService userGrantService;

    @RequestMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<Status> add(@RequestParam(value = "idProject") Long idProject,
                               @RequestParam(value = "idUser") Long idUser){
        try {
            userGrantService.addEntity(idUser, idProject, UserGrant.Permis.Dev);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new Status(Constantes.OPERATION_CODE_RATE,
                    e.getMessage()), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new Status(Constantes.OPERATION_CODE_REUSSI,
                Constantes.OPERATION_MSG_REUSSI), HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/getdevelopers", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<List<User>> getDevelopers(@RequestParam(value = "idProject") Long idProject){
        List<User> developers;
        try {
             developers = userGrantService.getDevelopersByEntity(idProject);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(developers, HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/getadmin", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<User> getAdmin(@RequestParam(value = "idProject") Long idProject) {
        User user;
        try{
            user = userGrantService.getAdminByEntity(idProject);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/getprojects", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<List<Project>> getProjects(@RequestParam(value = "user") Long idUser){
        List<Project> projects;

        try{
            projects = userGrantService.getProjectsByEntity(idUser);

        }catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(projects, HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/has", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<String> has(@RequestParam(value = "idUser") Long idUser,
                                                    @RequestParam(value = "idProject") Long idProject){
        boolean permission;
        try{
            permission = userGrantService.hasPermission(idUser, idProject);
        }catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(JsonUtil.convertToJson(new Status(Constantes.OPERATION_CODE_RATE,
                    Constantes.OPERATION_MSG_RATE)), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(JsonUtil.convertStringToJson("permission", permission + ""), HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/remove", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Status> remove (@RequestParam(value = "idUser") Long idUser,
                                                        @RequestParam(value = "idProject") Long idProject){
        try{
            /* On peut supprimer uniquement les permis avec developpers, s'il s'agit du admin
            * il faudrait supprimer le projet */
            userGrantService.deleteEntity(idUser, idProject, UserGrant.Permis.Dev);
        }catch (Exception ex) {
            //ex.printStackTrace();
            return new ResponseEntity<>(new Status(Constantes.OPERATION_CODE_RATE,
                    Constantes.OPERATION_MSG_RATE), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new Status(0, Constantes.OPERATION_MSG_REUSSI), HttpStatus.ACCEPTED);
    }


    @PostConstruct
    void init(){
        userGrantService = new UserGrantServiceImpl();
    }
}
