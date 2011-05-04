package com.ovsoftware.ictu.osb.tmfportal.service.common;

/**
 * Custom exception klasse welke aangeeft dat er een webservice exceptie is opgetreden.
 */
public class InvokerException extends Exception {

	/**
	 * Gegenereerde UID ivm serialisatie.
	 */
	private static final long serialVersionUID = 8935831152169945542L;

	/**
	 * Standaard constructor voor InvokerException.
	 */
	public InvokerException() {}
	
	/**
	 * Constructor met nested Exception.
	 *
	 * @param cause nested exception
	 */
	public InvokerException(Throwable cause) {
        super(cause);
    }
}
