/*
 * $Id: RtmfTemplateEndpointRouter.java 12886 2008-10-03 20:32:55Z rossmason $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package nl.rotterdam.rtmf.guc.routing.outbound;
	
import org.apache.log4j.Logger;
import org.mule.MuleServer;
import org.mule.RegistryContext;
import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.api.MuleSession;
import org.mule.api.endpoint.ImmutableEndpoint;
import org.mule.api.endpoint.OutboundEndpoint;
import org.mule.api.routing.CouldNotRouteOutboundMessageException;
import org.mule.api.routing.RoutePathNotFoundException;
import org.mule.api.routing.RoutingException;
import org.mule.config.i18n.CoreMessages;
import org.mule.routing.outbound.FilteringOutboundRouter;

/**
 * The RtmfTemplateEndpointRouter allows endpoints to be altered at runtime based on
 * properties set on the current event or fallback values set on the endpoint properties.
 * Templated values are expressed using square braces around a property name, i.e.
 *    axis:http://localhost:8082/MyService?method=#[header:SOAP_METHOD]
 *    jms://#[header:QUEUE_NAME]
 */
public class RtmfTemplateEndpointRouter extends FilteringOutboundRouter
{
	private static Logger logger = Logger.getLogger("rtmfguc.routing.outbounfd.RtmfTemplateEndpointRouter");


    public MuleMessage route(MuleMessage message, MuleSession session)
        throws RoutingException
    {
        MuleMessage result = null;

        if (endpoints == null || endpoints.size() == 0)
        {
            throw new RoutePathNotFoundException(CoreMessages.noEndpointsForRouter(), message, null);
        }

        try
        {
            OutboundEndpoint ep = (OutboundEndpoint) endpoints.get(0);
            String uri = ep.getEndpointURI().toString();

            if (logger.isDebugEnabled())
            {
                logger.debug("Uri before parsing is: " + uri);
            }

            uri = MuleServer.getMuleContext().getExpressionManager().parse(uri, message);

            if (logger.isDebugEnabled())
            {
                logger.debug("Uri after parsing is: " + uri);
            }

            ep = RegistryContext.getRegistry().lookupEndpointFactory().getEndpointBuilder(uri).buildOutboundEndpoint();

            if (!ep.getEndpointURI().getScheme().equalsIgnoreCase(ep.getEndpointURI().getScheme()))
            {
                throw new CouldNotRouteOutboundMessageException(CoreMessages.schemeCannotChangeForRouter(
                    ep.getEndpointURI().getScheme(), ep.getEndpointURI().getScheme()), message, ep);
            }

            if (ep.isSynchronous())
            {
                result = send(session, message, ep);
            }
            else
            {
                dispatch(session, message, ep);
            }
        }
        catch (MuleException e)
        {
            throw new CouldNotRouteOutboundMessageException(message, (ImmutableEndpoint) endpoints.get(0), e);
        }

        return result;
    }

}