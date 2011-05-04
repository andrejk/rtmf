package nl.rotterdam.rtmf;

import java.io.Serializable;

public class Zaak implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4364347852627563577L;
	String id;
	String status;

	public Zaak() {
		super();
	}
	
	public Zaak(String id, String status) {
		this.id = id;
		this.status = status;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
}
