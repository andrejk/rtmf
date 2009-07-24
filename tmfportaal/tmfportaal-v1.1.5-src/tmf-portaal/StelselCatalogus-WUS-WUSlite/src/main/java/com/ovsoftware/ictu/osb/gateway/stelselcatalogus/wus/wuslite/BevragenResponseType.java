
package com.ovsoftware.ictu.osb.gateway.stelselcatalogus.wus.wuslite;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BevragenResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BevragenResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="objectinstantie" type="{http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd}ObjectValueType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BevragenResponseType", propOrder = {
    "objectinstantie"
})
public class BevragenResponseType {

    @XmlElement(required = true)
    protected ObjectValueType objectinstantie;

    /**
     * Gets the value of the objectinstantie property.
     * 
     * @return
     *     possible object is
     *     {@link ObjectValueType }
     *     
     */
    public ObjectValueType getObjectinstantie() {
        return objectinstantie;
    }

    /**
     * Sets the value of the objectinstantie property.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectValueType }
     *     
     */
    public void setObjectinstantie(ObjectValueType value) {
        this.objectinstantie = value;
    }

}
