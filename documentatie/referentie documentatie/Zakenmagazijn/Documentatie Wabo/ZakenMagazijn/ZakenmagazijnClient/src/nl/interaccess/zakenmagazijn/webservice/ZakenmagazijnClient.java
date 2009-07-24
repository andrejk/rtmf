package nl.interaccess.zakenmagazijn.webservice;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;

import java.io.File;

import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.bind.JAXBElement;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import nl.interaccess.arch.webservice.WebserviceClient;
import nl.interaccess.util.WsFault;
import nl.interaccess.zakenmagazijn.Zakenmagazijn;
import nl.interaccess.zakenmagazijn.ZakenmagazijnException;
import nl.interaccess.zakenmagazijn.model.Actor;
import nl.interaccess.zakenmagazijn.model.ActorQuery;
import nl.interaccess.zakenmagazijn.model.ActorQueryResult;
import nl.interaccess.zakenmagazijn.model.CodeQueryType;
import nl.interaccess.zakenmagazijn.model.DocumentMetZaak;
import nl.interaccess.zakenmagazijn.model.DocumentZonderZaak;
import nl.interaccess.zakenmagazijn.model.FormulierType;
import nl.interaccess.zakenmagazijn.model.HoofdzaakRequest;
import nl.interaccess.zakenmagazijn.model.Medewerker;
import nl.interaccess.zakenmagazijn.model.ObjectFactory;
import nl.interaccess.zakenmagazijn.model.RelatieZaken;
import nl.interaccess.zakenmagazijn.model.Retour;
import nl.interaccess.zakenmagazijn.model.Stap;
import nl.interaccess.zakenmagazijn.model.StapUpdate;
import nl.interaccess.zakenmagazijn.model.Status;
import nl.interaccess.zakenmagazijn.model.Subject;
import nl.interaccess.zakenmagazijn.model.SubjectNatuurlijk;
import nl.interaccess.zakenmagazijn.model.Verzoek;
import nl.interaccess.zakenmagazijn.model.VerzoekQuery;
import nl.interaccess.zakenmagazijn.model.VerzoekQueryResponse;
import nl.interaccess.zakenmagazijn.model.VerzoekUpdate;
import nl.interaccess.zakenmagazijn.model.VerzoekidentificatieQueryType;
import nl.interaccess.zakenmagazijn.model.Zaak;
import nl.interaccess.zakenmagazijn.model.ZaakDetail;
import nl.interaccess.zakenmagazijn.model.ZaakDetailRequest;
import nl.interaccess.zakenmagazijn.model.ZaakQuery;
import nl.interaccess.zakenmagazijn.model.ZaakQueryResponse;
import nl.interaccess.zakenmagazijn.model.ZaakUpdate;
import nl.interaccess.zakenmagazijn.model.ZakenmagazijnFault;

import nl.rotterdam.util.XmlDataType;

import org.w3c.dom.Document;


public class ZakenmagazijnClient implements Zakenmagazijn {

    private WebserviceClient zakenmagazijnClient;

    private ObjectFactory zakenmagazijnObjectFactory;

    public ZakenmagazijnClient() {
        try {
            zakenmagazijnClient = 
                    new WebserviceClient("nl.interaccess.zakenmagazijn.model", 
                                         null);
        } catch (Throwable t) {
            throw new ZakenmagazijnException(t);
        }
        zakenmagazijnObjectFactory = new ObjectFactory();
    }

    /**
     * Sets the endpoint context of the web service. The URL for the endpoint gets ZakenmagagerManager added to it.
     * @deprecated Use setGateway instead
     * @param endpointUrl
     */
    public void setEndpoint(String endpointUrl) {
        zakenmagazijnClient.setUrl(endpointUrl + "/ZakenmagazijnManager");
    }

    /**
     * Sets the endpoint of the web service.
     * @param endpointUrl full endpoint url
     */
    public void setGateway(String endpointUrl) {
        zakenmagazijnClient.setUrl(endpointUrl);
    }

