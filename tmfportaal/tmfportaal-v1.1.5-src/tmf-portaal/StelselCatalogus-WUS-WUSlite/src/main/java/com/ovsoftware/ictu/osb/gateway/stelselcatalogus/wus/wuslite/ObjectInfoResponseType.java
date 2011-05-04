
package com.ovsoftware.ictu.osb.gateway.stelselcatalogus.wus.wuslite;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ObjectInfoResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ObjectInfoResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="objectInfo" type="{http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd}ObjectType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ObjectInfoResponseType", propOrder = {
    "objectInfo"
})
public class ObjectInfoResponseType {

    @XmlElement(required = true)
    protected ObjectType objectInfo;

    /**
     * Gets the value of the objectInfo property.
     * 
     * @return
     *     possible object is
     *     {@link ObjectType }
     *     
     */
    public ObjectType getObjectInfo() {
        return objectInfo;
    }

    /**
     * Sets the value of the objectInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectType }
     *     
     */
    public void setObjectInfo(ObjectType value) {
        this.objectInfo = value;
    }

}
