/**
 * 
 */
package nl.rotterdam.rtmf.form.pojo;

import java.io.Serializable;

/**
 * @author rweverwijk
 *
 */
public class Attribuut implements Serializable{
	private static final long serialVersionUID = 6748314807965171522L;

	private String id;
	private String betwijfeldeWaarde;
	private String voorstel;
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the betwijfeldeWaarde
	 */
	public String getBetwijfeldeWaarde() {
		return betwijfeldeWaarde;
	}
	/**
	 * @param betwijfeldeWaarde the betwijfeldeWaarde to set
	 */
	public void setBetwijfeldeWaarde(String betwijfeldeWaarde) {
		this.betwijfeldeWaarde = betwijfeldeWaarde;
	}
	/**
	 * @return the voorstel
	 */
	public String getVoorstel() {
		return voorstel;
	}
	/**
	 * @param voorstel the voorstel to set
	 */
	public void setVoorstel(String voorstel) {
		this.voorstel = voorstel;
	}
}
