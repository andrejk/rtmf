package nl.interaccess.zakenmagazijn;

import java.util.List;

import javax.xml.bind.JAXBElement;

import nl.interaccess.zakenmagazijn.model.Actor;
import nl.interaccess.zakenmagazijn.model.ActorQuery;
import nl.interaccess.zakenmagazijn.model.DocumentMetZaak;
import nl.interaccess.zakenmagazijn.model.DocumentZonderZaak;
import nl.interaccess.zakenmagazijn.model.HoofdzaakRequest;
import nl.interaccess.zakenmagazijn.model.RelatieZaken;
import nl.interaccess.zakenmagazijn.model.Retour;
import nl.interaccess.zakenmagazijn.model.Stap;
import nl.interaccess.zakenmagazijn.model.StapUpdate;
import nl.interaccess.zakenmagazijn.model.Status;
import nl.interaccess.zakenmagazijn.model.Verzoek;
import nl.interaccess.zakenmagazijn.model.VerzoekQuery;
import nl.interaccess.zakenmagazijn.model.VerzoekQueryResponse;
import nl.interaccess.zakenmagazijn.model.VerzoekUpdate;
import nl.interaccess.zakenmagazijn.model.Zaak;
import nl.interaccess.zakenmagazijn.model.ZaakDetail;
import nl.interaccess.zakenmagazijn.model.ZaakDetailRequest;
import nl.interaccess.zakenmagazijn.model.ZaakQuery;
import nl.interaccess.zakenmagazijn.model.ZaakQueryResponse;
import nl.interaccess.zakenmagazijn.model.ZaakUpdate;


public interface Zakenmagazijn {

    Retour verzoekCreatie(Verzoek verzoek) throws Exception;
    
    VerzoekQueryResponse verzoekQuery(VerzoekQuery verzoekQuery)  throws Exception;

    Retour verzoekUpdate(VerzoekUpdate verzoek)  throws Exception;

    Retour zaakUpdate(ZaakUpdate zaak)  throws Exception;
    
    ZaakQueryResponse zaakQuery(ZaakQuery zaakQuery)  throws Exception;
    
    ZaakDetail zaakQueryDetail(ZaakDetailRequest zaak) throws Exception;
    
    Retour stapCreatie(Stap stap) throws Exception;
    
    Retour stapUpdate(StapUpdate stap) throws Exception;
    
    Retour statusCreatie(Status status) throws Exception;
    
    Retour zaakCreatie(Zaak zaak) throws Exception;
    
    Retour relatieUpdate(RelatieZaken relatieZaken) throws Exception;
    
    Retour documentMetZaak(DocumentMetZaak documentMetZaak) throws Exception;

    Retour documentZonderZaak(DocumentZonderZaak documentZonderZaak) throws Exception;
    
    ZaakDetail hoofdzaak(HoofdzaakRequest request) throws Exception;
    
    List<Actor> actorQuery( ActorQuery query) throws Exception;
    
}
