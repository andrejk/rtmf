/**
 * 
 */
package nl.rotterdam.rtmf.guc.transformer;

import java.util.Date;

/**
 * @author rweverwijk
 * Dit is een pojo object om attachments in op te slaan
 */
public class Attachment {
	private String filename;
	private String data;
	private Date creationDate;
	
	/**
	 * @param filename
	 * @param data
	 */
	public Attachment(String filename, String data) {
		this.filename = filename;
		this.data = data;
	}
	/**
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}
	/**
	 * @param filename the filename to set
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}
	/**
	 * @return the data
	 */
	public String getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(String data) {
		this.data = data;
	}
	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}
	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
}
