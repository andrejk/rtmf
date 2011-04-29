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
package nl.rotterdam.rtmf.guc.filter;

import nl.rotterdam.rtmf.guc.exceptions.RtmfGucException;

import org.apache.log4j.Logger;
import org.mule.api.MuleMessage;

/**
 * StelselCatalogusCacheFilter kijkt in de payload of er basisregistratie tag
 * en/of object tag aanwezig is. Deze tag zal worden vergeleken met de opgegeven
 * tag in 'expectedService'. Indien de tags overeenkomen zal 'true' teruggegeven
 * worden.
 * 
 */
public class StelselCatalogusCacheFilter extends CacheFilterBase {

	Logger logger = Logger.getLogger(StelselCatalogusCacheFilter.class.getName());
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nl.rotterdam.rtmf.guc.filter.CacheFilterBase#accept(org.mule.api.MuleMessage
	 * )
	 */
	@Override
	public boolean accept(MuleMessage message) {
		boolean result = false;
		String payloadAsString;

		try {
			payloadAsString = message.getPayloadAsString();
		} catch (Exception cause) {
			throw new RtmfGucException("Message payload could not be transformed to a String", cause);
		}
		
		String targetServiceCalls = cache
				.determineServiceCallsForMessage(payloadAsString);
		logger.debug("StelselCatalogusCacheFilter: targetServiceCalls.equals(expectedService): "
		     + targetServiceCalls + ".equals(" + expectedService + ")");
		result = targetServiceCalls.equals(expectedService);
		return result;
	}

}
