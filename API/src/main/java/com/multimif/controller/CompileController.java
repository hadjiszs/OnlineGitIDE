package com.multimif.controller;

import com.multimif.compilation.Compile;
import com.multimif.git.GitConstantes;
import com.multimif.util.Constantes;
import com.multimif.util.JsonUtil;
import com.multimif.util.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Mahmoud on 15/11/2016.
 */
@RestController
@RequestMapping("/compile/{idCurrentUserStr}/{idProjectStr}/{branch}")
public class CompileController {

    @RequestMapping(method = RequestMethod.GET, produces = GitConstantes.APPLICATION_JSON_UTF8)
    @ResponseBody
    public ResponseEntity<String> compile(@PathVariable String idProjectStr,
                                          @PathVariable String idCurrentUserStr,
                                          @PathVariable String branch) {
        Long idProject = Long.valueOf(idProjectStr);
        Long idCurrentUser = Long.valueOf(idCurrentUserStr);

        System.out.println("compile");
        Compile compile = new Compile();
        String resultat = "erreur";

        try {
            resultat = compile.executeCompilation(idProject,idCurrentUser,branch);
        } catch (Exception ex) {
            return new ResponseEntity<String>(JsonUtil.convertToJson(new Status(Constantes.OPERATION_CODE_RATE,
                    ex.getMessage())), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<String>(JsonUtil.convertToJson(resultat), HttpStatus.OK);
    }
}
