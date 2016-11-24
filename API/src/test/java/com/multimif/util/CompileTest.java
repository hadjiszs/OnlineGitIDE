package com.multimif.util;

import com.multimif.compilation.Compile;
import com.multimif.model.Project;
import com.multimif.service.ProjectService;
import com.multimif.service.ProjectServiceImpl;
import com.multimif.service.UserService;
import com.multimif.service.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * @author Mahmoud Ayssami
 * @version 1.0
 * @since 1.0 23/11/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/api-servlet.xml"})
public class CompileTest extends TestUtil {

    private ProjectService projectService = new ProjectServiceImpl();
    private UserService userService = new UserServiceImpl();


    @Test
    public void executeCompilation() throws Exception {


        Compile c = new Compile();
        String result = c.executeCompilation((long) 1, (long) 1, "sdhjqd");
        System.out.println(result);


    }


    @Test
    public void suppressEntityTest() {
        boolean result = false;
        Exception exception = null;

        try {
            result = projectService.deleteEntity(project.getIdProject(), user.getIdUser());
        } catch (Exception e) {
            exception = e;
        }

        assertNull(exception);
        assertTrue(result);

        Project proj = null;

        try {
            proj = projectService.getEntityById(project.getIdProject());
        } catch (DataException e) {
            exception = e;
        }

        assertNotNull(exception);
        assertNull(proj);

        exception = null;

        try {
            result = userService.deleteEntity(user.getIdUser());
        } catch (DataException e) {
            exception = e;
        }

        assertNull(exception);
        assertTrue(result);
    }


}