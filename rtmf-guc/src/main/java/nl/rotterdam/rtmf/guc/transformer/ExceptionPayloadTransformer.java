package nl.rotterdam.rtmf.guc.transformer;

import org.apache.log4j.Logger;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageAwareTransformer;

public class ExceptionPayloadTransformer extends
		AbstractMessageAwareTransformer {
	@Override
	public Object transform(MuleMessage message, String outputEncoding)
			throws TransformerException {
		Logger log = Logger.getLogger(ExceptionPayloadTransformer.class
				.getName());
		log.debug("start ExceptionPayloadTransformer");
		if (message.getExceptionPayload() != null) {
			log.debug("Exception payload is not null :"
					+ message.getExceptionPayload().getRootException()
							.getMessage());
			log.info("setExceptionPayload to payload");
			message.setPayload(message.getExceptionPayload().getRootException()
					.getMessage());
		} else {
			log.debug("geen ExceptionPayload");
		}
		log.debug("end ExceptionPayloadTransformer");
		return message;
	}
}
