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
package nl.rotterdam.rtmf.guc.ping;


public class PingStelselcatalogusLand extends AbstractSoapPing {
	
	private String url = "http://twd720.resource.ta-twd.rotterdam.nl:10080/services";
	private String request = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">" + 
			"   <soapenv:Header xmlns:wsa=\"http://www.w3.org/2005/08/addressing\"><wsa:Action>http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen/getBasisregistratieListRequest/stelselBevragenService</wsa:Action><wsa:MessageID>uuid:bbfc87b6-40e8-49ed-a07e-9bf255866db5</wsa:MessageID><wsa:To>http://www.w3.org/2005/08/addressing/anonymous</wsa:To>" + 
			"</soapenv:Header>" + 
			"   <soapenv:Body>" + 
			"      <stel:getBasisregistratieList xmlns:stel=\"http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd\"/>" + 
			"   </soapenv:Body>" + 
			"</soapenv:Envelope>";
	private String errorMessage;

	protected String getRequest() {
		return request;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	
	@Override
	public String getResourceDescription() {
		return "Webservice van de landelijke Stelselcatalogus";
	}

	@Override
	public String getResourceName() {
		return "Stelselcatalogus landelijk";
	}
	
	@Override
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public static void main(String[] args) {
		PingStelselcatalogusLand pingStelselcatalogusRott = new PingStelselcatalogusLand();
		System.out.println("isAlive? " + pingStelselcatalogusRott.isAlive() + " errorMessage: " + pingStelselcatalogusRott.getErrorMessage());
	}
}
