package nl.rotterdam.rtmf.guc.transformer;

import org.apache.commons.lang.SerializationUtils;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageAwareTransformer;

public class CopyMessageTransformer extends AbstractMessageAwareTransformer {

	@Override
	public Object transform(MuleMessage message, String encoding)
			throws TransformerException {
		return SerializationUtils.clone(message);
	}

}