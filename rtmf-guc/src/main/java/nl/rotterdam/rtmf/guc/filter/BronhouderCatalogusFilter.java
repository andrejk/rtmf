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
