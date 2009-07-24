
package com.ovsoftware.ictu.osb.gateway.stelselcatalogus.wus.wuslite;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ObjectInfoRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ObjectInfoRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BRTag" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ObjectTag" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ObjectInfoRequestType", propOrder = {
    "brTag",
    "objectTag"
})
public class ObjectInfoRequestType {

    @XmlElement(name = "BRTag", required = true)
    protected String brTag;
    @XmlElement(name = "ObjectTag", required = true)
    protected String objectTag;

    /**
     * Gets the value of the brTag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBRTag() {
        return brTag;
    }

    /**
     * Sets the value of the brTag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBRTag(String value) {
        this.brTag = value;
    }

    /**
     * Gets the value of the objectTag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObjectTag() {
        return objectTag;
    }

    /**
     * Sets the value of the objectTag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObjectTag(String value) {
        this.objectTag = value;
    }

}
