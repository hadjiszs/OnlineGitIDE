package com.multimif.util;

/**
 * Created by amaia.nazabal on 10/20/16.
 */
public class Constantes {

    private Constantes() {

    }

    public static final int OPERATION_CODE_REUSSI = 0;

    public static final String OPERATION_MSG_REUSSI = "Transaction reussie";

    public static final int OPERATION_CODE_RATE = -1;

    public static final String OPERATION_MSG_RATE = "Transaction rate";

    public static final String ENTITY_FACTORY = "pu-multimif";

    public static final String PROJECT_JAVA = "Java";

    public static final String PROJECT_C = "C";

    public static final String PROJECT_CPP = "C++";

    public static final String PROJECT_MVN = "Maven";

    public static final String COMPILE_ACTION = "compile";
    public static final String CLEAN_ACTION = "clean";
    public static final String CLONE_ACTION = "clone";
    public static final String MV_TEMP_FILES_ACTION = "getTempFile";


    public static final String REPO_PATH = System.getProperty("user.dir") + "/repositories"; // depot des projets
    public static final String CLONE_PATH = System.getProperty("user.dir") + "/clone"; // depot des projets pour la compilation
    public static final String SCRIPTS_PATH = System.getProperty("user.dir") + "/scripts"; // scripts
    public static final String RESULTS_PATH = System.getProperty("user.dir") + "/results"; // resultat de la compilation
    public static String TEMPFILES_PATH = System.getProperty("user.dir") + "/tempFiles"; // les fichiers temp


    public static String SCRIPT_COMPILE_JAVA = "compileJava.sh"; // script pour compiler un projet Java
    public static String SCRIPT_COMPILE_MAVEN = "compileMaven.sh";
    public static String SCRIPT_COMPILE_CMAKE = "compileCMake.sh";

    public static String SCRIPT_CLONE = "clone.sh"; // script pour cloner
    public static String SCRIPT_CLEAN = "clean.sh"; // script pour un "clean" apres la compilation

    public static String SCRIPT_MV_TEMP_FILE = "mvTempFile.sh";


}
