package main.java.Controller;

import main.java.Util.Status;
import main.java.Model.User;
import main.java.Service.UserService;
import main.java.Service.UserServiceImpl;
import main.java.Util.Util;
import main.java.Util.Constantes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {
    private EntityManager entityManager;
    private UserService userService;

    @RequestMapping(value = "/add", produces = "application/json; charset=utf-8")
    public @ResponseBody ResponseEntity<String> add(@RequestParam(value="pseudo") String pseudo,
                                @RequestParam(value="mail") String mail, @RequestParam(value="pass") String pass){
        try{
            userService.addEntity(mail, pseudo, pass);
        }catch(Exception ex){
            return new ResponseEntity<String>(Util.convertToJson(new Status(-1, ex.getMessage())), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<String>(Util.convertToJson(new Status(Constantes.OPERATION_CODE_REUSSI,
                Constantes.OPERATION_MSG_REUSSI)), HttpStatus.OK);
    }

        @RequestMapping(value = "/get", produces = "application/json; charset=utf-8")
    public @ResponseBody ResponseEntity<String> get(@RequestParam(value="mail") String mail){
        User user;

        try{
            user = userService.getEntityByMail(mail);
        }catch(Exception ex){
            return new ResponseEntity<String>(Util.convertToJson(new Status(Constantes.OPERATION_CODE_RATE,
                    ex.getMessage())), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<String>(Util.convertToJson(user), HttpStatus.OK);
    }

    @RequestMapping(value = "/getall", produces = "application/json; charset=utf-8")
    public @ResponseBody ResponseEntity<String> getAll(){
        List<User> users;

        try{
            users = userService.getEntityList();
        }catch(Exception ex){
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
            return new ResponseEntity<String>(Util.convertToJson(new Status(Constantes.OPERATION_CODE_RATE,
                    ex.getMessage())), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<String>(Util.convertToJson(new Status(Constantes.OPERATION_CODE_REUSSI,
                Constantes.OPERATION_MSG_REUSSI)), HttpStatus.OK);
    }


    @RequestMapping(value = "/connect", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<String> get(@RequestParam(value="username") String username,
                                                    @RequestParam(value = "password") String passwd, HttpServletRequest request){


        HttpSession session = request.getSession();
        session.invalidate();

        Boolean auth;


        System.out.println("[API] [AUTH] " + username + ":" + passwd);
        try{
            auth = userService.authEntity(username,passwd);
        }catch (Exception e){
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
        if(auth){session.setAttribute("auth",true);};

        return new ResponseEntity<String>(auth.toString(),HttpStatus.OK);

    }


    @PostConstruct
    public void init(){
        entityManager = Persistence.createEntityManagerFactory(Constantes.ENTITY_FACTORY)
                 .createEntityManager();

        userService = new UserServiceImpl(entityManager);
    }

    @PreDestroy
    public void destroy(){
        entityManager.close();
    }
}