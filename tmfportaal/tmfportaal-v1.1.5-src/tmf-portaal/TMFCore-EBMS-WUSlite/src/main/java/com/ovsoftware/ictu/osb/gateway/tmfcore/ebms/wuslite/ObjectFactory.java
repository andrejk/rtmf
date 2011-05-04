
package com.ovsoftware.ictu.osb.gateway.tmfcore.ebms.wuslite;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.ovsoftware.ictu.osb.gateway.tmfcore.ebms.wuslite package. 
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

    private final static QName _ForwardTerugmelding_QNAME = new QName("http://wus.tmf.gbo.overheid.nl/wsdl/aanmeldService-V1.1.xsd", "forwardTerugmelding");
    private final static QName _Response_QNAME = new QName("http://tmfportal.ovsoftware.com/services/defaultreply.xsd", "response");
    private final static QName _Terugmelding_QNAME = new QName("http://wus.tmf.gbo.overheid.nl/wsdl/aanmeldService-V1.1.xsd", "terugmelding");
    private final static QName _FoutMelding_QNAME = new QName("http://wus.tmf.gbo.overheid.nl/wsdl/aanmeldService-V1.1.xsd", "foutMelding");
    private final static QName _StatusFout_QNAME = new QName("http://wus.tmf.gbo.overheid.nl/wsdl/aanmeldService-V1.1.xsd", "statusFout");
    private final static QName _Intrekking_QNAME = new QName("http://wus.tmf.gbo.overheid.nl/wsdl/aanmeldService-V1.1.xsd", "intrekking");
    private final static QName _Fault_QNAME = new QName("http://tmfportal.ovsoftware.com/services", "fault");
    private final static QName _OntvangstBevestiging_QNAME = new QName("http://wus.tmf.gbo.overheid.nl/wsdl/aanmeldService-V1.1.xsd", "ontvangstBevestiging");
    private final static QName _ForwardIntrekking_QNAME = new QName("http://wus.tmf.gbo.overheid.nl/wsdl/aanmeldService-V1.1.xsd", "forwardIntrekking");
    private final static QName _StatusBevestiging_QNAME = new QName("http://wus.tmf.gbo.overheid.nl/wsdl/aanmeldService-V1.1.xsd", "statusBevestiging");
    private final static QName _StatusMededeling_QNAME = new QName("http://wus.tmf.gbo.overheid.nl/wsdl/aanmeldService-V1.1.xsd", "statusMededeling");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.ovsoftware.ictu.osb.gateway.tmfcore.ebms.wuslite
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ObjectAttribuutType }
     * 
     */
    public ObjectAttribuutType createObjectAttribuutType() {
        return new ObjectAttribuutType();
    }

    /**
     * Create an instance of {@link TmfMetaDataType }
     * 
     */
    public TmfMetaDataType createTmfMetaDataType() {
        return new TmfMetaDataType();
    }

    /**
     * Create an instance of {@link TmfRHResponseType }
     * 
     */
    public TmfRHResponseType createTmfRHResponseType() {
        return new TmfRHResponseType();
    }

    /**
     * Create an instance of {@link IntrekkingRequestType }
     * 
     */
    public IntrekkingRequestType createIntrekkingRequestType() {
        return new IntrekkingRequestType();
    }

    /**
     * Create an instance of {@link TerugmeldingForwardType }
     * 
     */
    public TerugmeldingForwardType createTerugmeldingForwardType() {
        return new TerugmeldingForwardType();
    }

    /**
     * Create an instance of {@link Responsetype }
     * 
     */
    public Responsetype createResponsetype() {
        return new Responsetype();
    }

    /**
     * Create an instance of {@link MededelingRequestType }
     * 
     */
    public MededelingRequestType createMededelingRequestType() {
        return new MededelingRequestType();
    }

    /**
     * Create an instance of {@link AttachmentType }
     * 
     */
    public AttachmentType createAttachmentType() {
        return new AttachmentType();
    }

    /**
     * Create an instance of {@link IdentificatieType }
     * 
     */
    public IdentificatieType createIdentificatieType() {
        return new IdentificatieType();
    }

    /**
     * Create an instance of {@link TmfResponseType }
     * 
     */
    public TmfResponseType createTmfResponseType() {
        return new TmfResponseType();
    }

    /**
     * Create an instance of {@link ContactType }
     * 
     */
    public ContactType createContactType() {
        return new ContactType();
    }

    /**
     * Create an instance of {@link TerugmeldingRequestType }
     * 
     */
    public TerugmeldingRequestType createTerugmeldingRequestType() {
        return new TerugmeldingRequestType();
    }

    /**
     * Create an instance of {@link IntrekkingForwardType }
     * 
     */
    public IntrekkingForwardType createIntrekkingForwardType() {
        return new IntrekkingForwardType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TerugmeldingForwardType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://wus.tmf.gbo.overheid.nl/wsdl/aanmeldService-V1.1.xsd", name = "forwardTerugmelding")
    public JAXBElement<TerugmeldingForwardType> createForwardTerugmelding(TerugmeldingForwardType value) {
        return new JAXBElement<TerugmeldingForwardType>(_ForwardTerugmelding_QNAME, TerugmeldingForwardType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Responsetype }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tmfportal.ovsoftware.com/services/defaultreply.xsd", name = "response")
    public JAXBElement<Responsetype> createResponse(Responsetype value) {
        return new JAXBElement<Responsetype>(_Response_QNAME, Responsetype.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TerugmeldingRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://wus.tmf.gbo.overheid.nl/wsdl/aanmeldService-V1.1.xsd", name = "terugmelding")
    public JAXBElement<TerugmeldingRequestType> createTerugmelding(TerugmeldingRequestType value) {
        return new JAXBElement<TerugmeldingRequestType>(_Terugmelding_QNAME, TerugmeldingRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TmfResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://wus.tmf.gbo.overheid.nl/wsdl/aanmeldService-V1.1.xsd", name = "foutMelding")
    public JAXBElement<TmfResponseType> createFoutMelding(TmfResponseType value) {
        return new JAXBElement<TmfResponseType>(_FoutMelding_QNAME, TmfResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TmfRHResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://wus.tmf.gbo.overheid.nl/wsdl/aanmeldService-V1.1.xsd", name = "statusFout")
    public JAXBElement<TmfRHResponseType> createStatusFout(TmfRHResponseType value) {
        return new JAXBElement<TmfRHResponseType>(_StatusFout_QNAME, TmfRHResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IntrekkingRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://wus.tmf.gbo.overheid.nl/wsdl/aanmeldService-V1.1.xsd", name = "intrekking")
    public JAXBElement<IntrekkingRequestType> createIntrekking(IntrekkingRequestType value) {
        return new JAXBElement<IntrekkingRequestType>(_Intrekking_QNAME, IntrekkingRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tmfportal.ovsoftware.com/services", name = "fault")
    public JAXBElement<String> createFault(String value) {
        return new JAXBElement<String>(_Fault_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TmfResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://wus.tmf.gbo.overheid.nl/wsdl/aanmeldService-V1.1.xsd", name = "ontvangstBevestiging")
    public JAXBElement<TmfResponseType> createOntvangstBevestiging(TmfResponseType value) {
        return new JAXBElement<TmfResponseType>(_OntvangstBevestiging_QNAME, TmfResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IntrekkingForwardType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://wus.tmf.gbo.overheid.nl/wsdl/aanmeldService-V1.1.xsd", name = "forwardIntrekking")
    public JAXBElement<IntrekkingForwardType> createForwardIntrekking(IntrekkingForwardType value) {
        return new JAXBElement<IntrekkingForwardType>(_ForwardIntrekking_QNAME, IntrekkingForwardType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TmfRHResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://wus.tmf.gbo.overheid.nl/wsdl/aanmeldService-V1.1.xsd", name = "statusBevestiging")
    public JAXBElement<TmfRHResponseType> createStatusBevestiging(TmfRHResponseType value) {
        return new JAXBElement<TmfRHResponseType>(_StatusBevestiging_QNAME, TmfRHResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MededelingRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://wus.tmf.gbo.overheid.nl/wsdl/aanmeldService-V1.1.xsd", name = "statusMededeling")
    public JAXBElement<MededelingRequestType> createStatusMededeling(MededelingRequestType value) {
        return new JAXBElement<MededelingRequestType>(_StatusMededeling_QNAME, MededelingRequestType.class, null, value);
    }

}
