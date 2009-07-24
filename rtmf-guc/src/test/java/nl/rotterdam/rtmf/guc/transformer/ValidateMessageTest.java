package nl.rotterdam.rtmf.guc.transformer;

import nl.rotterdam.rtmf.guc.exceptions.RtmfGucException;

import org.junit.Test;
import org.mule.DefaultMuleMessage;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.message.DefaultExceptionPayload;
import org.mule.tck.FunctionalTestCase;

/**
 * 
 * @author tarem
 *
 */
public class ValidateMessageTest extends FunctionalTestCase{

	
	@Test
	public void testTransformEmptyMessage() {

		ValidateMessage validateMessage = new ValidateMessage();
		MuleMessage defaultMuleMessage = new DefaultMuleMessage(null);

		try {
			validateMessage.transform(defaultMuleMessage, null);
		} catch (RtmfGucException e) {
			assertTrue(e.getMessage().contains("Payload is niet gevuld"));
			assertTrue(e.getMessage().contains("<code>902</code>"));
			return;
		} catch (TransformerException e) {
			fail("TransformerException not expected");
		}
		fail("RtmfGucException was expected");
	}
	
	@Test
	public void testTransformBindingFault(){
		ValidateMessage validateMessage = new ValidateMessage();
		MuleMessage defaultMuleMessage = new DefaultMuleMessage("Cannot bind to address \"http://localhost:9090/gm/sozawa\" No component registered on that endpoint");
		
		
		try {
			validateMessage.transform(defaultMuleMessage, null);
		} catch (RtmfGucException e) {
			assertTrue(e.getMessage().contains("Cannot bind to address"));
			assertTrue(e.getMessage().contains("<code>901</code>"));
			return;
		} catch (TransformerException e) {
			fail("TransformerException not expected");
		}
		fail("RtmfGucException was expected");
	}
	
	@Test
	public void testTransformExceptionPayload(){
		ValidateMessage validateMessage = new ValidateMessage();
		MuleMessage defaultMuleMessage = new DefaultMuleMessage("<error><code></code><message>%s</message><exceptionPayload>%s</exceptionPayload></error>");
		DefaultExceptionPayload defaultExceptionPayload = new DefaultExceptionPayload(new RtmfGucException("<error><code></code><message>%s</message><exceptionPayload>%s</exceptionPayload></error>"));
		defaultMuleMessage.setExceptionPayload(defaultExceptionPayload);

		try {
			validateMessage.transform(defaultMuleMessage, null);
		} catch (RtmfGucException e) {
			assertTrue(e.getMessage().contains("<code></code>"));
			return;
		} catch (TransformerException e) {
			fail("TransformerException not expected");
		}
		fail("RtmfGucException was expected");
	}

	@Override
	protected String getConfigResources() {
		return "guc_generic_config.xml,rtmfguc-config.xml";
	}
	
	@Override
	public void doSetUp() throws Exception {
		super.doSetUp();

		// zorg ervoor dat mule blijft draaien en niet bij elke methode opnieuw
		// opstart
		setDisposeManagerPerSuite(true);
	}

}
