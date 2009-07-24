package com.ovsoftware.ictu.osb.tmfportal.web.intrekken;

/**
 * Deze class representeerd de data voor het intrek formulier.
 * 
 * @author OVSoftware
 *
 */
public class IntrekFormdata {
	private String reason = "";

	/**
	 * @param reason De toelichting voor de intrekking.
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	/**
	 * @return De toelichting voor de intrekking.
	 */
	public String getReason() {
		return reason;
	}	
}
