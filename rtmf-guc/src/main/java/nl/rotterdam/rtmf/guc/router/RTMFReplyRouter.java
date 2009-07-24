package nl.rotterdam.rtmf.guc.router;

import org.mule.routing.EventCorrelatorCallback;
import org.mule.routing.response.AbstractResponseAggregator;

/**
 * @author rweverwijk
 * Standaard RTMF reply router voor het samenvoegen van gesplitste berichten.
 */
public class RTMFReplyRouter extends AbstractResponseAggregator {

	@Override
	protected EventCorrelatorCallback getCorrelatorCallback() {
		return new RTMFCorrelationCallBack();
	}

}
