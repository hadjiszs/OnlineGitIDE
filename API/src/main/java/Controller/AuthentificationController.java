package main.java.Controller;

import main.java.Model.User;
import main.java.Service.UserService;
import main.java.Service.UserServiceImpl;
import main.java.Util.Constantes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.comparator.BooleanComparator;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;

/**
 * Created by Alexis on 14/11/2016.
 */
@RestController
@RequestMapping("/auth")
public class AuthentificationController {


    private EntityManager entityManager;
    private UserService userService;


    @RequestMapping("/")
    public @ResponseBody ResponseEntity<String> get(){

        System.out.println("lol");
        return new ResponseEntity<String>("dsdqs",HttpStatus.OK);

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
