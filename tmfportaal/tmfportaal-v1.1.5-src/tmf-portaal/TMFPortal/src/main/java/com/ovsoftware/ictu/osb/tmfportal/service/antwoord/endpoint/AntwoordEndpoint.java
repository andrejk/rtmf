package com.ovsoftware.ictu.osb.tmfportal.service.antwoord.endpoint;

import org.jdom.Element;
import org.jdom.Namespace;
import org.springframework.ws.server.endpoint.AbstractJDomPayloadEndpoint;

/**
 * De antwoordservice logt alle inkomende EBMS berichten, hetzij een antwoord of
 * een foutmelding. Configuratie vindt plaats mbv een spring-xml bestand. Logging
 * gebeurt naar het stdout-log van tomcat bij default.
 * 
 * @author pboevink
 *
 */
public class AntwoordEndpoint extends AbstractJDomPayloadEndpoint {
	private String nameSpace = "http://wus.tmf.gbo.overheid.nl/wsdl/aanmeldService-V1.1.xsd";
	
	/**
	 * Retourneert namespace.
	 * 
	 * @return De waarde van de interne namespace variabele
	 */
	public String getNameSpace() {
		return nameSpace;
	}

	/**
	 * Zet namespace.
	 * 
	 * @param nameSpace De nieuwe waarde voor nameSpace
	 */
	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
	}

	/**
	 * Retourneert het daadwerkelijke 'ack'-bericht met als
	 * namespace de waarde van 'nameSpace'.
	 * 
	 * @param arg0 Wordt niet gebruikt
	 * @return Het ack-bericht in de vorm van een Element
	 */
	protected Element invokeInternal(Element arg0) {
		Namespace replyNamespace = Namespace.getNamespace("as", nameSpace);
		return new Element("ack", replyNamespace);
	}
}
