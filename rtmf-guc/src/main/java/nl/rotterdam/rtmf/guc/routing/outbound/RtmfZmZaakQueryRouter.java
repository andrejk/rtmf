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
package nl.rotterdam.rtmf.guc.routing.outbound;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.rotterdam.rtmf.guc.common.DocumentParser;

import org.mule.DefaultMuleMessage;
import org.mule.api.MessagingException;
import org.mule.api.MuleMessage;
import org.mule.api.MuleSession;
import org.mule.api.config.MuleProperties;
import org.mule.api.endpoint.ImmutableEndpoint;
import org.mule.api.endpoint.OutboundEndpoint;
import org.mule.api.routing.CouldNotRouteOutboundMessageException;
import org.mule.api.routing.RoutePathNotFoundException;
import org.mule.api.routing.RoutingException;
import org.mule.config.i18n.CoreMessages;
import org.mule.config.i18n.MessageFactory;
import org.mule.routing.outbound.FilteringOutboundRouter;

/**
 * <code>RtmfZmZaakQueryRouter</code> is een zeer specifieke router voor het
 * ophalen van ZaakQuery en ZaakDetail. Dit is nodig omdat de services van het
 * zakenmagazijn en TMF niet met elkaar overeen komen. Voor het ophalen van een
 * overzicht in TMF (OphalenMeldingStatus) worden de attributen opgehaald. Deze
 * attributen krijgen we echter niet terug van het Zakenmagazijn. Daarom moeten
 * we voor elke gevonden zaak met de ZaakQuery een ZaakDetail ophalen om aan de
 * attributen te komen.
 * 
 * @author Ron van Weverwijk
 * 
 */
public class RtmfZmZaakQueryRouter extends FilteringOutboundRouter {

	@Override
	public MuleMessage route(MuleMessage message, MuleSession session)
			throws RoutingException {
		MuleMessage resultMessage;
		if (endpoints == null || endpoints.size() == 0) {
			throw new RoutePathNotFoundException(CoreMessages
					.noEndpointsForRouter(), message, null);
		} else if (endpoints.size() != 2) {
			throw new CouldNotRouteOutboundMessageException(MessageFactory.createStaticMessage("Er moeten 2 endpoints aanwezig zijn om de RMTFZMZaakQueryRouter te gebruiken."), message, null);
		}
		List<MuleMessage> results = new ArrayList<MuleMessage>();
		try {
			OutboundEndpoint endpoint;
			// Eerst beide calls uitvoeren 
			for (int i = 0; i < endpoints.size(); i++)
            {
                endpoint = (OutboundEndpoint) endpoints.get(i);
                if(endpoint.getFilter()==null || (endpoint.getFilter()!=null && endpoint.getFilter().accept(message)))
                {
                    if (((DefaultMuleMessage) message).isConsumable())
                    {
                        throw new MessagingException(
                            CoreMessages.cannotCopyStreamPayload(message.getPayload().getClass().getName()),
                            message);
                    }
                    
                    MuleMessage clonedMessage = new DefaultMuleMessage(message.getPayload(), message);
                    
                    results.add(send(session, clonedMessage, endpoint));
                }
            }
			
			// We hebben nu de call naar ZaakQuery gedaan. We kunnen nu bepalen welke zaken er gevonden zijn.
			// Daarmee kunnen we dan ZaakDetail gaan bevragen.
			List<String> gevondenZaakIdentificaties = DocumentParser.getAllValues(results.get(1).getPayloadAsString(), "/Envelope/Body/ZaakQueryResponse/Zaak/Zaakidentificatie");
			for (int i = 0; i < gevondenZaakIdentificaties.size(); i++) {
				String zaakIdentificatie = gevondenZaakIdentificaties.get(i);
				endpoint = (OutboundEndpoint) endpoints.get(1);
				if (endpoint.getFilter() == null
						|| (endpoint.getFilter() != null && endpoint
								.getFilter().accept(message))) {
					if (((DefaultMuleMessage) message).isConsumable()) {
						throw new MessagingException(CoreMessages
								.cannotCopyStreamPayload(message.getPayload()
										.getClass().getName()), message);
					}

					MuleMessage clonedMessage = new DefaultMuleMessage(createZaakDetailPayload(zaakIdentificatie), message);
					MuleMessage response = send(session, clonedMessage, endpoint);
					if (i == 0) {
						Map<String, String> map = new HashMap<String,String>();
						map.put(zaakIdentificatie, response.getPayloadAsString());
						response.setPayload(map);
						results.add(response);
						
					}
					else {
						((HashMap<String,String>)results.get(results.size()-1).getPayload()).put(zaakIdentificatie, response.getPayloadAsString());
					}
				}
			}
			resultMessage = new DefaultMuleMessage(results
					.toArray((new MuleMessage[] {})));
			resultMessage.setProperty(
					MuleProperties.MULE_CORRELATION_ID_PROPERTY,
					results.get(0).getProperty(
							MuleProperties.MULE_CORRELATION_ID_PROPERTY));
		} catch (Exception e) {
			throw new CouldNotRouteOutboundMessageException(message,
					(ImmutableEndpoint) endpoints.get(0), e);
		}

		return resultMessage;
	}
	
	/**
	 * Deze methode maakt een request payload voor ZaakDetail met de identificatie als key
	 * @param identificatie
	 * @return
	 */
	private String createZaakDetailPayload(String identificatie) {
		String basePayload = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:stat=\"http://www.interaccess.nl/webplus/statuswfm_v2\">" + 
				"   <soapenv:Header/>" + 
				"   <soapenv:Body>" + 
				"      <stat:ZaakDetailRequest>" + 
				"         <stat:Identificatie>%s</stat:Identificatie>" + 
				"         <stat:Oge_id>0</stat:Oge_id>" + 
				"      </stat:ZaakDetailRequest>" + 
				"   </soapenv:Body>" + 
				"</soapenv:Envelope>";
		return String.format(basePayload, identificatie);
	}
}