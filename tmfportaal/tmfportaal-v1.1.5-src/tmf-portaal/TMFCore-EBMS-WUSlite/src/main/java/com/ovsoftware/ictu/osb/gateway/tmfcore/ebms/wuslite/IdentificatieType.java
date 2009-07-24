
package com.ovsoftware.ictu.osb.gateway.tmfcore.ebms.wuslite;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IdentificatieType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IdentificatieType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="oin" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="on" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="oun" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IdentificatieType", propOrder = {
    "oin",
    "on",
    "oun"
})
public class IdentificatieType {

    @XmlElement(required = true)
    protected String oin;
    protected String on;
    protected String oun;

    /**
     * Gets the value of the oin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOin() {
        return oin;
    }

    /**
     * Sets the value of the oin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOin(String value) {
        this.oin = value;
    }

    /**
     * Gets the value of the on property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOn() {
        return on;
    }

    /**
     * Sets the value of the on property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOn(String value) {
        this.on = value;
    }

    /**
     * Gets the value of the oun property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOun() {
        return oun;
    }

    /**
     * Sets the value of the oun property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOun(String value) {
        this.oun = value;
    }

}
