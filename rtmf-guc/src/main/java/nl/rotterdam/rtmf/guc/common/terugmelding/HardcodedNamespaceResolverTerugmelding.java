package nl.rotterdam.rtmf.guc.common.terugmelding;

import java.util.Iterator;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;

/**
 * Dit is een 'convenient' class welke gebruikt wordt om de NamespaceContext van
 * een Terugmelding te zetten op een XPath.
 * 
 * @author Enno Buis
 * 
 */
public class HardcodedNamespaceResolverTerugmelding implements NamespaceContext {

	/**
	 * This method returns the uri for all prefixes needed. Wherever possible it
	 * uses XMLConstants.
	 * 
	 * @param prefix
	 * @return uri
	 */
	public String getNamespaceURI(String prefix) {
		if (prefix == null) {
			throw new IllegalArgumentException("No prefix provided!");
		} else if (prefix.equals(XMLConstants.DEFAULT_NS_PREFIX)) {
			return "http://schemas.xmlsoap.org/soap/envelope/";
		} else if (prefix.equals("ns2")) {
			return "http://wus.tmf.gbo.overheid.nl/wsdl/aanmeldService-V1.1.xsd";
		} else if (prefix.equals("stell")) {
			return "http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.2.xsd";
		} else if (prefix.equals("stel")) {
			return "http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd";
		} else {
			return XMLConstants.NULL_NS_URI;
		}
	}

	public String getPrefix(String namespaceURI) {
		// Not needed in this context.
		return null;
	}

	@SuppressWarnings("unchecked")
	public Iterator getPrefixes(String namespaceURI) {
		// Not needed in this context.
		return null;
	}
}