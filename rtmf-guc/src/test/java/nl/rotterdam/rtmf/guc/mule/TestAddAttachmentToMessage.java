package nl.rotterdam.rtmf.guc.mule;

import org.mule.DefaultMuleMessage;
import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.FunctionalTestCase;

public class TestAddAttachmentToMessage extends FunctionalTestCase {

	@Override
	protected String getConfigResources() {
		return "add-attachment-test-mule-config.xml";
	}

	public void testAddAttachment() {
		MuleClient myClient;
		MuleMessage response = null;
		try {
			myClient = new MuleClient();
			response =  myClient.send("vm://FromTestCase", new DefaultMuleMessage(
					"<dummy>terugmelding</dummy>"));
			//response = myClient.request("vm://ToTestCase", 5000);
		} catch (MuleException e) {
			fail(e.getDetailedMessage());
		}

		assertNotNull(response);
		assertNotNull(response.getPayload());
		//assertEquals(((String) response.getPayload()), "Test Payload");
		assertTrue(response.getAttachmentNames().size() != 0);

	}

}
