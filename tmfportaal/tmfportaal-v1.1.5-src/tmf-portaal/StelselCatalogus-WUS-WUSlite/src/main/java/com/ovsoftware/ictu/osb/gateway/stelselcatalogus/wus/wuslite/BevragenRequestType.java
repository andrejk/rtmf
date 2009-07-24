
package com.ovsoftware.ictu.osb.gateway.stelselcatalogus.wus.wuslite;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BevragenRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BevragenRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="brTag" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="objectTag" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="objectId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BevragenRequestType", propOrder = {
    "brTag",
    "objectTag",
    "objectId"
})
public class BevragenRequestType {

    @XmlElement(required = true)
    protected String brTag;
    @XmlElement(required = true)
    protected String objectTag;
    @XmlElement(required = true)
    protected String objectId;

    /**
     * Gets the value of the brTag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBrTag() {
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
    public void setBrTag(String value) {
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

    /**
     * Gets the value of the objectId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObjectId() {
        return objectId;
    }

    /**
     * Sets the value of the objectId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObjectId(String value) {
        this.objectId = value;
    }

}
