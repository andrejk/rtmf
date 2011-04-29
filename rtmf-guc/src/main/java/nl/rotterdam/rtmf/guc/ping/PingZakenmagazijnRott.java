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

public class PingZakenmagazijnRott extends AbstractSoapPing {

	private String url = "http://192.168.157.134:8888/zm/ZakenmagazijnManager";
	// We doen in deze request naar Oge_id 01 omdat daar niets in staat en dat dus snel response geeft.
	// We willen hier alleen de beschikbaarheid aantonen, dus daarvoor is dit voldoende.
	private String request = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:stat=\"http://www.interaccess.nl/webplus/statuswfm_v2\">" + 
			"	<soapenv:Header/>" + 
			"	<soapenv:Body>" + 
			"	   <stat:ZaakQuery returnVerzoekIdentificatie=\"false\">" + 
			"	      <stat:Zaaktypecode exact=\"true\">TMDG</stat:Zaaktypecode>" + 
			"	       <stat:Oge_id>01</stat:Oge_id>" + 
			"	   </stat:ZaakQuery>" + 
			"	</soapenv:Body>" + 
			"</soapenv:Envelope>";
	private String errorMessage;
	
	protected String getRequest() {
		return request;
	}

	/**
	 * @param url
	 *            the url to set
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
		return "Webservice van het Zakenmagazijn van Rotterdam";
	}

	@Override
	public String getResourceName() {
		return "Zakenmagazijn Rotterdam";
	}
	
	@Override
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public static void main(String[] args) {
		PingZakenmagazijnRott pingStelselcatalogusRott = new PingZakenmagazijnRott();
		System.out.println("isAlive? " + pingStelselcatalogusRott.isAlive()
				+ " errorMessage: "
				+ pingStelselcatalogusRott.getErrorMessage());
	}
}
