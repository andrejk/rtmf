package nl.rotterdam.shared.dms;

import java.text.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import nl.rotterdam.shared.docloods.DocLoodsApplications;
import nl.rotterdam.shared.docloods.Fieldnames;
import nl.rotterdam.util.CommonUtils;

public class DocumentRegistratie {

/*
    public final static String[] FIELDS = {"R_TOEGANG_ID", 
        "PD_FILE_NAME", 
        "PD_FILEPT_NO",
        "X4298", 
        "R_ZAAKNRDIENST",
        "R_ZNR", 
        "TYPE_ID",
        "DOCNAME", 
        "USER_ID", 
        "R_STATUS_ID",
        "R_RICHTING_ID", 
        "R_BEHANDELAAR",
        "FORM_NAME", 
        "TYPIST_ID", 
        "R_EINDDATUM",
        "APP_ID", 
        "R_ONDERWERP",
        "R_CASEMGR_ID", 
        "R_TOELICHTING",
        "R_DATUMDOCUMENT",
        "CREATION_DATE"
        };
    */
    public final static String[] FIELDS = {
    "R_ZNR",
    "R_ZAAKNRDIENST",
    "X4298",
    "TYPIST_ID",
    "FORM_NAME",
    "DOCNAME",
    "TYPE_ID",
    "APP_ID",
    "USER_ID",
    "R_RICHTING_ID",
    "R_STATUS_ID",
    "R_TOEGANG_ID",
    "R_DATUMDOCUMENT",
    "R_BEHANDELAAR",
    "R_TOELICHTING",
    "R_BEHANDOND",
    "R_KENMERK"
    };
    
    public final static String[] SEARCH_FIELDS = { 
        "R_ZAAKNRDIENST", 
        "TYPE_ID", 
        "DOCNAME", 
        "R_RICHTING_ID", 
        "R_EINDDATUM",
        "APP_ID",
        "DOCNUM", 
        "X2726",
        "R_DIENST_ID"}; //"VERSION"
    public final static String FORM_NAME = "R_STUK_DOS";

    private HashMap<String,String> fields = new HashMap<String,String>();
    private int docnum;
    private int version;
    private boolean selected;
    private boolean searchResult = false;


    public DocumentRegistratie() {
        setFormName( FORM_NAME);
    }
    
    /*
    public DocumentRegistratie(String dienst, String zaaknummerDienst, int zaakregistratieNummer, String typeId, String docname, String date,String userId, String richtingId, String statusId, String[] behandelaar, String appId) {
        /*
        this();
        setDienst(dienst);
        setZaaknummerDienst(zaaknummerDienst);
        setZaakregistratieNummer(zaakregistratieNummer);
        setTypeId(typeId);
        setDocname(docname);
        setUserId(userId);
        setRichtingId(richtingId);
        setStatusId(statusId);
        setBehandelaar(behandelaar);
        setTypistId(userId);
        setAppId(appId);
        //

        fields.put("R_ZNR",""+zaakregistratieNummer);
        fields.put("R_ZAAKNRDIENST",zaaknummerDienst);
        fields.put("X4298","DSV");
        fields.put("TYPIST_ID",userId);
        fields.put("FORM_NAME","R_STUK_DOS");
        fields.put("DOCNAME",docname);
        fields.put("TYPE_ID","CORRESPONDENTIE");
        fields.put("APP_ID",DocLoodsApplications.getInstance().getApplicationByFile(docname));
        fields.put("USER_ID",userId);
        fields.put("R_RICHTING_ID","Binnenkomend");
        fields.put("R_STATUS_ID","Definitief");
        fields.put("R_TOEGANG_ID","Organisatie");   
        fields.put("R_DATUMDOCUMENT",date);
        if( behandelaar != null) {
            fields.put("R_BEHANDELAAR", CommonUtils.join(behandelaar, ";"));
        } else {
            fields.put("R_BEHANDELAAR", "GEEN");
        }
        fields.put("R_TOELICHTING","-toelichting-");
    }
    */
    
    /**
     * return a copy of the current instance
     * @return
     */
    public DocumentRegistratie clone(){
        DocumentRegistratie clone = new DocumentRegistratie();
        clone.fields.putAll(this.fields);
        //clone.version = this.version;
        return clone;
    }

    public void setDienst(String dienst) {
        fields.put(Fieldnames.X4298, dienst);
    }

    public String getDienst() {
        return fields.get(Fieldnames.X4298);
    }
    
    public void setKenmerk(String kenmerk) {
        fields.put(Fieldnames.R_KENMERK, kenmerk);
    }
    
    public String getKenmerk() {
        return fields.get(Fieldnames.R_KENMERK);
    }

