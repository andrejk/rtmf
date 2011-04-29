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

import nl.rotterdam.rtmf.guc.common.RTMFStringUtils;

import org.mule.DefaultMuleMessage;
import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.api.MuleSession;
import org.mule.api.config.MuleProperties;
import org.mule.api.endpoint.OutboundEndpoint;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.routing.CouldNotRouteOutboundMessageException;
import org.mule.api.routing.RoutePathNotFoundException;
import org.mule.api.routing.RoutingException;
import org.mule.config.i18n.CoreMessages;
import org.mule.routing.outbound.FilteringOutboundRouter;
import org.mule.transport.NullPayload;

/**
 * <code>RTMFChainingRouter</code> wordt gebruikt om een mule message
 * achtereenvolgens naar verschillende endpoints te sturen. De resultaat payload
 * van een 'stap' wordt steeds toegevoegd aan de payload van de originele mule
 * message. Deze wordt omgezet van een 'single' payload naar een payload array.
 * 
 * @author Enno Buis
 * 
 */
public class RTMFChainingRouter extends FilteringOutboundRouter {

	@Override
	public void initialise() throws InitialisationException {
		super.initialise();
		/*
		 * Hebben we endpoints ?
		 */
		if (endpoints == null || endpoints.size() == 0) {
			throw new InitialisationException(CoreMessages
					.objectIsNull("endpoints"), this);
		}
	}

	public MuleMessage route(MuleMessage message, MuleSession session)
			throws RoutingException {
		MuleMessage resultToReturn = null;

		/*
		 * Hebben we endpoints ?
		 */
		if (endpoints == null || endpoints.size() == 0) {
			throw new RoutePathNotFoundException(CoreMessages
					.noEndpointsForRouter(), message, null);
		}

		final int endpointsCount = endpoints.size();
		if (logger.isDebugEnabled()) {
			logger.debug("Van plan om " + endpointsCount
					+ " endpoints aan elkaar te ketenen.");
		}

		// Ref nodig voor een error message
		OutboundEndpoint endpoint = null;

		try {
			/*
			 * Maak een clone van het originele bericht.
			 */
			MuleMessage originalMessage = new DefaultMuleMessage(message
					.getPayload(), message);
			/*
			 * Definieer een tussen resultaat
			 */
			MuleMessage intermediaryResult = message;

			for (int i = 0; i < endpointsCount; i++) {
				endpoint = getEndpoint(i, intermediaryResult);
				if(endpoint.getFilter()==null || endpoint.getFilter().accept(message))
                {
					/*
					 * Als het niet de laatste endpoint in de keten is, dan forceren
					 * van een 'synchronous' aanroep, anders raken we de response
					 * kwijt.
					 */
					boolean lastEndpointInChain = (i == endpointsCount - 1);
	
					if (logger.isDebugEnabled()) {
						logger.debug("Versturen van een Chained bericht '"
								+ i
								+ "': "
								+ (intermediaryResult == null ? "null"
										: intermediaryResult.toString()));
					}
	
					/*
					 * Alle geregistreerde endpoints op de RTMFChaining router
					 * moeten de RemoteSync enabled hebben. Hier deze property op
					 * true zetten. MULE-3643
					 */
					intermediaryResult.setProperty(
							MuleProperties.MULE_REMOTE_SYNC_PROPERTY, true);
	
					/*
					 * Voor alle behalve het laatste endpoint
					 */
					if (!lastEndpointInChain) {
						logger.debug("Aanroepen van de Send ...");
						MuleMessage localResult = send(session, intermediaryResult,
								endpoint);
						/*
						 * null result kan worden verpakt in een NullPayload
						 */
						if (localResult != null && intermediaryResult != null) {
							if (localResult.getPayload() != NullPayload
									.getInstance()) {
	
								/*
								 * We moeten de Correlation en ReplyTo info
								 * doorzetten, want er is geen garantie dat een
								 * extern systeem deheaders zal bewaren (de meeste
								 * zullen dat NIET doen).
								 */
								processIntermediaryResult(localResult,
										intermediaryResult);
	
								try {
									expandIntermediaryResult(originalMessage,
											localResult, intermediaryResult);
								} catch (Exception e) {
									throw new CouldNotRouteOutboundMessageException(
											message, endpoint, e);
								}
	
							} else {
								logger.debug("De payload is: {NullPayload}");
								intermediaryResult = localResult;
							}
						} else {
							intermediaryResult = localResult;
						}
	
						if (logger.isDebugEnabled()) {
							logger
									.debug("Ontvangen Chain resultaat '"
											+ i
											+ "': "
											+ (intermediaryResult != null ? intermediaryResult
													.toString()
													: "null"));
						}
	
						if (intermediaryResult == null
								|| intermediaryResult.getPayload() == NullPayload
										.getInstance()) {
							/*
							 * Als er een error is opgetreden, zorg er dan voor dat
							 * er een exception payload wordt teruggeven naast een
							 * NullPayload.
							 */
							resultToReturn = intermediaryResult;
							logger
									.warn("RTMFChaining router kan geen verdere endpoints verwerken. "
											+ "Er is geen resultaat teruggekomen van endpoint aanroep: "
											+ endpoint);
							break;
						}
						// zorg ervoor dat de resultToReturn gevult wordt met de resultaten
						resultToReturn = intermediaryResult;
					} else {
						/*
						 * Het laatste endpoint wat aangeroepen gaat worden. Gebruik
						 * de 'sync/async' parameter.
						 */
						if (endpoint.isSynchronous()) {
							resultToReturn = send(session, intermediaryResult,
									endpoint);
	
							if (logger.isDebugEnabled()) {
								logger.debug("Laatste RTMFChain resultaat '"
										+ i
										+ "': "
										+ (resultToReturn == null ? "null"
												: resultToReturn.toString()));
							}
	
							/*
							 * null result kan worden verpakt in een NullPayload
							 */
							if (resultToReturn != null
									&& intermediaryResult != null) {
								if (resultToReturn.getPayload() != NullPayload
										.getInstance()) {
									/*
									 * Zorg dat het correaltie ID weer meegenomen
									 * wordt.
									 */
									processIntermediaryResult(resultToReturn,
											intermediaryResult);
	
									try {
										expandIntermediaryResult(originalMessage,
												resultToReturn, intermediaryResult);
									} catch (Exception e) {
										throw new CouldNotRouteOutboundMessageException(
												message, endpoint, e);
									}
									resultToReturn = intermediaryResult;
								} else {
									if (resultToReturn.getExceptionPayload() != null) {
										logger.debug("1:Exception payload gevonden");
									}
								}
	
							} else {
								if (resultToReturn.getExceptionPayload() != null) {
									logger.debug("2:Exception payload gevonden");
								}
							}
	
						} else {
							/*
							 * Reset de vorige call om misvattingen te voorkomen
							 */
							resultToReturn = null;
							dispatch(session, intermediaryResult, endpoint);
						}
					}
                }
			}
			logger.debug("adding the properties to the resultToReturn message");
			Object property = originalMessage.getProperty("attachmentKey");
			logger.debug("The value of attachmentKey is now: " + property);
			if (resultToReturn != null && property != null) {
				resultToReturn.setProperty("attachmentKey", property);
			}
		} catch (MuleException e) {
			throw new CouldNotRouteOutboundMessageException(message, endpoint,
					e);
		}
		return resultToReturn;
	}

