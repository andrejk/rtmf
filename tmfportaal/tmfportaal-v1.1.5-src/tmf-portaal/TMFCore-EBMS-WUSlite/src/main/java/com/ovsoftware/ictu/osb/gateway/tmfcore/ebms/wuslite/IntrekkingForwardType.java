
package com.ovsoftware.ictu.osb.gateway.tmfcore.ebms.wuslite;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IntrekkingForwardType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IntrekkingForwardType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="metaData" type="{http://wus.tmf.gbo.overheid.nl/wsdl/aanmeldService-V1.1.xsd}TmfMetaDataType"/>
 *         &lt;element name="intrekking" type="{http://wus.tmf.gbo.overheid.nl/wsdl/aanmeldService-V1.1.xsd}IntrekkingRequestType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IntrekkingForwardType", propOrder = {
    "metaData",
    "intrekking"
})
public class IntrekkingForwardType {

    @XmlElement(required = true)
    protected TmfMetaDataType metaData;
    @XmlElement(required = true)
    protected IntrekkingRequestType intrekking;

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
     * Gets the value of the intrekking property.
     * 
     * @return
     *     possible object is
     *     {@link IntrekkingRequestType }
     *     
     */
    public IntrekkingRequestType getIntrekking() {
        return intrekking;
    }

    /**
     * Sets the value of the intrekking property.
     * 
     * @param value
     *     allowed object is
     *     {@link IntrekkingRequestType }
     *     
     */
    public void setIntrekking(IntrekkingRequestType value) {
        this.intrekking = value;
    }

}
