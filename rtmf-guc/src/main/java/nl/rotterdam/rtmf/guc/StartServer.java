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
package nl.rotterdam.rtmf.guc;

import org.apache.log4j.Logger;
import org.mule.api.MuleContext;
import org.mule.api.MuleException;
import org.mule.api.config.ConfigurationBuilder;
import org.mule.api.config.ConfigurationException;
import org.mule.api.context.MuleContextFactory;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.config.spring.SpringXmlConfigurationBuilder;
import org.mule.context.DefaultMuleContextFactory;

/**
 * Class om de mule server op te starten.
 * 
 * Wordt ook gebruikt in de Selenese test cases. 
 * 
 * Om problemen te voorkomen als de unit tests allemaal achter elkaar gedraait worden 
 * (Mule raakt het spoor beister met allen stops en starts van de mule context), wordt
 * er nu met een singleton oplossing (workaround) gewerkt. Er is dan maar 1 context aanwezig
 * voor alle Selenese testen.
 * 
 * @author rweverwijk 
 */
public class StartServer {
	private static Logger logger = Logger
			.getLogger(StartServer.class.getName());
	
	// need to specify this before the StartServer static variable, because this
	// RESOURCES is needed in the constructor
	private static final String[] RESOURCES = { 
		// appliction mule config files
		"guc_generic_config.xml",
		"rtmfguc-config.xml",
		"rtmfguc-zm-config.xml",
		// mock mule config files
		"rtmfguc-mocks-config.xml",
		"rtmfguc-mocks-zm-config.xml"
		};
	
	// the singleton start server class.
	private static StartServer server = new StartServer();
	
	private MuleContext context = null;

	/**
	 * Default constructor welke de mule server start.
	 */
	private StartServer() {
		this.context = setupServer(RESOURCES);
		try {
			this.context.start();
		} catch (MuleException e) {
			throw new RuntimeException("Failed to start Mule", e );
		}
	}

	/**
	 * This class starts the Mule server using the mule-config.xml configuration
	 * file.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		// org.apache.log4j.BasicConfigurator.configure();

		StartServer.singleton().startServer();

	}

	private MuleContext setupServer(String[] resources) {
		try {
			MuleContextFactory factory = new DefaultMuleContextFactory();
			ConfigurationBuilder builder = new SpringXmlConfigurationBuilder(
					resources);
			return factory.createMuleContext(builder);
		} catch (ConfigurationException ce) {
			throw new RuntimeException("Failed to create MuleContext for "
					+ resources, ce);
		} catch (InitialisationException e) {
			throw new RuntimeException("Failed to create MuleContext for "
					+ resources, e);
		}
	}

	/**
	 * Methode om mule te starten op basis van de context welke in de
	 * constructor gezet is.
	 */
	public void startServer() {
		logger.warn("Ignored startServer() call for Mule, only ONE Mule context is running as singleton.");
	}

	/**
	 * stop de server.
	 */
	public void stopServer() {
		logger.warn("Ignored stopServer() call for Mule, only ONE Mule context is running as singleton: it is not stopped unless JVM stops.");
	}
	
	public static StartServer singleton() {
		return server;
	}

}
