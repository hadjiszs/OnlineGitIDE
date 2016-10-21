package Controller;

import Service.FolderTraverse;
import com.fasterxml.jackson.annotation.JsonRawValue;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.json.JSONObject;
import java.io.File;

/**
 * Created by Alexis on 19/10/2016.
 */

@Controller
@RequestMapping("/tree")
public class FileTreeController {


    // /api/tree/get?path=
    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<String> get(@RequestParam(value="path") String pathDir){
        FolderTraverse ft;
        String path = "C:/Users/Alexis/Desktop/Master/Projet Intellij/MultiMif/API/" + pathDir;
        System.out.println(path);
        try{
            ft = new FolderTraverse(new File(path));
        }catch (Exception e){
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(ft.toJsonFileTree().toString(),HttpStatus.OK);
    }

}
