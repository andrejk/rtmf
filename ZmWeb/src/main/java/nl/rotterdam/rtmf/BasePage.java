package nl.rotterdam.rtmf;

import java.util.Properties;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * Homepage
 */
public class BasePage extends WebPage {

	private static final long serialVersionUID = 1L;
	@SpringBean
	private Properties properties;
	public BasePage() {
		add(new ExternalLink("headerHelpLink", properties.getProperty("rtmf.zmweb.helpLink")));
		add(new ExternalLink("footerHelpLink", properties.getProperty("rtmf.zmweb.helpLink")));
	}
}
