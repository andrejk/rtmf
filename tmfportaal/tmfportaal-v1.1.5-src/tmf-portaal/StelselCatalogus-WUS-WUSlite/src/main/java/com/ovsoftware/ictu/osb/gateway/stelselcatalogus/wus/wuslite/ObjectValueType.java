
package com.ovsoftware.ictu.osb.gateway.stelselcatalogus.wus.wuslite;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ObjectValueType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ObjectValueType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="objectInfo" type="{http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd}ObjectHeaderType"/>
 *         &lt;element name="attributeValues" type="{http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd}AttribuutValueType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ObjectValueType", propOrder = {
    "objectInfo",
    "attributeValues"
})
public class ObjectValueType {

    @XmlElement(required = true)
    protected ObjectHeaderType objectInfo;
    protected List<AttribuutValueType> attributeValues;

    /**
     * Gets the value of the objectInfo property.
     * 
     * @return
     *     possible object is
     *     {@link ObjectHeaderType }
     *     
     */
    public ObjectHeaderType getObjectInfo() {
        return objectInfo;
    }

    /**
     * Sets the value of the objectInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectHeaderType }
     *     
     */
    public void setObjectInfo(ObjectHeaderType value) {
        this.objectInfo = value;
    }

    /**
     * Gets the value of the attributeValues property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the attributeValues property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttributeValues().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AttribuutValueType }
     * 
     * 
     */
    public List<AttribuutValueType> getAttributeValues() {
        if (attributeValues == null) {
            attributeValues = new ArrayList<AttribuutValueType>();
        }
        return this.attributeValues;
    }

}
