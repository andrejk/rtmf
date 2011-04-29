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

import org.apache.tools.ant.filters.StringInputStream;
import org.junit.Test;
import org.mule.DefaultMuleMessage;

/**
 * @author rweverwijk
 *
 */
public class RemoveAttachmentsTransformerTest {
	private String baseXML = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:aan=\"http://wus.tmf.gbo.overheid.nl/wsdl/aanmeldService-V1.1.xsd\"><soapenv:Header/><soapenv:Body><aan:terugmelding><aan:meldingKenmerk>?</aan:meldingKenmerk><aan:tijdstempelAanlevering>?</aan:tijdstempelAanlevering><aan:basisRegistratie>?</aan:basisRegistratie><aan:objectTag>?</aan:objectTag><aan:objectIdentificatie>?</aan:objectIdentificatie><aan:toelichting>?</aan:toelichting><!--1 or more repetitions:--><aan:attributen><aan:attribuutIdentificatie>?</aan:attribuutIdentificatie><aan:betwijfeldeWaarde>?</aan:betwijfeldeWaarde><aan:voorstel>?</aan:voorstel></aan:attributen><!--Optional:--><aan:contactInfo><!--Optional:--><aan:naam>?</aan:naam><!--Optional:--><aan:telefoon>?</aan:telefoon><!--Optional:--><aan:email>?</aan:email></aan:contactInfo><!--Zero or more repetitions:--><aan:attachment><aan:filename>file1</aan:filename><aan:base64attachment>cid:971467825229</aan:base64attachment></aan:attachment><aan:attachment><aan:filename>file2</aan:filename><aan:base64attachment>cid:971467825229</aan:base64attachment></aan:attachment></aan:terugmelding></soapenv:Body></soapenv:Envelope>";
	/**
	 * Test method for {@link nl.rotterdam.rtmf.guc.transformer.MoveAttachmentsToStoreTransformer#transform(org.mule.api.MuleMessage, java.lang.String)}.
	 */
	@Test
	public void testTransformMuleMessageString() {
		MoveAttachmentsToStoreTransformer removeAttachmentsTransformer = new MoveAttachmentsToStoreTransformer();
		AttachmentStore store = new AttachmentStore();
		removeAttachmentsTransformer.setStore(store);
		DefaultMuleMessage defaultMuleMessage = new DefaultMuleMessage(new StringInputStream(baseXML));
		Object filterAttachments = removeAttachmentsTransformer.filterAttachments(defaultMuleMessage);
		Object property = defaultMuleMessage.getProperty("attachments");
		System.out.println(filterAttachments);
	}

}
