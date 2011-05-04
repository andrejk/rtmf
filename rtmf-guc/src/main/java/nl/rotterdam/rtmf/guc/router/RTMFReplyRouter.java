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
