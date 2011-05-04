
package com.ovsoftware.ictu.osb.gateway.tmfcore.wus.wuslite;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ophalenMeldingStatusRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ophalenMeldingStatusRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="meldingStatusRequest" type="{http://wus.tmf.gbo.overheid.nl/wsdl/ophaalService-V1.1.xsd}ophalenStatusRequestType" minOccurs="0"/>
 *         &lt;element name="meldingKenmerk" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tmfKenmerk" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ophalenMeldingStatusRequestType", propOrder = {
    "meldingStatusRequest",
    "meldingKenmerk",
    "tmfKenmerk"
})
public class OphalenMeldingStatusRequestType {

    protected OphalenStatusRequestType meldingStatusRequest;
    protected String meldingKenmerk;
    protected String tmfKenmerk;

    /**
     * Gets the value of the meldingStatusRequest property.
     * 
     * @return
     *     possible object is
     *     {@link OphalenStatusRequestType }
     *     
     */
    public OphalenStatusRequestType getMeldingStatusRequest() {
        return meldingStatusRequest;
    }

    /**
     * Sets the value of the meldingStatusRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link OphalenStatusRequestType }
     *     
     */
    public void setMeldingStatusRequest(OphalenStatusRequestType value) {
        this.meldingStatusRequest = value;
    }

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

}
