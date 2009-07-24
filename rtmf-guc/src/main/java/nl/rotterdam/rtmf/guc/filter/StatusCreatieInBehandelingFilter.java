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
