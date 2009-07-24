
package com.ovsoftware.ictu.osb.gateway.tmfcore.ebms.wuslite;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for IntrekkingRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IntrekkingRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="meldingKenmerk" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="tijdstempelAanlevering" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="betreftTmfKenmerk" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="toelichting" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IntrekkingRequestType", propOrder = {
    "meldingKenmerk",
    "tijdstempelAanlevering",
    "betreftTmfKenmerk",
    "toelichting"
})
public class IntrekkingRequestType {

    @XmlElement(required = true)
    protected String meldingKenmerk;
    @XmlElement(required = true)
    protected XMLGregorianCalendar tijdstempelAanlevering;
    @XmlElement(required = true)
    protected String betreftTmfKenmerk;
    @XmlElement(required = true)
    protected String toelichting;

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
     * Gets the value of the tijdstempelAanlevering property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTijdstempelAanlevering() {
        return tijdstempelAanlevering;
    }

    /**
     * Sets the value of the tijdstempelAanlevering property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTijdstempelAanlevering(XMLGregorianCalendar value) {
        this.tijdstempelAanlevering = value;
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