    private Object invoke(WebserviceClient webserviceClient, Object object) {
        return invoke(webserviceClient, object, null);
    }
    
    private Object invoke(WebserviceClient webserviceClient, Object object, String action) {
        List l;

        try {
            l = webserviceClient.call(object, action);
        } catch (WsFault t) {
            if( t.getFaultObject() instanceof ZakenmagazijnFault) {
                throw new ZakenmagazijnException(((ZakenmagazijnFault)t.getFaultObject()).getZakenmagazijnMessage());
            }
            throw new ZakenmagazijnException(t);
        } catch (Throwable t) {
            throw new ZakenmagazijnException(t);
        }

        object = null;
        if (l != null && l.size() > 0) {
            object = l.get(0);
        }

        if (object instanceof JAXBElement) {
            object = ((JAXBElement)object).getValue();
        }

        return object;
    }

    public Retour verzoekCreatie(Verzoek verzoek) {
        return (Retour)invoke(zakenmagazijnClient, 
                              zakenmagazijnObjectFactory.createVerzoek(verzoek), "\"verzoekcreatie\"");
    }

    public VerzoekQueryResponse verzoekQuery(VerzoekQuery verzoekQuery) {
        return (VerzoekQueryResponse)invoke(zakenmagazijnClient, 
                                            zakenmagazijnObjectFactory.createVerzoekQuery(verzoekQuery), "\"verzoekquery\"");
    }

    public Retour verzoekUpdate(VerzoekUpdate verzoek) {
        return (Retour)invoke(zakenmagazijnClient, 
                              zakenmagazijnObjectFactory.createVerzoekUpdate(verzoek), "\"verzoekupdate\"");
    }

    public Retour zaakUpdate(ZaakUpdate zaak) {
        return (Retour)invoke(zakenmagazijnClient, 
                              zakenmagazijnObjectFactory.createZaakUpdate(zaak), "\"zaakupdate\"");
    }

    public ZaakQueryResponse zaakQuery(ZaakQuery zaakQuery) {
        Object response = 
            invoke(zakenmagazijnClient, zakenmagazijnObjectFactory.createZaakQuery(zaakQuery), "\"zaakquery\"");

        if (response instanceof Retour)
            throw new RuntimeException(((Retour)response).getOperatiefout());

        return (ZaakQueryResponse)response;
    }

    public ZaakDetail zaakQueryDetail(ZaakDetailRequest zaak) {
        return (ZaakDetail)invoke(zakenmagazijnClient, zaak, "\"zaakdetail\"");
    }

    public Retour documentMetZaak(DocumentMetZaak documentMetZaak) {
        return (Retour)invoke(zakenmagazijnClient, 
                              zakenmagazijnObjectFactory.createDocumentMetZaak(documentMetZaak), "\"document_met_zaakcreatie\"");
    }

    public Retour documentZonderZaak(DocumentZonderZaak documentZonderZaak) {
        return (Retour)invoke(zakenmagazijnClient, 
                              zakenmagazijnObjectFactory.createDocumentZonderZaak(documentZonderZaak), "\"documentcreatie\"");
    }

    public Retour stapCreatie(Stap stap) {
        return (Retour)invoke(zakenmagazijnClient, 
                              zakenmagazijnObjectFactory.createStap(stap), "\"stapcreatie\"");
    }

    public Retour statusCreatie(Status status) {
        return (Retour)invoke(zakenmagazijnClient, 
                              zakenmagazijnObjectFactory.createStatus(status), "\"statuscreatie\"");
    }

    public Retour zaakCreatie(Zaak zaak) {
        return (Retour)invoke(zakenmagazijnClient, 
                              zakenmagazijnObjectFactory.createZaak(zaak), "\"zaakcreatie\"");
    }

    public Retour relatieUpdate(RelatieZaken relatieZaken) {
        return (Retour)invoke(zakenmagazijnClient, 
                              zakenmagazijnObjectFactory.createRelatieZaken(relatieZaken), "\"relatieupdate\"");
    }

