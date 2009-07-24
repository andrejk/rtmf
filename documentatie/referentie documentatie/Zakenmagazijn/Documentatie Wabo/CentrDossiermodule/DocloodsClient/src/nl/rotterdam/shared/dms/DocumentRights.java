package nl.rotterdam.shared.dms;

public class DocumentRights {
    public final static int PERM_REGISTRATIE_BEKIJKEN = 0x1;
    public final static int PERM_REGISTRATIE_WIJZIGEN = 0x2;
    public final static int PERM_DOCUMENT_BEKIJKEN = 0x4;
    public final static int PERM_DOCUMENT_OPHALEN = 0x8;
    public final static int PERM_DOCUMENT_BEWERKEN = 0x10;
    public final static int PERM_KOPIEREN = 0x20;
    public final static int PERM_VERWIJDEREN = 0x40;
    public final static int PERM_TOEGANGSRECHTEN = 0x80;
    private String userid;
    private boolean group;
    private int perm;
    
    // controls checkbox enabled/disabled status for doc rights of "gebruikers"
    private int groupPerm;
    
    // hold the right style on this object, necessary for ajax updates of list
    private String styleClass;
    public static final String STYLECLASS_COLLAPSED = "rightsDepeartmentCollapsed";
    public static final String STYLECLASS_EXPANDED = "rightsDepeartmentExpanded";
    
    public DocumentRights() {
    }
    
    // central handler to set privileges
    private void setPrivilege (int privilege, boolean value) {
        boolean hasPrivilege;
        switch (privilege) {
            case DocumentRights.PERM_REGISTRATIE_BEKIJKEN:
                hasPrivilege = isRegistratieBekijken();
                break;
            case DocumentRights.PERM_REGISTRATIE_WIJZIGEN:
                hasPrivilege = isRegistratieWijzigen();
                break;
            case DocumentRights.PERM_DOCUMENT_BEKIJKEN:
                hasPrivilege = isDocumentBekijken();
                break;
            case DocumentRights.PERM_DOCUMENT_OPHALEN:
                hasPrivilege = isDocumentOphalen();
                break;
            case DocumentRights.PERM_DOCUMENT_BEWERKEN:
                hasPrivilege = isDocumentBewerken();
                break;
            case DocumentRights.PERM_KOPIEREN:
                hasPrivilege = isKopieren();
                break;
            case DocumentRights.PERM_VERWIJDEREN:
                hasPrivilege = isVerwijderen();
                break;
            case DocumentRights.PERM_TOEGANGSRECHTEN:
                hasPrivilege = isToegangsrechten();
                break;
            default:
                return;
        }

        if (value) {
            if (! hasPrivilege) perm = perm + privilege;
        }
        else {
            if (hasPrivilege) perm = perm - privilege;
        }
    }
    
    // central handler to set privileges
    private void setDisabled (int privilege, boolean value) {
        boolean disabled;
        switch (privilege) {
            case DocumentRights.PERM_REGISTRATIE_BEKIJKEN:
                disabled = isRegBekijkenDisabled();
                break;
            case DocumentRights.PERM_REGISTRATIE_WIJZIGEN:
                disabled = isRegWijzigenDisabled();
                break;
            case DocumentRights.PERM_DOCUMENT_BEKIJKEN:
                disabled = isDocBekijkenDisabled();
                break;
            case DocumentRights.PERM_DOCUMENT_OPHALEN:
                disabled = isDocOphalenDisabled();
                break;
            case DocumentRights.PERM_DOCUMENT_BEWERKEN:
                disabled = isDocWijzigenDisabled();
                break;
            case DocumentRights.PERM_KOPIEREN:
                disabled = isKopierenDisabled();
                break;
            case DocumentRights.PERM_VERWIJDEREN:
                disabled = isVerwijderenDisabled();
                break;
            case DocumentRights.PERM_TOEGANGSRECHTEN:
                disabled = isToegangsrechtenDisabled();
                break;
            default:
                return;
        }

        if (value) {
            if (! disabled) groupPerm = groupPerm + privilege;
        }
        else {
            if (disabled) groupPerm = groupPerm - privilege;
        }
    }

    // getters and setters
    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserid() {
        return userid;
    }

    public void setGroup(boolean group) {
        this.group = group;
    }

    public boolean isGroup() {
        return group;
    }

    public void setRegistratieBekijken(boolean perm1) {
        setPrivilege(PERM_REGISTRATIE_BEKIJKEN, perm1);
    }

    public boolean isRegistratieBekijken() {
        return (perm & PERM_REGISTRATIE_BEKIJKEN) > 0;
    }

