package nl.rotterdam.rtmf.guc.exceptions;

public class RtmfGucException extends RuntimeException {

	private static final long serialVersionUID = -5829106534977345293L;

	/**
	 * Instantiates a new RtmfGuc exception.
	 * 
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public RtmfGucException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new RtmfGuc exception.
	 * 
	 * @param message
	 *            the message
	 */
	public RtmfGucException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new RtmfGuc exception.
	 * 
	 * @param cause
	 *            the cause
	 */
	public RtmfGucException(Throwable cause) {
		super(cause);
	}

}
