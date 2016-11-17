package Controller;

import Model.User;
import Service.UserService;
import Service.UserServiceImpl;
import Util.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


@RestController
@RequestMapping("/user")
public class UserController {
    private EntityManager entityManager;
    private UserService userService;
    private static final Logger LOGGER = Logger.getLogger( UserController.class.getName() );
    @RequestMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<String> add(@RequestParam(value="pseudo") String pseudo,
                                @RequestParam(value="mail") String mail, @RequestParam(value="pass") String pass){
        Long id;

        try{
            id = userService.addEntity(mail, pseudo, pass);
        }catch(Exception ex){
            LOGGER.log( Level.FINE, ex.toString(), ex);
            return new ResponseEntity<String>(Util.convertToJson(new Status(-1, ex.getMessage())), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<String>(Util.convertToJson(new StatusOK(Constantes.OPERATION_CODE_REUSSI,
                Constantes.OPERATION_MSG_REUSSI, id)), HttpStatus.OK);
    }

        @RequestMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<String> get(@RequestParam(value="mail") String mail){
        User user;

        try{
            user = userService.getEntityByMail(mail);
        }catch(Exception ex){
            LOGGER.log( Level.FINE, ex.toString(), ex);
            return new ResponseEntity<String>(Util.convertToJson(new Status(Constantes.OPERATION_CODE_RATE,
                    ex.getMessage())), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<String>(Util.convertToJson(user), HttpStatus.OK);
    }

    @RequestMapping(value = "/getall", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<String> getAll(){
        List<User> users;

        try{
            users = userService.getEntityList();
        }catch(Exception ex){
            LOGGER.log( Level.FINE, ex.toString(), ex);
            return new ResponseEntity<String>(Util.convertToJson(new Status(Constantes.OPERATION_CODE_RATE,
                    ex.getMessage())), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<String>(Util.convertListToJson(users), HttpStatus.OK);
    }

    @RequestMapping(value = "/remove", produces = "application/json; charset=utf-8")
    public @ResponseBody ResponseEntity<String> remove(@RequestParam(value="mail") String mail){

        try{
            userService.deleteEntity(mail);
        }catch(Exception ex){
            LOGGER.log( Level.FINE, ex.toString(), ex);
            return new ResponseEntity<String>(Util.convertToJson(new Status(Constantes.OPERATION_CODE_RATE,
                    ex.getMessage())), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<String>(Util.convertToJson(new Status(Constantes.OPERATION_CODE_REUSSI,
                Constantes.OPERATION_MSG_REUSSI)), HttpStatus.OK);
    }

    @PostConstruct
    public void init(){
        userService = new UserServiceImpl();
    }
}