package Controller;

import Service.FolderTraverse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;

/**
 * Created by Alexis on 20/10/2016.
 */
@RestController
@RequestMapping("/file")
public class FileController {

    // /api/file/get?name="blalbla/blablal"&hash_key="sjkdqsjdqklj"
    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<String> get(@RequestParam(value="path") String pathDir){

        // On récupère le git de l'user

        return new ResponseEntity<String>("salut",HttpStatus.OK);
    }

    /**
     * Met à jour le brouillon
     * /api/file/update?path="blalbla/blablal"&hash_key="sjkdqsjdqklj"
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<String> update(@RequestParam(value="path") String pathDir,
                                                       @RequestParam(value="hashkey") String hashkey){

        // On récupère le git de l'user

        return new ResponseEntity<String>("salut",HttpStatus.OK);
    }

    /**
     * Enregistre le fichier
     * @param pathDir
     * @param hashkey
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<String> update(@RequestParam(value="path") String pathDir,
                                                       @RequestParam(value="hashkey") String hashkey){

        // On récupère le git de l'user

        return new ResponseEntity<String>("salut",HttpStatus.OK);
    }

}
