package com.multimif.model;

/**
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 11/22/16.
 */
public enum ExtensionType {
    JAVA("java"),
    CPP("cpp");


    public final String extension;

    ExtensionType(String extension){
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }
}
