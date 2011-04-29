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
package nl.rotterdam.rtmf.guc.filter;

import nl.rotterdam.rtmf.guc.StelselCatalogusCache;

import org.mule.api.MuleMessage;
import org.mule.api.routing.filter.Filter;

/**
 * Base cache filter. Zorgt er voor dat de cache (database) te vinden is, en de
 * service waarnaar gezocht wordt. Beide properties worden mbv spring
 * geinjecteerd. <br>
 * Deze class wordt door de andere filter classen ge-extend.
 * 
 * @author Enno Buis
 * 
 */
public class CacheFilterBase implements Filter {
	protected StelselCatalogusCache cache;
	protected String expectedService;

	/**
	 * Spring injected
	 */
	public void setStelselCatalogusCache(
			StelselCatalogusCache stelselCatalogusCache) {
		this.cache = stelselCatalogusCache;
	}

	public void setExpectedService(String expectedService) {
		this.expectedService = expectedService;
	}

	@Override
	public boolean accept(MuleMessage message) {
		return false;
	}

}
