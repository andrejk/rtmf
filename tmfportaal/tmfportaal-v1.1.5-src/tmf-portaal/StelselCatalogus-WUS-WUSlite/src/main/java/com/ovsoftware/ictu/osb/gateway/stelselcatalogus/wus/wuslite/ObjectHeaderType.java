
package com.ovsoftware.ictu.osb.gateway.stelselcatalogus.wus.wuslite;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ObjectHeaderType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ObjectHeaderType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="tag" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="naam" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="bevraagbaar" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="instructie" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ObjectHeaderType", propOrder = {
    "tag",
    "naam",
    "bevraagbaar",
    "instructie"
})
public class ObjectHeaderType {

    @XmlElement(required = true)
    protected String tag;
    @XmlElement(required = true)
    protected String naam;
    protected boolean bevraagbaar;
    @XmlElement(required = true)
    protected String instructie;

    /**
     * Gets the value of the tag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTag() {
        return tag;
    }

    /**
     * Sets the value of the tag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTag(String value) {
        this.tag = value;
    }

    /**
     * Gets the value of the naam property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Sets the value of the naam property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNaam(String value) {
        this.naam = value;
    }

    /**
     * Gets the value of the bevraagbaar property.
     * 
     */
    public boolean isBevraagbaar() {
        return bevraagbaar;
    }

    /**
     * Sets the value of the bevraagbaar property.
     * 
     */
    public void setBevraagbaar(boolean value) {
        this.bevraagbaar = value;
    }

    /**
     * Gets the value of the instructie property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstructie() {
        return instructie;
    }

    /**
     * Sets the value of the instructie property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstructie(String value) {
        this.instructie = value;
    }

}
