package Service;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;


/**
 * Created by Alexis on 12/10/2016.
 */
public class FolderTraverseTests {

    @Test
    public void testGetFileTree() throws Exception{
        // Un dossier vide
        FolderTraverse ft = new FolderTraverse(new File("src/main/java/Application"));
        Assert.assertEquals(1,ft.getFileTree().size());

        // Un dossier avec un seul fichier
        FolderTraverse ft1 = new FolderTraverse(new File("src/main/java/API/Service"));
        Assert.assertTrue(2 <= ft1.getFileTree().size());

        // Un dossier avec des sous-repertoire
        FolderTraverse ft2 = new FolderTraverse(new File("src"));
        Assert.assertTrue(2 <= ft2.getFileTree().size());
    }

    @Test
    public void testToJsonFileTree() throws Exception{
        System.out.printf(System.getProperty("user.dir"));
        FolderTraverse ft = new FolderTraverse(new File("src"));
        Assert.assertNotNull(ft.toJsonFileTree());
    }

    @Test(expected = FileNotFoundException.class)
    public void testFileNotFoundFolderTraverse() throws Exception{
        FolderTraverse ft = new FolderTraverse(new File("dhjqshdjqhjkdhjqd"));
    }

    @Test
    public void testToStringFileTree() throws Exception{
        FolderTraverse ft = new FolderTraverse(new File("src"));
        Assert.assertNotNull(ft.toString());
    }
}