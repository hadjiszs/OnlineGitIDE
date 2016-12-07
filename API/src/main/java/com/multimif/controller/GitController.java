package com.multimif.controller;

import com.multimif.git.GitConstantes;
import com.multimif.git.Util;
import com.multimif.model.Project;
import com.multimif.model.TemporaryFile;
import com.multimif.model.User;
import com.multimif.model.UserGrant;
import com.multimif.service.*;
import com.multimif.util.Constantes;
import com.multimif.util.DataException;
import com.multimif.util.JsonUtil;
import com.multimif.util.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.json.JsonObject;
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
    private TemporaryFileService temporaryFileService = new TemporaryFileServiceImpl();

    /**
     * Service de gestion des utilisateurs
     */
    private UserService userService = new UserServiceImpl();

    /**
     * Service de gestion des projets
     */
    private ProjectService projectService = new ProjectServiceImpl();

    /**
     * Service de gestion des autorisations
     */
    private UserGrantService userGrantService = new UserGrantServiceImpl();

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
                                          @PathVariable String currentUser,
                                          @RequestParam(value = "path") String path) {
        JsonObject ret;
        TemporaryFile file;

        try {
            String raw = idUser + idRepository + path;
            file = temporaryFileService.getEntityByHash(String.valueOf(raw.hashCode()));

            return new ResponseEntity<>(JsonUtil.convertToJson(file),HttpStatus.OK);
        } catch (DataException ex) {

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
    }

    /**
     * Cette méthode récupére l'arborescence d'un commit particulier
     *
     * @param idUser       l'id de l'utilisateur
     * @param idRepository l'id du dépôt
     * @param revision     la revision spéficiée
     * @param useTemporaryFiles si "true" : les temporaryFiles de l'utilisateur sont pris en compte pour construire l'arborescence
     * @return une chaîne de characteres en format json
     */
    @RequestMapping(value = "/tree/{revision}/{useTemporaryFiles}", method = RequestMethod.GET, produces = GitConstantes.APPLICATION_JSON_UTF8)
    @ResponseBody
    public ResponseEntity<String> getTree(@PathVariable String idUser,
                                          @PathVariable String idRepository,
                                          @PathVariable String revision,
                                          @PathVariable String useTemporaryFiles) {
        JsonObject ret;

        try {
            String author = getUsernameById(idUser);
            String repository = getNameRepositoryById(idRepository);
            List<TemporaryFile> list = null;
            if (useTemporaryFiles.equals("true")) {
                list = temporaryFileService.getEntityByUserProject(Long.parseLong(idUser), Long.parseLong(idRepository));
                ret = Util.getArborescence(author, repository, revision, list,  true);
            } else {
                ret = Util.getArborescence(author, repository, revision, list,  false);
            }
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
     * Commit tous les fichiers modifiés pour la branche courante de l'utilisateur
     *
     * @param idUser       l'id de l'utilisateur
     * @param idRepository l'id du dépôt
     * @param branch       le nom de la branche
     * @return une chaîne de charactères en format json
     */
    @RequestMapping(value = "/makeCommit/{branch}", method = RequestMethod.POST, produces = GitConstantes.APPLICATION_JSON_UTF8)
    @ResponseBody
    public ResponseEntity<String> postMakeCommit(@PathVariable String idUser,
                                                 @PathVariable String currentUser,
                                                 @PathVariable String idRepository,
                                                 @PathVariable String branch,
                                                 @RequestParam(value = "message") String message) {

        JsonObject ret;
        List<TemporaryFile> files = null;

        try {
            String author = getUsernameById(idUser);
            String repository = getNameRepositoryById(idRepository);

            User commiter = userService.getEntityById(Long.parseLong(currentUser));
            files = temporaryFileService.getEntityByUserProject(Long.parseLong(currentUser), Long.parseLong(idRepository));
            System.out.println("giles: " + files);
            ret = Util.makeCommit(author, repository, branch, commiter, files, message);
            if (ret == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            try {
                temporaryFileService.deleteAllEntity(files);
            } catch (Exception ex) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        try {
            temporaryFileService.deleteAllEntity(files);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(ret.toString(), HttpStatus.OK);
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
    //ATENTION : pour cette requête : idUser = L'utilisateur en cours. En effet, on créé un nouveau dépot. C'est le current user qui est donc creator
    //La variable idRepository est inutile
    @RequestMapping(value = "/clone/{newname}/{type}", method = RequestMethod.POST, produces = GitConstantes.APPLICATION_JSON_UTF8)
    @ResponseBody
    public ResponseEntity<String> postClone(@PathVariable String idUser,
                                            @RequestParam(value="url") String url,
                                     @PathVariable String idRepository,
                                     @PathVariable String newname,
                                     @PathVariable String type) {
        JsonObject ret = null;
        Project.TypeProject newType;
        switch (type) {
            case "JAVA":
                newType = Project.TypeProject.JAVA;
                break;
            case "CMAKE":
                newType = Project.TypeProject.CMAKE;
                break;
            case "MAVEN":
                newType = Project.TypeProject.MAVEN;
                break;
            default:
                newType = Project.TypeProject.JAVA;
        }

        try {
            Project project = projectService.addEntity(newname, newType, Long.parseLong(idUser));
            userGrantService.addEntity(Long.parseLong(idUser), project.getIdProject(), UserGrant.PermissionType.ADMIN);
            String author = getUsernameById(idUser);
            String repository = getNameRepositoryById(idRepository);
            ret = Util.cloneRemoteRepo(author, repository, url);
            if (ret == null) {
                return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<String>(ret.toString(), HttpStatus.OK);
    }

    @RequestMapping(value = "/getbranch/{commitId}", method = RequestMethod.POST, produces = GitConstantes.APPLICATION_JSON_UTF8)
    @ResponseBody
    public ResponseEntity<String> postGetBranch(@PathVariable String idUser,
                                                   @PathVariable String currentUser,
                                                   @PathVariable String idRepository,
                                                   @PathVariable String commitId) {
        JsonObject ret;
        try {
            String author = getUsernameById(idUser);
            String repository = getNameRepositoryById(idRepository);

            ret = Util.getBranch(author, repository, commitId);
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
     * Creation branche
     *
     * @param idUser       l'id de l'utilisateur
     * @param currentUser  le proprietaire du dépôt
     * @param idRepository l'id du dépôt
     * @param newBranch    le nom de la branche
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

    /**
     * Savoir si oui ou non il y a des fichiers temporaires lié à un user sur un projet
     * afin de modérer le changement de branche côté client
     *
     * @param currentUser  l'utilisateur courant, celui qui fait la requete
     * @param idUser       le créateur du projet
     * @param idRepository l'id du repository courant
     * @param path         le chemin du nouveau fichier
     * @return boolean
     */
    @RequestMapping(value = "/haveTemporaryFile", method = RequestMethod.GET, produces = GitConstantes.APPLICATION_JSON_UTF8)
    @ResponseBody
    public ResponseEntity<String> haveTemporaryFile(@PathVariable String currentUser,
                                                    @PathVariable String idUser,
                                                    @PathVariable String idRepository,
                                                    @RequestParam(value="path") String path) {
        List<TemporaryFile> files = null;
        try {
            files = temporaryFileService.getEntityByUserProject(Long.parseLong(currentUser), Long.parseLong(idRepository));
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        boolean haveTemporaryFile = files.size() == 0;

        return new ResponseEntity<String>(JsonUtil.convertStringToJson("haveTemporaryFile", haveTemporaryFile? "true" : "false"), HttpStatus.ACCEPTED);
    }

    /**
     * Creation fichier
     *
     * @param currentUser  l'utilisateur courant, celui qui fait la requete
     * @param idRepository l'id du repository courant
     * @param path         le chemin du nouveau fichier
     * @return
     */
    @RequestMapping(value = "/create/file/{branch}", method = RequestMethod.GET, produces = GitConstantes.APPLICATION_JSON_UTF8)
    @ResponseBody
    public ResponseEntity<String> postCreateFile(@PathVariable String currentUser,
                                                 @PathVariable String idRepository,
                                                 @RequestParam(value="path") String path) {
        Long idrepo = Long.valueOf(idRepository);
        TemporaryFile newFile = null;

        // Ajout du temporary file vide
        try {
            newFile = temporaryFileService.addEntity(Long.valueOf(currentUser), "", path, idrepo);
            if(newFile == null)
                return new ResponseEntity<>(JsonUtil.convertToJson(new Status(Constantes.OPERATION_CODE_RATE,
                        Constantes.OPERATION_MSG_RATE)), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (DataException e) {
            LOGGER.log(Level.FINE, e.getMessage(), e);
            return new ResponseEntity<>(JsonUtil.convertToJson(new Status(Constantes.OPERATION_CODE_RATE,
                    e.getMessage())), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            LOGGER.log(Level.FINE, e.getMessage(), e);
            return new ResponseEntity<>(JsonUtil.convertToJson(new Status(Constantes.OPERATION_CODE_RATE,
                    Constantes.OPERATION_MSG_RATE)), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(JsonUtil.convertToJson(new Status(Constantes.OPERATION_CODE_REUSSI,
                Constantes.OPERATION_MSG_REUSSI)), HttpStatus.ACCEPTED);
    }

    //Merge
    @RequestMapping(value = "/merge/{branchname}/{revision}", method = RequestMethod.POST, produces = GitConstantes.APPLICATION_JSON_UTF8)
    @ResponseBody
    public ResponseEntity<String> postMerge(@PathVariable String idUser,
                                            @PathVariable String currentUser,
                                            @PathVariable String idRepository,
                                            @PathVariable String branchname,
                                            @PathVariable String revision) {
        JsonObject ret;

        try {
            String author = getUsernameById(idUser);
            String repository = getNameRepositoryById(idRepository);
            ret = Util.merge(author, repository, branchname, revision);

            if (ret == null) {
                throw new DataException("");
            }
        } catch (DataException ex) {
            LOGGER.log(Level.FINE, ex.getMessage(), ex);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            LOGGER.log(Level.FINE, e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(ret.toString(), HttpStatus.OK);
    }
}
