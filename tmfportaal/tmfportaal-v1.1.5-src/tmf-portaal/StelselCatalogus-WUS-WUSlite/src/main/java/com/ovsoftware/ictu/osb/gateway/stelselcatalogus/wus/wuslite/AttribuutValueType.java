
package com.ovsoftware.ictu.osb.gateway.stelselcatalogus.wus.wuslite;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AttribuutValueType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AttribuutValueType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="attribuutInfo" type="{http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd}AttribuutType"/>
 *         &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AttribuutValueType", propOrder = {
    "attribuutInfo",
    "value"
})
public class AttribuutValueType {

    @XmlElement(required = true)
    protected AttribuutType attribuutInfo;
    @XmlElement(required = true)
    protected String value;

    /**
     * Gets the value of the attribuutInfo property.
     * 
     * @return
     *     possible object is
     *     {@link AttribuutType }
     *     
     */
    public AttribuutType getAttribuutInfo() {
        return attribuutInfo;
    }

    /**
     * Sets the value of the attribuutInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttribuutType }
     *     
     */
    public void setAttribuutInfo(AttribuutType value) {
        this.attribuutInfo = value;
    }

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

}
