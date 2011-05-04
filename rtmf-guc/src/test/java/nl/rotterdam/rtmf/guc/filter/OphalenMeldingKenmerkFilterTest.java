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
package nl.rotterdam.rtmf.guc.filter;

import org.mule.DefaultMuleMessage;
import org.mule.api.MuleMessage;
import org.mule.tck.FunctionalTestCase;


/**
 * @author rweverwijk
 *
 */
public class OphalenMeldingKenmerkFilterTest extends FunctionalTestCase{
	
	public void testFilter() {
		OphalenMeldingKenmerkFilter filter = new OphalenMeldingKenmerkFilter();
		MuleMessage message = new DefaultMuleMessage("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" >\r\n" + 
				"   <soapenv:Header/>\r\n" + 
				"   <soapenv:Body>\r\n" + 
				"      <oph:ophalenMeldingKenmerk xmlns:oph=\"http://wus.tmf.gbo.overheid.nl/wsdl/ophaalService-V1.1.xsd\">\r\n" + 
				"         <oph:meldingKenmerk>123(m) - (K:TMD.09.10.00001)</oph:meldingKenmerk>\r\n" + 
				"      </oph:ophalenMeldingKenmerk>\r\n" + 
				"   </soapenv:Body>\r\n" + 
				"</soapenv:Envelope>");
		filter.setPrefix("K");
		assertTrue( filter.accept(message));
		filter.setPrefix("B");
		assertFalse( filter.accept(message));
		
		
		
	}

	/* (non-Javadoc)
	 * @see org.mule.tck.FunctionalTestCase#getConfigResources()
	 */
	@Override
	protected String getConfigResources() {
		return "rtmfguc-component-test-mule-config.xml";
	}
}
