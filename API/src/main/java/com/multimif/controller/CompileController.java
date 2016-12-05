package com.multimif.controller;

import com.multimif.compilation.Compile;
import com.multimif.git.GitConstantes;
import com.multimif.util.Constantes;
import com.multimif.util.DataException;
import com.multimif.util.JsonUtil;
import com.multimif.util.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.json.JsonObject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Mahmoud on 15/11/2016.
 */
@RestController
@RequestMapping("/compile/{idCurrentUserStr}/{idProjectStr}/{branch}")
public class CompileController {

    private static final Logger LOGGER = Logger.getLogger(CompileController.class.getName());

    @RequestMapping(method = RequestMethod.GET, produces = GitConstantes.APPLICATION_JSON_UTF8)
    @ResponseBody
    public ResponseEntity<String> compile(@PathVariable String idProjectStr,
                                          @PathVariable String idCurrentUserStr,
                                          @PathVariable String branch) {
        Long idProject = Long.valueOf(idProjectStr);
        Long idCurrentUser = Long.valueOf(idCurrentUserStr);

        Compile compile = null;

        // Instanciation compilation
        try {
            compile = new Compile(idProject, idCurrentUser, branch);
        } catch (DataException ex) {
            LOGGER.log(Level.FINE, ex.toString(), ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        JsonObject ret;

        // Execution du process
        try {
            ret = compile.execute();
        } catch (Exception ex) {
            LOGGER.log(Level.FINE, ex.toString(), ex);
            return new ResponseEntity<>(JsonUtil.convertToJson(new Status(Constantes.OPERATION_CODE_RATE,
                    ex.getMessage())), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(ret.toString(), HttpStatus.OK);
    }
}
