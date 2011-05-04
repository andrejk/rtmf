
package com.ovsoftware.ictu.osb.gateway.tmfcore.wus.wuslite;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for objectAttribuutType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="objectAttribuutType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idAttribuut" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="betwijfeldeWaarde" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="voorstel" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "objectAttribuutType", propOrder = {
    "idAttribuut",
    "betwijfeldeWaarde",
    "voorstel"
})
public class ObjectAttribuutType {

    @XmlElement(required = true)
    protected String idAttribuut;
    @XmlElement(required = true)
    protected String betwijfeldeWaarde;
    @XmlElement(required = true)
    protected String voorstel;

    /**
     * Gets the value of the idAttribuut property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdAttribuut() {
        return idAttribuut;
    }

    /**
     * Sets the value of the idAttribuut property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdAttribuut(String value) {
        this.idAttribuut = value;
    }

    /**
     * Gets the value of the betwijfeldeWaarde property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBetwijfeldeWaarde() {
        return betwijfeldeWaarde;
    }

    /**
     * Sets the value of the betwijfeldeWaarde property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBetwijfeldeWaarde(String value) {
        this.betwijfeldeWaarde = value;
    }

    /**
     * Gets the value of the voorstel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVoorstel() {
        return voorstel;
    }

    /**
     * Sets the value of the voorstel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVoorstel(String value) {
        this.voorstel = value;
    }

}
