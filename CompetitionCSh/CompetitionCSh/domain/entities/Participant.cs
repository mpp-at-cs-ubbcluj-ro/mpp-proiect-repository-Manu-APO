namespace CompetitionCSh.domain.entities;

public class Participant : SystemUser
{
    public Participant(string userName, string password, string firstName, string lastName) : base(userName, password,
        firstName, lastName)
    {
    }
}