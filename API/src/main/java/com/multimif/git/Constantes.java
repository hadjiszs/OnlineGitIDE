package com.multimif.git;

/**
 * @author p1317074
 * @version 1.0
 * @since 1.0 15/11/16.
 */

public class Constantes {

    public static final String REPOPATH = "repositories/";

    // FIXME: obligé de mettre le chemin absolue pour la création d'un depot,
    // ça ne peut pas etre un chemin relatif
    // donc il faut modifier ce chemin pour y mettre le votre
    // et il faudra mettre le bon chemin lorsqu'on deploiera sur la VM
    public static final String REPO_FULLPATH = "/Users/amaia.nazabal/test" + REPOPATH;

    public static final String APPLICATION_JSON_UTF8 = "application/json;charset=UTF-8";

}
