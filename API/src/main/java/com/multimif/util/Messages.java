package com.multimif.util;

/**
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 11/21/16.
 */
public class Messages {

    public static final String USER_NOT_EXISTS = "User doesn't exist";

    public static final String USER_ALREADY_EXISTS = "User already exists";

    public static final String FILE_NOT_EXISTS = "The temporary file doesn't exists.";

    public static final String FILE_ALREADY_EXISTS = "The temporary file already exists.";

    public static final String PERMISSION_NOT_EXISTS = "Permission doesn't exists";

    public static final String PERMISSION_ALREADY_EXISTS = "Permission already exists";

    public static final String PROJECT_WITHOUT_OWNER = "This project doesn't have owner";

    public static final String USER_AUTHENTICATION_FAILED = "Incorrect password";

    public static final String USER_CANT_CREATED = "User can't be created";

    public static final String PROJECT_UNSPECIFIED_ID = "Unspecified project identifier";

    public static final String PROJECT_NOT_EXISTS = "Project doesn't exists";

    public static final String PROJECT_ALREADY_EXISTS = "Project already exists";

    public static final String PROJECT_NAME_ALREADY_EXISTS = "You already have an project with the same name";

    public static final String PROJECT_DELETE_CONTROL = "Only the admin has permissions for remove the project";

    public static final String GIT_REPOSITORY_NOT_EXISTS = "Le dépôt n'existe pas";

    public static final String GIT_CANT_OPEN_REPOSITORY = "The repository could not be opened";

    public static final String GIT_CANT_LIST_BRANCH = "The branch list could not be retrieved.";

    public static final String GIT_BRANCH_CANT_CREATED = "The branch could not be created";

    public static final String GIT_CANT_CLONE_REPOSITORY = "The repository could not be cloned";

    public static final String GIT_REPOSITORY_NAME_ERROR = "The name of repository isn't the correct name";

    public static final String GIT_LOG_ERROR = "Failed to get log repository";

    static final String ZIP_MOVE_ERROR = "Le dossier n'existe pas";


    private Messages(){
        /* On cache le constructeur
        * parce qu'on ne veut pas que cette classe soit instanciée */
    }
}
