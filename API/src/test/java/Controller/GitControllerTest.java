package Controller;

import Git.Util;
import Model.Project;
import Model.User;
import Service.ProjectService;
import Service.ProjectServiceImpl;
import Service.UserService;
import Service.UserServiceImpl;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by p1317074 on 17/11/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/api-servlet.xml" })
public class GitControllerTest {

    private static final String USER_NAME = "userTest";
    private static final String MAIL = "mail@mail.com";
    private static final String DIR_NAME = "TestGitRepository";

    private static User USER = null;
    private static Project PROJECT = null;
    private MockMvc mockMvc;
    private static final String REMOTE_URL = "https://github.com/hadjiszs/Interpolation.git";

    @Before
    public void init1() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new GitController()).build();
    }

    @BeforeClass
    public static void init() throws Exception {
        Util.cloneRemoteRepo("userTest", "TestGitRepository", REMOTE_URL);

        // Service
        UserService userService = new UserServiceImpl();
        ProjectService projectService = new ProjectServiceImpl();

        Project project = new Project("TestGitRepository", "0", Project.TypeProject.JAVA, "TestGitRepository");

        USER = userService.addEntity(USER_NAME, MAIL, "hashkey");
        PROJECT = projectService.addEntity(project, USER.getIdUser());
    }

    @AfterClass
    public static void end() throws Exception {
        Boolean test = Util.deleteRepository("userTest", "TestGitRepository");
        Assert.assertTrue(test);
    }

    @Test
    public void testGetFile() throws Exception {

        mockMvc.perform(get("/git/" + USER.getIdUser() + "/" + PROJECT.getIdProject() + "/" + "70ad3b45d04d53ad77f0444a3cc9e33e657e9779" + "?path=src/CMakeLists.txt"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestControllerUtils.APPLICATION_JSON_UTF8))
                .andExpect(content().string(Git.Util.getContent(USER_NAME, DIR_NAME, "70ad3b45d04d53ad77f0444a3cc9e33e657e9779", "src/CMakeLists.txt").toString()));

    }

    @Test
    public void testGetTree() throws Exception {
        mockMvc.perform(get("/git/" + USER.getIdUser() + "/" + PROJECT.getIdProject()  + "/tree/70ad3b45d04d53ad77f0444a3cc9e33e657e9779"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestControllerUtils.APPLICATION_JSON_UTF8))
                .andExpect(content().string(Git.Util.getArborescence(USER_NAME, DIR_NAME, "70ad3b45d04d53ad77f0444a3cc9e33e657e9779").toString()));

    }

    @Test
    public void testGetBranches() throws Exception {
        mockMvc.perform(get("/git/" + USER.getIdUser() + "/" + PROJECT.getIdProject()  + "/branches"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestControllerUtils.APPLICATION_JSON_UTF8))
                .andExpect(content().string(Git.Util.getBranches(USER_NAME, DIR_NAME).toString()));

    }

    @Test
    public void testGetListCommit() throws Exception {
        mockMvc.perform(get("/git/" + USER.getIdUser() + "/" + PROJECT.getIdProject() + "/listCommit/" + "6973050f16380117b412aef271bf7993a16694cf"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestControllerUtils.APPLICATION_JSON_UTF8))
                .andExpect(content().string(Git.Util.getCommits(USER_NAME, DIR_NAME, "6973050f16380117b412aef271bf7993a16694cf").toString()));
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

    @Test
    public void testPostMerge() throws Exception {
        mockMvc.perform(post("/git/" + USER.getIdUser() + "/" + PROJECT.getIdProject()  + "/merge/" + "master/6973050f16380117b412aef271bf7993a16694cf"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestControllerUtils.APPLICATION_JSON_UTF8))
                .andExpect(content().string("{\"status\":\"Merged\"}"));
    }
}