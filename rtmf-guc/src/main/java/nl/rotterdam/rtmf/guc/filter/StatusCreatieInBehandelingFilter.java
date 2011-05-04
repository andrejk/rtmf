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

import java.util.List;

import nl.rotterdam.rtmf.guc.common.DocumentParser;
import nl.rotterdam.rtmf.guc.exceptions.RtmfGucException;

import org.apache.log4j.Logger;
import org.mule.api.MuleMessage;
import org.mule.api.routing.filter.Filter;

/**
 * Dit filter kan gebruikt worden om te bepalen of er een statusCreatie gedaan moet worden voor het in behandeling
 * nemen van een zaak. Dit mag alleen gebeuren als een zaak nog niet in behandeling is.
 * @author rweverwijk
 *
 */
public class StatusCreatieInBehandelingFilter implements Filter {
	Logger logger = Logger.getLogger(StatusCreatieInBehandelingFilter.class.getName());
	@Override
	public boolean accept(MuleMessage message) {
		boolean result = false;
		String[] payloads = (String[]) message.getPayload();
		if (payloads.length >= 3 && payloads[2].contains("ZaakDetailResponse")) {
			// We halen alle statussen op uit het ZaakDetail bericht. 
			// Het is niet mogelijk om terug te gaan in status
			List<String> allValues = DocumentParser.getAllValues(payloads[2], "/Envelope/Body/ZaakDetailResponse/Status/Statuscode");
			logger.debug("Gevonden statussen: " + allValues);
			if (!allValues.contains("IB")) {
				logger.debug("status IB niet gevonden");
				result = true;
			}
		} else {
			throw new RtmfGucException("We verwachten dat de 3e payload een zaakDetail bericht is.");
		}
		
		return result;
	}
}
