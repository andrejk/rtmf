
package com.ovsoftware.ictu.osb.gateway.tmfcore.wus.wuslite;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ophalenStatusResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ophalenStatusResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="statusResponseList" type="{http://wus.tmf.gbo.overheid.nl/wsdl/ophaalService-V1.1.xsd}terugmeldResponseTypeList"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ophalenStatusResponseType", propOrder = {
    "statusResponseList"
})
public class OphalenStatusResponseType {

    @XmlElement(required = true)
    protected TerugmeldResponseTypeList statusResponseList;

    /**
     * Gets the value of the statusResponseList property.
     * 
     * @return
     *     possible object is
     *     {@link TerugmeldResponseTypeList }
     *     
     */
    public TerugmeldResponseTypeList getStatusResponseList() {
        return statusResponseList;
    }

    /**
     * Sets the value of the statusResponseList property.
     * 
     * @param value
     *     allowed object is
     *     {@link TerugmeldResponseTypeList }
     *     
     */
    public void setStatusResponseList(TerugmeldResponseTypeList value) {
        this.statusResponseList = value;
    }

}
