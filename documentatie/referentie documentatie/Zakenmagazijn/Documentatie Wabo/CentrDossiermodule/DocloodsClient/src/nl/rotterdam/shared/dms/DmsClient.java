package nl.rotterdam.shared.dms;

import java.io.File;

import java.io.InputStream;

import java.text.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nl.onefox.ebus._2009._3._20.AuthInfo;
import nl.onefox.ebus._2009._3._20.attachment.ReadDocumentMsg;
import nl.onefox.ebus._2009._3._20.attachment.WriteDocumentMsg;
import nl.onefox.ebus._2009._3._20.data.Field;
import nl.onefox.ebus._2009._3._20.data.Fields;
import nl.onefox.ebus._2009._3._20.data.LookupField;
import nl.onefox.ebus._2009._3._20.data.LookupItem;
import nl.onefox.ebus._2009._3._20.data.LookupItems;
import nl.onefox.ebus._2009._3._20.data.ReturnFields;
import nl.onefox.ebus._2009._3._20.data.SearchCriteria;
import nl.onefox.ebus._2009._3._20.data.SearchItem;
import nl.onefox.ebus._2009._3._20.data.SearchItems;
import nl.onefox.ebus._2009._3._20.data.SortField;
import nl.onefox.ebus._2009._3._20.data.SortFields;
import nl.onefox.ebus._2009._3._20.data.Trustee;
import nl.onefox.ebus._2009._3._20.data.Trustees;
import nl.onefox.ebus._2009._3._20.document.ReadContentResultMsg;
import nl.onefox.ebus._2009._3._20.lookup.GetLookupListMsg;
import nl.onefox.ebus._2009._3._20.profile.CreateProfileMsg;
import nl.onefox.ebus._2009._3._20.profile.EditProfileMsg;
import nl.onefox.ebus._2009._3._20.profile.GetProfileMsg;
import nl.onefox.ebus._2009._3._20.profile.QueueForDeleteProfileMsg;
import nl.onefox.ebus._2009._3._20.profile.ResultMsg;
import nl.onefox.ebus._2009._3._20.search.SearchProfileMsg;

import nl.onefox.ebus._2009._3._20.security.GetTrusteeMsg;

import nl.onefox.ebus._2009._3._20.security.SetTrusteeMsg;

import nl.rotterdam.shared.docloods.DocLoodsApplication;
import nl.rotterdam.shared.docloods.DocLoodsApplications;
import nl.rotterdam.shared.docloods.DocLoodsAttachmentClient;
import nl.rotterdam.shared.docloods.DocLoodsLookupClient;
import nl.rotterdam.shared.docloods.DocLoodsProfileClient;
import nl.rotterdam.shared.docloods.DocLoodsSearchClient;
import nl.rotterdam.shared.docloods.DocLoodsSecurityClient;
import nl.rotterdam.util.Configuration;


public class DmsClient {
    private static final String DOCLOODS_ENDPOINT = "docloods.endpoint";

    AuthInfo authInfo = new AuthInfo();
    DocLoodsProfileClient profileClient = new DocLoodsProfileClient(Configuration.getProperty(DOCLOODS_ENDPOINT, "http://10.19.10.154/ebus/") + "ProfileService.svc");
    DocLoodsAttachmentClient attachmentClient = new DocLoodsAttachmentClient(Configuration.getProperty(DOCLOODS_ENDPOINT, "http://10.19.10.154/ebus/") + "AttachmentService.svc");
    DocLoodsSearchClient searchClient = new DocLoodsSearchClient(Configuration.getProperty(DOCLOODS_ENDPOINT, "http://10.19.10.154/ebus/") + "SearchService.svc");
    DocLoodsLookupClient lookupClient = new DocLoodsLookupClient(Configuration.getProperty(DOCLOODS_ENDPOINT, "http://10.19.10.154/ebus/") + "LookupService.svc");
    DocLoodsSecurityClient securityClient = new DocLoodsSecurityClient(Configuration.getProperty(DOCLOODS_ENDPOINT, "http://10.19.10.154/ebus/") + "SecurityService.svc");
    static nl.onefox.ebus._2009._3._20.attachment.ObjectFactory attachmentObjectFactory = new nl.onefox.ebus._2009._3._20.attachment.ObjectFactory();
    static nl.onefox.ebus._2009._3._20.profile.ObjectFactory profileObjectFactory = new nl.onefox.ebus._2009._3._20.profile.ObjectFactory();
    static nl.onefox.ebus._2009._3._20.search.ObjectFactory searchObjectFactory = new nl.onefox.ebus._2009._3._20.search.ObjectFactory();
    static nl.onefox.ebus._2009._3._20.security.ObjectFactory securityObjectFactory = new nl.onefox.ebus._2009._3._20.security.ObjectFactory();


