
package com.ovsoftware.ictu.osb.gateway.tmfcore.wus.wuslite;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for terugmeldMetaCoreType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="terugmeldMetaCoreType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="tmfKenmerk" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="berichtSoort" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idOrganisatie" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="naamOrganisatie" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "terugmeldMetaCoreType", propOrder = {
    "tmfKenmerk",
    "berichtSoort",
    "idOrganisatie",
    "naamOrganisatie"
})
public class TerugmeldMetaCoreType {

    @XmlElement(required = true)
    protected String tmfKenmerk;
    @XmlElement(required = true)
    protected String berichtSoort;
    @XmlElement(required = true)
    protected String idOrganisatie;
    @XmlElement(required = true)
    protected String naamOrganisatie;

    /**
     * Gets the value of the tmfKenmerk property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTmfKenmerk() {
        return tmfKenmerk;
    }

    /**
     * Sets the value of the tmfKenmerk property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTmfKenmerk(String value) {
        this.tmfKenmerk = value;
    }

    /**
     * Gets the value of the berichtSoort property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBerichtSoort() {
        return berichtSoort;
    }

    /**
     * Sets the value of the berichtSoort property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBerichtSoort(String value) {
        this.berichtSoort = value;
    }

    /**
     * Gets the value of the idOrganisatie property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdOrganisatie() {
        return idOrganisatie;
    }

    /**
     * Sets the value of the idOrganisatie property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdOrganisatie(String value) {
        this.idOrganisatie = value;
    }

    /**
     * Gets the value of the naamOrganisatie property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNaamOrganisatie() {
        return naamOrganisatie;
    }

    /**
     * Sets the value of the naamOrganisatie property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNaamOrganisatie(String value) {
        this.naamOrganisatie = value;
    }

}
