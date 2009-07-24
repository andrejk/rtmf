package nl.rotterdam.shared.dms;

import java.text.ParseException;

import java.util.Date;
import java.util.HashMap;

import java.util.List;

import nl.rotterdam.shared.docloods.Fieldnames;

public class DocumentSearch  {

    public static final String[] FIELDS = { "R_KENMERK", "R_TOELICHTING", "R_ZNR", "R_ZAAKNRDIENST", "R_DIENST_ID", "R_CASEMGR_ID", "DOCNUM", "TYPE_ID", "DOCNAME", "AUTHOR_ID", "CREATION_DATE", "TYPIST_ID", "LAST_EDIT_ID", "APP_ID", "R_TOEGANG", "R_BEHANDELAAR", "R_BEHANDOND", "LAST_EDIT_DATE", "STATUS", "R_RICHTING", "R_STATUS", "GROUP_ID" };

    public static final String FORM_NAME = "R_ZOEKEN_DOS";

    private HashMap<String, String> fields = new HashMap<String, String>();
    private boolean selected;

    private String statusId;
    private String richtingId;
    private String toegangId;
    private boolean deleted;
    
    private boolean displayed = false;

    public void setZaaknummerDMS(int zaaknummerDMS) {
        fields.put("R_ZNR", String.valueOf(zaaknummerDMS));
    }

    public int getZaaknummerDMS() {
        String docnr = fields.get("R_ZNR");
        if (docnr == null)
            return -1;
        return Integer.parseInt(docnr);
    }

    public void setRichting(int zaaknummerDMS) {
        fields.put("R_RICHTING", String.valueOf(zaaknummerDMS));
    }

    public int getRichting() {
        String docnr = fields.get("R_RICHTING");
        if (docnr == null)
            return -1;
        return Integer.parseInt(docnr);
    }

    public void setStatus(int zaaknummerDMS) {
        fields.put("R_STATUS", String.valueOf(zaaknummerDMS));
    }

    public int getStatus() {
        String docnr = fields.get("R_STATUS");
        if (docnr == null)
            return -1;
        return Integer.parseInt(docnr);
    }

    public void setZaaknummerZkn(String zaaknummerZkn) {
        fields.put("R_ZAAKNRDIENST", zaaknummerZkn);
    }

    public String getZaaknummerZkn() {
        return fields.get("R_ZAAKNRDIENST");
    }
    
    public void setToelichting(String toelichting) {
        fields.put("R_TOELICHTING", toelichting);
    }
    
    public String getToelichting() {
        String t = fields.get("R_TOELICHTING");
        if (t == null || t.length() == 0) {
            t = "Geen toelichting";
        }
        return t;
    }
    
    public void setKenmerk(String kenmerk) {
        fields.put(Fieldnames.R_KENMERK, kenmerk);
    }
    
    public String getKenmerk() {
        String t = fields.get(Fieldnames.R_KENMERK);
        if (t == null || t.length() == 0) {
            t = "Geen kenmerk";
        }
        return t;
    }

    public void setZaakDienst(String zaakDienst) {
        fields.put("R_DIENST_ID", zaakDienst);
    }

    public String getZaakDienst() {
        return fields.get("R_DIENST_ID");
    }

    public void setDocnr(int docnr) {
        fields.put("DOCNUM", String.valueOf(docnr));
    }

    public int getDocnr() {
        String docnr = fields.get("DOCNUM");
        if (docnr == null)
            return -1;
        return Integer.parseInt(docnr);
    }

    public void setCasemanager(String casemanager) {
        fields.put("R_CASEMGR_ID", casemanager);
    }

    public String getCasemanager() {
        return fields.get("R_CASEMGR_ID");
    }

    public void setDoctype(String doctype) {
        fields.put("TYPE_ID", doctype);
    }

    public String getDoctype() {
        return fields.get("TYPE_ID");
    }

    public void setDocnaam(String docnaam) {
        fields.put("DOCNAME", docnaam);
    }

    public String getDocnaam() {
        return fields.get("DOCNAME");
    }

