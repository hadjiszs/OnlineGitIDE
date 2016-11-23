package com.multimif.git;

import com.multimif.model.Project;
import com.multimif.model.TemporaryFile;
import com.multimif.model.User;
import org.eclipse.jgit.api.Git;
import org.gitective.core.CommitUtils;
import org.junit.*;
import org.junit.rules.TestName;

import javax.json.JsonObject;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private static final String NEW_DIR_NAME = "Nouveau_repository";


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
        Util.deleteRepository(USER, NEW_DIR_NAME);
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
        String nomCreator = USER;
        String nomRepository = NEW_DIR_NAME;
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
        JsonObject branches = Util.getBranches(USER, DIR_NAME);

        Assert.assertNotNull(branches);
        System.out.println(branches);
    }

    @Test
    public void testMakeCommit() throws Exception {
        User commiter = new User("42", "LeCommiter", "LeCommiter@testCommit.fr");
        Project project = new Project(DIR_NAME, Project.TypeProject.JAVA);
        List<TemporaryFile> files = new ArrayList<TemporaryFile>();
        TemporaryFile file;
        for (int i = 0; i< 5 ; i++) {
            file = new TemporaryFile(commiter, "content" + i, project, GitConstantes.REPO_FULLPATH + USER + "/" + DIR_NAME + ".git/" + "src/testfile" + i);
            files.add(file);
        }
        JsonObject res = Util.makeCommit(USER, DIR_NAME, "master", commiter, files, "MESSAGE DU COMMIT");
        Assert.assertNotNull(res);
        System.out.println(res);
    }

//    @Test
//    public void testMerge() throws  Exception {
//        Git git = Git.open(new File(GitConstantes.REPOPATH + USER + "/" + DIR_NAME + ".git"));
//        //System.out.println(git.getRepository().getBranch());
//        JsonObject res = Util.createBranch(USER, DIR_NAME, "newbranch");
//        git.checkout().setCreateBranch(false)
//                .setName("newbranch")
//                .call();
//        //System.out.println(git.getRepository().getBranch());
//        Assert.assertNotNull(res);
//        String path = "repositories/" + USER + "/" + DIR_NAME + ".git/";
//        Charset utf8 = StandardCharsets.UTF_8;
//        List<String> lines = Arrays.asList("1st line", "2nd line");
//        Files.write(Paths.get(path + "src/testfile.c"), lines, utf8);
//        git.add()
//                .addFilepattern("src/testfile.c")
//                .call();
//
//        System.out.println(git.status().call().getAdded().toString());
//        //TODO Add method Util.MakeCommit instead of :
//        git.commit()
//                .setAll(true)
//                .setAuthor("TEST", "test.test@test.fr")
//                .setMessage("Ajout d'un fichier")
//                .call();
//
//        git.checkout().setCreateBranch(false)
//                .setName("master")
//                .call();
//        List<String> lines2 = Arrays.asList("1st lineBABABA", "2nd lineBABABA");
//        Files.write(Paths.get(path + "src/testfile.c"), lines2, utf8);
//        git.add()
//                .addFilepattern("src/testfile.c")
//                .call();
//
//        System.out.println(git.status().call().getAdded().toString());
//        //TODO Add method Util.MakeCommit instead of :
//        git.commit()
//                .setAll(true)
//                .setAuthor("TEST2", "test2.test@test.fr")
//                .setMessage("Ajout d'un fichier")
//                .call();
//
//
//        //if (CommitUtils.getMaster(git.getRepository()) == CommitUtils.getHead(git.getRepository())) {System.out.println("NON !");}
//        JsonObject res2 = Util.merge(USER, DIR_NAME, "newbranch", CommitUtils.getMaster(git.getRepository()).getName());
//        Assert.assertNotNull(res2);
//        System.out.println(res2.toString());
//
//    }
}