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
package nl.rotterdam.rtmf.guc.test;

import nl.rotterdam.rtmf.guc.StartServer;

import org.subethamail.wiser.Wiser;

import com.thoughtworks.selenium.SeleneseTestCase;

/**
 * Deze base class kan gebruikt worden door de diverse Selenium Use Case tests.
 * Het zorgt ervoor dat de Mule server wordt gestart en wordt afgesloten.
 * 
 * @author Enno Buis
 * 
 */
public class RtmfSeleniumUseCaseBase extends SeleneseTestCase {

	final static protected String PAGE_WAIT_TIMEOUT = "50000";
	private Wiser smtpServer;
		
	@Override
	public void setUp() throws Exception {
		setUp("http://localhost:9191/", "*chrome");
		StartServer.singleton().startServer();
		// start smtp server mock
		smtpServer = new Wiser();
		smtpServer.setPort(18089);
		smtpServer.start();
	}

	@Override
	public void tearDown() throws Exception {
		smtpServer.stop();
		StartServer.singleton().stopServer();
		super.tearDown();
	}

}
