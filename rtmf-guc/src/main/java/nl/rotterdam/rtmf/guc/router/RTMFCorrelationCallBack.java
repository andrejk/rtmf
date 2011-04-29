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
package nl.rotterdam.rtmf.guc.router;

import org.mule.DefaultMuleMessage;
import org.mule.api.MuleMessage;
import org.mule.api.config.MuleProperties;
import org.mule.routing.AggregationException;
import org.mule.routing.CollectionCorrelatorCallback;
import org.mule.routing.inbound.EventGroup;

/**
 * @author rweverwijk Een standaard RTMF correlationCallBack welke de
 *         MuleMessages in een Array zet in de payload van het return element
 */
public class RTMFCorrelationCallBack extends CollectionCorrelatorCallback {

	@Override
	public MuleMessage aggregateEvents(EventGroup events)
			throws AggregationException {
		DefaultMuleMessage newMessage = new DefaultMuleMessage(events
				.toMessageCollection().getMessagesAsArray());

		// Omdat we een situatie hebben waar we 2 multicasters achter elkaar
		// hebben en we hier een nieuw bericht maken verliezen we het
		// correlation_id van het eerste bericht. Om ervoor te zorgen dat ook
		// het eerste bericht weer gecorreleerd kan worden copieren we het
		// correlation_id van zijn child ook weer naar het nieuwe bericht.
		MuleMessage[] payload = (MuleMessage[]) newMessage.getPayload();
		newMessage.setProperty(
						MuleProperties.MULE_CORRELATION_ID_PROPERTY,
						payload[0]
								.getProperty(MuleProperties.MULE_CORRELATION_ID_PROPERTY));
		return newMessage;
	}
}
