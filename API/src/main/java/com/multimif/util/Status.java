package com.multimif.util;

/**
 * @author Amaia Nazábal
 * @version 1.0
 * @since 1.0 10/20/16.
 */
public class Status {
	private int code;
	private String message;

	public Status(){
		/*
		 * On ajout le constructeur par défaut pour le mapping de jackson
		 */
	}

	/**
	 * @param code code 0 si la transaction a fini correctemente, sinon -1
	 * @param message un message à la fin de la transaction
	 */
	public Status(int code, String message){
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