    public DmsClient(UserInfo userInfo) {
        if (Configuration.getProperty(DOCLOODS_ENDPOINT, "http://10.19.10.154/ebus/") == null || Configuration.getProperty(DOCLOODS_ENDPOINT, "http://10.19.10.154/ebus/").equals("disabled"))
            throw new RuntimeException("DOCLOODS_DISABLED: " + Configuration.getProperty(DOCLOODS_ENDPOINT, "http://10.19.10.154/ebus/"));
        authInfo.setLibrary(userInfo.getLibrary());
        authInfo.setUsername(userInfo.getUserId());
        authInfo.setPassword(userInfo.getPassword());
    }

    /**
     * Stelt de te gebruiken timeouts in voor deze client.
     *
     * @param soapConnectionTimeout aantal milliseconden dat een connect zal blijven wachten
     * voordat een timeout optreedt. null voor geen timeout.
     * @param soapReadTimeout aantal milliseconden dat een read zal blijven wachten voordat
     * een timeout optreedt. null voor geen timeout.
     * @return this, voor gemakkelijke syntax: DmsClient.getInstance().setTimeout(1000,1000).doe_iets()
     */
    public DmsClient setTimeouts(Integer soapConnectionTimeout, Integer soapReadTimeout) {
        profileClient.setTimeouts(soapConnectionTimeout, soapReadTimeout);
        attachmentClient.setTimeouts(soapConnectionTimeout, soapReadTimeout);
        searchClient.setTimeouts(soapConnectionTimeout, soapReadTimeout);
        lookupClient.setTimeouts(soapConnectionTimeout, soapReadTimeout);
        securityClient.setTimeouts(soapConnectionTimeout, soapReadTimeout);
        return this;
    }

    public void setUserInfo(UserInfo userInfo) {
        authInfo.setLibrary(userInfo.getLibrary());
        authInfo.setUsername(userInfo.getUserId());
        authInfo.setPassword(userInfo.getPassword());
    }

    public int createZaakRegistratie(ZaakRegistratie zaakregistratie) throws Exception {
        CreateProfileMsg createProfile = new CreateProfileMsg();
        createProfile.setForm(zaakregistratie.getFormName());
        Fields fields = new Fields();
        createProfile.setFields(fields);
        for (String field: ZaakRegistratie.FIELDS) {
            if (zaakregistratie.get(field) != null) {
                Field f = new Field();
                f.setName(field);
                f.setValue(zaakregistratie.get(field));
                fields.getField().add(f);
            }
        }

        ResultMsg result = profileClient.createProfile(authInfo, createProfile);
        return result.getDocNumber().intValue();

    }

    public List<String> getDocumentToegangPrimaryKeys(String filter) throws Exception {
        return getLookupValues("R_TOEGANG", "%PRIMARY_KEY", "R_TOEGANG_ID01", filter);
    }

    public List<String> getDocumentStatussen() throws Exception {
        return getLookupValues("R_STATUS", "R_STATUS_ID01", "R_STATUS_TYPE", "D");
    }

    public List<String> getZaakStatussen() throws Exception {
        return getLookupValues("R_STATUS", "R_STATUS_ID01", "R_STATUS_TYPE", "Z");
    }

    public List<String> getGeslachten() throws Exception {
        return getLookupValues("R_GESLACHT", "R_GESLACHT_ID", null, null);
    }

    public List<String> getToegangen() throws Exception {
        return getLookupValues("R_TOEGANG_ENABLED", "R_TOEGANG_ID01", null, null);
    }

    public List<String> getTypes() throws Exception {
        return getLookupValues("DOCTYPES_STUK", "TYPE_ID01", null, null);
    }

    public List<String> getRichtingen() throws Exception {
        return getLookupValues("R_RICHTING", "R_RICHTING_ID01", null, null);
    }

