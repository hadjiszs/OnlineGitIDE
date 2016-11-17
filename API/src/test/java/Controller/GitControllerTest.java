package Controller;

import Git.Util;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by p1317074 on 17/11/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class, WebApplicationContext.class})
@WebAppConfiguration
public class GitControllerTest {

    private MockMvc mockMvc;
    private static final String REMOTE_URL = "https://github.com/hadjiszs/Interpolation.git";

    @Before
    public void init() throws Exception {
        Util.cloneRemoteRepo("userTest", "TestGitRepository.git", REMOTE_URL);
    }

    @After
    public void end() throws Exception {
        Boolean test = Util.deleteRepository("userTest", "TestGitRepository.git");
        Assert.assertTrue(test);
    }

    @Test
    public void testGetFile() throws Exception {

        mockMvc.perform(get("/git/userTest/TestGitRepository/revision/src/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestControllerUtils.APPLICATION_JSON_UTF8))
                .andExpect(content().string(Git.Util.getContent("testUser", "testRepo", "revision", "src/").toString()));

    }

    @Test
    public void testGetTree() throws Exception {

    }

    @Test
    public void testGetBranches() throws Exception {

    }

    @Test
    public void testGetListCommit() throws Exception {

    }

    @Test
    public void testPostMakeCommit() throws Exception {

    }

    @Test
    public void testGetDiffBranch() throws Exception {

    }

    @Test
    public void testGetShowCommit() throws Exception {

    }

    @Test
    public void testGetArchive() throws Exception {

    }

    @Test
    public void testPostClone() throws Exception {

    }

    @Test
    public void testPostCreateBranch() throws Exception {

    }

    @Test
    public void testPostCreateFile() throws Exception {

    }
}