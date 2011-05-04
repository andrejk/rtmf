package nl.rotterdam.rtmf;

import nl.rotterdam.rtmf.wicket.error.ZMWebErrorPage;
import nl.rotterdam.rtmf.wicket.error.ZMWebPageExpiredPage;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.settings.IApplicationSettings;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

/**
 * Application object for your web application. If you want to run this application without deploying, run the Start class.
 * 
 * @see nl.rotterdam.rtmf.Start#main(String[])
 */
public class WicketApplication extends WebApplication
{    
    /**
     * Constructor
     */
	public WicketApplication()
	{
	}
	
	@Override
	protected void init()
	{
		addComponentInstantiationListener(new SpringComponentInjector(this));
		IApplicationSettings applicationSettings = getApplicationSettings();
		applicationSettings.setInternalErrorPage(ZMWebErrorPage.class);
		applicationSettings.setPageExpiredErrorPage(ZMWebPageExpiredPage.class);
	}
	
	
	
	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<HomePage> getHomePage()
	{
		return HomePage.class;
	}

}
