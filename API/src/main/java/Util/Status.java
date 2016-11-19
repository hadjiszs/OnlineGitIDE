package Util;

public class Status {
	private int code;
	private String message;

	/**
	 * On ajout le constructeur par défaut pour le mapping de jackson
	 */
	public Status(){}

	/**
	 * @param code
	 * @param message
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
