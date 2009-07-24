
package com.ovsoftware.ictu.osb.gateway.tmfcore.wus.wuslite;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for mededelingTypeList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="mededelingTypeList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="mededelingList" type="{http://wus.tmf.gbo.overheid.nl/wsdl/ophaalService-V1.1.xsd}mededelingType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "mededelingTypeList", propOrder = {
    "mededelingList"
})
public class MededelingTypeList {

    protected List<MededelingType> mededelingList;

    /**
     * Gets the value of the mededelingList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the mededelingList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMededelingList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MededelingType }
     * 
     * 
     */
    public List<MededelingType> getMededelingList() {
        if (mededelingList == null) {
            mededelingList = new ArrayList<MededelingType>();
        }
        return this.mededelingList;
    }

}
