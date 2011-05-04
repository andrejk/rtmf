package com.ovsoftware.ictu.osb.tmfportal.service.common;

import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;
import javax.xml.ws.handler.soap.SOAPMessageContext;
/**
 * Dit is een custom HandlerResolver welke dus de HandlerResolver interface
 * implementeert. Hiermee kan een custom Handler zoals de MessageLoggingHandler
 * worden toegevoegd aan een Service zodat de berichten gelogd kunnen worden.
 * 
 * @author ktinselboer
 *
 */
public class CustomHandlerResolver implements HandlerResolver {

	@SuppressWarnings("unchecked")
	private List<Handler> handlerLijst = new ArrayList<Handler>();
	
	/**
	 * Retourneert de huidige lijst met Handler's van deze custom
	 * HandlerResolver.
	 * 
	 * @param portInfo Wordt niet gebruikt, noodzakelijk ivm implementatie interface
	 * @return De lijst met Handler-objecten
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Handler> getHandlerChain(PortInfo portInfo) {
		return handlerLijst;
	}
	
	/**
	 * Voegt een Handler van het type SOAPMessageContext
	 * toe aan de lijst met Handler's.
	 * 
	 * @param handler De toe te voegen Handler
	 */
	public void addHandler(Handler<SOAPMessageContext> handler) {
		handlerLijst.add(handler);		
	}

}