	/**
	 * Verwerk de tussentijdse resultaten van een aanroep. Deze methode wordt
	 * <strong>alleen</strong> aangeroepen als beide tussentijdse resultaten
	 * beschikbaar zijn (niet null).
	 * <p>
	 * <p/>
	 * Overriding methoden moeten
	 * <code>super(localResult, intermediaryResult)</code> aanroepen, behalve
	 * wanneer ze de correlatie workflow aanpassen.
	 * <p/>
	 * De default implementatie zet de volgende properties door:
	 * <ul>
	 * <li>correlationId
	 * <li>correlationSequence
	 * <li>correlationGroupSize
	 * <li>replyTo
	 * </ul>
	 * 
	 * @param localResult
	 *            resultaat van de laatste endpoint aanroep.
	 * @param intermediaryResult
	 *            de message welke door de aanroepen heen gaat.
	 */
	protected void processIntermediaryResult(MuleMessage localResult,
			MuleMessage intermediaryResult) {
		logger.debug("processIntermediaryResult");
		localResult.setCorrelationId(intermediaryResult.getCorrelationId());
		localResult.setCorrelationSequence(intermediaryResult
				.getCorrelationSequence());
		localResult.setCorrelationGroupSize(intermediaryResult
				.getCorrelationGroupSize());
		localResult.setReplyTo(intermediaryResult.getReplyTo());
	}

	/**
	 * Voeg de payload van het tijdelijke resultaat toe aan het payload array
	 * van de mule message welke door alle endpoints heen wandeld.
	 * 
	 * @param localResult
	 *            Het tijdelijke resultaat mule message
	 * @param intermediaryResult
	 *            De mule message welke door alle payloads heen loopt
	 * @throws Exception
	 */
	private void expandIntermediaryResult(MuleMessage originalMessage,
			MuleMessage localResult, MuleMessage intermediaryResult)
			throws Exception {

		if (localResult.getPayload() != NullPayload.getInstance()) {
			
			/*
			 * Check of de nieuwe payload een payload array is
			 */
			String[] localResultPayloads = RTMFStringUtils.payloadToArray(localResult.getPayloadAsString());
			logger.debug("Aantal result payloads: " + localResultPayloads.length);
			/*
			 * Resultaat teruggekregen van de nieuwe aanroep in de keten
			 */
			String[] originalPayloads = RTMFStringUtils.payloadToArray(originalMessage.getPayloadAsString());
			logger
					.debug("Gevonden aantal payloads: "
							+ originalPayloads.length);
			logger.debug("Aanmaken nieuw payload array");
			String[] newPayloads = new String[originalPayloads.length + localResultPayloads.length];
			logger.debug("Lengte nieuw payload array: " + newPayloads.length);
			
			/*
			 * Eerst de originele payload kopieren
			 */
			for (int i = 0; i < originalPayloads.length; i++) {
				logger.debug("kopieren payload: " + i);
				newPayloads[i] = originalPayloads[i];
			}
			
			/*
			 * De nieuwe payloads kopieren
			 */
			for (int i = 0; i < localResultPayloads.length; i++) {
				logger.debug("kopieren result payloads: " + i);
				newPayloads[originalPayloads.length + i] = localResultPayloads[i];
			}
			
			/*
			 * Verwijder eventuele '{' of '}' (overblijfselen van payload array) TODO dubbel? gebeurd ook al in de RTMFStringUtils
			 */
			for (int i=0; i < newPayloads.length; i++) {
				if (newPayloads[i].startsWith("{")) {
					newPayloads[i] = newPayloads[i].substring(1);
				}
				if (newPayloads[i].endsWith("}")) {
					newPayloads[i] = newPayloads[i].substring(0, newPayloads[i]
							.length() - 1);
				}
			}
			
			intermediaryResult.setPayload(newPayloads);
			originalMessage.setPayload(newPayloads);
			logger.debug("Nieuwe aantal payloads: " + originalPayloads.length);
			for (int i = 0; i < newPayloads.length; i++) {
				logger.debug("Payload[" + i + "]: " + newPayloads[i]);

			}
		}
	}
}