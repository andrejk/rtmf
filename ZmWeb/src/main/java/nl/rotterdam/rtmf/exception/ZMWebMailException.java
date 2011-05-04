/**
 * 
 */
package nl.rotterdam.rtmf.exception;

/**
 * @author rweverwijk
 *
 */
public class ZMWebMailException extends ZMWebException {
	private static final long serialVersionUID = 8397981192265670137L;
	/**
	 * @param e
	 */
	public ZMWebMailException(Exception e) {
		super(e);
	}
}
