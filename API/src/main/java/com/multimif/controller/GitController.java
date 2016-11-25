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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author p1317074
 * @version 1.0
 * @since 1.0 16/11/16.
 */
@Controller
// currentUser = celui qui a la session
// idUser = l'id du creator du projet
@RequestMapping("/git/{currentUser}/{idUser}/{idRepository}")
public class GitController {

    private static final Logger LOGGER = Logger.getLogger(GitController.class.getName());

    /**
     * Service de fichier temporaire
     */
    private TemporaryFileService temporaryFileService;

    /*
     * Service de gestion de droit
     */
    private UserService userService;

    /**
     * Service de gestion de droit
     */
    private ProjectService projectService;

    /**
     * Methode interne pour recuperer le pseudo d'un user à partir de son id
     *
     * @param idUser id de l'user
     * @return son pseudo
     */
    private String getUsernameById(String idUser) throws DataException {
        Long id = Long.valueOf(idUser);
        User user = userService.getEntityById(id);

        return user.getUsername();
    }

    /**
     * Methode interne pour recuperer le nom de repository par l'id
     *
     * @param idRepository id de l'user
     * @return nom du repository
     */
    private String getNameRepositoryById(String idRepository) throws DataException {
        Long id = Long.valueOf(idRepository);
        Project project = projectService.getEntityById(id);

        return project.getName();
    }

