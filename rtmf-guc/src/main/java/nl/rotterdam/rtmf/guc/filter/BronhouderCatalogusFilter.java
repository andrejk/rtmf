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

import nl.rotterdam.rtmf.guc.bronhouder.catalogus.BronhouderCatalogus;
import nl.rotterdam.rtmf.guc.exceptions.RtmfGucException;

import org.mule.api.MuleMessage;
import org.mule.api.routing.filter.Filter;

public class BronhouderCatalogusFilter implements Filter {

	private BronhouderCatalogus bronhouderCatalogus;
	private String expectedService;

	public void setBronhouderCatalogus(BronhouderCatalogus bronhouderCatalogus) {
		this.bronhouderCatalogus = bronhouderCatalogus;
	}

	public void setExpectedService(String expectedService) {
		this.expectedService = expectedService;
	}

	@Override
	public boolean accept(MuleMessage message) {
		boolean result = false;

		String payloadAsString;
		try {
			payloadAsString = message.getPayloadAsString();
		} catch (Exception cause) {
			throw new RtmfGucException(
					"Message payload could not be transformed to a String",
					cause);
		}
		String targetServiceCalls = bronhouderCatalogus
				.determineBereikenViaForTerugmelding(payloadAsString);
		result = targetServiceCalls.equalsIgnoreCase(expectedService);

		return result;
	}

}
