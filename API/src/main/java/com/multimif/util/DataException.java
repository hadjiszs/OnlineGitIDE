package com.multimif.util;

/**
 *
 * Cette exception a été définiée pour récupérer les exceptions qui sont propres de
 * la logique de notre application.
 *
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 10/21/16.
 */
public class DataException extends Exception {

    public DataException(String exception){
        super(exception);
    }
}
