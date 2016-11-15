package Controller;



import Util.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.EntityManager;

/**
 * Created by Mahmoud on 15/11/2016.
 */
public class CompileController {
    private EntityManager entityManager;

    @RequestMapping(value = "/get", produces = "application/json; charset=utf-8")
    public @ResponseBody
    ResponseEntity<String> get(@RequestParam(value="mail") String mail){


        try{

        }catch(Exception ex){
            return new ResponseEntity<String>(Util.convertToJson(new Status(Constantes.OPERATION_CODE_RATE,
                    ex.getMessage())), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<String>(Util.convertToJson("reponse"), HttpStatus.OK);
    }

}
