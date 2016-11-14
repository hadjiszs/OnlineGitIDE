package Controller;

import Service.APIService;
import Service.UserServiceImpl;
import Util.Constantes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import Model.User;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 * Created by amaia.nazabal on 10/19/16.
 */

@RestController
public class APIController {

    @RequestMapping("/add")
    public User user(@RequestParam(value="pseudo") String pseudo,
                     @RequestParam(value="mail") String mail){
        return new User(mail, pseudo, "bla bla bla");
    }

    @RequestMapping("/new")
    public void index(){
        System.out.println("default");
    }

    @PostConstruct
    public void init() {
        APIService.persistance();
    }

    @PreDestroy
    public void destroy(){
        APIService.close();
    }
}