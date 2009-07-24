package nl.rotterdam.rtmf.guc.filter;

import java.util.regex.Pattern;

import nl.rotterdam.rtmf.guc.exceptions.RtmfGucException;

import org.mule.api.MuleMessage;
import org.mule.api.routing.filter.Filter;

/**
 * Dit filter kan gebruikt worden om te bepalen of een OphaalMeldingKenmerk request 
 * naar de landelijke of de Rotterdamse service moet. 
 * @author rweverwijk
 *
 */
public class OphalenMeldingKenmerkFilter implements Filter {
	
	private String prefix;

	@Override
	public boolean accept(MuleMessage message) {

		String payloadAsString;
		try {
			payloadAsString = message.getPayloadAsString();
			payloadAsString = payloadAsString.replaceAll("\n", "");
			payloadAsString = payloadAsString.replaceAll("\r", "");
		} catch (Exception cause) {
			throw new RtmfGucException(
					"Message payload could not be transformed to a String",
					cause);
		}
		Pattern pattern = Pattern.compile(String.format(".* - \\(%s:.*", prefix), Pattern.MULTILINE);
		boolean result = pattern.matcher(payloadAsString).matches();

		return result;
	}
	
	/**
	 * @return the prefix
	 */
	public String getPrefix() {
		return prefix;
	}

	/**
	 * @param prefix the prefix to set
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

}
