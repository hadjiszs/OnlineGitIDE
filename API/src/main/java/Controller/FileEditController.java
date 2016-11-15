package Controller;

import Model.User;
import Service.FileService;
import Service.UserService;
import Service.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

/**
 * Created by hadjiszs on 21/10/16.
 */
@RestController
@RequestMapping("/file")
public class FileEditController {
    public FileService fileService;

    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<String> get(@RequestParam(value="id") Long id,
                               @RequestParam(value="projet") String projet) {

        //FileService.get(id, projet);

//        try{
//            user = userService.getEntityByMail(mail);
//        }catch(Exception ex){
//            return new ResponseEntity<String>(Util.convertToJson(new Util.Status(Util.Constantes.OPERATION_CODE_RATE,
//                    ex.getMessage())), HttpStatus.NOT_FOUND);
//        }
//
//        return new ResponseEntity<String>(Util.convertToJson(user), HttpStatus.OK);
        // @FIXME : renvoyer une réponse correcte
        return new ResponseEntity<String>("tmp ok", HttpStatus.OK);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<String> edit(@RequestParam(value="mail") String mail,
                                 @RequestParam(value="id") String id, // ID hexa corresponodant à l'ObjectID
                                 @RequestParam(value="contenue") String contenue) {
        UserService userService = new UserServiceImpl();
        User u = null;

        try {
            u = userService.getEntityByMail(mail);
        } catch (Exception e) {
            e.printStackTrace();
        }

        fileService.edit(u, id, contenue);

//        try{
//            user = userService.getEntityByMail(mail);
//        }catch(Exception ex){
//            return new ResponseEntity<String>(Util.convertToJson(new Util.Status(Util.Constantes.OPERATION_CODE_RATE,
//                    ex.getMessage())), HttpStatus.NOT_FOUND);
//        }
//
        // @FIXME : renvoyer une réponse correcte
        return new ResponseEntity<String>("tmp ok", HttpStatus.OK);
    }

    @PostConstruct
    public void init() {
        fileService = new FileService();
    }
}
