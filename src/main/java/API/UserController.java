package API;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.io.IOException;

@Controller
@RequestMapping("/user")  // /api/user/
public class UserController {

    protected final Log logger = LogFactory.getLog(getClass());

    @RequestMapping("/")
    public String defaultRequest(HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws ServletException, IOException {
        logger.info("User");
        System.out.println("salut2");
        model.addAttribute("message", "Hello Spring MVC Framework!");
        return "/User/user";
    }

}