    private List<Gebruiker> getGebruikers(Gebruiker filter, String lookupId, boolean getColumnHeaders) throws Exception {
        GetLookupListMsg lookupMsg = new GetLookupListMsg();
        lookupMsg.setForm(DocumentRegistratie.FORM_NAME);
        lookupMsg.setLookupId(lookupId);
        SearchCriteria criteria = new SearchCriteria();
        lookupMsg.setSearchCriteria(criteria);
        if (filter.getUserId() != null) {
            Field search = new Field();
            search.setName("USER_ID01");
            search.setValue("*" + filter.getUserId() + "*");
            criteria.getSearchCriterium().add(search);
        }
        if (filter.getFullName() != null) {
            Field search = new Field();
            search.setName("FULL_NAME01");
            search.setValue("*" + filter.getFullName() + "*");
            criteria.getSearchCriterium().add(search);
        }
        if (filter.getGroupName() != null) {
            Field search = new Field();
            search.setName("GROUP_ID01");
            search.setValue("*" + filter.getGroupName() + "*");
            criteria.getSearchCriterium().add(search);
        }
        if (filter.getGroupId() != null) {
            Field search = new Field();
            search.setName("GROUP_ID01");
            search.setValue("*" + filter.getGroupName() + "*");
            criteria.getSearchCriterium().add(search);
        }
        //            if (filter.getDienst() != null) {
        //                Field search = new Field();
        //                search.setName("R_DIENST_BESCHR");
        //                search.setValue("*" + filter.getDienst() + "*");
        //                criteria.getSearchCriterium().add(search);
        //            }
        LookupItems items = lookupClient.getLookupList(authInfo, lookupMsg);
        List<Gebruiker> values = new ArrayList<Gebruiker>();
        for (LookupItem item: items.getLookupItem()) {
            Gebruiker gebruiker = new Gebruiker();
            gebruiker.setUserId(getField(item, "USER_ID01"));
            gebruiker.setFullName(getField(item, "FULL_NAME01"));
            gebruiker.setGroupName(getField(item, "GROUP_ID01"));
            gebruiker.setGroupId(getField(item, "GROUP_ID01"));
            //                gebruiker.setDienst(getField(item, "R_DIENST_BESCHR"));

            // columnheaders
            if (getColumnHeaders) {
                gebruiker.setColumnHeaderUserId(getFieldTitle(item, "USER_ID01"));
                gebruiker.setColumnHeaderFullName(getFieldTitle(item, "FULL_NAME01"));
                gebruiker.setColumnHeaderGroupName(getFieldTitle(item, "GROUP_ID01"));
            }

            values.add(gebruiker);
        }
        return values;
    }

    public List<Gebruiker> getGebruikers(Gebruiker filter) throws Exception {
        return getGebruikers(filter, "_PEOPLE_ALL", false);
    }

    public List<KeyValue> getGebruikerFieldTitles() throws Exception {
        Gebruiker filter = new Gebruiker();
        filter.setUserId("GEEN");
        List<Gebruiker> gebruikerList = getGebruikers(filter, "_PEOPLE_ALL", true);
        List<KeyValue> titleList = new ArrayList<KeyValue>();
        for (Gebruiker gebruiker: gebruikerList) {
            KeyValue userIdHeader = new KeyValue();
            userIdHeader.setKey(Gebruiker.TITLE_USERID);
            userIdHeader.setValue(gebruiker.getColumnHeaderUserId());
            KeyValue fullNameHeader = new KeyValue();
            fullNameHeader.setKey(Gebruiker.TITLE_FULLNAME);
            fullNameHeader.setValue(gebruiker.getColumnHeaderFullName());
            KeyValue groupIdHeader = new KeyValue();
            groupIdHeader.setKey(Gebruiker.TITLE_GROUPID);
            groupIdHeader.setValue(gebruiker.getColumnHeaderGroupName());
            titleList.add(userIdHeader);
            titleList.add(fullNameHeader);
            titleList.add(groupIdHeader);
            break;
        }
        return titleList;
    }

    private List<Gebruiker> getGebruikers_backup(Gebruiker filter, String lookupId) throws Exception {
        GetLookupListMsg lookupMsg = new GetLookupListMsg();
        lookupMsg.setForm(DocumentRegistratie.FORM_NAME);
        lookupMsg.setLookupId(lookupId);
        SearchCriteria criteria = new SearchCriteria();
        lookupMsg.setSearchCriteria(criteria);
        if (filter.getUserId() != null) {
            Field search = new Field();
            search.setName("USER_ID01");
            search.setValue("*" + filter.getUserId() + "*");
            criteria.getSearchCriterium().add(search);
        }
        if (filter.getFullName() != null) {
            Field search = new Field();
            search.setName("FULL_NAME01");
            search.setValue("*" + filter.getFullName() + "*");
            criteria.getSearchCriterium().add(search);
        }
        if (filter.getGroupName() != null) {
            Field search = new Field();
            search.setName("GROUP_NAME");
            search.setValue("*" + filter.getGroupName() + "*");
            criteria.getSearchCriterium().add(search);
        }
        if (filter.getDienst() != null) {
            Field search = new Field();
            search.setName("R_DIENST_BESCHR");
            search.setValue("*" + filter.getDienst() + "*");
            criteria.getSearchCriterium().add(search);
        }
        LookupItems items = lookupClient.getLookupList(authInfo, lookupMsg);
        List<Gebruiker> values = new ArrayList<Gebruiker>();
        for (LookupItem item: items.getLookupItem()) {
            Gebruiker gebruiker = new Gebruiker();
            gebruiker.setUserId(getField(item, "USER_ID01"));
            gebruiker.setFullName(getField(item, "FULL_NAME01"));
            gebruiker.setGroupName(getField(item, "GROUP_NAME"));
            gebruiker.setDienst(getField(item, "R_DIENST_BESCHR"));

            // columnheaders
            gebruiker.setColumnHeaderUserId(getFieldTitle(item, "USER_ID01"));
            gebruiker.setColumnHeaderFullName(getFieldTitle(item, "FULL_NAME01"));
            gebruiker.setColumnHeaderGroupName(getFieldTitle(item, "GROUP_NAME"));

            values.add(gebruiker);
        }
        return values;
    }