    public void setZaaknummerDienst(String zaaknummerDienst) {
       fields.put(Fieldnames.R_ZAAKNRDIENST, zaaknummerDienst);
    }

    public String getZaaknummerDienst() {
        return fields.get(Fieldnames.R_ZAAKNRDIENST);
    }

    public void setZaakregistratieNummer(int zaakregistratieNummer) {
        fields.put(Fieldnames.R_ZNR, String.valueOf(zaakregistratieNummer));
    }

    public int getZaakregistratieNummer() {
        if (fields.get(Fieldnames.R_ZNR) != null)  {
            return Integer.parseInt(fields.get(Fieldnames.R_ZNR));        
        }
        return -1;
    }

    public void setTypeId(String typeId) {
        fields.put(Fieldnames.TYPE_ID, typeId);
    }

    public String getTypeId() {
        return fields.get(Fieldnames.TYPE_ID);
    }

    public void setUserId(String userId) {
        fields.put(Fieldnames.USER_ID, String.valueOf(userId));
    }

    public String getUserId() {
        return fields.get(Fieldnames.USER_ID);
    }

    public void setRichtingId(String richtingId) {
        fields.put(Fieldnames.R_RICHTING_ID, richtingId);
    }

    public String getRichtingId() {
        return fields.get(Fieldnames.R_RICHTING_ID);
    }

    public void setStatusId(String statusId) {
        fields.put(Fieldnames.R_STATUS_ID, statusId);
    }

    public String getStatusId() {
        return fields.get(Fieldnames.R_STATUS_ID);
    }

    public void setBehandelaar(String[] behandelaar) {
        if( behandelaar == null) {
            fields.put(Fieldnames.R_BEHANDELAAR, "GEEN");
        } else {
            fields.put(Fieldnames.R_BEHANDELAAR, CommonUtils.join(behandelaar, ";"));
        }
    }
    
    public void setBetrokkenOnderdelen(String[] onderdelen) {
        if (onderdelen == null) {
            fields.put(Fieldnames.R_BEHANDOND,"NVT");
        }
        else {
            fields.put(Fieldnames.R_BEHANDOND, CommonUtils.join(onderdelen, ";"));
        }
    }

    public String[] getBehandelaar() {
        String behandelaar = fields.get(Fieldnames.R_BEHANDELAAR);
        if( behandelaar == null) return null;
        if (behandelaar.length() == 0) return null;
        if (behandelaar.equals("GEEN")) return null;
        return behandelaar.split(";");
    }

    public void setTypistId(String typistId) {
        fields.put(Fieldnames.TYPIST_ID, typistId);
    }

    public String getTypistId() {
        return fields.get(Fieldnames.TYPIST_ID);
    }

    public void setFormName(String formName) {
        fields.put(Fieldnames.FORM_NAME, formName);
    }

    public String getFormName() {
        return fields.get(Fieldnames.FORM_NAME);
    }

    public void setAppId(String appId) {
        fields.put(Fieldnames.APP_ID, appId);
    }

    public String getAppId() {
        return fields.get(Fieldnames.APP_ID);
    }

    public void setToegangId(String appId) {
        fields.put(Fieldnames.R_TOEGANG_ID, appId);
    }

    public String getToegangId() {
        return fields.get(Fieldnames.R_TOEGANG_ID);
    }
    
    public void setDocumentdatum(Date documentdatum) {
        fields.put(Fieldnames.R_DATUMDOCUMENT, DateUtils.fromDate(documentdatum, searchResult));
    }
    
    public Date getDocumentdatum() throws ParseException {
        return DateUtils.toDate(fields.get(Fieldnames.R_DATUMDOCUMENT), searchResult);
    }

    public void setDocname(String docname) {
        fields.put(Fieldnames.DOCNAME, docname);
    }

    public String getDocname() {
        return fields.get(Fieldnames.DOCNAME);
    }
    
    public void setToelichting(String toelichting) {
        fields.put(Fieldnames.R_TOELICHTING,  toelichting);
    }
    
    public String getToelichting() {
        return fields.get(Fieldnames.R_TOELICHTING);
    }
    
    void set(String field, String value) {
        if( field.equals(Fieldnames.DOCNUM) ) {
            docnum = Integer.parseInt(value);
        } else if( field.equals(Fieldnames.VERSION) ) {
            version = Integer.parseInt(value);
        } else
            fields.put(field, value);
    }

    String get(String field) {
        return fields.get(field);
    }

    public void setDocnum(int docnum) {
        this.docnum = docnum;
    }

    public int getDocnum() {
        return docnum;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getVersion() {
        return version;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSearchResult(boolean searchResult) {
        this.searchResult = searchResult;
    }

    public boolean isSearchResult() {
        return searchResult;
    }
}
