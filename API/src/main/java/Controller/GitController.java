package Controller;

import Git.Constantes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.json.JsonObject;

/**
 * Created by p1317074 on 16/11/16.
 */

@Controller
@RequestMapping("/git/{author}/{repository}")
public class GitController {

    //Contenu d'un fichier
    @RequestMapping(value = "/{revision}", method = RequestMethod.GET, produces = Constantes.APPLICATION_JSON_UTF8)
    public @ResponseBody
    ResponseEntity<String> getFile(@PathVariable String author, @PathVariable String repository, @PathVariable String revision, @RequestParam(value="path") String path){
        JsonObject ret = null;
        try{
            ret = Git.Util.getContent(author, repository, revision, path);
            if (ret == null) {return new ResponseEntity<String>(HttpStatus.NOT_FOUND); }
        }catch (Exception e){
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>(ret.toString(),HttpStatus.OK);
    }

    //Arborescence d'un commit particulier
    @RequestMapping(value = "/tree/{revision}", method = RequestMethod.GET, produces = Constantes.APPLICATION_JSON_UTF8)
    public @ResponseBody
    ResponseEntity<String> getTree(@PathVariable String author, @PathVariable String repository, @PathVariable String revision){
        JsonObject ret = null;
        try{
            ret = Git.Util.getArborescence(author, repository, revision);
            if (ret == null) {return new ResponseEntity<String>(HttpStatus.NOT_FOUND); }
        }catch (Exception e){
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>(ret.toString(),HttpStatus.OK);
    }

    //liste des branches
    @RequestMapping(value = "/branches", method = RequestMethod.GET, produces = Constantes.APPLICATION_JSON_UTF8)
    public @ResponseBody
    ResponseEntity<String> getBranches(@PathVariable String author, @PathVariable String repository){
        JsonObject ret = null;
        try{
            ret = Git.Util.getBranches(author, repository);
            if (ret == null) {return new ResponseEntity<String>(HttpStatus.NOT_FOUND); }
        }catch (Exception e){
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>(ret.toString(),HttpStatus.OK);
    }

    //liste des commits d'une branche
    @RequestMapping(value = "/listCommit/{branch}", method = RequestMethod.GET, produces = Constantes.APPLICATION_JSON_UTF8)
    public @ResponseBody
    ResponseEntity<String> getListCommit(@PathVariable String author, @PathVariable String repository, @PathVariable String branch){
        JsonObject ret = null;
        try{
            ret = Git.Util.getCommits(author, repository, branch);
            if (ret == null) {return new ResponseEntity<String>(HttpStatus.NOT_FOUND); }
        }catch (Exception e){
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>(ret.toString(),HttpStatus.OK);
    }


    //Commit tout les fichiers modifiés pour une branche donnée
    @RequestMapping(value = "/makeCommit/{branch}", method = RequestMethod.POST, produces = Constantes.APPLICATION_JSON_UTF8)
    public @ResponseBody
    ResponseEntity<String> postMakeCommit(@PathVariable String author, @PathVariable String repository, @PathVariable String branch){
        //TODO
        return null;
    }

    //diff entre le fichier en cours de modification et le dernier commit
    @RequestMapping(value = "/diff/{branch}/{path}", method = RequestMethod.GET, produces = Constantes.APPLICATION_JSON_UTF8)
    public @ResponseBody
    ResponseEntity<String> getDiffBranch(@PathVariable String author, @PathVariable String repository, @PathVariable String branch, @PathVariable String path){
        //TODO
        return null;
    }

    //diff concernant un commit en particulier
    @RequestMapping(value = "/showCommit/{revision}", method = RequestMethod.GET, produces = Constantes.APPLICATION_JSON_UTF8)
    public @ResponseBody
    ResponseEntity<String> getShowCommit(@PathVariable String author, @PathVariable String repository, @PathVariable String revision){
        //TODO
        return null;
    }

    //Téléchargement du dépot sous forme d'archive
    @RequestMapping(value = "/archive/{branch}", method = RequestMethod.GET, produces = Constantes.APPLICATION_JSON_UTF8)
    public @ResponseBody
    ResponseEntity<String> getArchive(@PathVariable String author, @PathVariable String repository, @PathVariable String branch){
        //TODO
        return null;
    }

    //Clone un repo distant
    @RequestMapping(value = "/clone/{url}", method = RequestMethod.POST, produces = Constantes.APPLICATION_JSON_UTF8)
    public @ResponseBody
    ResponseEntity<String> postClone(@PathVariable String author, @PathVariable String repository, @PathVariable String url){
        //TODO
        return null;
    }

    //Creation branche
    @RequestMapping(value = "/create/branch/{newBranch}", method = RequestMethod.POST, produces = Constantes.APPLICATION_JSON_UTF8)
    public @ResponseBody
    ResponseEntity<String> postCreateBranch(@PathVariable String author, @PathVariable String repository, @PathVariable String newBranch){
        //TODO
        return null;
    }

    //Creation fichier
    @RequestMapping(value = "/create/file/{branch}/{path}", method = RequestMethod.POST, produces = Constantes.APPLICATION_JSON_UTF8)
    public @ResponseBody
    ResponseEntity<String> postCreateFile(@PathVariable String author, @PathVariable String repository, @PathVariable String branch, @PathVariable String path){
        //TODO
        return null;
    }

}
