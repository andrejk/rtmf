/**
 * 
 */
package nl.rotterdam.rtmf.form.pojo;

import java.io.Serializable;

import nl.rotterdam.rtmf.form.helper.SelectOption;

/**
 * @author rweverwijk
 * Dit is een Pojo om te gebruiken in het bijwerken status scherm
 */
public class BijwerkenStatusForm implements Serializable{
	private static final long serialVersionUID = -6747013185135333912L;
	private String toelichting;
	private SelectOption statusTerugmelding;
	/**
	 * @return the toelichting
	 */
	public String getToelichting() {
		return toelichting;
	}
	/**
	 * @param toelichting the toelichting to set
	 */
	public void setToelichting(String toelichting) {
		this.toelichting = toelichting;
	}
	/**
	 * @return the statusTerugmelding
	 */
	public SelectOption getStatusTerugmelding() {
		return statusTerugmelding;
	}
	/**
	 * @param statusTerugmelding the statusTerugmelding to set
	 */
	public void setStatusTerugmelding(SelectOption statusTerugmelding) {
		this.statusTerugmelding = statusTerugmelding;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ZoekForm [statusTerugmelding=" + statusTerugmelding.getKey()
				+ ", toelichting=" + toelichting + "]";
	}
	
	
}
