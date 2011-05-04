
package com.ovsoftware.ictu.osb.gateway.tmfcore.ebms.wuslite;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for TerugmeldingRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TerugmeldingRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="meldingKenmerk" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="tijdstempelAanlevering" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="basisRegistratie" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="objectTag" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="objectIdentificatie" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="toelichting" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="attributen" type="{http://wus.tmf.gbo.overheid.nl/wsdl/aanmeldService-V1.1.xsd}ObjectAttribuutType" maxOccurs="unbounded"/>
 *         &lt;element name="contactInfo" type="{http://wus.tmf.gbo.overheid.nl/wsdl/aanmeldService-V1.1.xsd}ContactType" minOccurs="0"/>
 *         &lt;element name="attachment" type="{http://wus.tmf.gbo.overheid.nl/wsdl/aanmeldService-V1.1.xsd}AttachmentType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TerugmeldingRequestType", propOrder = {
    "meldingKenmerk",
    "tijdstempelAanlevering",
    "basisRegistratie",
    "objectTag",
    "objectIdentificatie",
    "toelichting",
    "attributen",
    "contactInfo",
    "attachment"
})
public class TerugmeldingRequestType {

    @XmlElement(required = true)
    protected String meldingKenmerk;
    @XmlElement(required = true)
    protected XMLGregorianCalendar tijdstempelAanlevering;
    @XmlElement(required = true)
    protected String basisRegistratie;
    @XmlElement(required = true)
    protected String objectTag;
    @XmlElement(required = true)
    protected String objectIdentificatie;
    @XmlElement(required = true)
    protected String toelichting;
    @XmlElement(required = true)
    protected List<ObjectAttribuutType> attributen;
    protected ContactType contactInfo;
    protected List<AttachmentType> attachment;

    /**
     * Gets the value of the meldingKenmerk property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMeldingKenmerk() {
        return meldingKenmerk;
    }

    /**
     * Sets the value of the meldingKenmerk property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMeldingKenmerk(String value) {
        this.meldingKenmerk = value;
    }

    /**
     * Gets the value of the tijdstempelAanlevering property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTijdstempelAanlevering() {
        return tijdstempelAanlevering;
    }

    /**
     * Sets the value of the tijdstempelAanlevering property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTijdstempelAanlevering(XMLGregorianCalendar value) {
        this.tijdstempelAanlevering = value;
    }

    /**
     * Gets the value of the basisRegistratie property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBasisRegistratie() {
        return basisRegistratie;
    }

    /**
     * Sets the value of the basisRegistratie property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBasisRegistratie(String value) {
        this.basisRegistratie = value;
    }

    /**
     * Gets the value of the objectTag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObjectTag() {
        return objectTag;
    }

    /**
     * Sets the value of the objectTag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObjectTag(String value) {
        this.objectTag = value;
    }

    /**
     * Gets the value of the objectIdentificatie property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObjectIdentificatie() {
        return objectIdentificatie;
    }

    /**
     * Sets the value of the objectIdentificatie property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObjectIdentificatie(String value) {
        this.objectIdentificatie = value;
    }

    /**
     * Gets the value of the toelichting property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToelichting() {
        return toelichting;
    }

    /**
     * Sets the value of the toelichting property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToelichting(String value) {
        this.toelichting = value;
    }

    /**
     * Gets the value of the attributen property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the attributen property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttributen().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ObjectAttribuutType }
     * 
     * 
     */
    public List<ObjectAttribuutType> getAttributen() {
        if (attributen == null) {
            attributen = new ArrayList<ObjectAttribuutType>();
        }
        return this.attributen;
    }

    /**
     * Gets the value of the contactInfo property.
     * 
     * @return
     *     possible object is
     *     {@link ContactType }
     *     
     */
    public ContactType getContactInfo() {
        return contactInfo;
    }

    /**
     * Sets the value of the contactInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContactType }
     *     
     */
    public void setContactInfo(ContactType value) {
        this.contactInfo = value;
    }

    /**
     * Gets the value of the attachment property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the attachment property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttachment().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AttachmentType }
     * 
     * 
     */
    public List<AttachmentType> getAttachment() {
        if (attachment == null) {
            attachment = new ArrayList<AttachmentType>();
        }
        return this.attachment;
    }

}
