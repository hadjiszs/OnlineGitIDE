package com.multimif.util;

import com.multimif.controller.UserController;
import com.multimif.git.GitConstantes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 11/23/16.
 *
 * @see <a href="http://stackoverflow.com/questions/15968883/how-to-zip-a-folder-itself-using-java">
 *     http://stackoverflow.com/questions/15968883/how-to-zip-a-folder-itself-using-java
 *     </a>
 */
public class ZipUtil {

    private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());


    private ZipUtil(){
        /* On cache le constructeur parce que c'est une classe utilitaire */
    }

    /**
     *
     * Cette méthode compresse un dossier dans un fichier en format ZIP et le garde
     * dans le dossier de repositories
     *
     * @param sourceDirPath l'addresse du dossier qu'on veut compresser
     * @param zipFilePath le nom du fichier zip
     * @throws IOException retourne une exception si on ne trouve pas le dossier
     */
    public static void compress(String sourceDirPath, String zipFilePath) throws IOException {
        Path p = Files.createFile(Paths.get(zipFilePath));

        ZipOutputStream zs = new ZipOutputStream(Files.newOutputStream(p));
        try {
            Path pp = Paths.get(sourceDirPath);
            Files.walk(pp)
                    .filter(path -> !path.toFile().isDirectory())
                    .forEach(path -> {
                        String sp = path.toAbsolutePath().toString().replace(pp.toAbsolutePath().toString(), "")
                                .replace(path.getFileName().toString(), "");
                        ZipEntry zipEntry = new ZipEntry(sp + "/" + path.getFileName().toString());
                        try {
                            zs.putNextEntry(zipEntry);
                            zs.write(Files.readAllBytes(path));
                            zs.closeEntry();
                        } catch (Exception e) {
                            LOGGER.log(Level.FINE, e.getMessage(), e);
                        }
                    });
        } finally {
            zs.close();
        }
    }

    /**
     *
     * Cette méthode déplace un fichier dans une addresse donnée
     *
     * @param sourcePath l'addresse du fichier qu'on veut déplacer
     * @param zipFile la nouvelle addresse du fichier
     * @throws DataException retourne une exception si il'y a un error dans le deplacemment.
     */
    public static void moveZipFile(String sourcePath, String zipFile) throws DataException {
        Path target = Paths.get(new StringBuilder().append(GitConstantes.ZIP_DIRECTORY)
                .append(File.separator).append(zipFile).toString());
        Path source = Paths.get(sourcePath);

        try {
            Files.move(source, target, REPLACE_EXISTING);
        } catch (IOException e) {
            LOGGER.log(Level.OFF, e.getMessage(), e);
            throw new DataException(Messages.ZIP_MOVE_ERROR);
        }
    }

    /**
     *
     * Cette méthode supprime un fichier qui se trouve dans l'addresse
     * indiqué.
     *
     * @param zipFilePath l'addresse avec le nom du fichier
     */
    public static void deleteZipFile(String zipFilePath){
        try {
            Files.delete(Paths.get(zipFilePath));
        } catch (IOException e) {
            LOGGER.log(Level.FINE, e.getMessage(), e);
        }
    }
}