    /**
     * Contenu d'un fichier
     *
     * @param idUser       l'id de l'utilisateur
     * @param idRepository l'id du dépôt
     * @param revision     la revision spéficiée
     * @param path         l'addresse du fichier
     * @return une chaîne de characteres en format json
     */
    @RequestMapping(value = "/{revision}", method = RequestMethod.GET, produces = GitConstantes.APPLICATION_JSON_UTF8)
    @ResponseBody
    public ResponseEntity<String> getFile(@PathVariable String idUser,
                                          @PathVariable String idRepository,
                                          @PathVariable String revision,
                                          @RequestParam(value = "path") String path) {
        JsonObject ret;

        // TODO: verifier que le fichier (path) n'existe pas deja dans TemporaryFile

        try {
            String author = getUsernameById(idUser);
            String repository = getNameRepositoryById(idRepository);

            ret = Util.getContent(author, repository, revision, path);
            if (ret == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (DataException e) {
            LOGGER.log(Level.FINE, e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            LOGGER.log(Level.FINE, e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(ret.toString(), HttpStatus.OK);
    }

    /**
     * Cette méthode récupére l'arborescence d'un commit particulier
     *
     * @param idUser       l'id de l'utilisateur
     * @param idRepository l'id du dépôt
     * @param revision     la revision spéficiée
     * @return une chaîne de characteres en format json
     */
    @RequestMapping(value = "/tree/{revision}", method = RequestMethod.GET, produces = GitConstantes.APPLICATION_JSON_UTF8)
    @ResponseBody
    public ResponseEntity<String> getTree(@PathVariable String idUser,
                                          @PathVariable String idRepository,
                                          @PathVariable String revision) {
        JsonObject ret;

        try {
            String author = getUsernameById(idUser);
            String repository = getNameRepositoryById(idRepository);

            ret = Util.getArborescence(author, repository, revision);
            if (ret == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (DataException e) {
            LOGGER.log(Level.FINE, e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            LOGGER.log(Level.FINE, e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(ret.toString(), HttpStatus.OK);
    }

    /**
     * Cette méthode liste les branches
     *
     * @param idUser       l'id de l'utilisateur
     * @param idRepository l'id du dépôt
     * @return une chaîne de characteres en format json
     */
    @RequestMapping(value = "/branches", method = RequestMethod.GET, produces = GitConstantes.APPLICATION_JSON_UTF8)
    @ResponseBody
    public ResponseEntity<String> getBranches(@PathVariable String idUser,
                                              @PathVariable String idRepository) {
        JsonObject ret;

        try {
            String author = getUsernameById(idUser);
            String repository = getNameRepositoryById(idRepository);

            ret = Util.getBranches(author, repository);
            if (ret == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (DataException e) {
            LOGGER.log(Level.FINE, e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            LOGGER.log(Level.FINE, e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(ret.toString(), HttpStatus.OK);
    }

    /**
     * Retourne les meta infos liées à un commit
     *
     * @param idUser       l'id de l'utilisateur
     * @param idRepository l'id du dépôt
     * @param revision     la revision spéficiée
     * @return une chaîne de characteres en format json
     */
    @RequestMapping(value = "/info/{revision}", method = RequestMethod.GET, produces = GitConstantes.APPLICATION_JSON_UTF8)
    @ResponseBody
    public ResponseEntity<String> getInfoCommit(@PathVariable String idUser,
                                                @PathVariable String idRepository,
                                                @PathVariable String revision) {
        JsonObject ret;

        try {
            String author = getUsernameById(idUser);
            String repository = getNameRepositoryById(idRepository);

            ret = Util.getInfoCommit(author, repository, revision);
            if (ret == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (DataException e) {
            LOGGER.log(Level.FINE, e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            LOGGER.log(Level.FINE, e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(ret.toString(), HttpStatus.OK);
    }

    /**
     * Retourne la liste des commits d'une branche
     *
     * @param idUser       l'id de l'utilisateur
     * @param idRepository l'id du dépôt
     * @param branch       le nom de la branche
     * @return une chaîne de characteres en format json
     */
    @RequestMapping(value = "/listCommit/{branch}", method = RequestMethod.GET, produces = GitConstantes.APPLICATION_JSON_UTF8)
    @ResponseBody
    public ResponseEntity<String> getListCommit(@PathVariable String idUser,
                                                @PathVariable String idRepository,
                                                @PathVariable String branch) {
        JsonObject ret;

        try {
            String author = getUsernameById(idUser);
            String repository = getNameRepositoryById(idRepository);

            ret = Util.getCommits(author, repository, branch);
            if (ret == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (DataException e) {
            LOGGER.log(Level.FINE, e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            LOGGER.log(Level.FINE, e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(ret.toString(), HttpStatus.OK);
    }


    /**
     * Commit tout les fichiers modifiés pour la branche courante de l'utilisateur
     *
     * @param idUser       l'id de l'utilisateur
     * @param idRepository l'id du dépôt
     * @param branch       le nom de la branche
     * @return une chaîne de characteres en format json
     */
    // TODO: renvoyer l'id de la revision
    @RequestMapping(value = "/makeCommit/{branch}", method = RequestMethod.POST, produces = GitConstantes.APPLICATION_JSON_UTF8)
    @ResponseBody
    public ResponseEntity<String> postMakeCommit(@PathVariable String idUser,
                                          @PathVariable String currentUser,
                                          @PathVariable String idRepository,
                                          @PathVariable String branch,
                                          @RequestParam(value = "message") String message) {
        TemporaryFileService fileService = new TemporaryFileServiceImpl();

        JsonObject ret;
        List<TemporaryFile> files = null;

        try {
            String author = getUsernameById(idUser);
            String repository = getNameRepositoryById(idRepository);

            User commiter = userService.getEntityById(Long.parseLong(currentUser));
            files = fileService.getEntityByUserProject(Long.parseLong(currentUser), Long.parseLong(idRepository));
            ret = Util.makeCommit(author, repository, branch, commiter, files, message);
            if (ret == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            try {
                fileService.deleteAllEntity(files);
            } catch (Exception ex) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        try {
            fileService.deleteAllEntity(files);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(ret.toString(), HttpStatus.OK);
    }

    /**
     * diff entre le fichier en cours de modification et le dernier commit
     *
     * @param idUser       l'id de l'utilisateur
     * @param idRepository l'id du dépôt
     * @param branch       le nom de la branche
     * @param path         l'addresse du fichier
     * @return une chaîne de characteres en format json
     */
    @RequestMapping(value = "/diff/{branch}/{path}", method = RequestMethod.GET, produces = GitConstantes.APPLICATION_JSON_UTF8)
    @ResponseBody
    public ResponseEntity<String> getDiffBranch(@PathVariable String idUser,
                                         @PathVariable String currentUser,
                                         @PathVariable String idRepository,
                                         @PathVariable String branch,
                                         @PathVariable String path) {
        //TODO
        return null;
    }

    /**
     * diff concernant un commit en particulier
     *
     * @param idUser       l'id de l'utilisateur
     * @param idRepository l'id du dépôt
     * @param revision     la revision spéficiée
     * @return une chaîne de characteres en format json
     */
    @RequestMapping(value = "/showCommit/{revision}", method = RequestMethod.GET, produces = GitConstantes.APPLICATION_JSON_UTF8)
    @ResponseBody
    public ResponseEntity<String> getShowCommit(@PathVariable String idUser,
                                         @PathVariable String currentUser,
                                         @PathVariable String idRepository,
                                         @PathVariable String revision) {
        JsonObject ret;

        try {
            String author = getUsernameById(idUser);
            String repository = getNameRepositoryById(idRepository);

            ret = Util.showCommit(author, repository, revision);
            if (ret == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(ret.toString(), HttpStatus.OK);
    }

    /**
     * Téléchargement du dépot sous forme d'archive
     *
     * @param idUser       l'id de l'utilisateur
     * @param idRepository l'id du dépôt
     * @param branch       le nom de la branche le nom de la branche
     * @return une chaîne de characteres en format json chaîne de characteres en format json avec le nom du fichier zip
     */
    @RequestMapping(value = "/archive/{branch}", method = RequestMethod.GET, produces = GitConstantes.APPLICATION_JSON_UTF8)
    @ResponseBody
    public ResponseEntity<String> getArchive(@PathVariable String idUser,
                                             @PathVariable String idRepository,
                                             @PathVariable String branch) {
        JsonObject ret;

        try {
            String author = getUsernameById(idUser);
            String repository = getNameRepositoryById(idRepository);

            ret = Util.getArchive(author, repository, branch);
        } catch (DataException e) {
            LOGGER.log(Level.FINE, e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            LOGGER.log(Level.FINE, e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(ret.toString(), HttpStatus.OK);
    }

    /**
     * Clone un repo distant
     *
     * @param idUser       l'id de l'utilisateur
     * @param idRepository l'id du dépôt
     * @param url          l'addresse
     * @return une chaîne de characteres en format json
     */
    @RequestMapping(value = "/clone/{url}", method = RequestMethod.POST, produces = GitConstantes.APPLICATION_JSON_UTF8)
    @ResponseBody
    public ResponseEntity<String> postClone(@PathVariable String idUser,
                                            @PathVariable String currentUser,
                                            @PathVariable String idRepository,
                                            @PathVariable String url) {
        //TODO clone
        return null;
    }

    // TODO: get nom de branche à partir d'une revision de commit

    /**
     * Creation branche
     *
     * @param idUser l'id de l'utilisateur
     * @param currentUser  le proprietaire du dépôt
     * @param idRepository l'id du dépôt
     * @param newBranch le nom de la branche
     * @return une chaîne de characteres en format json
     */
    @RequestMapping(value = "/create/branch/{newBranch}", method = RequestMethod.POST, produces = GitConstantes.APPLICATION_JSON_UTF8)
    @ResponseBody
    public ResponseEntity<String> postCreateBranch(@PathVariable String idUser,
                                                   @PathVariable String currentUser,
                                                   @PathVariable String idRepository,
                                                   @PathVariable String newBranch) {
        JsonObject ret;

        try {
            String author = getUsernameById(idUser);
            String repository = getNameRepositoryById(idRepository);

            ret = Util.createBranch(author, repository, newBranch);
            if (ret == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (DataException e) {
            LOGGER.log(Level.FINE, e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            LOGGER.log(Level.FINE, e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(ret.toString(), HttpStatus.OK);
    }

    // TODO: requete pour savoir si oui ou non il y a des fichiers temporaires lié
    // à un user et à un projet

    /**
     * Creation fichier
     *
     * @param idUser       le créateur du projet
     * @param currentUser  l'utilisateur courant, celui qui fait la requete
     * @param branch       la branche courante de l'utilisateur
     * @param idRepository l'id du repository courant
     * @param path         le chemin du nouveau fichier
     * @return
     */
    @RequestMapping(value = "/create/file/{branch}", method = RequestMethod.GET, produces = GitConstantes.APPLICATION_JSON_UTF8)
    @ResponseBody
    public ResponseEntity<String> postCreateFile(@PathVariable String idUser,
                                          @PathVariable String currentUser,
                                          @PathVariable String branch,
                                          @PathVariable String idRepository,
                                          @RequestParam(value="path") String path) {
        UserService userService = new UserServiceImpl();

        JsonObject ret = null;
        String author = null;
        String repository = null;

        try {
            author = getUsernameById(idUser);
            repository = getNameRepositoryById(idRepository);
        } catch (DataException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Long idrepo = Long.valueOf(idRepository);
        TemporaryFile newFile = null;

        // Ajout du temporary file vide
        try {
            newFile = temporaryFileService.addEntity(Long.valueOf(currentUser), "", "", path, idrepo);
            if(newFile == null)
                return new ResponseEntity<>(JsonUtil.convertToJson(new Status(Constantes.OPERATION_CODE_RATE,
                        Constantes.OPERATION_MSG_RATE)), HttpStatus.ACCEPTED);
        } catch (DataException e) {
            LOGGER.log(Level.FINE, e.getMessage(), e);
            return new ResponseEntity<>(JsonUtil.convertToJson(new Status(Constantes.OPERATION_CODE_RATE,
                    Constantes.OPERATION_MSG_RATE)), HttpStatus.ACCEPTED);
        }

        // commit du nouveau fichier pour qu'il soit accessible lors du get arborescence
        User commiter = null;
        try {
            commiter = userService.getEntityById(Long.parseLong(currentUser));
        } catch (DataException e) {
            e.printStackTrace();
        }

        ArrayList<TemporaryFile> file = new ArrayList<>();
        file.add(newFile);

        try {
            ret = Util.makeCommit(author, repository, branch, commiter, file, "add new file: "+path);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (ret == null)
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(JsonUtil.convertToJson(new Status(Constantes.OPERATION_CODE_REUSSI,
                Constantes.OPERATION_MSG_REUSSI)), HttpStatus.ACCEPTED);
    }

    //Merge
    @RequestMapping(value = "/merge/{branchname}/{revision}", method = RequestMethod.POST, produces = GitConstantes.APPLICATION_JSON_UTF8)
    @ResponseBody
    public ResponseEntity<String> postMerge(@PathVariable String author,
                                     @PathVariable String currentUser,
                                     @PathVariable String repository,
                                     @PathVariable String branchname,
                                     @PathVariable String revision) {
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

        return new ResponseEntity<>(ret.toString(), HttpStatus.OK);
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
