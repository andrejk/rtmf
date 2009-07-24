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
