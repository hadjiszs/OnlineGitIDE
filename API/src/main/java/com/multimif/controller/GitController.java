package com.multimif.controller;

import com.multimif.git.Constantes;
import com.multimif.git.Util;
import com.multimif.model.Project;
import com.multimif.model.User;
import com.multimif.service.*;
import com.multimif.util.DataException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.json.JsonObject;

/**
 * Created by p1317074 on 16/11/16.
 */

@Controller
@RequestMapping("/git/{idUser}/{idRepository}")
public class GitController {

    /**
     * Service de fichier temporaire
     */
    TemporaryFileService temporaryFileService;

    /*
     * Service de gestion de droit
     */
    UserService userService;

    /**
     * Service de gestion de droit
     */
    ProjectService projectService;

    /**
     * Methode interne pour recuperer le pseudo d'un user à partir de son id
     *
     * @param idUser id de l'user
     * @return son pseudo
     */
    public String getUsernameById(String idUser) {
        Long id = Long.valueOf(idUser);
        User user = null;
        try {
            user = userService.getEntityById(id);
        } catch (DataException e) {
            e.printStackTrace();
        }

        return user.getUsername();
    }

    /**
     * Methode interne pour recuperer le nom de repository par l'id
     *
     * @param idRepository id de l'user
     * @return nom du repository
     */
    public String getNameRepositoryById(String idRepository) {
        Long id = Long.valueOf(idRepository);
        Project project = null;
        try {
            project = projectService.getEntityById(id);
        } catch (DataException e) {
            e.printStackTrace();
        }

        return project.getName();
    }

