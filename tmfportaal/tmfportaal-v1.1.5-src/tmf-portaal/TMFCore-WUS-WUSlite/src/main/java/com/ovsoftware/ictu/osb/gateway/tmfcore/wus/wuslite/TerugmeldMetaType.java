
package com.ovsoftware.ictu.osb.gateway.tmfcore.wus.wuslite;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for terugmeldMetaType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="terugmeldMetaType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="terugmeldMCore" type="{http://wus.tmf.gbo.overheid.nl/wsdl/ophaalService-V1.1.xsd}terugmeldMetaCoreType"/>
 *         &lt;element name="afdeling" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tijdstempelOntvangst" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="tijdstempelGemeld" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="statusMelding" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="tijdstempelStatus" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="toelichting" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0" maxOccurs="1"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "terugmeldMetaType", propOrder = {
    "terugmeldMCore",
    "afdeling",
    "tijdstempelOntvangst",
    "tijdstempelGemeld",
    "statusMelding",
    "tijdstempelStatus",
    "toelichting"
})
public class TerugmeldMetaType {

    @XmlElement(required = true)
    protected TerugmeldMetaCoreType terugmeldMCore;
    protected String afdeling;
    @XmlElement(required = true)
    protected XMLGregorianCalendar tijdstempelOntvangst;
    protected XMLGregorianCalendar tijdstempelGemeld;
    @XmlElement(required = true)
    protected String statusMelding;
    @XmlElement(required = true)
    protected XMLGregorianCalendar tijdstempelStatus;
    protected String toelichting;

    /**
     * Gets the value of the terugmeldMCore property.
     * 
     * @return
     *     possible object is
     *     {@link TerugmeldMetaCoreType }
     *     
     */
    public TerugmeldMetaCoreType getTerugmeldMCore() {
        return terugmeldMCore;
    }

    /**
     * Sets the value of the terugmeldMCore property.
     * 
     * @param value
     *     allowed object is
     *     {@link TerugmeldMetaCoreType }
     *     
     */
    public void setTerugmeldMCore(TerugmeldMetaCoreType value) {
        this.terugmeldMCore = value;
    }

    /**
     * Gets the value of the afdeling property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAfdeling() {
        return afdeling;
    }

    /**
     * Sets the value of the afdeling property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAfdeling(String value) {
        this.afdeling = value;
    }

    /**
     * Gets the value of the tijdstempelOntvangst property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTijdstempelOntvangst() {
        return tijdstempelOntvangst;
    }

    /**
     * Sets the value of the tijdstempelOntvangst property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTijdstempelOntvangst(XMLGregorianCalendar value) {
        this.tijdstempelOntvangst = value;
    }

    /**
     * Gets the value of the tijdstempelGemeld property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTijdstempelGemeld() {
        return tijdstempelGemeld;
    }

    /**
     * Sets the value of the tijdstempelGemeld property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTijdstempelGemeld(XMLGregorianCalendar value) {
        this.tijdstempelGemeld = value;
    }

    /**
     * Gets the value of the statusMelding property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatusMelding() {
        return statusMelding;
    }

    /**
     * Sets the value of the statusMelding property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatusMelding(String value) {
        this.statusMelding = value;
    }

    /**
     * Gets the value of the tijdstempelStatus property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTijdstempelStatus() {
        return tijdstempelStatus;
    }

    /**
     * Sets the value of the tijdstempelStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTijdstempelStatus(XMLGregorianCalendar value) {
        this.tijdstempelStatus = value;
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
}