    public List<Gebruiker> getGebruikers_backupk(Gebruiker filter) throws Exception {
        return getGebruikers_backup(filter, "PEOPLE");
    }

    public List<Gebruiker> getCasemanagers_backup(Gebruiker filter) throws Exception {
        return getGebruikers_backup(filter, "CASEMANAGERS");
    }

    public List<String> getLookupValues(String lookupId, String field, String filterField, String filterValue) throws Exception {
        GetLookupListMsg lookupMsg = new GetLookupListMsg();
        lookupMsg.setForm(DocumentRegistratie.FORM_NAME);
        lookupMsg.setLookupId(lookupId);
        LookupItems items = lookupClient.getLookupList(authInfo, lookupMsg);
        List<String> values = new ArrayList<String>();
        for (LookupItem item: items.getLookupItem()) {
            if (filterField == null || filterValue.equals(getField(item, filterField))) {
                values.add(getField(item, field));
            }
        }
        return values;
    }

    public List<KeyValue> getRichtingenKV() throws Exception {
        return getKeyValue("R_RICHTING", "%PRIMARY_KEY", "R_RICHTING_ID01");
    }

    public List<KeyValue> getStatussenKV() throws Exception {
        return getKeyValue("R_STATUS", "%PRIMARY_KEY", "R_STATUS_ID01");
    }

    public List<KeyValue> getToegangKV() throws Exception {
        return getKeyValue("R_TOEGANG", "%PRIMARY_KEY", "R_TOEGANG_ID01");
    }

    public List<KeyValue> getGroups(String filter) throws Exception {
        return getKeyValue("_GROUPS_ENABLED", "GROUP_ID01", "GROUP_NAME", "GROUP_ID", filter);
    }

    public List<KeyValue> getUsersInGroup(String groupId) throws Exception {
        return getKeyValue("R_GEBRUIKERS_TONEN", "USER_ID01", "FULL_NAME01", "GROUP_ID01", groupId);
    }

    public List<KeyValue> getKeyValue(String lookupId, String keyField, String valueField) throws Exception {
        return getKeyValue(lookupId, keyField, valueField, null, null);
    }

    public List<KeyValue> getKeyValue(String lookupId, String keyField, String valueField, String filterField, String filterValue) throws Exception {
        GetLookupListMsg lookupMsg = new GetLookupListMsg();
        lookupMsg.setForm(DocumentRegistratie.FORM_NAME);
        lookupMsg.setLookupId(lookupId);
        if (filterField != null) {
            SearchCriteria search = new SearchCriteria();
            Field field = new Field();
            field.setName(filterField);
            field.setValue(filterValue);
            search.getSearchCriterium().add(field);
            lookupMsg.setSearchCriteria(search);
        }
        LookupItems items = lookupClient.getLookupList(authInfo, lookupMsg);
        List<KeyValue> values = new ArrayList<KeyValue>();
        for (LookupItem item: items.getLookupItem()) {
            KeyValue value = new KeyValue();
            value.setKey(getField(item, keyField));
            value.setValue(getField(item, valueField));
            values.add(value);
        }
        return values;
    }

    public String getPrimaryGroup() throws Exception {
        GetLookupListMsg lookupMsg = new GetLookupListMsg();
        lookupMsg.setForm(DocumentRegistratie.FORM_NAME);
        lookupMsg.setLookupId("R_GEBRUIKERS_TONEN");
        lookupMsg.setSearchCriteria(new SearchCriteria());
        lookupMsg.getSearchCriteria().getSearchCriterium().add(new Field());
        lookupMsg.getSearchCriteria().getSearchCriterium().get(0).setName("USER_ID01");
        lookupMsg.getSearchCriteria().getSearchCriterium().get(0).setValue(authInfo.getUsername());
        LookupItems items = lookupClient.getLookupList(authInfo, lookupMsg);
        for (LookupItem item: items.getLookupItem()) {
            return getField(item, "GROUP_ID01");
        }
        return null;
    }

    public int addAttachment(DocumentRegistratie documentregistratie, File document) throws Exception {
        WriteDocumentMsg msg = new WriteDocumentMsg();
        msg.setForm(documentregistratie.getFormName());
        msg.setDocNumber(documentregistratie.getDocnum());
        ReturnFields returnFields = new ReturnFields();
        msg.setReturnFields(attachmentObjectFactory.createWriteDocumentMsgReturnFields(returnFields));
        returnFields.getReturnField().add("DOCNAME");
        attachmentClient.writeDocument(authInfo, msg, document);
        return documentregistratie.getDocnum();
    }

    public int createDocumentRegistratie(DocumentRegistratie documentregistratie, File document) throws Exception {
        return createDocumentRegistratie(documentregistratie, document, false);
    }

