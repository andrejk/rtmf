
package com.ovsoftware.ictu.osb.gateway.tmfcore.ebms.wuslite;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for TmfMetaDataType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TmfMetaDataType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TmfKenmerk" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Berichtsoort" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="tijdstempelAflevering" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="Identificatie" type="{http://wus.tmf.gbo.overheid.nl/wsdl/aanmeldService-V1.1.xsd}IdentificatieType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TmfMetaDataType", propOrder = {
    "tmfKenmerk",
    "berichtsoort",
    "tijdstempelAflevering",
    "identificatie"
})
public class TmfMetaDataType {

    @XmlElement(name = "TmfKenmerk", required = true)
    protected String tmfKenmerk;
    @XmlElement(name = "Berichtsoort", required = true)
    protected String berichtsoort;
    @XmlElement(required = true)
    protected XMLGregorianCalendar tijdstempelAflevering;
    @XmlElement(name = "Identificatie", required = true)
    protected IdentificatieType identificatie;

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
     * Gets the value of the berichtsoort property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBerichtsoort() {
        return berichtsoort;
    }

    /**
     * Sets the value of the berichtsoort property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBerichtsoort(String value) {
        this.berichtsoort = value;
    }

    /**
     * Gets the value of the tijdstempelAflevering property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTijdstempelAflevering() {
        return tijdstempelAflevering;
    }

    /**
     * Sets the value of the tijdstempelAflevering property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTijdstempelAflevering(XMLGregorianCalendar value) {
        this.tijdstempelAflevering = value;
    }

    /**
     * Gets the value of the identificatie property.
     * 
     * @return
     *     possible object is
     *     {@link IdentificatieType }
     *     
     */
    public IdentificatieType getIdentificatie() {
        return identificatie;
    }

    /**
     * Sets the value of the identificatie property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdentificatieType }
     *     
     */
    public void setIdentificatie(IdentificatieType value) {
        this.identificatie = value;
    }

}