    //Contenu d'un fichier
    @RequestMapping(value = "/{revision}", method = RequestMethod.GET, produces = Constantes.APPLICATION_JSON_UTF8)
    public @ResponseBody
    ResponseEntity<String> getFile(@PathVariable String idUser,
                                   @PathVariable String idRepository,
                                   @PathVariable String revision,
                                   @RequestParam(value="path") String path) {
        JsonObject ret = null;
        String author = getUsernameById(idUser);
        String repository = getNameRepositoryById(idRepository);

        try{
            ret = Util.getContent(author, repository, revision, path);
            if (ret == null) {return new ResponseEntity<String>(HttpStatus.NOT_FOUND); }
        }catch (Exception e){
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>(ret.toString(),HttpStatus.OK);
    }

    //Arborescence d'un commit particulier
    @RequestMapping(value = "/tree/{revision}", method = RequestMethod.GET, produces = Constantes.APPLICATION_JSON_UTF8)
    public @ResponseBody
    ResponseEntity<String> getTree(@PathVariable String idUser,
                                   @PathVariable String idRepository,
                                   @PathVariable String revision) {
        JsonObject ret = null;
        String author = getUsernameById(idUser);
        String repository = getNameRepositoryById(idRepository);

        try {
            ret = Util.getArborescence(author, repository, revision);
            if (ret == null) {return new ResponseEntity<String>(HttpStatus.NOT_FOUND); }
        } catch (Exception e) {
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>(ret.toString(),HttpStatus.OK);
    }

    //liste des branches
    @RequestMapping(value = "/branches", method = RequestMethod.GET, produces = Constantes.APPLICATION_JSON_UTF8)
    public @ResponseBody
    ResponseEntity<String> getBranches(@PathVariable String idUser,
                                       @PathVariable String idRepository){
        JsonObject ret = null;
        String author = getUsernameById(idUser);
        String repository = getNameRepositoryById(idRepository);

        try{
            ret = Util.getBranches(author, repository);
            if (ret == null) {return new ResponseEntity<String>(HttpStatus.NOT_FOUND); }
        }catch (Exception e){
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>(ret.toString(),HttpStatus.OK);
    }

    //liste des branches
    @RequestMapping(value = "/info/{revision}", method = RequestMethod.GET, produces = Constantes.APPLICATION_JSON_UTF8)
    public @ResponseBody
    ResponseEntity<String> getInfoCommit(@PathVariable String idUser,
                                         @PathVariable String idRepository,
                                         @PathVariable String revision){
        JsonObject ret = null;
        String author = getUsernameById(idUser);
        String repository = getNameRepositoryById(idRepository);

        try{
            ret = Util.getInfoCommit(author, repository, revision);
            if (ret == null) {return new ResponseEntity<String>(HttpStatus.NOT_FOUND); }
        }catch (Exception e){
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>(ret.toString(),HttpStatus.OK);
    }

    //liste des commits d'une branche
    @RequestMapping(value = "/listCommit/{branch}", method = RequestMethod.GET, produces = Constantes.APPLICATION_JSON_UTF8)
    public @ResponseBody
    ResponseEntity<String> getListCommit(@PathVariable String idUser,
                                         @PathVariable String idRepository,
                                         @PathVariable String branch) {
        JsonObject ret = null;
        String author = getUsernameById(idUser);
        String repository = getNameRepositoryById(idRepository);

        try{
            ret = Util.getCommits(author, repository, branch);
            if (ret == null) {return new ResponseEntity<String>(HttpStatus.NOT_FOUND); }
        }catch (Exception e){
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>(ret.toString(),HttpStatus.OK);
    }


    //Commit tout les fichiers modifiés pour une branche donnée
    @RequestMapping(value = "/makeCommit/{branch}", method = RequestMethod.POST, produces = Constantes.APPLICATION_JSON_UTF8)
    public @ResponseBody
    ResponseEntity<String> postMakeCommit(@PathVariable String idUser,
                                          @PathVariable String idRepository,
                                          @PathVariable String branch) {
        //TODO
        return null;
    }

    //diff entre le fichier en cours de modification et le dernier commit
    @RequestMapping(value = "/diff/{branch}/{path}", method = RequestMethod.GET, produces = Constantes.APPLICATION_JSON_UTF8)
    public @ResponseBody
    ResponseEntity<String> getDiffBranch(@PathVariable String idUser,
                                         @PathVariable String idRepository,
                                         @PathVariable String branch,
                                         @PathVariable String path) {
        //TODO
        return null;
    }

    //diff concernant un commit en particulier
    @RequestMapping(value = "/showCommit/{revision}", method = RequestMethod.GET, produces = Constantes.APPLICATION_JSON_UTF8)
    public @ResponseBody
    ResponseEntity<String> getShowCommit(@PathVariable String idUser,
                                         @PathVariable String idRepository,
                                         @PathVariable String revision) {
        JsonObject ret = null;
        String author = getUsernameById(idUser);
        String repository = getNameRepositoryById(idRepository);

        try {
            ret = Util.showCommit(author, repository, revision);
            if (ret == null) { return new ResponseEntity<String>(HttpStatus.NOT_FOUND); }
        } catch (Exception e) {
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>(ret.toString(),HttpStatus.OK);
    }

    //Téléchargement du dépot sous forme d'archive
    @RequestMapping(value = "/archive/{branch}", method = RequestMethod.GET, produces = Constantes.APPLICATION_JSON_UTF8)
    public @ResponseBody
    ResponseEntity<String> getArchive(@PathVariable String idUser,
                                      @PathVariable String idRepository,
                                      @PathVariable String branch) {
        //TODO
        return null;
    }

    //Clone un repo distant
    @RequestMapping(value = "/clone/{url}", method = RequestMethod.POST, produces = Constantes.APPLICATION_JSON_UTF8)
    public @ResponseBody
    ResponseEntity<String> postClone(@PathVariable String idUser,
                                     @PathVariable String idRepository,
                                     @PathVariable String url) {
        //TODO
        return null;
    }

    //Creation branche
    @RequestMapping(value = "/create/branch/{newBranch}", method = RequestMethod.POST, produces = Constantes.APPLICATION_JSON_UTF8)
    public @ResponseBody
    ResponseEntity<String> postCreateBranch(@PathVariable String idUser,
                                            @PathVariable String idRepository,
                                            @PathVariable String newBranch) {
        JsonObject ret = null;
        String author = getUsernameById(idUser);
        String repository = getNameRepositoryById(idRepository);

        try {
            ret = Util.createBranch(author, repository, newBranch);
            if (ret == null) { return new ResponseEntity<String>(HttpStatus.NOT_FOUND); }
        } catch (Exception e) {
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<String>(ret.toString(), HttpStatus.OK);
    }

    //Creation fichier
    @RequestMapping(value = "/create/file/{branch}/{path}", method = RequestMethod.POST, produces = Constantes.APPLICATION_JSON_UTF8)
    public @ResponseBody
    ResponseEntity<String> postCreateFile(@PathVariable String idUser,
                                          @PathVariable String idRepository,
                                          @PathVariable String branch,
                                          @PathVariable String path) {
        JsonObject ret = null;
//        Long id = Long.valueOf(idUser);
//        String author = getUsernameById(idUser);
//        String repository = getNameRepositoryById(idRepository);
//
//        // Ajout du temporary file
//
//        temporaryFileService.addEntity(idUser, "hashkey", "", path, idRepository);
//
//        try {
//            if (ret == null) { return new ResponseEntity<String>(HttpStatus.NOT_FOUND); }
//        } catch (Exception e) {
//            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }

        return new ResponseEntity<String>(ret.toString(), HttpStatus.OK);
    }

    /**
     * Initialise les services utilisés par la classe
     */
    @PostConstruct
    public void init() {
        temporaryFileService = new TemporaryFileServiceImpl();
        userService = new UserServiceImpl();
        projectService = new ProjectServiceImpl();
    }
}
