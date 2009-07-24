
package com.ovsoftware.ictu.osb.gateway.tmfcore.wus.wuslite;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ophalenMededelingRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ophalenMededelingRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="nieuweStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="vanDatum" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="totDatum" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ophalenMededelingRequestType", propOrder = {
    "nieuweStatus",
    "vanDatum",
    "totDatum"
})
public class OphalenMededelingRequestType {

    protected String nieuweStatus;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar vanDatum;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar totDatum;

    /**
     * Gets the value of the nieuweStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNieuweStatus() {
        return nieuweStatus;
    }

    /**
     * Sets the value of the nieuweStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNieuweStatus(String value) {
        this.nieuweStatus = value;
    }

    /**
     * Gets the value of the vanDatum property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getVanDatum() {
        return vanDatum;
    }

    /**
     * Sets the value of the vanDatum property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setVanDatum(XMLGregorianCalendar value) {
        this.vanDatum = value;
    }

    /**
     * Gets the value of the totDatum property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTotDatum() {
        return totDatum;
    }

    /**
     * Sets the value of the totDatum property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTotDatum(XMLGregorianCalendar value) {
        this.totDatum = value;
    }

}
