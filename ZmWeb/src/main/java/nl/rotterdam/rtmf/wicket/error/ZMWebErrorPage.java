/**
 * 
 */
package nl.rotterdam.rtmf.wicket.error;

import nl.rotterdam.rtmf.BasePage;
import nl.rotterdam.rtmf.HomePage;

import org.apache.wicket.markup.html.link.Link;

/**
 * @author rweverwijk
 *
 */
public class ZMWebErrorPage extends BasePage{
	public ZMWebErrorPage() {
		super();
		add(new Link("linkHome") {
			private static final long serialVersionUID = -5016350039112898953L;

			/* (non-Javadoc)
			 * @see org.apache.wicket.markup.html.link.Link#onClick()
			 */
			@Override
			public void onClick() {
				setResponsePage(HomePage.class);
			}
		});
	}
	
	/**
	 * @see org.apache.wicket.Page#isErrorPage()
	 */
	@Override
	public boolean isErrorPage()
	{
		return true;
	}
}
