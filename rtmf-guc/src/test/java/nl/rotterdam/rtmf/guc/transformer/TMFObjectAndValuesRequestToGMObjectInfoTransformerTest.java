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

import org.junit.Test;

/**
 * @author rweverwijk
 *
 */
public class TMFObjectAndValuesRequestToGMObjectInfoTransformerTest {
	private String baseXML = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\"> <S:Header><To xmlns=\"http://www.w3.org/2005/08/addressing\">http://localhost:63081/guc/rtmfguc/stelselBevragenService</To><Action xmlns=\"http://www.w3.org/2005/08/addressing\">http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen/getObjectInfoAndValuesRequest/stelselBevragenService</Action><ReplyTo xmlns=\"http://www.w3.org/2005/08/addressing\"><Address>http://www.w3.org/2005/08/addressing/anonymous</Address></ReplyTo><MessageID xmlns=\"http://www.w3.org/2005/08/addressing\">uuid:93130acb-d59d-492d-9558-200bcd2579b5</MessageID></S:Header> <S:Body><ns2:getObjectInfoAndValues xmlns=\"http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd\" xmlns:ns2=\"http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.2.xsd\"><BRTag>GBA</BRTag><ObjectTag>01</ObjectTag></ns2:getObjectInfoAndValues></S:Body></S:Envelope>";
	/**
	 * Test method for {@link nl.rotterdam.rtmf.guc.transformer.MoveAttachmentsToStoreTransformer#transform(org.mule.api.MuleMessage, java.lang.String)}.
	 */
	@Test
	public void testTransformMuleMessageString() {
		TMFObjectAndValuesRequestToGMObjectInfo tmfObjectAndValueToObjectInfoTrans = new TMFObjectAndValuesRequestToGMObjectInfo();
		String nieuweXML = tmfObjectAndValueToObjectInfoTrans.transformMessage(baseXML);
		System.out.println(nieuweXML);
	}

}