    public ZaakDetail hoofdzaak(HoofdzaakRequest request) {
        return (ZaakDetail)invoke(zakenmagazijnClient, request, "\"hoofdzaak\"");
    }

    public Retour stapUpdate(StapUpdate stap) {
        return (Retour)invoke(zakenmagazijnClient, 
                              zakenmagazijnObjectFactory.createStapUpdate(stap), "\"stapupdate\"");
    }

    public List<Actor> actorQuery(ActorQuery actorQuery) {
        ActorQueryResult result = (ActorQueryResult)invoke(zakenmagazijnClient, actorQuery, "\"actorquery\"");
        return result.getActor();
    }
    
    public static void main(String[] args) throws Exception {
        ZakenmagazijnClient client = new ZakenmagazijnClient();
        client.setGateway("http://twd668:7780/event/ESBBriksMidOffice/Zakenmagazijn");
        
        // <tns:Verzoek xmlns:tns="http://www.interaccess.nl/webplus/statuswfm_v2">
        // <tns:Subject>
        //   <tns:Subject_natuurlijk>
        //   <tns:Bsn>950029105</tns:Bsn>
        //   </tns:Subject_natuurlijk>
        // </tns:Subject>
        // <tns:Aanvrager>V O O R  Achternaam</tns:Aanvrager>
        // <tns:DatumIndiening>--T00:00:00</tns:DatumIndiening>
        // <tns:Verzoekidentificatie>115</tns:Verzoekidentificatie>
        // <tns:VerzoekOmschrijving/>
        // <tns:VerzoekToelichting>Wel,Ik wil graag het hele Kralingse bos kappen tbv een chemische fabriek voor de productie van dioxine. Gaat dit lukken?</tns:VerzoekToelichting>
        // </tns:Verzoek>
 
        Verzoek verzoek = new Verzoek();
        verzoek.setSubject(new Subject());
        verzoek.getSubject().setSubjectNatuurlijk(new SubjectNatuurlijk());
        verzoek.getSubject().getSubjectNatuurlijk().setBsn("950029105");
        verzoek.setAanvrager("V O O R  Achternaam");
        verzoek.setVerzoekidentificatie("115");
        XmlDataType xmlDataType = new XmlDataType();
        verzoek.setDatumIndiening( xmlDataType.getCalendar());
        File file = new File("C:/work/wabo/etc/aanvraaggoed.xml");

        FormulierType formulierType = new ObjectFactory().createFormulierType();
DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
DocumentBuilder db =dbf.newDocumentBuilder();
        Document d = db.parse(file);
        formulierType.setAny(d);
        verzoek.setFormulier(formulierType);
        
        verzoek.setVerzoekOmschrijving("qergwqer");
        verzoek.setVerzoekToelichting("Wel,Ik wil graag het hele Kralingse bos kappen tbv een chemische fabriek voor de productie van dioxine. Gaat dit lukken?");
        client.verzoekCreatie(verzoek);
        
/*        ZaakQuery query = new ZaakQuery();
        Medewerker medewerker =   new ObjectFactory().createMedewerker();
        medewerker.setMdwId("wfaulk");
        query.setZaakVerantwoordelijkeMdw(medewerker);
        query.setOgeId("0");
        
        CodeQueryType codeQueryTypeOmv = new ObjectFactory().createCodeQueryType();
        codeQueryTypeOmv.setExact(true);
        codeQueryTypeOmv.setValue("OMV");
        CodeQueryType codeQueryTypeOmvAct = new ObjectFactory().createCodeQueryType();
        codeQueryTypeOmvAct.setExact(true);
        codeQueryTypeOmvAct.setValue("OMVACT");
        query.getZaaktypecode().add(codeQueryTypeOmv);
        query.getZaaktypecode().add(codeQueryTypeOmvAct);
        ZaakQueryResponse zaakQueryResponse =   client.zaakQuery(query);
        List<Zaak> zakenList =  zaakQueryResponse.getZaak();        
        for (Zaak zaak zakenList){
            System.out.println("zaakidentificatie" + );
        }
 */       
    }
}
