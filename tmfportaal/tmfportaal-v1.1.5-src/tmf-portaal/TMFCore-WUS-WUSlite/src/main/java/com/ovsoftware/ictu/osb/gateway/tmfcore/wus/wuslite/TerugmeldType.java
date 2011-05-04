
package com.ovsoftware.ictu.osb.gateway.tmfcore.wus.wuslite;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for terugmeldType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="terugmeldType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="meldingKenmerk" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="naamContact" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="telefoonContact" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="emailContact" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="tijdstempelAanlever" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="tagBR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="tagObject" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idObject" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="objectAttributen" type="{http://wus.tmf.gbo.overheid.nl/wsdl/ophaalService-V1.1.xsd}objectAttribuutTypeList"/>
 *         &lt;element name="toelichting" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "terugmeldType", propOrder = {
    "meldingKenmerk",
    "naamContact",
    "telefoonContact",
    "emailContact",
    "tijdstempelAanlever",
    "tagBR",
    "tagObject",
    "idObject",
    "objectAttributen",
    "toelichting",
    "status"
})
public class TerugmeldType {

    @XmlElement(required = true)
    protected String meldingKenmerk;
    @XmlElement(required = true)
    protected String naamContact;
    @XmlElement(required = true)
    protected String telefoonContact;
    @XmlElement(required = true)
    protected String emailContact;
    @XmlElement(required = true)
    protected XMLGregorianCalendar tijdstempelAanlever;
    @XmlElement(required = true)
    protected String tagBR;
    @XmlElement(required = true)
    protected String tagObject;
    @XmlElement(required = true)
    protected String idObject;
    @XmlElement(required = true)
    protected ObjectAttribuutTypeList objectAttributen;
    @XmlElement(required = true)
    protected String toelichting;
    @XmlElement(required = true)
    protected String status;

    /**
     * Gets the value of the meldingKenmerk property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMeldingKenmerk() {
        return meldingKenmerk;
    }

    /**
     * Sets the value of the meldingKenmerk property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMeldingKenmerk(String value) {
        this.meldingKenmerk = value;
    }

    /**
     * Gets the value of the naamContact property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNaamContact() {
        return naamContact;
    }

    /**
     * Sets the value of the naamContact property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNaamContact(String value) {
        this.naamContact = value;
    }

    /**
     * Gets the value of the telefoonContact property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTelefoonContact() {
        return telefoonContact;
    }

    /**
     * Sets the value of the telefoonContact property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTelefoonContact(String value) {
        this.telefoonContact = value;
    }

    /**
     * Gets the value of the emailContact property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmailContact() {
        return emailContact;
    }

    /**
     * Sets the value of the emailContact property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmailContact(String value) {
        this.emailContact = value;
    }

    /**
     * Gets the value of the tijdstempelAanlever property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public XMLGregorianCalendar getTijdstempelAanlever() {
        return tijdstempelAanlever;
    }

    /**
     * Sets the value of the tijdstempelAanlever property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTijdstempelAanlever(XMLGregorianCalendar value) {
        this.tijdstempelAanlever = value;
    }

    /**
     * Gets the value of the tagBR property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTagBR() {
        return tagBR;
    }

    /**
     * Sets the value of the tagBR property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTagBR(String value) {
        this.tagBR = value;
    }

    /**
     * Gets the value of the tagObject property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTagObject() {
        return tagObject;
    }

    /**
     * Sets the value of the tagObject property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTagObject(String value) {
        this.tagObject = value;
    }

    /**
     * Gets the value of the idObject property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdObject() {
        return idObject;
    }

    /**
     * Sets the value of the idObject property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdObject(String value) {
        this.idObject = value;
    }

    /**
     * Gets the value of the objectAttributen property.
     * 
     * @return
     *     possible object is
     *     {@link ObjectAttribuutTypeList }
     *     
     */
    public ObjectAttribuutTypeList getObjectAttributen() {
        return objectAttributen;
    }

    /**
     * Sets the value of the objectAttributen property.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectAttribuutTypeList }
     *     
     */
    public void setObjectAttributen(ObjectAttribuutTypeList value) {
        this.objectAttributen = value;
    }

    /**
     * Gets the value of the toelichting property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToelichting() {
        return toelichting;
    }

    /**
     * Sets the value of the toelichting property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToelichting(String value) {
        this.toelichting = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
	public String getStatus() {
		return status;
	}

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
	public void setStatus(String status) {
		this.status = status;
	}
}
