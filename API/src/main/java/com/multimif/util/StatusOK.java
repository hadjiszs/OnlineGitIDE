package com.multimif.util;

/**
 * Created by amaia.nazabal on 11/15/16.
 */
public class StatusOK extends Status {
    private Long id;

    public StatusOK(){}

    public StatusOK(int code, String message, Long id){
        super(code, message);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
