package nl.rotterdam.rtmf.guc.ping;

public class PingStelselcatalogusRott extends AbstractSoapPing {

	private String url = "http://twd676.resource.ta-twd.rotterdam.nl:2100/domein/gm/sozawe";
	private String request = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
			+ "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">"
			+ "	<soapenv:Header xmlns:wsa=\"http://www.w3.org/2005/08/addressing\">"
			+ "		<wsa:Action>http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen/getBasisregistratieListRequest/stelselBevragenService"
			+ "		</wsa:Action>"
			+ "		<wsa:MessageID>uuid:84b42edc-6715-48c2-9b93-680c5a55c080"
			+ "		</wsa:MessageID>"
			+ "		<wsa:To>http://twd720.resource.ta-twd.rotterdam.nl:10080/services"
			+ "		</wsa:To>"
			+ "	</soapenv:Header>"
			+ "	<soapenv:Body>"
			+ "		<sb-xsd:getBasisregistratieList xmlns:sb-xsd=\"http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1-IOO.xsd\"/>"
			+ "	</soapenv:Body>" + "</soapenv:Envelope>";
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
		return "Webservice van de Stelselcatalogus van Rotterdam";
	}

	@Override
	public String getResourceName() {
		return "Stelselcatalogus Rotterdam";
	}
	
	@Override
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public static void main(String[] args) {
		PingStelselcatalogusRott pingStelselcatalogusRott = new PingStelselcatalogusRott();
		System.out.println("isAlive? " + pingStelselcatalogusRott.isAlive()
				+ " errorMessage: "
				+ pingStelselcatalogusRott.getErrorMessage());
	}
}
