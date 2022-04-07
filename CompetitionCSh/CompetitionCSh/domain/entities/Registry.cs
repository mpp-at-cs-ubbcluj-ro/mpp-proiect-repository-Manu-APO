namespace CompetitionCSh.domain.entities;

public class Registry : SystemUser
{
    public Registry(string userName, string password, string firstName, string lastName, bool isAdmin) : base(
        userName, password, firstName, lastName)
    {
        this.isAdmin = isAdmin;
    }

    public bool isAdmin { get; set; }
}