    public void setDocnauteur(String docnauteur) {
        fields.put("AUTHOR_ID", docnauteur);
    }

    public String getDocnauteur() {
        return fields.get("AUTHOR_ID");
    }

    public void setDocdatumAanm(Date docdatumAanm) {
        fields.put("CREATION_DATE", DateUtils.fromDate(docdatumAanm, true));
    }

    public Date getDocdatumAanm() throws ParseException {
        return DateUtils.toDate(fields.get("CREATION_DATE"), true);
    }

    public void setDocuserAanm(String docuserAanm) {
        fields.put("TYPIST_ID", docuserAanm);
    }

    public String getDocuserAanm() {
        return fields.get("TYPIST_ID");
    }

    public void setDocdatumWijz(Date docdatumWijz) {
        fields.put("LAST_EDIT_DATE", DateUtils.fromDate(docdatumWijz, true));
    }

    public Date getDocdatumWijz() throws ParseException {
        return DateUtils.toDate(fields.get("LAST_EDIT_DATE"), true);
    }

    public void setDocuserWijz(String docuserWijz) {
        fields.put("LAST_EDIT_ID", docuserWijz);
    }

    public String getDocuserWijz() {
        return fields.get("LAST_EDIT_ID");
    }

    public void setApplicatie(String docuserWijz) {
        fields.put("APP_ID", docuserWijz);
    }

    public String getApplicatie() {
        return fields.get("APP_ID");
    }

    public void setToegang(int docuserWijz) {
        fields.put("R_TOEGANG", String.valueOf(docuserWijz));
    }

    public int getToegang() {
        String docnr = fields.get("R_TOEGANG");
        if (docnr == null)
            return -1;
        return Integer.parseInt(docnr);
    }

    public void setBehandelaars(String docuserWijz) {
        fields.put("R_BEHANDELAAR", docuserWijz);
    }

    public String getBehandelaars() {
        return fields.get("R_BEHANDELAAR");
    }

    public void setBehandelendeAfdeling(String docuserWijz) {
        fields.put("R_BEHANDOND", docuserWijz);
    }

    public String getBehandelendeAfdeling() {
        return fields.get("R_BEHANDOND");
    }

    public void setBeschikbaarheid(String docuserWijz) {
        fields.put("STATUS", docuserWijz);
    }

    String get(String field) {
        return fields.get(field);
    }

    void set(String string, String string1) {
        fields.put(string, string1);
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getStatusId() {
        return statusId;
    }

    public String getStatusIdAbbreviation() {
        if( statusId == null) return "";
        return statusId.substring(0, 1);
    }

    public void setRichtingId(String richtingId) {
        this.richtingId = richtingId;
    }

    public String getRichtingId() {
        return richtingId;
    }

    public void setToegangId(String toegangId) {
        this.toegangId = toegangId;
    }

    public String getToegangId() {
        return toegangId;
    }

    public void setOrganisatieCode(String organisatieCode) {
        fields.put("GROUP_ID", organisatieCode);
    }

    public String getOrganisatieCode() {
        return (String)fields.get("GROUP_ID");
    }
    
    public String getBeschikbaarheid () {
        String retval;
        String status = fields.get("STATUS");
        if (status.equals("0")) {
            retval = "true";
        }
        else {
            retval = "false";
        }
        return retval;
    }

    public String getBeschikbaarheidText () {
        String retval;
        String status = fields.get("STATUS");
        if (status.equals("0")) {
            retval = "Beschikbaar";
        }
        else {
            retval = "Niet beschikbaar";
        }
        return retval;
    }

    public DocumentSearch copy() throws CloneNotSupportedException {
        DocumentSearch clone = new DocumentSearch();
        clone.fields = ( HashMap<String, String>)this.fields.clone();
        clone.richtingId = richtingId;
        clone.statusId = statusId;
        clone.toegangId = toegangId;
        return clone;
    }

    public void setDisplayed(boolean displayed) {
        this.displayed = displayed;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isDeleted() {
        return deleted;
    }
}
