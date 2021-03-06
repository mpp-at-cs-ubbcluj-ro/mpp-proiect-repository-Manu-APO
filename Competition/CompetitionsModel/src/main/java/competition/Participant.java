package competition;

public class Participant extends SystemUser {

    public Participant(Long id,String username, String password, String firstName, String lastName) {
        super(username, password, firstName, lastName);
        setId(id);
    }

    public Participant() {
    }

    public Participant(String username, String password, String firstName, String lastName) {
        super(username, password, firstName, lastName);
    }

    @Override
    public String toString() {
        return "Participant{ " + super.toString() + "}";
    }
}
