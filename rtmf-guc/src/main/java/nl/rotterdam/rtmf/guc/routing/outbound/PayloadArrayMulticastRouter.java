package nl.rotterdam.rtmf.guc.routing.outbound;

import java.util.ArrayList;
import java.util.List;

import org.mule.DefaultMuleMessage;
import org.mule.api.MessagingException;
import org.mule.api.MuleMessage;
import org.mule.api.MuleSession;
import org.mule.api.config.MuleProperties;
import org.mule.api.endpoint.ImmutableEndpoint;
import org.mule.api.endpoint.OutboundEndpoint;
import org.mule.api.routing.CouldNotRouteOutboundMessageException;
import org.mule.api.routing.RoutePathNotFoundException;
import org.mule.api.routing.RoutingException;
import org.mule.config.i18n.CoreMessages;
import org.mule.routing.outbound.FilteringOutboundRouter;

public class PayloadArrayMulticastRouter extends FilteringOutboundRouter
{

    public MuleMessage route(MuleMessage message, MuleSession session)
        throws RoutingException
    {
    	MuleMessage resultMessage;
        if (endpoints == null || endpoints.size() == 0)
        {
            throw new RoutePathNotFoundException(CoreMessages.noEndpointsForRouter(), message, null);
        }
 
        List<MuleMessage> results = new ArrayList<MuleMessage>(endpoints.size());
        try
        {
            OutboundEndpoint endpoint;
            for (int i = 0; i < endpoints.size(); i++)
            {
                endpoint = (OutboundEndpoint) endpoints.get(i);
                if(endpoint.getFilter()==null || (endpoint.getFilter()!=null && endpoint.getFilter().accept(message)))
                {
                    if (((DefaultMuleMessage) message).isConsumable())
                    {
                        throw new MessagingException(
                            CoreMessages.cannotCopyStreamPayload(message.getPayload().getClass().getName()),
                            message);
                    }
                    
                    MuleMessage clonedMessage = new DefaultMuleMessage(message.getPayload(), message);
                    
                    results.add(send(session, clonedMessage, endpoint));
                }
            }
            resultMessage = new DefaultMuleMessage(results.toArray((new MuleMessage[]{})));
    		resultMessage.setProperty(
					MuleProperties.MULE_CORRELATION_ID_PROPERTY,
					results.get(0).getProperty(MuleProperties.MULE_CORRELATION_ID_PROPERTY));
    		for (MuleMessage current : results) {
    			if (current.getProperty("attachmentKey") != null) {
    				logger.debug("Er is een propertie attachmentKey gevonden en op de resultMessage gezet");
    				resultMessage.setProperty("attachmentKey", current.getProperty("attachmentKey"));
    				break;
    			}
    		}
        }
        catch (Exception e)
        {
            throw new CouldNotRouteOutboundMessageException(message, (ImmutableEndpoint) endpoints.get(0), e);
        }
       
        
        return  resultMessage;
    }
}
