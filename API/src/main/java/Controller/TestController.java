package Controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.stereotype.Controller;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.io.IOException;

@Controller
@RequestMapping("/test")
public class TestController {

   protected final Log logger = LogFactory.getLog(getClass());

    @RequestMapping(value="/create", method = RequestMethod.GET) // /api/test/create
    public ModelAndView create(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.info("Nouveau test");
        System.out.println("sqkjdkjdkjqdsl");
        return new ModelAndView("/view/Test/createTest.jsp");
    }

    @RequestMapping("/")
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        logger.info("Returning hello view");
        System.out.println("salut");

        return new ModelAndView("/view/Test/test.jsp");
    }

}