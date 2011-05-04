/*
 * Copyright (c) 2009-2011 Gemeente Rotterdam
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the European Union Public Licence (EUPL),
 * version 1.1 (or any later version).
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * European Union Public Licence for more details.
 *
 * You should have received a copy of the European Union Public Licence
 * along with this program. If not, see
 * http://www.osor.eu/eupl/european-union-public-licence-eupl-v.1.1
*/
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
