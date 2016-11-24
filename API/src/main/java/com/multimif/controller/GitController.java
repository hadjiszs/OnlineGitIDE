package com.multimif.controller;

import com.multimif.git.GitConstantes;
import com.multimif.git.Util;
import com.multimif.model.Project;
import com.multimif.model.TemporaryFile;
import com.multimif.model.User;
import com.multimif.service.*;
import com.multimif.util.Constantes;
import com.multimif.util.DataException;
import com.multimif.util.JsonUtil;
import com.multimif.util.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.json.JsonObject;
import java.util.List;

/**
 * Created by p1317074 on 16/11/16.
 */

@Controller
// currentUser = celui qui a la session
// idUser = l'id du creator du projet
@RequestMapping("/git/{currentUser}/{idUser}/{idRepository}")
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
    @RequestMapping(value = "/{revision}", method = RequestMethod.GET, produces = GitConstantes.APPLICATION_JSON_UTF8)
    public @ResponseBody
    ResponseEntity<String> getFile(@PathVariable String currentUser,
                                   @PathVariable String idUser,
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
    @RequestMapping(value = "/tree/{revision}", method = RequestMethod.GET, produces = GitConstantes.APPLICATION_JSON_UTF8)
    public @ResponseBody
    ResponseEntity<String> getTree(@PathVariable String currentUser,
                                   @PathVariable String idUser,
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
    @RequestMapping(value = "/branches", method = RequestMethod.GET, produces = GitConstantes.APPLICATION_JSON_UTF8)
    public @ResponseBody
    ResponseEntity<String> getBranches(@PathVariable String currentUser,
                                       @PathVariable String idUser,
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

    //Info d'un commit
    @RequestMapping(value = "/info/{revision}", method = RequestMethod.GET, produces = GitConstantes.APPLICATION_JSON_UTF8)
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
    @RequestMapping(value = "/listCommit/{branch}", method = RequestMethod.GET, produces = GitConstantes.APPLICATION_JSON_UTF8)
    public @ResponseBody
    ResponseEntity<String> getListCommit(@PathVariable String idUser,
                                         @PathVariable String currentUser,
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

    @RequestMapping(value = "/makeCommit/{branch}/{currentUser}", method = RequestMethod.POST, produces = GitConstantes.APPLICATION_JSON_UTF8)
    public @ResponseBody
    ResponseEntity<String> postMakeCommit(@PathVariable String idUser,
                                          @PathVariable String currentUser,
                                          @PathVariable String idRepository,
                                          @PathVariable String branch,
                                          @RequestParam(value = "message") String message) {
        TemporaryFileService fileService = new TemporaryFileServiceImpl();
        UserService userService = new UserServiceImpl();

        JsonObject ret = null;
        String author = getUsernameById(idUser);
        String repository = getNameRepositoryById(idRepository);
        List<TemporaryFile> files = null;

        try{
            User commiter = userService.getEntityById(Long.parseLong(currentUser));
            files = fileService.getEntityByUserProject(Long.parseLong(currentUser), Long.parseLong(idRepository));
            ret = Util.makeCommit(author, repository, branch, commiter, files, message);
            if (ret == null) {return new ResponseEntity<String>(HttpStatus.NOT_FOUND); }
        }catch (Exception e){
            try {
                fileService.deleteAllEntity(files);
            } catch (Exception ex) {
                return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        try {
            fileService.deleteAllEntity(files);
        } catch (Exception e) {
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>(ret.toString(),HttpStatus.OK);
    }

    //diff entre le fichier en cours de modification et le dernier commit
    @RequestMapping(value = "/diff/{branch}/{path}", method = RequestMethod.GET, produces = GitConstantes.APPLICATION_JSON_UTF8)
    public @ResponseBody
    ResponseEntity<String> getDiffBranch(@PathVariable String idUser,
                                         @PathVariable String currentUser,
                                         @PathVariable String idRepository,
                                         @PathVariable String branch,
                                         @PathVariable String path) {
        //TODO
        return null;
    }

    //diff concernant un commit en particulier
    @RequestMapping(value = "/showCommit/{revision}", method = RequestMethod.GET, produces = GitConstantes.APPLICATION_JSON_UTF8)
    public @ResponseBody
    ResponseEntity<String> getShowCommit(@PathVariable String idUser,
                                         @PathVariable String currentUser,
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
    @RequestMapping(value = "/archive/{branch}", method = RequestMethod.GET, produces = GitConstantes.APPLICATION_JSON_UTF8)
    public @ResponseBody
    ResponseEntity<String> getArchive(@PathVariable String idUser,
                                      @PathVariable String currentUser,
                                      @PathVariable String idRepository,
                                      @PathVariable String branch) {
        //TODO
        return null;
    }

    //Clone un repo distant
    @RequestMapping(value = "/clone/{url}", method = RequestMethod.POST, produces = GitConstantes.APPLICATION_JSON_UTF8)
    public @ResponseBody
    ResponseEntity<String> postClone(@PathVariable String idUser,
                                     @PathVariable String currentUser,
                                     @PathVariable String idRepository,
                                     @PathVariable String url) {
        //TODO
        return null;
    }

    //Creation branche
    @RequestMapping(value = "/create/branch/{newBranch}", method = RequestMethod.POST, produces = GitConstantes.APPLICATION_JSON_UTF8)
    public @ResponseBody
    ResponseEntity<String> postCreateBranch(@PathVariable String idUser,
                                            @PathVariable String currentUser,
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

    // TODO: requete pour savoir si oui ou non il y a des fichiers temporaires lié
    // à un user et à un projet

    //Creation fichier
    /// SALUT bonjour
    /**
     *
     * @param idUser       le créateur du projet
     * @param currentUser  l'utilisateur courant, celui qui fait la requete
     * @param idRepository l'id du repository courant
     * @param path         le chemin du nouveau fichier
     * @return
     */
    @RequestMapping(value = "/create/file", method = RequestMethod.GET, produces = GitConstantes.APPLICATION_JSON_UTF8)
    public @ResponseBody
    ResponseEntity<String> postCreateFile(@PathVariable String idUser,
                                          @PathVariable String currentUser,
                                          @PathVariable String idRepository,
                                          @PathVariable String branch,
                                          @RequestParam(value="path") String path) {
        Long idrepo = Long.valueOf(idRepository);

        // Ajout du temporary file, vide
        try {
            if(temporaryFileService.addEntity(Long.valueOf(currentUser), "", path, idrepo) == null)
                return new ResponseEntity<>(JsonUtil.convertToJson(new Status(Constantes.OPERATION_CODE_RATE,
                        Constantes.OPERATION_MSG_RATE)), HttpStatus.ACCEPTED);
        } catch (DataException e) {
            e.printStackTrace();
            return new ResponseEntity<>(JsonUtil.convertToJson(new Status(Constantes.OPERATION_CODE_RATE,
                    Constantes.OPERATION_MSG_RATE)), HttpStatus.ACCEPTED);
        }

        return new ResponseEntity<>(JsonUtil.convertToJson(new Status(Constantes.OPERATION_CODE_REUSSI,
                Constantes.OPERATION_MSG_REUSSI)), HttpStatus.ACCEPTED);
    }

    //Merge
    @RequestMapping(value = "/merge/{branchname}/{revision}", method = RequestMethod.POST, produces = GitConstantes.APPLICATION_JSON_UTF8)
    public @ResponseBody
    ResponseEntity<String> postMerge(@PathVariable String author,
                                     @PathVariable String currentUser,
                                     @PathVariable String repository,
                                     @PathVariable String branchname,
                                     @PathVariable String revision){
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
