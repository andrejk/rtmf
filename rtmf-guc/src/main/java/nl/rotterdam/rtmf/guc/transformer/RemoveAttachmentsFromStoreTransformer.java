package nl.rotterdam.rtmf.guc.transformer;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageAwareTransformer;

public class RemoveAttachmentsFromStoreTransformer extends
		AbstractMessageAwareTransformer {

	AttachmentStore store;
	
	@Override
	public Object transform(MuleMessage message, String outputEncoding)
			throws TransformerException {
			
		Object property = message.getProperty("attachmentKey");
		if (property != null && property instanceof String) {
			store.removeAttachment((String)property);
		} else {
			logger.info("De property attachmentKey is niet gevonden in de muleMessage. De attachment kan nu niet verwijderd worden uit de store.");
		}
		
		return message;
	}
	
	/**
	 * @param store the store to set
	 */
	public void setStore(AttachmentStore store) {
		this.store = store;
	}
}
