package nl.rotterdam.rtmf.guc.ping;


public class PingOphalenStatusLand extends AbstractSoapPing {
	
	private String url = "http://twd720.resource.ta-twd.rotterdam.nl:10080/services";
	private String request = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">" + 
			"   <soapenv:Header xmlns:wsa=\"http://www.w3.org/2005/08/addressing\"><wsa:Action>http://wus.tmf.gbo.overheid.nl/wsdl/ophalenService/ophalenMeldingStatusRequest/ophaalService</wsa:Action><wsa:MessageID>uuid:f0b72c2e-49e2-4b54-80fa-18c9b812f79c</wsa:MessageID> <wsa:To>http://www.w3.org/2005/08/addressing/anonymous</wsa:To>" + 
			"</soapenv:Header>" + 
			"   <soapenv:Body>" + 
			"      <oph:ophalenMeldingStatus xmlns:oph=\"http://wus.tmf.gbo.overheid.nl/wsdl/ophaalService-V1.1.xsd\">" + 
			"         <oph:meldingKenmerk>123</oph:meldingKenmerk>" + 
			"      </oph:ophalenMeldingStatus>" + 
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
		return "Webservice van de landelijke Ophaalservice voor het ophalen van terugmeldingen";
	}

	@Override
	public String getResourceName() {
		return "Ophaalservice landelijk";
	}

	@Override
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public static void main(String[] args) {
		PingOphalenStatusLand pingStelselcatalogusRott = new PingOphalenStatusLand();
		System.out.println("isAlive? " + pingStelselcatalogusRott.isAlive() + " errorMessage: " + pingStelselcatalogusRott.getErrorMessage());
	}
}
