
package com.ovsoftware.ictu.osb.gateway.tmfcore.wus.wuslite;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for terugmeldResponseTypeList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="terugmeldResponseTypeList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="terugmeldResponseList" type="{http://wus.tmf.gbo.overheid.nl/wsdl/ophaalService-V1.1.xsd}terugmeldResponseType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "terugmeldResponseTypeList", propOrder = {
    "terugmeldResponseList"
})
public class TerugmeldResponseTypeList {

    protected List<TerugmeldResponseType> terugmeldResponseList;

    /**
     * Gets the value of the terugmeldResponseList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the terugmeldResponseList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTerugmeldResponseList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TerugmeldResponseType }
     * 
     * 
     */
    public List<TerugmeldResponseType> getTerugmeldResponseList() {
        if (terugmeldResponseList == null) {
            terugmeldResponseList = new ArrayList<TerugmeldResponseType>();
        }
        return this.terugmeldResponseList;
    }

}
