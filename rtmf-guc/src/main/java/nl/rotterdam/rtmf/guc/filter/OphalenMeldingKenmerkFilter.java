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

import java.util.regex.Pattern;

import nl.rotterdam.rtmf.guc.exceptions.RtmfGucException;

import org.mule.api.MuleMessage;
import org.mule.api.routing.filter.Filter;

/**
 * Dit filter kan gebruikt worden om te bepalen of een OphaalMeldingKenmerk request 
 * naar de landelijke of de Rotterdamse service moet. 
 * @author rweverwijk
 *
 */
public class OphalenMeldingKenmerkFilter implements Filter {
	
	private String prefix;

	@Override
	public boolean accept(MuleMessage message) {

		String payloadAsString;
		try {
			payloadAsString = message.getPayloadAsString();
			payloadAsString = payloadAsString.replaceAll("\n", "");
			payloadAsString = payloadAsString.replaceAll("\r", "");
		} catch (Exception cause) {
			throw new RtmfGucException(
					"Message payload could not be transformed to a String",
					cause);
		}
		Pattern pattern = Pattern.compile(String.format(".* - \\(%s:.*", prefix), Pattern.MULTILINE);
		boolean result = pattern.matcher(payloadAsString).matches();

		return result;
	}
	
	/**
	 * @return the prefix
	 */
	public String getPrefix() {
		return prefix;
	}

	/**
	 * @param prefix the prefix to set
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

}
