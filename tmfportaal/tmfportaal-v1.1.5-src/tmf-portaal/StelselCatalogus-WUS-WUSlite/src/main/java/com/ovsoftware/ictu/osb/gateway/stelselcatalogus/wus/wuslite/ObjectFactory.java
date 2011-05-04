
package com.ovsoftware.ictu.osb.gateway.stelselcatalogus.wus.wuslite;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.ovsoftware.ictu.osb.gateway.stelselcatalogus.wus.wuslite package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetBasisregistratieListResponse_QNAME = new QName("http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd", "getBasisregistratieListResponse");
    private final static QName _GetBasisregistratieList_QNAME = new QName("http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd", "getBasisregistratieList");
    private final static QName _GobFault_QNAME = new QName("http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd", "gobFault");
    private final static QName _GetObjectInfo_QNAME = new QName("http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd", "getObjectInfo");
    private final static QName _GetObjectInfoResponse_QNAME = new QName("http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd", "getObjectInfoResponse");
    private final static QName _Bevragen_QNAME = new QName("http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd", "bevragen");
    private final static QName _GetObjectTypeList_QNAME = new QName("http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd", "getObjectTypeList");
    private final static QName _GetObjectTypeListResponse_QNAME = new QName("http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd", "getObjectTypeListResponse");
    private final static QName _BevragenResponse_QNAME = new QName("http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd", "bevragenResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.ovsoftware.ictu.osb.gateway.stelselcatalogus.wus.wuslite
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link BasisregistratieType }
     * 
     */
    public BasisregistratieType createBasisregistratieType() {
        return new BasisregistratieType();
    }

    /**
     * Create an instance of {@link BevragenResponseType }
     * 
     */
    public BevragenResponseType createBevragenResponseType() {
        return new BevragenResponseType();
    }

    /**
     * Create an instance of {@link ObjectValueType }
     * 
     */
    public ObjectValueType createObjectValueType() {
        return new ObjectValueType();
    }

    /**
     * Create an instance of {@link MeldingType }
     * 
     */
    public MeldingType createMeldingType() {
        return new MeldingType();
    }

    /**
     * Create an instance of {@link ObjectInfoRequestType }
     * 
     */
    public ObjectInfoRequestType createObjectInfoRequestType() {
        return new ObjectInfoRequestType();
    }

    /**
     * Create an instance of {@link BevragenRequestType }
     * 
     */
    public BevragenRequestType createBevragenRequestType() {
        return new BevragenRequestType();
    }

    /**
     * Create an instance of {@link ObjectInfoResponseType }
     * 
     */
    public ObjectInfoResponseType createObjectInfoResponseType() {
        return new ObjectInfoResponseType();
    }

    /**
     * Create an instance of {@link ObjectHeaderType }
     * 
     */
    public ObjectHeaderType createObjectHeaderType() {
        return new ObjectHeaderType();
    }

    /**
     * Create an instance of {@link AttribuutValueType }
     * 
     */
    public AttribuutValueType createAttribuutValueType() {
        return new AttribuutValueType();
    }

    /**
     * Create an instance of {@link BasisregistratieListResponseType }
     * 
     */
    public BasisregistratieListResponseType createBasisregistratieListResponseType() {
        return new BasisregistratieListResponseType();
    }

    /**
     * Create an instance of {@link ObjectType }
     * 
     */
    public ObjectType createObjectType() {
        return new ObjectType();
    }

    /**
     * Create an instance of {@link ObjectTypeListResponseType }
     * 
     */
    public ObjectTypeListResponseType createObjectTypeListResponseType() {
        return new ObjectTypeListResponseType();
    }

    /**
     * Create an instance of {@link ObjectTypeListRequestType }
     * 
     */
    public ObjectTypeListRequestType createObjectTypeListRequestType() {
        return new ObjectTypeListRequestType();
    }

    /**
     * Create an instance of {@link AttribuutType }
     * 
     */
    public AttribuutType createAttribuutType() {
        return new AttribuutType();
    }

    /**
     * Create an instance of {@link BasisregistratieListRequestType }
     * 
     */
    public BasisregistratieListRequestType createBasisregistratieListRequestType() {
        return new BasisregistratieListRequestType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BasisregistratieListResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd", name = "getBasisregistratieListResponse")
    public JAXBElement<BasisregistratieListResponseType> createGetBasisregistratieListResponse(BasisregistratieListResponseType value) {
        return new JAXBElement<BasisregistratieListResponseType>(_GetBasisregistratieListResponse_QNAME, BasisregistratieListResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BasisregistratieListRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd", name = "getBasisregistratieList")
    public JAXBElement<BasisregistratieListRequestType> createGetBasisregistratieList(BasisregistratieListRequestType value) {
        return new JAXBElement<BasisregistratieListRequestType>(_GetBasisregistratieList_QNAME, BasisregistratieListRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MeldingType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd", name = "gobFault")
    public JAXBElement<MeldingType> createGobFault(MeldingType value) {
        return new JAXBElement<MeldingType>(_GobFault_QNAME, MeldingType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObjectInfoRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd", name = "getObjectInfo")
    public JAXBElement<ObjectInfoRequestType> createGetObjectInfo(ObjectInfoRequestType value) {
        return new JAXBElement<ObjectInfoRequestType>(_GetObjectInfo_QNAME, ObjectInfoRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObjectInfoResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd", name = "getObjectInfoResponse")
    public JAXBElement<ObjectInfoResponseType> createGetObjectInfoResponse(ObjectInfoResponseType value) {
        return new JAXBElement<ObjectInfoResponseType>(_GetObjectInfoResponse_QNAME, ObjectInfoResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BevragenRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd", name = "bevragen")
    public JAXBElement<BevragenRequestType> createBevragen(BevragenRequestType value) {
        return new JAXBElement<BevragenRequestType>(_Bevragen_QNAME, BevragenRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObjectTypeListRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd", name = "getObjectTypeList")
    public JAXBElement<ObjectTypeListRequestType> createGetObjectTypeList(ObjectTypeListRequestType value) {
        return new JAXBElement<ObjectTypeListRequestType>(_GetObjectTypeList_QNAME, ObjectTypeListRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObjectTypeListResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd", name = "getObjectTypeListResponse")
    public JAXBElement<ObjectTypeListResponseType> createGetObjectTypeListResponse(ObjectTypeListResponseType value) {
        return new JAXBElement<ObjectTypeListResponseType>(_GetObjectTypeListResponse_QNAME, ObjectTypeListResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BevragenResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://wus.tmf.gbo.overheid.nl/wsdl/stelselBevragen-V1.1.xsd", name = "bevragenResponse")
    public JAXBElement<BevragenResponseType> createBevragenResponse(BevragenResponseType value) {
        return new JAXBElement<BevragenResponseType>(_BevragenResponse_QNAME, BevragenResponseType.class, null, value);
    }

}
