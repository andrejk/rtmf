
package com.ovsoftware.ictu.osb.gateway.stelselcatalogus.wus.wuslite;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BasisregistratieListResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BasisregistratieListResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="basisregistratieList" type="{http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd}BasisregistratieType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BasisregistratieListResponseType", propOrder = {
    "basisregistratieList"
})
public class BasisregistratieListResponseType {

    protected List<BasisregistratieType> basisregistratieList;

    /**
     * Gets the value of the basisregistratieList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the basisregistratieList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBasisregistratieList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BasisregistratieType }
     * 
     * 
     */
    public List<BasisregistratieType> getBasisregistratieList() {
        if (basisregistratieList == null) {
            basisregistratieList = new ArrayList<BasisregistratieType>();
        }
        return this.basisregistratieList;
    }

}
