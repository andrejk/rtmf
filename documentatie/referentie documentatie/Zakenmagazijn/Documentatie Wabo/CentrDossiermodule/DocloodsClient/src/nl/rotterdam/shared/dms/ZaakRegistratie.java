package nl.rotterdam.shared.dms;

import java.util.HashMap;
import java.util.ResourceBundle;

import nl.rotterdam.shared.docloods.Fieldnames;


public class ZaakRegistratie {
    private static ResourceBundle bundle = ResourceBundle.getBundle("dms_properties");

    //public final static String[] FIELDS = {"X2623", "R_ZAAKNRDIENST", "APP_ID", "PD_FILEPT_NO", "DOCNAME", "USER_ID", "R_STATUS_ID", "AUTHOR_ID", "TYPIST_ID", "FORM_NAME", "TYPE_ID", "STORAGE"};
    public final static String[] FIELDS = {
    "PD_FILEPT_NO",
    "R_ZAAKNRDIENST",
    "X2623",
    "VE",
    "X5485",
    "APP_ID",
    "DOCNAME",
    "USER_ID",
    "R_STATUS_ID",
    "R_TOEGANG_ID",
    "TYPIST_ID",
    "R_BETROKONDERD",
    "AUTHOR_ID",
    "FORM_NAME",
    "TYPE_ID",
    "STORAGE"
    };
    public final static String FORM_NAME = "R_ZAAK_DOS";
    
    private HashMap<String,String> fields = new HashMap<String,String>();


    public ZaakRegistratie(String dienst, String zaaknrDienst, String docname, String userId, String statusId, String dossiermap) {

        setAppId("PAPER");
        setAuthorId("GEEN");
        setTypeId("ZAAK");
        setStorage("P");
        setFormName(FORM_NAME);

        setDienst(dienst); 
        if (statusId != null) setStatusId(statusId);
        setFileptNo(dossiermap);

        setTypistId(userId);
        if (zaaknrDienst != null) setZaaknrDienst(zaaknrDienst);
        if (docname != null) setDocname(docname);
        if (userId != null) setUserId(userId);
        
        // from dms_properties.properties:
        set (Fieldnames.VE, bundle.getString(Fieldnames.VE));
        set (Fieldnames.X5485, bundle.getString(Fieldnames.X5485));
        set (Fieldnames.R_TOEGANG_ID, bundle.getString(Fieldnames.R_TOEGANG_ID));
        set (Fieldnames.R_BETROKONDERD, bundle.getString(Fieldnames.R_BETROKONDERD));
        
//        set("VE","DSV-OMV");
//        set("X5485","DSV");
//        set("R_TOEGANG_ID","ORGANISATIE");
//        set("R_BETROKOND","DGCH-OMV;DGCR-OMV;DGDH-OMV;DGFE-OMV;DGHH-OMV;DGHS-OMV;DGHV-OMV;DGIJ-OMV;DGKC-OMV;DGNO-OMV;DGOV-OMV;DGPA-OMV;DGPE-OMV;GW-OMV;OBR-OMV;PZR-OMV;DSV");
    }

    public void setDienst(String dienst) {
        fields.put("X2623", dienst);
    }

    public String getDienst() {
        return fields.get("X2623");
    }

    public void setZaaknrDienst(String zaaknrDienst) {
        fields.put(Fieldnames.R_ZAAKNRDIENST, zaaknrDienst);
    }

    public String getZaaknrDienst() {
        return fields.get(Fieldnames.R_ZAAKNRDIENST);
    }

    public void setAppId(String appId) {
        fields.put(Fieldnames.APP_ID, appId);
    }

    public String getAppId() {
        return fields.get(Fieldnames.APP_ID);
    }

    public void setFileptNo(String fileptNo) {
        fields.put(Fieldnames.PD_FILEPT_NO, fileptNo);
    }

    public String getFileptNo() {
        return fields.get(Fieldnames.PD_FILEPT_NO);
    }

    public void setDocname(String docname) {
        fields.put(Fieldnames.DOCNAME, docname);
    }

    public String getDocname() {
        return fields.get(Fieldnames.DOCNAME);
    }

    public void setUserId(String userId) {
        fields.put(Fieldnames.USER_ID, userId);
    }

    public String getUserId() {
        return fields.get(Fieldnames.USER_ID);
    }

    public void setStatusId(String statusId) {
        fields.put(Fieldnames.R_STATUS_ID, statusId);
    }

    public String getStatusId() {
        return fields.get(Fieldnames.R_STATUS_ID);
    }

    public void setAuthorId(String authorId) {
        fields.put(Fieldnames.AUTHOR_ID, authorId);
    }

    public String getAuthorId() {
        return fields.get(Fieldnames.AUTHOR_ID);
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

    public void setTypeId(String typeId) {
        fields.put(Fieldnames.TYPE_ID, typeId);
    }

    public String getTypeId() {
        return fields.get(Fieldnames.TYPE_ID);
    }

    public void setStorage(String storage) {
        fields.put(Fieldnames.STORAGE, storage);
    }

    public String getStorage() {
        return fields.get(Fieldnames.STORAGE);
    }
    
    void set(String field, String value) {
        fields.put(field, value);
    }

    String get(String field) {
        return fields.get(field);
    }


}
