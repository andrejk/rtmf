
package com.ovsoftware.ictu.osb.gateway.tmfcore.wus.wuslite;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ophalenMededelingResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ophalenMededelingResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="mededelingResponseList" type="{http://wus.tmf.gbo.overheid.nl/wsdl/ophaalService-V1.1.xsd}mededelingTypeList"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ophalenMededelingResponseType", propOrder = {
    "mededelingResponseList"
})
public class OphalenMededelingResponseType {

    @XmlElement(required = true)
    protected MededelingTypeList mededelingResponseList;

    /**
     * Gets the value of the mededelingResponseList property.
     * 
     * @return
     *     possible object is
     *     {@link MededelingTypeList }
     *     
     */
    public MededelingTypeList getMededelingResponseList() {
        return mededelingResponseList;
    }

    /**
     * Sets the value of the mededelingResponseList property.
     * 
     * @param value
     *     allowed object is
     *     {@link MededelingTypeList }
     *     
     */
    public void setMededelingResponseList(MededelingTypeList value) {
        this.mededelingResponseList = value;
    }

}
