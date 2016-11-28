package com.multimif.util;

import com.multimif.compilation.Compile;
import com.multimif.service.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.json.JsonObject;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * @author Mahmoud Ayssami
 * @version 1.0
 * @since 1.0 23/11/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/api-servlet.xml"})
public class CompileTest extends TestUtil {
    private TemporaryFileService temporaryFileService = new TemporaryFileServiceImpl();
    private ProjectService projectService = new ProjectServiceImpl();
    private UserService userService = new UserServiceImpl();

    @Test
    public void executeCompilation() throws Exception {
        Compile c = new Compile((long) 6, (long) 6, "sdhjqd");
        JsonObject ret = c.execute();
        System.out.println(ret.toString());
    }
}