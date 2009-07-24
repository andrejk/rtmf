package nl.rotterdam.rtmf.guc;

/**
 * Een enum om de magic values "GM" "TMF" en "Both" te vervangen in de code. 
 * 
 * De nieuwe aanduidingen zijn respectivelijk Landelijk, Rotterdam, Beide.
 * 
 * @author Peter Paul Bakker
 */
public enum RtmfExterneService {
	
	Landelijk("TMF"), Rotterdam("GM"), Beide("Both");

	private String naam;
	
	RtmfExterneService(String naam) {
		this.naam = naam;
	}
	
	public String toString() {
		return naam;
	}
	
}
