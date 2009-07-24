
package com.ovsoftware.ictu.osb.gateway.tmfcore.wus.wuslite;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for mededelingType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="mededelingType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="tmfKenmerk" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="tijdstempelStatusChange" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="brKenmerk" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="betreftTmfKenmerk" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="nieuweStatus" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "mededelingType", propOrder = {
    "tmfKenmerk",
    "tijdstempelStatusChange",
    "brKenmerk",
    "betreftTmfKenmerk",
    "nieuweStatus"
})
public class MededelingType {

    @XmlElement(required = true)
    protected String tmfKenmerk;
    @XmlElement(required = true)
    protected XMLGregorianCalendar tijdstempelStatusChange;
    @XmlElement(required = true)
    protected String brKenmerk;
    @XmlElement(required = true)
    protected String betreftTmfKenmerk;
    @XmlElement(required = true)
    protected String nieuweStatus;

    /**
     * Gets the value of the tmfKenmerk property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTmfKenmerk() {
        return tmfKenmerk;
    }

    /**
     * Sets the value of the tmfKenmerk property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTmfKenmerk(String value) {
        this.tmfKenmerk = value;
    }

    /**
     * Gets the value of the tijdstempelStatusChange property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTijdstempelStatusChange() {
        return tijdstempelStatusChange;
    }

    /**
     * Sets the value of the tijdstempelStatusChange property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTijdstempelStatusChange(XMLGregorianCalendar value) {
        this.tijdstempelStatusChange = value;
    }

    /**
     * Gets the value of the brKenmerk property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBrKenmerk() {
        return brKenmerk;
    }

    /**
     * Sets the value of the brKenmerk property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBrKenmerk(String value) {
        this.brKenmerk = value;
    }

    /**
     * Gets the value of the betreftTmfKenmerk property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBetreftTmfKenmerk() {
        return betreftTmfKenmerk;
    }

    /**
     * Sets the value of the betreftTmfKenmerk property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBetreftTmfKenmerk(String value) {
        this.betreftTmfKenmerk = value;
    }

    /**
     * Gets the value of the nieuweStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNieuweStatus() {
        return nieuweStatus;
    }

    /**
     * Sets the value of the nieuweStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNieuweStatus(String value) {
        this.nieuweStatus = value;
    }

}
