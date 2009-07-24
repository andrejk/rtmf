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
