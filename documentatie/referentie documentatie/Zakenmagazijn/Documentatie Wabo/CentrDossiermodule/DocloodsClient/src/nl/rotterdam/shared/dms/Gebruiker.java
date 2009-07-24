package nl.rotterdam.shared.dms;

import java.util.HashMap;
import java.util.List;

import nl.rotterdam.util.CommonUtils;


public class Gebruiker {
    private boolean selected;
    private String userId;
    private String fullName;
    private String groupId;
    private String groupName;
    private String dienst;
    
    // column headers
    public static final String TITLE_USERID ="TITLE_USERID";
    public static final String TITLE_FULLNAME ="TITLE_FULLNAME";
    public static final String TITLE_GROUPID ="TITLE_GROUPID";
    private String columnHeaderUserId;
    private String columnHeaderFullName;
    private String columnHeaderGroupName;
    
    
    public Gebruiker() {
    }
    
    public Gebruiker (String userId, UserInfo userInfo) throws Exception {
        // perform dms search using userId as filter
        // should yield precisely one Gebruiker object
         Gebruiker filter = new Gebruiker();
         filter.setUserId(userId);
         DmsClient dmsClient = new DmsClient(userInfo);
         List<Gebruiker> gebruikers;
         gebruikers = dmsClient.getGebruikers(filter);
         if (gebruikers == null) throw new Exception ("No Gebruiker found");
         if (gebruikers.isEmpty()) throw new Exception ("No Gebruiker found");
         if (gebruikers.size() > 1)  throw new Exception ("Too many Gebruikers found");
         for (Gebruiker g : gebruikers){
             this.userId = g.getUserId();
             this.fullName = g.getFullName();
             this.groupName = g.getGroupName();
             this.dienst = g.getDienst();
         }
    }
    
    /**
     * 
     * @param userIds
     * @param userInfo
     * @return List<Gebruiker> on input of String[] userIds
     * @throws Exception
     */
    public static List<Gebruiker> getGebruikersByUserIds (String[] userIds, UserInfo userInfo) throws Exception {
        List<Gebruiker> gebruikers;
        DmsClient dmsClient = new DmsClient(userInfo);
        Gebruiker filter = new Gebruiker();
        String csvUserIds = CommonUtils.join(userIds, ";");
        filter.setUserId(csvUserIds);
//        gebruikers = dmsClient.getCasemanagers(filter);
        gebruikers = dmsClient.getGebruikers(filter);
        return gebruikers;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setDienst(String dienst) {
        this.dienst = dienst;
    }

    public String getDienst() {
        return dienst;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setColumnHeaderUserId(String columnHeaderUserId) {
        this.columnHeaderUserId = columnHeaderUserId;
    }

    public String getColumnHeaderUserId() {
        return columnHeaderUserId;
    }

    public void setColumnHeaderFullName(String columnHeaderFullName) {
        this.columnHeaderFullName = columnHeaderFullName;
    }

    public String getColumnHeaderFullName() {
        return columnHeaderFullName;
    }

    public void setColumnHeaderGroupName(String columnHeaderGroupName) {
        this.columnHeaderGroupName = columnHeaderGroupName;
    }

    public String getColumnHeaderGroupName() {
        return columnHeaderGroupName;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupId() {
        return groupId;
    }
}
