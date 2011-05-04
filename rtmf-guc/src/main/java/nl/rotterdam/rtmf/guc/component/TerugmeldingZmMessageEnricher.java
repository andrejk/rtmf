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
package nl.rotterdam.rtmf.guc.component;

import nl.rotterdam.rtmf.guc.bronhouder.catalogus.BronhouderCatalogus;
import nl.rotterdam.rtmf.guc.exceptions.RtmfGucException;

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;

public class TerugmeldingZmMessageEnricher implements Callable {

	private BronhouderCatalogus bronhouderCatalogus;

	public void setBronhouderCatalogus(BronhouderCatalogus bronhouderCatalogus) {
		this.bronhouderCatalogus = bronhouderCatalogus;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mule.api.lifecycle.Callable#onCall(org.mule.api.MuleEventContext)
	 */
	@Override
	public Object onCall(MuleEventContext muleEventContext) throws Exception {

		String bereikenVia = null;
		String bereikenAdres = null;

		MuleMessage message = muleEventContext.getMessage();
		bereikenVia = bronhouderCatalogus
				.determineBereikenViaForTerugmelding(message
						.getPayloadAsString());
		bereikenAdres = bronhouderCatalogus
				.determineBereikenAdresForTerugmelding(message
						.getPayloadAsString());

		if (bereikenAdres != null) {
			if (bereikenVia != null) {
				if ("File".equalsIgnoreCase(bereikenVia)) {
					/*
					 * Daar de expression valuator
					 * #[header:bronhouder.bereikenAdres] niet werkt, vullen we
					 * hier het 'PATH' adres alvast in de header properties in,
					 * zodat het FILE outbound-endpoint het 'kan' oppikken.
					 * 
					 * LET OP: Helaas kijkt file:outbound-endpoint hier niet
					 * naar, en moet er nog watr anders worden verzonnen !!!
					 */
					message.setProperty("bronhouder.bereikenAdres", bereikenAdres);
				} else if ("Email".equalsIgnoreCase(bereikenVia)) {
					/*
					 * Daar de expression valuator
					 * #[header:bronhouder.bereikenAdres] niet werkt, vullen we
					 * hier het 'TO' adres alvast in de header properties in,
					 * zodat het SMTP outbound-endpoint het oppikt.
					 */
					message.setProperty("toAddresses", bereikenAdres);
				} else {
					throw new RtmfGucException(
							String
									.format(
											"Geen geldige waarde gevonden voor 'bereikenVia' in de bronhouder catalog: %s",
											bereikenVia));
				}
			} else {
				throw new RtmfGucException(
						String
								.format(
										"Geen 'bereikenVia' gevonden in de bronhouder catalog voor bronhouder (basisRegistratie): %s",
										message.getPayloadAsString()));
			}

		} else {
			throw new RtmfGucException(
					String
							.format(
									"Geen 'bereikenAdres' gevonden in de bronhouder catalog voor bronhouder (basisRegistratie): %s",
									message.getPayloadAsString()));
		}

		return message;
	}

}
