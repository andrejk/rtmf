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
package nl.rotterdam.rtmf.guc.transformer;

import java.util.List;

import nl.rotterdam.rtmf.guc.bronhouder.bean.BronhouderInfo;
import nl.rotterdam.rtmf.guc.bronhouder.catalogus.BronhouderCatalogus;
import nl.rotterdam.rtmf.guc.common.DocumentParser;
import nl.rotterdam.rtmf.guc.exceptions.RtmfGucException;

import org.apache.log4j.Logger;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageAwareTransformer;

public class IntrekkingAddBereikenAdresTransformer extends AbstractMessageAwareTransformer {

	private BronhouderCatalogus bronhouderCatalogus;

	public void setBronhouderCatalogus(BronhouderCatalogus bronhouderCatalogus) {
		this.bronhouderCatalogus = bronhouderCatalogus;
	}

	/* (non-Javadoc)
	 * @see org.mule.transformer.AbstractMessageAwareTransformer#transform(org.mule.api.MuleMessage, java.lang.String)
	 */
	@Override
	public Object transform(MuleMessage message, String outputEncoding)
			throws TransformerException {
		Logger log = Logger.getLogger(IntrekkingAddBereikenAdresTransformer.class);
		String basisRegistratie = DocumentParser.parseTmfDocument(((String[])message.getPayload())[2],
		"/Envelope/Body/ZaakDetailResponse/Kenmerk[kenmerkBron='basisRegistratie']/kenmerk/text()");
		// We halen hier de Bronhouder gegevens op op basis van de basisRegistratie
		// We gaan er dan dus vanuit dat we de volgende structuur aantreffen in de propertie file
		// bronhouder1.basisregistratie1=GBA
		// Dus dat Basisregistraties als objecten zijn gedeclareerd in de propertiefile.
		List<BronhouderInfo> bronhouderInfoByBasisregistratie = bronhouderCatalogus.getBronhouderInfoByBasisregistratie(basisRegistratie);
		if (bronhouderInfoByBasisregistratie == null || bronhouderInfoByBasisregistratie.isEmpty()) {
			throw new RtmfGucException("We hebben geen bronhouder kunnen vinden voor de basisregistratie: " + basisRegistratie);
		}
		log.debug("Er is een bronhouder gevonden voor basisregistratie " + basisRegistratie);
		if (bronhouderInfoByBasisregistratie.get(0).getBereikenVia().equalsIgnoreCase("Email")) {
			message.setProperty("toAddresses", bronhouderInfoByBasisregistratie.get(0).getBereikenAdres());
			log.debug("Er is een Emailadres gevonden voor basisregistratie " + bronhouderInfoByBasisregistratie.get(0).getBereikenAdres());
		} else if (bronhouderInfoByBasisregistratie.get(0).getBereikenVia().equalsIgnoreCase("File")) {
			message.setProperty("fileName", bronhouderInfoByBasisregistratie.get(0).getBereikenAdres());
			log.debug("Bereiken via is een File naar pad : " + bronhouderInfoByBasisregistratie.get(0).getBereikenAdres());
		} else {
			throw new RtmfGucException("We hebben geen email adres kunnen vinden voor de basisregistratie: " + basisRegistratie);
		}
		
	
		return message;
	}

	
}
