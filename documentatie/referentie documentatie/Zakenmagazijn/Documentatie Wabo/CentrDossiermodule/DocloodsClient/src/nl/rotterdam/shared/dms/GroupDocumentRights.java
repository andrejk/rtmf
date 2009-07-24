package nl.rotterdam.shared.dms;

import java.util.List;

public class GroupDocumentRights extends Group {
    private DocumentRights groupRights;
    private List<DocumentRights> userRightsList;
    private boolean showUserRights;
    
    public GroupDocumentRights() {
    }

    // getters  and setters
    public void setGroupRights(DocumentRights documentRights) {
        this.groupRights = documentRights;
    }

    public DocumentRights getGroupRights() {
        return groupRights;
    }

    public void setUserRightsList(List<DocumentRights> userRights) {
        this.userRightsList = userRights;
    }

    public List<DocumentRights> getUserRightsList() {
        return userRightsList;
    }

    public void setShowUserRights(boolean showUserRights) {
        this.showUserRights = showUserRights;
    }

    public boolean isShowUserRights() {
        return showUserRights;
    }
}
