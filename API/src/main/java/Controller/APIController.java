package main.java.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import main.java.Model.User;

import javax.annotation.PostConstruct;

/**
 * Created by amaia.nazabal on 10/19/16.
 */

@RestController
@RequestMapping("/")
public class APIController {

    @PostConstruct
    public void init(){
        System.out.println("INIT");
    }

    @RequestMapping("/")
    public String get(){

        System.out.println("salut");
        return "mdr";
    }

    @RequestMapping(value = "/add")
    public User user(@RequestParam(value="pseudo") String pseudo,
                     @RequestParam(value="mail") String mail){
        return new User(mail, pseudo);
    }

    @RequestMapping("/new")
    public void index(){
        System.out.println("default");
    }
}