package nl.rotterdam.rtmf.guc.transformer;

import org.apache.log4j.Logger;
import org.mule.DefaultMuleMessage;
import org.mule.api.MuleMessage;
import org.mule.api.MuleMessageCollection;
import org.mule.api.config.MuleProperties;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageAwareTransformer;

public class MulticastSyncCollectionTransformer extends AbstractMessageAwareTransformer {
	Logger log = Logger.getLogger(MulticastSyncCollectionTransformer.class.getName());
	@Override
	public Object transform(MuleMessage message, String encoding)
			throws TransformerException {
		//return SerializationUtils.clone(message);
		// om de synchrone multicast implementatie compatible te houden met de oorspronkelijke
		// asynchrone implementatie, wordt het resultaat gecopieerd in een formaat compatible
		// met RTMFCOrrecltionCallBack
		log.info("transform: " + message.getClass().getName());
		assert(message instanceof MuleMessageCollection);
		MuleMessageCollection msgs = (MuleMessageCollection) message;
		
		DefaultMuleMessage newMessage = new DefaultMuleMessage(msgs.getMessagesAsArray());

		// Omdat we een situatie hebben waar we 2 multicasters achter elkaar
		// hebben en we hier een nieuw bericht maken verliezen we het
		// correlation_id van het eerste bericht. Om ervoor te zorgen dat ook
		// het eerste bericht weer gecorreleerd kan worden copieren we het
		// correlation_id van zijn child ook weer naar het nieuwe bericht.
		MuleMessage[] payload = (MuleMessage[]) newMessage.getPayload();
		newMessage.setProperty(
						MuleProperties.MULE_CORRELATION_ID_PROPERTY,
						payload[0]
								.getProperty(MuleProperties.MULE_CORRELATION_ID_PROPERTY));
		return newMessage;
	}
}