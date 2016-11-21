package com.multimif.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Alexis on 19/10/2016.
 */

@Controller
@RequestMapping("/tree")
public class FileTreeController {
/*

    // /api/tree/getEntityById?path=
    @RequestMapping(value = "/getByMail", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<String> getByMail(@RequestParam(value="path") String pathDir){
        FolderTraverse ft;
        String path = "C:/Users/Alexis/Desktop/Master/Projet Intellij/MultiMif/API/" + pathDir;
        System.out.println(path);
        try{
            ft = new FolderTraverse(new File(path));
        }catch (Exception e){
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(ft.toJsonFileTree().toString(),HttpStatus.OK);
    }*/
}
