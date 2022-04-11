using CompetitionCSh.domain.entities;

namespace CompetitionCSh.repository.interfaces;

public interface IRegistryRepository : ICrudRepository<string, Registry>
{
    Registry findRegistryByCredetials(string username, string password);
}