package Controller;


import Util.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;

/**
 * Created by Mahmoud on 15/11/2016.
 */
@RestController
@RequestMapping("/compile/{branch}")
public class CompileController {

    private EntityManager entityManager;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public
    @ResponseBody
    ResponseEntity<String> compile(@RequestParam("idProject") long idProject,@RequestParam("idCurrentUser") Long idCurrentUser,@PathVariable String branch) {

        System.out.println("compile");
        Compile compile=new Compile();
        String Resultat ="erreur";
        try {
        Resultat   = compile.executeCompilation(idProject,idCurrentUser,branch);
        } catch (Exception ex) {
            return new ResponseEntity<String>(JsonUtil.convertToJson(new Status(Constantes.OPERATION_CODE_RATE,
                    ex.getMessage())), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<String>(JsonUtil.convertToJson(Resultat), HttpStatus.OK);
    }

}
