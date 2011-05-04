/**
 * 
 */
package nl.rotterdam.rtmf.form.helper;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * @author rweverwijk
 * Dit is een generieke SelectOption welke we kunnen gebruiken in een select om een mooie
 * key / value te krijgen in een html select element
 */
public class SelectOption implements Serializable{
	private static final long serialVersionUID = -6930928702071169711L;
	
	@SuppressWarnings("unchecked")
	public static final List<SelectOption> statusList = Arrays.asList(new SelectOption[]{	new SelectOption("ontvangen", "Ontvangen"),
			new SelectOption("gemeld", "Gemeld"),
			new SelectOption("onderzoek", "In onderzoek"),
			new SelectOption("ingetr", "Ingetrokken"),
			new SelectOption("gestaakt", "Gestaakt"),
			new SelectOption("nietontv", "Niet ontvankelijk"),
			new SelectOption("onderzocht", "Onderzocht")});
	
	private String key;
	
	private String value;

	public SelectOption(String key, String value) {
		this.key = key;
		this.value = value;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SelectOption other = (SelectOption) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		return true;
	}

}
