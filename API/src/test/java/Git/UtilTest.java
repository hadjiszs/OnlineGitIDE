package Git;

import org.junit.*;

import javax.json.JsonObject;

/**
 * Created by p1317074 on 15/11/16.
 */
public class UtilTest {

    private static final String REMOTE_URL = "https://github.com/hadjiszs/Interpolation.git";

    @BeforeClass
    public static void init() throws Exception {
        Util.cloneRemoteRepo("userTest", "TestGitRepository", REMOTE_URL);
    }

    @AfterClass
    public static void end() throws Exception {
        Boolean test = Util.deleteRepository("userTest", "TestGitRepository");
        Assert.assertTrue(test);
    }

    @Test
    public void testGetArborescence() throws Exception {

        //Creation of the JsonObject for the new repository, revision 70ad3b45d04d53ad77f0444a3cc9e33e657e9779
        JsonObject object = Util.getArborescence("userTest", "TestGitRepository", "70ad3b45d04d53ad77f0444a3cc9e33e657e9779");
        System.out.println(object.toString());
    }

    @Test
    public void testGetContent() throws Exception {

        //Recuperation du contenu d'un fichier pour une certaine révision
        JsonObject content = Util.getContent("userTest", "TestGitRepository", "f7ef6d9d3d5ad33656aaa2996272f686e7fd485c", "src/CMakeLists.txt");
        Assert.assertNotNull(content);
        System.out.println(content);
    }

    @Test
    public void testGetBranches() throws Exception {
        //Recuperation du contenu d'un fichier pour une certaine révision
        JsonObject branches = Util.getBranches("userTest", "TestGitRepository");
        Assert.assertNotNull(branches);
        System.out.println(branches);
    }









}