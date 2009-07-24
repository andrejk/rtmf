
package com.ovsoftware.ictu.osb.gateway.tmfcore.wus.wuslite;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ophalenTmfKenmerkResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ophalenTmfKenmerkResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="kenmerkResponseList" type="{http://wus.tmf.gbo.overheid.nl/wsdl/ophaalService-V1.1.xsd}statusTypeList"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ophalenTmfKenmerkResponseType", propOrder = {
    "kenmerkResponseList"
})
public class OphalenTmfKenmerkResponseType {

    @XmlElement(required = true)
    protected StatusTypeList kenmerkResponseList;

    /**
     * Gets the value of the kenmerkResponseList property.
     * 
     * @return
     *     possible object is
     *     {@link StatusTypeList }
     *     
     */
    public StatusTypeList getKenmerkResponseList() {
        return kenmerkResponseList;
    }

    /**
     * Sets the value of the kenmerkResponseList property.
     * 
     * @param value
     *     allowed object is
     *     {@link StatusTypeList }
     *     
     */
    public void setKenmerkResponseList(StatusTypeList value) {
        this.kenmerkResponseList = value;
    }

}
