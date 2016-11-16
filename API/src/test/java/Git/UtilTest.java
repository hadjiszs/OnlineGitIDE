package Git;

import junit.framework.TestCase;
import org.eclipse.jgit.api.Git;
import org.junit.Test;

import javax.json.JsonObject;
import java.io.File;

/**
 * Created by p1317074 on 15/11/16.
 */
public class UtilTest extends TestCase {

    private static final String REMOTE_URL = "https://github.com/hadjiszs/Interpolation.git";


    @Test
    public void testGetArborescence() throws Exception {

        Util.cloneRemoteRepo("userTest", "TestGitRepository.git", REMOTE_URL);

        //Creation of the JsonObject for the new repository, revision 70ad3b45d04d53ad77f0444a3cc9e33e657e9779
        JsonObject object = Util.getArborescence("userTest", "TestGitRepository.git", "70ad3b45d04d53ad77f0444a3cc9e33e657e9779");
        System.out.println(object.toString());
        Boolean test = Util.deleteRepository("userTest", "TestRepository.git");

    }
}