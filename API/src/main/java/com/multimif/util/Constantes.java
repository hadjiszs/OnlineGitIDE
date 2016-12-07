package com.multimif.util;

/**
 * @author Amaia Nazabal
 * @version 1.0
 * @since 1.0 10/20/16.
 */
public class Constantes {
    private static final String USER_DIRECTORY = "user.dir";

    public static final int OPERATION_CODE_REUSSI = 0;

    public static final String OPERATION_MSG_REUSSI = "Transaction reussie";

    public static final int OPERATION_CODE_RATE = -1;

    public static final String OPERATION_MSG_RATE = "Transaction rate";

    public static final String ENTITY_FACTORY = "pu-multimif";


    public static final String COMPILE_ACTION = "compile";
    public static final String CLEAN_ACTION = "clean";
    public static final String CLONE_ACTION = "clone";


    public static final String REPO_PATH = System.getProperty(USER_DIRECTORY) + "/API/repositories"; // depot des projets
    public static final String CLONE_PATH = System.getProperty(USER_DIRECTORY) + "/API/clone"; // depot des projets pour la compilation
    public static final String SCRIPTS_PATH = System.getProperty(USER_DIRECTORY) + "/API/scripts"; // scripts
    public static final String RESULTS_PATH = System.getProperty(USER_DIRECTORY) + "/API/results"; // resultat de la compilation
    public static final String TEMPFILES_PATH = System.getProperty(USER_DIRECTORY) + "/API/tempFiles"; // les fichiers temp


    public static final String SCRIPT_COMPILE_JAVA = "compileJava.sh"; // script pour compiler un projet Java
    public static final String SCRIPT_COMPILE_MAVEN = "compileMaven.sh";
    public static final String SCRIPT_COMPILE_CMAKE = "compileCMake.sh";

    public static final String SCRIPT_CLONE = "clone.sh"; // script pour cloner
    public static final String SCRIPT_CLEAN = "clean.sh"; // script pour un "clean" apres la compilation

    public static final String SCRIPT_MV_TEMP_FILE = "mvTempFile.sh";


    private Constantes() {

    }

}
