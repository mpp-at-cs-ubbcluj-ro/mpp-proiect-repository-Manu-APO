package competition;

public class Registry extends SystemUser {

    private boolean isAdmin;

    public Registry(String userName, String password, String firstName, String lastName, boolean isAdmin) {
        super(userName, password, firstName, lastName);
        this.isAdmin = isAdmin;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
