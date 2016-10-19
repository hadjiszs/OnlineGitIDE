package Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import Model.User;

/**
 * Created by amaia.nazabal on 10/19/16.
 */

@RestController
@RequestMapping("/api")
public class APIController {

    @RequestMapping("/add")
    public User user(@RequestParam(value="pseudo") String pseudo,
                     @RequestParam(value="mail") String mail){
        return new User(pseudo, mail);
    }

}
