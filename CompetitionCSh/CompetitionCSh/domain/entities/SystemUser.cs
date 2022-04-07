using CompetitionCSh.domain.interfaces;

namespace CompetitionCSh.domain.entities;

public class SystemUser : IHasId<string>
{
    public SystemUser(string userName, string password, string firstName, string lastName)
    {
        UserName = userName;
        Password = password;
        FirstName = firstName;
        LastName = lastName;
    }

    public string UserName { get; set; }
    public string Password { get; set; }

    public string FirstName { get; set; }
    public string LastName { get; set; }
    public string Id { get; set; }

    public override string ToString()
    {
        return
            $"SystemUser id {Id} ,userName {UserName}, password {Password}, firstName {FirstName}, lastName {LastName}";
    }
}