    public int createDocumentRegistratie(DocumentRegistratie documentregistratie, InputStream document) throws Exception {
        return createDocumentRegistratie(documentregistratie, document, false);
    }

    public int createDocumentRegistratie(DocumentRegistratie documentregistratie, File document, boolean newVersion) throws Exception {
        CreateProfileMsg createProfile = new CreateProfileMsg();
        createProfile.setForm(documentregistratie.getFormName());
        Fields fields = new Fields();
        createProfile.setFields(fields);
        for (String field: DocumentRegistratie.FIELDS) {
            if (documentregistratie.get(field) != null) {
                Field f = new Field();
                f.setName(field);
                f.setValue(documentregistratie.get(field));
                fields.getField().add(f);
            }
        }

        ResultMsg result = profileClient.createProfile(authInfo, createProfile);
        documentregistratie.setDocnum(result.getDocNumber());

        if (document != null) {
            WriteDocumentMsg msg = new WriteDocumentMsg();
            msg.setForm(documentregistratie.getFormName());
            msg.setDocNumber(result.getDocNumber());
            //msg.setCreateNewVersion(Boolean.FALSE);
            msg.setCreateNewVersion(newVersion);
            msg.setCheckin(Boolean.TRUE);
            ReturnFields returnFields = new ReturnFields();
            msg.setReturnFields(attachmentObjectFactory.createWriteDocumentMsgReturnFields(returnFields));
            returnFields.getReturnField().add("DOCNAME");
            attachmentClient.writeDocument(authInfo, msg, document);
        }

        return result.getDocNumber().intValue();
    }

    public int createDocumentRegistratie(DocumentRegistratie documentregistratie, InputStream document, boolean newVersion) throws Exception {
        CreateProfileMsg createProfile = new CreateProfileMsg();
        createProfile.setForm(documentregistratie.getFormName());
        Fields fields = new Fields();
        createProfile.setFields(fields);
        for (String field: DocumentRegistratie.FIELDS) {
            if (documentregistratie.get(field) != null) {
                Field f = new Field();
                f.setName(field);
                f.setValue(documentregistratie.get(field));
                fields.getField().add(f);
            }
        }

        ResultMsg result = profileClient.createProfile(authInfo, createProfile);
        documentregistratie.setDocnum(result.getDocNumber());

        if (document != null) {
            WriteDocumentMsg msg = new WriteDocumentMsg();
            msg.setForm(documentregistratie.getFormName());
            msg.setDocNumber(result.getDocNumber());
            //msg.setCreateNewVersion(Boolean.FALSE);
            msg.setCreateNewVersion(newVersion);
            msg.setCheckin(Boolean.TRUE);
            ReturnFields returnFields = new ReturnFields();
            msg.setReturnFields(attachmentObjectFactory.createWriteDocumentMsgReturnFields(returnFields));
            returnFields.getReturnField().add("DOCNAME");
            attachmentClient.writeDocument(authInfo, msg, document);
        }

        return result.getDocNumber().intValue();
    }
    //    public ZaakRegistratie getZaakRegistratie(int docnummer) throws Exception {
    //        GetProfileMsg getProfile = new GetProfileMsg();
    //        getProfile.setForm(ZaakRegistratie.FORM_NAME);
    //        getProfile.setDocNumber(docnummer);
    //        ReturnFields returnFields = new ReturnFields();
    //        for (String field: ZaakRegistratie.FIELDS) {
    //            returnFields.getReturnField().add(field);
    //        }
    //        getProfile.setReturnFields(profileObjectFactory.createGetProfileMsgReturnFields(returnFields));
    //
    //        ResultMsg result = profileClient.getProfile(authInfo, getProfile);
    ////        ZaakRegistratie zaakRegistratie = new ZaakRegistratie();
    //        xZaakRegistratie zaakRegistratie = new ZaakRegistratie("DSV", null, null, null, null, "003385(A)");
    //
    //        for (Field f: result.getFields().getValue().getField()) {
    //            zaakRegistratie.set(f.getName(), f.getValue());
    //        }
    //
    //        return zaakRegistratie;
    //    }

    /**
     * Retrieve zaaknr derived from docnum
     * @param docnum
     * @return
     * @throws Exception
     */
    public int getZaakRegistratieNr(int docnum) throws Exception {
        int zaaknr = -1;
        GetProfileMsg getProfile = new GetProfileMsg();
        getProfile.setForm("R_STUK_DOS");
        getProfile.setDocNumber(docnum);
        ReturnFields returnFields = new ReturnFields();
        String field = "R_ZNR";
        returnFields.getReturnField().add(field);
        getProfile.setReturnFields(profileObjectFactory.createGetProfileMsgReturnFields(returnFields));
        ResultMsg result = profileClient.getProfile(authInfo, getProfile);
        for (Field f: result.getFields().getValue().getField()) {
            zaaknr = new Integer(f.getValue()).intValue();
            break;
        }
        return zaaknr;
    }

