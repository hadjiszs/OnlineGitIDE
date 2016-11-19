package Controller;

import Model.User;
import Service.UserService;
import Service.UserServiceImpl;
import Util.Constantes;
import Util.JsonUtil;
import Util.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @PostConstruct
    public void init(){
        userService = new UserServiceImpl();
    }
    private static final Logger LOGGER = Logger.getLogger( UserController.class.getName() );
    @RequestMapping(value = "/add", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<User> add(
                                @RequestParam(value="username") String username,
                                @RequestParam(value="mail") String mail,
                                @RequestParam(value="password") String password){
        User user;

        try{
            user = userService.addEntity(mail, username,password);
        }catch(Exception ex){
            LOGGER.log( Level.FINE, ex.toString(), ex);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<User> get(@RequestParam(value="username") String username,
                                                  @RequestParam(value = "password") String passwd){


        User user;

        try{
            user = userService.authEntity(username,passwd);
        }catch (Exception ex){
            LOGGER.log( Level.FINE, ex.toString(), ex);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }


    @RequestMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<String> get(@RequestParam(value="mail") String mail){
        User user;

        try{
            user = userService.getEntityByMail(mail);
        }catch(Exception ex){
            LOGGER.log( Level.FINE, ex.toString(), ex);
            return new ResponseEntity<>(JsonUtil.convertToJson(new Status(Constantes.OPERATION_CODE_RATE,
                    ex.getMessage())), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(JsonUtil.convertToJson(user), HttpStatus.ACCEPTED);
    }


    @RequestMapping(value = "/getall", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<List<User>> getAll(){
        List<User> users;

        try{
            users = userService.getEntityList();
        }catch(Exception ex){
            LOGGER.log( Level.FINE, ex.toString(), ex);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(users, HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/remove", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Status> remove(@RequestParam(value="idUser") Long idUser){

        try{
            userService.deleteEntity(idUser);
        }catch(Exception ex){
            LOGGER.log( Level.FINE, ex.toString(), ex);
            return new ResponseEntity<>(new Status(Constantes.OPERATION_CODE_RATE,
                    ex.getMessage()), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new Status(Constantes.OPERATION_CODE_REUSSI,
                Constantes.OPERATION_MSG_REUSSI), HttpStatus.ACCEPTED);
    }

}
