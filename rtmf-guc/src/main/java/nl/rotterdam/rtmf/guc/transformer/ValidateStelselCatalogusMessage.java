/**
 * 
 */
package nl.rotterdam.rtmf.guc.transformer;

import org.apache.log4j.Logger;
import org.mule.api.MuleMessage;

/**
 * @author tarem
 * 
 */
public class ValidateStelselCatalogusMessage extends ValidateSoapMessage {
	// fout codes die in error bericht voorkomen
	private static final String STELSELCATALOGUS_ERROR_STRING = "StelselCatalogus Fout";

	Logger log = Logger.getLogger(ValidateStelselCatalogusMessage.class
			.getName());
	String exceptionMessage = null;

	@Override
	public String doValidate(MuleMessage message, String outputEncoding) {

		exceptionMessage = super.doValidate(message, outputEncoding);
		log.debug("Start Validate StelselCatalogus");
		try {
			if (exceptionMessage == null) {
				checkNamespaceStelselBevragen(message);
			}
		} catch (Exception e) {
			exceptionMessage = String.format(exceptionMessageString,
					STELSELCATALOGUS_VALIDATIE_CODE_STRING, "Error validate Message", e
							.getMessage());
		}

		return exceptionMessage;
	}

	private void checkNamespaceStelselBevragen(MuleMessage message)
			throws Exception {
		log.debug("Check namespace stelsel bevragen");
		if (!message.getPayloadAsString().contains(
				"http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1")) {
			String foutBericht = "Geen namespace voor stelselbevragen in bericht";

			log.warn(foutBericht);

			exceptionMessage = String
					.format(
							exceptionMessageString,
							ValidateMessage.STELSELCATALOGUS_GEEN_STELSELCATALOGUSSCHEMA_CODE_STRING,
							STELSELCATALOGUS_ERROR_STRING, foutBericht);
		}
	}
}