    public void setRegistratieWijzigen(boolean perm1) {
        setPrivilege(PERM_REGISTRATIE_WIJZIGEN, perm1);
    }

    public boolean isRegistratieWijzigen() {
        return (perm & PERM_REGISTRATIE_WIJZIGEN) > 0;
    }
    
    public void setDocumentBekijken(boolean perm1) {
        setPrivilege(PERM_DOCUMENT_BEKIJKEN, perm1);
    }

    public boolean isDocumentBekijken() {
        return (perm & PERM_DOCUMENT_BEKIJKEN) > 0;
    }
    
    public void setDocumentOphalen(boolean perm1) {
        setPrivilege(PERM_DOCUMENT_OPHALEN, perm1);
    }

    public boolean isDocumentOphalen() {
        return (perm & PERM_DOCUMENT_OPHALEN) > 0;
    }
    
    public void setDocumentBewerken(boolean perm1) {
        setPrivilege(PERM_DOCUMENT_BEWERKEN, perm1);
    }

    public boolean isDocumentBewerken() {
        return (perm & PERM_DOCUMENT_BEWERKEN) > 0;
    }
    
    public void setKopieren(boolean perm1) {
        setPrivilege(PERM_KOPIEREN, perm1);
    }

    public boolean isKopieren() {
        return (perm & PERM_KOPIEREN) > 0;
    }
    
    public void setVerwijderen(boolean perm1) {
        setPrivilege(PERM_VERWIJDEREN, perm1);
    }

    public boolean isVerwijderen() {
        return (perm & PERM_VERWIJDEREN) > 0;
    }
    
    public void setToegangsrechten(boolean perm8) {
        setPrivilege(PERM_TOEGANGSRECHTEN, perm8);
    }

    public boolean isToegangsrechten() {
        return (perm & PERM_TOEGANGSRECHTEN) > 0;
    }

    public void setPerm(int perm) {
        this.perm = perm;
    }

    public int getPerm() {
        return perm;
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        return styleClass;
    }

    public void setRegBekijkenDisabled(boolean regBekijkenDisabled) {
        setDisabled(DocumentRights.PERM_REGISTRATIE_BEKIJKEN, regBekijkenDisabled);
    }

    public boolean isRegBekijkenDisabled() {
        return (groupPerm & PERM_REGISTRATIE_BEKIJKEN) > 0;
    }

    public void setRegWijzigenDisabled(boolean regWijzigenDisabled) {
        setDisabled(DocumentRights.PERM_REGISTRATIE_WIJZIGEN, regWijzigenDisabled);
    }

    public boolean isRegWijzigenDisabled() {
        return (groupPerm & PERM_REGISTRATIE_WIJZIGEN) > 0;
    }

    public void setDocBekijkenDisabled(boolean docBekijkenDisabled) {
        setDisabled(DocumentRights.PERM_DOCUMENT_BEKIJKEN, docBekijkenDisabled);
    }

    public boolean isDocBekijkenDisabled() {
        return (groupPerm & PERM_DOCUMENT_BEKIJKEN) > 0;
    }

    public void setDocOphalenDisabled(boolean docOphalenDisabled) {
        setDisabled(DocumentRights.PERM_DOCUMENT_OPHALEN, docOphalenDisabled);
    }

    public boolean isDocOphalenDisabled() {
        return (groupPerm & PERM_DOCUMENT_OPHALEN) > 0;
    }

    public void setDocWijzigenDisabled(boolean docWijzigenDisabled) {
        setDisabled(DocumentRights.PERM_DOCUMENT_BEWERKEN, docWijzigenDisabled);
    }

    public boolean isDocWijzigenDisabled() {
        return (groupPerm & PERM_DOCUMENT_BEWERKEN) > 0;
    }

    public void setKopierenDisabled(boolean kopierenDisabled) {
        setDisabled(DocumentRights.PERM_KOPIEREN, kopierenDisabled);
    }

    public boolean isKopierenDisabled() {
        return (groupPerm & PERM_KOPIEREN) > 0;
    }

    public void setVerwijderenDisabled(boolean verwijderenDisabled) {
        setDisabled(DocumentRights.PERM_VERWIJDEREN, verwijderenDisabled);
    }

    public boolean isVerwijderenDisabled() {
        return (groupPerm & PERM_VERWIJDEREN) > 0;
    }

    public void setToegangsrechtenDisabled(boolean toegangsrechtenDisabled) {
        setDisabled(DocumentRights.PERM_TOEGANGSRECHTEN, toegangsrechtenDisabled);
    }

    public boolean isToegangsrechtenDisabled() {
        return (groupPerm & PERM_TOEGANGSRECHTEN) > 0;
    }
}
