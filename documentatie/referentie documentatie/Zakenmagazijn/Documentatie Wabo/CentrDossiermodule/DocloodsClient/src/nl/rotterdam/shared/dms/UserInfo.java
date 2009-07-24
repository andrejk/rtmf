package nl.rotterdam.shared.dms;

public class UserInfo {
    private String userId;
    private String password;
    private String library; // = "HB_L";
    public UserInfo(String userId, String password, String library) {
        this.userId = userId;
        this.password = password;
        this.library =library;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setLibrary(String library) {
        this.library = library;
    }

    public String getLibrary() {
        return library;
    }
}