    /**
     * get list of case-involved departments
     * @param casenr
     * @return
     */
    public KeyValue getDepartmentList(int casenr) throws Exception {
        GetProfileMsg getProfile = new GetProfileMsg();
        getProfile.setForm("R_ZAAK_DOS");
        getProfile.setDocNumber(casenr);
        ReturnFields returnFields = new ReturnFields();
        String departmentField = "R_BETROKONDERD";
        String organisationField = "X5485";
        returnFields.getReturnField().add(departmentField);
        returnFields.getReturnField().add(organisationField);
        getProfile.setReturnFields(profileObjectFactory.createGetProfileMsgReturnFields(returnFields));
        ResultMsg result = profileClient.getProfile(authInfo, getProfile);
        KeyValue kv = new KeyValue();
        for (Field f: result.getFields().getValue().getField()) {
            if (f.getName().equals(departmentField)) {
                kv.setValue(f.getValue());
            } else if (f.getName().equals(organisationField)) {
                kv.setKey(f.getValue());
            }

        }
        return kv;
    }

    /**
     * Returns a list of departments belonging to a given organisation
     * @param organisation
     * @return
     * @throws Exception
     */
    public List<Group> getDepartmentsByOrganisation(String organisation) throws Exception {
        List<Group> departmentList = new ArrayList<Group>();
        GetLookupListMsg lookupMsg = new GetLookupListMsg();
        lookupMsg.setForm("R_STUK_DOS");
        lookupMsg.setLookupId("R_AFDELING_TONEN");
        lookupMsg.setSearchCriteria(new SearchCriteria());
        lookupMsg.getSearchCriteria().getSearchCriterium().add(new Field());
        lookupMsg.getSearchCriteria().getSearchCriterium().get(0).setName("GROUP_ID");
        lookupMsg.getSearchCriteria().getSearchCriterium().get(0).setValue(organisation + "*");
        LookupItems items = lookupClient.getLookupList(authInfo, lookupMsg);
        for (LookupItem item: items.getLookupItem()) {
            String groupName = getField(item, "GROUP_ID");
            Group group = new Group();
            group.setName(groupName);
            group.setAbbreviation(groupName);
            departmentList.add(group);
        }
        return departmentList;
    }

