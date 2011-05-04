/*
 * Copyright (c) 2009-2011 Gemeente Rotterdam
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the European Union Public Licence (EUPL),
 * version 1.1 (or any later version).
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * European Union Public Licence for more details.
 *
 * You should have received a copy of the European Union Public Licence
 * along with this program. If not, see
 * http://www.osor.eu/eupl/european-union-public-licence-eupl-v.1.1
*/
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
