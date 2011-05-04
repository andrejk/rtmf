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

import java.util.List;

import nl.rotterdam.rtmf.guc.bronhouder.bean.BronhouderInfo;
import nl.rotterdam.rtmf.guc.bronhouder.catalogus.BronhouderCatalogus;
import nl.rotterdam.rtmf.guc.common.DocumentParser;
import nl.rotterdam.rtmf.guc.exceptions.RtmfGucException;

import org.mule.api.MuleMessage;
import org.mule.api.routing.filter.Filter;

public class IntrekkingBronhouderCatalogusFilter implements Filter {

	private BronhouderCatalogus bronhouderCatalogus;
	private String expectedService;

	public void setBronhouderCatalogus(BronhouderCatalogus bronhouderCatalogus) {
		this.bronhouderCatalogus = bronhouderCatalogus;
	}

	public void setExpectedService(String expectedService) {
		this.expectedService = expectedService;
	}

	/* 
	 * @see org.mule.api.routing.filter.Filter#accept(org.mule.api.MuleMessage)
	 * Dit filter bepaald of het bericht verstuur moet worden naar de aangegeven service.
	 * Dit wordt bepaald obv de bronhouderCatalogus.
	 */
	@Override
	public boolean accept(MuleMessage message) {
		boolean result = false;
		String basisRegistratie = DocumentParser.parseTmfDocument(((String[])message.getPayload())[2],
		"/Envelope/Body/ZaakDetailResponse/Kenmerk[kenmerkBron='basisRegistratie']/kenmerk/text()");
		// We halen hier de Bronhouder gegevens op op basis van de basisRegistratie
		// We gaan er dan dus vanuit dat we de volgende structuur aantreffen in de propertie file
		// bronhouder1.object1=GBA
		// Dus dat Basisregistraties als objecten zijn gedeclareerd in de propertiefile.
		List<BronhouderInfo> bronhouderInfoByBasisregistratie = bronhouderCatalogus.getBronhouderInfoByBasisregistratie(basisRegistratie);
		if (bronhouderInfoByBasisregistratie == null || bronhouderInfoByBasisregistratie.isEmpty()) {
			throw new RtmfGucException("We hebben geen bronhouder kunnen vinden voor de basisregistratie: " + basisRegistratie);
		}
		if (bronhouderInfoByBasisregistratie.get(0).getBereikenVia().equalsIgnoreCase(expectedService)) {
			result = true;
		} 
		return result;

	}

}
