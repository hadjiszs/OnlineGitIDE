package Controller;

import Service.APIService;
import Util.Util;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created by amaia.nazabal on 10/19/16.
 */

@RestController
@RequestMapping("/")
public class APIController {

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<String> get(){
        return new ResponseEntity<String>(Util.convertStringToJson("API","OK"), HttpStatus.OK);
    }

    @PostConstruct
    public void init() {
        System.out.println("[API] [Controller] Init");
        APIService.persistance();
    }

    @PreDestroy
    public void destroy(){
        APIService.close();
    }
}