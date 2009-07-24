
package com.ovsoftware.ictu.osb.gateway.tmfcore.wus.wuslite;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ophalenMeldingKenmerkResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ophalenMeldingKenmerkResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="meldingMetaData" type="{http://wus.tmf.gbo.overheid.nl/wsdl/ophaalService-V1.1.xsd}terugmeldMetaType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ophalenMeldingKenmerkResponseType", propOrder = {
    "meldingMetaData"
})
public class OphalenMeldingKenmerkResponseType {

    @XmlElement(required = true)
    protected TerugmeldMetaType meldingMetaData;

    /**
     * Gets the value of the meldingMetaData property.
     * 
     * @return
     *     possible object is
     *     {@link TerugmeldMetaType }
     *     
     */
    public TerugmeldMetaType getMeldingMetaData() {
        return meldingMetaData;
    }

    /**
     * Sets the value of the meldingMetaData property.
     * 
     * @param value
     *     allowed object is
     *     {@link TerugmeldMetaType }
     *     
     */
    public void setMeldingMetaData(TerugmeldMetaType value) {
        this.meldingMetaData = value;
    }

}
