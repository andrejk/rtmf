
package com.ovsoftware.ictu.osb.gateway.tmfcore.ebms.wuslite;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TmfRHResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TmfRHResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://wus.tmf.gbo.overheid.nl/wsdl/aanmeldService-V1.1.xsd}TmfResponseType">
 *       &lt;attribute name="BrKenmerk" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TmfRHResponseType")
public class TmfRHResponseType
    extends TmfResponseType
{

    @XmlAttribute(name = "BrKenmerk")
    protected String brKenmerk;

    /**
     * Gets the value of the brKenmerk property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBrKenmerk() {
        return brKenmerk;
    }

    /**
     * Sets the value of the brKenmerk property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBrKenmerk(String value) {
        this.brKenmerk = value;
    }

}
