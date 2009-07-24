
package com.ovsoftware.ictu.osb.gateway.tmfcore.wus.wuslite;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for terugmeldResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="terugmeldResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="terugmeld" type="{http://wus.tmf.gbo.overheid.nl/wsdl/ophaalService-V1.1.xsd}terugmeldType"/>
 *         &lt;element name="terugmeldMCore" type="{http://wus.tmf.gbo.overheid.nl/wsdl/ophaalService-V1.1.xsd}terugmeldMetaCoreType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "terugmeldResponseType", propOrder = {
    "terugmeld",
    "terugmeldMCore"
})
public class TerugmeldResponseType {

    @XmlElement(required = true)
    protected TerugmeldType terugmeld;
    @XmlElement(required = true)
    protected TerugmeldMetaCoreType terugmeldMCore;

    /**
     * Gets the value of the terugmeld property.
     * 
     * @return
     *     possible object is
     *     {@link TerugmeldType }
     *     
     */
    public TerugmeldType getTerugmeld() {
        return terugmeld;
    }

    /**
     * Sets the value of the terugmeld property.
     * 
     * @param value
     *     allowed object is
     *     {@link TerugmeldType }
     *     
     */
    public void setTerugmeld(TerugmeldType value) {
        this.terugmeld = value;
    }

    /**
     * Gets the value of the terugmeldMCore property.
     * 
     * @return
     *     possible object is
     *     {@link TerugmeldMetaCoreType }
     *     
     */
    public TerugmeldMetaCoreType getTerugmeldMCore() {
        return terugmeldMCore;
    }

    /**
     * Sets the value of the terugmeldMCore property.
     * 
     * @param value
     *     allowed object is
     *     {@link TerugmeldMetaCoreType }
     *     
     */
    public void setTerugmeldMCore(TerugmeldMetaCoreType value) {
        this.terugmeldMCore = value;
    }

}