    public DocumentRegistratie getDocumentRegistratie(int docnummer) {
        DocumentRegistratie documentRegistratie = new DocumentRegistratie();
        try {
            GetProfileMsg getProfile = new GetProfileMsg();
            getProfile.setForm(DocumentRegistratie.FORM_NAME);
            getProfile.setDocNumber(docnummer);

            ReturnFields returnFields = new ReturnFields();
            for (String field: DocumentRegistratie.FIELDS) {
                returnFields.getReturnField().add(field);
            }
            getProfile.setReturnFields(profileObjectFactory.createGetProfileMsgReturnFields(returnFields));
            ResultMsg result = profileClient.getProfile(authInfo, getProfile);

            documentRegistratie.setDocnum(docnummer);
            for (Field f: result.getFields().getValue().getField()) {
                documentRegistratie.set(f.getName(), f.getValue());
            }

            // force docdate 01-01-1753 to null
            documentRegistratie.setDocumentdatum(correctDocumentDate(documentRegistratie.getDocumentdatum()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return documentRegistratie;
    }

    public DocumentRegistratie checkoutDocument(int docnum) throws Exception {
        // checkout
        ReadDocumentMsg msg = new ReadDocumentMsg();
        msg.setDocNumber(docnum);
        msg.setForm(DocumentRegistratie.FORM_NAME);
        msg.setCheckout(true);
        ReadContentResultMsg resultMsg = attachmentClient.readDocument(authInfo, msg);
        // get doc reg
        return getDocumentRegistratie(docnum);
    }

    public void saveNewVersion(int docnum, File document) throws Exception {
        saveDocument(docnum, document, true, true);
    }

    public void replaceDocument(int docnum, File document) throws Exception {
        saveDocument(docnum, document, false, false);
        saveDocument(docnum, (File)null, false, true);
    }
    
    private void saveDocument(int docnum, File document, boolean newVersion, boolean checkin) throws Exception {
        WriteDocumentMsg msg = new WriteDocumentMsg();
        msg.setCreateNewVersion(newVersion);
        msg.setCheckin(checkin);
        msg.setDocNumber(docnum);
        msg.setForm(DocumentRegistratie.FORM_NAME);
        attachmentClient.writeDocument(authInfo, msg, document);
    }
    public void replaceDocument(int docnum, InputStream document) throws Exception {
        saveDocument(docnum, document, false, false);
        saveDocument(docnum, (File)null, false, true);
    }
    
    private void saveDocument(int docnum, InputStream document, boolean newVersion, boolean checkin) throws Exception {
        WriteDocumentMsg msg = new WriteDocumentMsg();
        msg.setCreateNewVersion(newVersion);
        msg.setCheckin(checkin);
        msg.setDocNumber(docnum);
        msg.setForm(DocumentRegistratie.FORM_NAME);
        attachmentClient.writeDocument(authInfo, msg, document);
    }
    

    

    private Date correctDocumentDate(Date docDate) throws ParseException {
        // force docdate 01-01-1753 to null
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date testDate = format.parse("01-01-1753");
        if (docDate.equals(testDate)) {
            return null;
        } else
            return docDate;
    }

    public DocumentRegistratie editDocumentRegistratie(DocumentRegistratie documentregistratie) throws Exception {
        EditProfileMsg getProfile = new EditProfileMsg();
        getProfile.setForm(DocumentRegistratie.FORM_NAME);
        getProfile.setDocNumber(documentregistratie.getDocnum());
        Fields fields = new Fields();
        getProfile.setFields(fields);
        for (String field: DocumentRegistratie.FIELDS) {
            if (documentregistratie.get(field) != null) {
                Field f = new Field();
                f.setName(field);
                f.setValue(documentregistratie.get(field));
                fields.getField().add(f);
            }
        }

        ReturnFields returnFields = new ReturnFields();
        for (String field: DocumentRegistratie.FIELDS) {
            returnFields.getReturnField().add(field);
        }
        getProfile.setReturnFields(profileObjectFactory.createEditProfileMsgReturnFields(returnFields));
        ResultMsg result = profileClient.editProfile(authInfo, getProfile);
        DocumentRegistratie documentRegistratie = new DocumentRegistratie();
        for (Field f: result.getFields().getValue().getField()) {
            documentRegistratie.set(f.getName(), f.getValue());
        }


        return documentRegistratie;
    }

    public void deleteDocumentRegistratie(int docnum) throws Exception {
        QueueForDeleteProfileMsg queueForDelete = new QueueForDeleteProfileMsg();
        queueForDelete.setDocNumber(docnum);
        queueForDelete.setForm(DocumentRegistratie.FORM_NAME);
        profileClient.queueForDeleteProfile(authInfo, queueForDelete);
    }

    public List<DocumentRights> getRights(int docnummer) throws Exception {
        GetTrusteeMsg msg = new GetTrusteeMsg();
        msg.setForm(DocumentRegistratie.FORM_NAME);
        msg.setDocNumber(docnummer);
        Trustees t = securityClient.getTrustees(authInfo, msg);
        List<DocumentRights> result = new ArrayList<DocumentRights>();
        for (Trustee trustee: t.getTrustee()) {
            DocumentRights rights = new DocumentRights();
            rights.setUserid(trustee.getName());
            if (trustee.getFlag() == 1)
                rights.setGroup(true);
            rights.setPerm(trustee.getRights());
            result.add(rights);
        }
        return result;
    }

    public List<DocumentRights> setRights(int docnummer, List<DocumentRights> rightsList) throws Exception {
        SetTrusteeMsg msg = new SetTrusteeMsg();
        msg.setForm(DocumentRegistratie.FORM_NAME);
        msg.setDocNumber(docnummer);
        msg.setTrustees(new Trustees());
        for (DocumentRights r: rightsList) {
            Trustee trustee = new Trustee();
            trustee.setName(r.getUserid());
            trustee.setFlag(r.isGroup() ? 1 : 2);
            trustee.setRights(r.getPerm());
            msg.getTrustees().getTrustee().add(trustee);
        }
        Trustees t = securityClient.setTrustees(authInfo, msg);
        List<DocumentRights> result = new ArrayList<DocumentRights>();
        for (Trustee trustee: t.getTrustee()) {
            DocumentRights rights = new DocumentRights();
            rights.setUserid(trustee.getName());
            if (trustee.getFlag() == 1)
                rights.setGroup(true);
            rights.setPerm(trustee.getRights());
            result.add(rights);
        }
        return result;
    }

    public DocLoodsApplications getDocLoodsApplications() throws Exception {
        List<DocLoodsApplication> toepassingen = getToepassingen();
        return new DocLoodsApplications(toepassingen);
    }

    /**
     * Returns a list of document processing applications registered in the dms system
     * @return
     * @throws Exception
     */
    public List<DocLoodsApplication> getToepassingen() throws Exception {
        SearchProfileMsg searchProfileMsg = new SearchProfileMsg();
        searchProfileMsg.setForm(DocLoodsApplication.FORM_NAME);
        searchProfileMsg.setSearchCriteria(new SearchCriteria());
        for (KeyValue kv: DocLoodsApplication.getSearchFields()) {
            Field field = new Field();
            field.setName(kv.getKey());
            field.setValue(kv.getValue());
            searchProfileMsg.getSearchCriteria().getSearchCriterium().add(field);
        }

        ReturnFields returnFields = new ReturnFields();
        for (String field: DocLoodsApplication.FIELDS) {
            returnFields.getReturnField().add(field);
        }
        searchProfileMsg.setReturnFields(searchObjectFactory.createSearchProfileMsgReturnFields(returnFields));

        SortFields sortFields = new SortFields();
        SortField sf = new SortField();
        sf.setName("APPLICATION");
        sf.setAscending(true);
        sortFields.getSortField().add(sf);
        searchProfileMsg.setSortFields(searchObjectFactory.createSearchProfileMsgSortFields(sortFields));

        List<DocLoodsApplication> appList = new ArrayList<DocLoodsApplication>();

        SearchItems searchItems = searchClient.searchProfile(authInfo, searchProfileMsg);
        for (SearchItem item: searchItems.getSearchItem()) {
            DocLoodsApplication app = new DocLoodsApplication();
            for (Field f: item.getFields().getValue().getField()) {
                app.set(f.getName(), f.getValue());
            }
            appList.add(app);
        }

        return appList;
    }

    public List<DocumentSearch> getDocumentRegistratie(DocumentSearch search) throws Exception {
        SearchProfileMsg searchProfileMsg = new SearchProfileMsg();
        searchProfileMsg.setForm(DocumentSearch.FORM_NAME);
        searchProfileMsg.setSearchCriteria(new SearchCriteria());
        for (String field: DocumentSearch.FIELDS) {
            if (search.get(field) != null) {
                Field f = new Field();
                f.setName(field);
                f.setValue(search.get(field));
                searchProfileMsg.getSearchCriteria().getSearchCriterium().add(f);
            }
        }

        ReturnFields returnFields = new ReturnFields();
        for (String field: DocumentSearch.FIELDS) {
            returnFields.getReturnField().add(field);
        }
        searchProfileMsg.setReturnFields(searchObjectFactory.createSearchProfileMsgReturnFields(returnFields));
        List<DocumentSearch> documenten = new ArrayList<DocumentSearch>();
        SearchItems searchItems = searchClient.searchProfile(authInfo, searchProfileMsg);
        for (SearchItem item: searchItems.getSearchItem()) {
            DocumentSearch document = new DocumentSearch();
            for (Field f: item.getFields().getValue().getField()) {
                document.set(f.getName(), f.getValue());
            }
            documenten.add(document);
        }

        return documenten;
    }

    public List<DocumentSearch> xxxgetDocumentRegistratie(DocumentSearch search) throws Exception {
        SearchProfileMsg searchProfileMsg = new SearchProfileMsg();
        searchProfileMsg.setForm(DocumentSearch.FORM_NAME);
        searchProfileMsg.setSearchCriteria(new SearchCriteria());
        for (String field: DocumentSearch.FIELDS) {
            if (search.get(field) != null) {
                Field f = new Field();
                f.setName(field);
                f.setValue(search.get(field));
                searchProfileMsg.getSearchCriteria().getSearchCriterium().add(f);
            }
        }

        ReturnFields returnFields = new ReturnFields();
        for (String field: DocumentSearch.FIELDS) {
            returnFields.getReturnField().add(field);
        }
        searchProfileMsg.setReturnFields(searchObjectFactory.createSearchProfileMsgReturnFields(returnFields));
        List<DocumentSearch> documenten = new ArrayList<DocumentSearch>();
        SearchItems searchItems = searchClient.searchProfile(authInfo, searchProfileMsg);
        for (SearchItem item: searchItems.getSearchItem()) {
            DocumentSearch document = new DocumentSearch();
            for (Field f: item.getFields().getValue().getField()) {
                document.set(f.getName(), f.getValue());
            }
            documenten.add(document);
        }

        return documenten;
    }

    public byte[] getDocument(int docnummer, Integer version) throws Exception {
        ReadDocumentMsg msg = new ReadDocumentMsg();
        msg.setForm(DocumentRegistratie.FORM_NAME);
        msg.setDocNumber(docnummer);
        msg.setCheckout(Boolean.FALSE);
        if (version != null) {
            msg.setVersion(version.intValue());
        }
        ReturnFields fields = new ReturnFields();
        msg.setReturnFields(attachmentObjectFactory.createReadDocumentMsgReturnFields(fields));
        ReadContentResultMsg result = attachmentClient.readDocument(authInfo, msg);
        return result.getContent().getValue();
    }


    private String getField(LookupItem item, String string) {
        for (LookupField field: item.getLookupFields().getValue().getLookupField()) {
            if (field.getPropertyName().equals(string)) {
                return field.getData();
            }
        }
        return null;
    }

    private String getFieldTitle(LookupItem item, String string) {
        for (LookupField field: item.getLookupFields().getValue().getLookupField()) {
            if (field.getPropertyName().equals(string)) {
                return field.getTitle();
            }
        }
        return null;
    }

}
