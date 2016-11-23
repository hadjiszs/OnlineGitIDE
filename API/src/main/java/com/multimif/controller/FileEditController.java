package com.multimif.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hadjiszs on 21/10/16.
 */
@RestController
@RequestMapping("/file")
public class FileEditController {

    /*public FichierUtiliseServiceImpl fichierUtiliseServiceImpl;
    UserService userService;
    private static final Logger LOGGER = Logger.getLogger( FileEditController.class.getName() );

    @RequestMapping(value = "/getByMail", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<String> getByMail(@RequestParam(value="idProject") Long id,
                               @RequestParam(value="IdProject") Long idProject) {

        fichierUtiliseServiceImpl.getEntityById(id, projet);
        User user;
        try{
            user = userService.getEntityById(id);
        }catch(Exception ex){
            return new ResponseEntity<>(JsonUtil.convertToJson(new Status(GitConstantes.OPERATION_CODE_RATE,
                    ex.getMessage())), HttpStatus.NOT_FOUND);
        }

        // @FIXME : renvoyer une réponse correcte
        return new ResponseEntity<>(JsonUtil.convertToJson(user), HttpStatus.ACCEPTED);

        //return new ResponseEntity<>("tmp ok", HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<String> edit(@RequestParam(value="mail") String mail,
                                 @RequestParam(value="id") String id, // ID hexa corresponodant à l'ObjectID
                                 @RequestParam(value="contenue") String contenue) {

        User u = null;

        try {
            u = userService.getEntityByMail(mail);
        } catch (Exception e) {
            LOGGER.log(Level.FINE, e.toString(), e);
        }

        fichierUtiliseServiceImpl.editEntity(u, id, contenue);

        try{
            user = userService.getEntityByMail(mail);
        }catch(Exception ex){
            return new ResponseEntity<String>(JsonUtil.convertToJson(new JsonUtil.Status(JsonUtil.GitConstantes.OPERATION_CODE_RATE,
                    ex.getMessage())), HttpStatus.NOT_FOUND);
        }

        // @FIXME : renvoyer une réponse correcte
        return new ResponseEntity<>("tmp ok", HttpStatus.ACCEPTED);
    }

    @PostConstruct
    public void init() {
        fichierUtiliseServiceImpl = new FichierUtiliseServiceImpl();
        userService = new UserServiceImpl();
    }*/
}
