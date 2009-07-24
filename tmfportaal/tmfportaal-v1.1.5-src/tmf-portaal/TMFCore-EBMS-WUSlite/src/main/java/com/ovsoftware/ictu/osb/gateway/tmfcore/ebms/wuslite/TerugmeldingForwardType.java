
package com.ovsoftware.ictu.osb.gateway.tmfcore.ebms.wuslite;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TerugmeldingForwardType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TerugmeldingForwardType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="metaData" type="{http://wus.tmf.gbo.overheid.nl/wsdl/aanmeldService-V1.1.xsd}TmfMetaDataType"/>
 *         &lt;element name="terugmelding" type="{http://wus.tmf.gbo.overheid.nl/wsdl/aanmeldService-V1.1.xsd}TerugmeldingRequestType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TerugmeldingForwardType", propOrder = {
    "metaData",
    "terugmelding"
})
public class TerugmeldingForwardType {

    @XmlElement(required = true)
    protected TmfMetaDataType metaData;
    @XmlElement(required = true)
    protected TerugmeldingRequestType terugmelding;

    /**
     * Gets the value of the metaData property.
     * 
     * @return
     *     possible object is
     *     {@link TmfMetaDataType }
     *     
     */
    public TmfMetaDataType getMetaData() {
        return metaData;
    }

    /**
     * Sets the value of the metaData property.
     * 
     * @param value
     *     allowed object is
     *     {@link TmfMetaDataType }
     *     
     */
    public void setMetaData(TmfMetaDataType value) {
        this.metaData = value;
    }

    /**
     * Gets the value of the terugmelding property.
     * 
     * @return
     *     possible object is
     *     {@link TerugmeldingRequestType }
     *     
     */
    public TerugmeldingRequestType getTerugmelding() {
        return terugmelding;
    }

    /**
     * Sets the value of the terugmelding property.
     * 
     * @param value
     *     allowed object is
     *     {@link TerugmeldingRequestType }
     *     
     */
    public void setTerugmelding(TerugmeldingRequestType value) {
        this.terugmelding = value;
    }

}
