package competition;

public class Participant extends SystemUser {

    public Participant(String userName, String password, String firstName, String lastName) {
        super(userName, password, firstName, lastName);
    }

    @Override
    public String toString() {
        return "Participant{ " + super.toString() + "}";
    }
}
