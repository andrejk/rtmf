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

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * @author rweverwijk
 *
 */
public class AddAttachmentsToTerugmeldingTransformerTest {
	private String baseXML = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:aan=\"http://wus.tmf.gbo.overheid.nl/wsdl/aanmeldService-V1.1.xsd\"><soapenv:Header/><soapenv:Body><aan:terugmelding><aan:meldingKenmerk>?</aan:meldingKenmerk><aan:tijdstempelAanlevering>?</aan:tijdstempelAanlevering><aan:basisRegistratie>?</aan:basisRegistratie><aan:objectTag>?</aan:objectTag><aan:objectIdentificatie>?</aan:objectIdentificatie><aan:toelichting>?</aan:toelichting><!--1 or more repetitions:--><aan:attributen><aan:attribuutIdentificatie>?</aan:attribuutIdentificatie><aan:betwijfeldeWaarde>?</aan:betwijfeldeWaarde><aan:voorstel>?</aan:voorstel></aan:attributen><!--Optional:--><aan:contactInfo><!--Optional:--><aan:naam>?</aan:naam><!--Optional:--><aan:telefoon>?</aan:telefoon><!--Optional:--><aan:email>?</aan:email></aan:contactInfo><!--Zero or more repetitions:--></aan:terugmelding></soapenv:Body></soapenv:Envelope>";
	/**
	 * Test method for {@link nl.rotterdam.rtmf.guc.transformer.MoveAttachmentsToStoreTransformer#transform(org.mule.api.MuleMessage, java.lang.String)}.
	 */
	@Test
	public void testTransformMuleMessageString() {
		AddAttachmentToTerugmelding addAttachmentToTerugmeldingTransformer = new AddAttachmentToTerugmelding();
		AttachmentStore store = new AttachmentStore();
		List<Attachment> attachments = new ArrayList<Attachment>();
		attachments.add(new Attachment("test.pdf", "cid:971467825229"));
		store.storeAttachment("123", attachments);
		addAttachmentToTerugmeldingTransformer.setStore(store);
		String nieuweXML = addAttachmentToTerugmeldingTransformer.buildAttachmentList(baseXML, "123");
		System.out.println(nieuweXML);
	}

}
