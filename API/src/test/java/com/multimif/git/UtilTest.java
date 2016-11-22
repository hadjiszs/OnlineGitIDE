package com.multimif.git;

import org.junit.*;
import org.junit.rules.TestName;

import javax.json.JsonObject;

import static org.junit.Assert.assertNotNull;


/**
 * @author p1317074
 * @version 1.0
 * @since 1.0 15/11/16.
 */
public class UtilTest {

    private static final String REMOTE_URL = "https://github.com/hadjiszs/Interpolation.git";
    private static final String USER = "userTest";
    private static final String DIR_NAME = "TestGitRepository";

    @Rule public TestName name = new TestName();

    @Before
    public void setUp() throws Exception {
        System.out.println("--- TEST : "+ name.getMethodName() +" ---");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("  ");
    }

    @BeforeClass
    public static void init() throws Exception {
        Util.cloneRemoteRepo(USER, DIR_NAME, REMOTE_URL);
    }

    @AfterClass
    public static void end() throws Exception {
        System.out.println("# Suppression du dépôt ; fin tests #");
        Util.deleteRepository(USER, DIR_NAME);
    }

    @Test
    public void testGetArborescence() throws Exception {
        // Creation of the JsonObject for the new repository
        // Recuperation de l'aborescence associé au commit de la revision suivante
        String revision = "70ad3b45d04d53ad77f0444a3cc9e33e657e9779";

        JsonObject object = Util.getArborescence(USER, DIR_NAME, revision);
        System.out.println(object.toString());

        assertNotNull("dsd", object);
    }

    @Test
    public void testGetContent() throws Exception {
        //Recuperation du contenu d'un fichier pour une certaine révision
        String revision = "70ad3b45d04d53ad77f0444a3cc9e33e657e9779";
        String path = "src/CMakeLists.txt";

        JsonObject content = Util.getContent(USER, DIR_NAME, revision, path);

        System.out.println(content);
    }

    @Test
    public void testCreateBranch() throws Exception {
        // Creation d'une branche
        String nomBranche = "nouvelle_branche";
        GitStatus statusAttendu = GitStatus.BRANCH_CREATED;

        JsonObject content = Util.createBranch(USER, DIR_NAME, nomBranche);

        Assert.assertEquals(
                "Erreur lors de création de branche : "+ nomBranche,
                content.get("code").toString(),
                statusAttendu.toString());
    }

    @Test
    public void testCreateRepository() throws Exception {
        // Creation d'une branche
        String nomCreator = "johndoe";
        String nomRepository = "Nouveau_Repository";
        GitStatus statusAttendu = GitStatus.REPOSITORY_CREATED;

        JsonObject content = Util.createRepository(nomCreator, nomRepository);

        Assert.assertEquals(
                "Erreur de création du repository : "+ nomCreator +"/" + nomRepository,
                content.get("code").toString(),
                statusAttendu.toString());
    }

    @Test
    public void testShowCommit() throws Exception {
        // Montre les diff entre un commit et son/ses parent(s)
        String revision = "3edb3fd64790ee408d9eb59c7fad0115ba58caff";

        JsonObject content = Util.showCommit(USER, DIR_NAME, revision);

        System.out.println(content);
    }

    @Test
    public void testGetBranches() throws Exception {
        //Recuperation du contenu d'un fichier pour une certaine révision
        JsonObject branches = Util.getBranches(USER, DIR_NAME);

        Assert.assertNotNull(branches);
        System.out.println(branches);
    }
}