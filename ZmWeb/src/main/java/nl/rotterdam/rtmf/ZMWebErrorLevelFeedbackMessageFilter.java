/**
 * 
 */
package nl.rotterdam.rtmf;

import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.IFeedbackMessageFilter;

/**
 * @author rweverwijk
 *
 */
public class ZMWebErrorLevelFeedbackMessageFilter implements IFeedbackMessageFilter{
	private int errorLevel;
	/**
	 * @param minimumErrorLevel
	 */
	public ZMWebErrorLevelFeedbackMessageFilter(int minimumErrorLevel) {
		this.errorLevel = minimumErrorLevel;
	}
	
	

	private static final long serialVersionUID = -2777568568556160640L;
	/* (non-Javadoc)
	 * @see org.apache.wicket.feedback.IFeedbackMessageFilter#accept(org.apache.wicket.feedback.FeedbackMessage)
	 */
	public boolean accept(FeedbackMessage message) {
		return message.getLevel() == errorLevel;
	}

}
