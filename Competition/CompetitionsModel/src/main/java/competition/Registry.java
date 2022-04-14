package competition;

public class Registry extends SystemUser {

    private boolean isAdmin;

    public Registry(String username, String password, String firstName, String lastName, boolean isAdmin) {
        super(username, password, firstName, lastName);
        this.isAdmin = isAdmin;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
