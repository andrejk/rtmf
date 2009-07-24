
package com.ovsoftware.ictu.osb.gateway.stelselcatalogus.wus.wuslite;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ObjectType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ObjectType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ObjectHeaderInfo" type="{http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd}ObjectHeaderType"/>
 *         &lt;element name="attributen" type="{http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd}AttribuutType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ObjectType", propOrder = {
    "objectHeaderInfo",
    "attributen"
})
public class ObjectType {

    @XmlElement(name = "ObjectHeaderInfo", required = true)
    protected ObjectHeaderType objectHeaderInfo;
    protected List<AttribuutType> attributen;

    /**
     * Gets the value of the objectHeaderInfo property.
     * 
     * @return
     *     possible object is
     *     {@link ObjectHeaderType }
     *     
     */
    public ObjectHeaderType getObjectHeaderInfo() {
        return objectHeaderInfo;
    }

    /**
     * Sets the value of the objectHeaderInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectHeaderType }
     *     
     */
    public void setObjectHeaderInfo(ObjectHeaderType value) {
        this.objectHeaderInfo = value;
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
     * {@link AttribuutType }
     * 
     * 
     */
    public List<AttribuutType> getAttributen() {
        if (attributen == null) {
            attributen = new ArrayList<AttribuutType>();
        }
        return this.attributen;
    }

}
