using CompetitionCSh.domain.entities;
using CompetitionCSh.repository.interfaces;

namespace CompetitionCSh.service
{
    public class CompetitionService
    {
        public IParticipantRepository ParticipantRepository;
        public ITrialRepository TrialRepository;
        public IRegistryRepository RegistryRepository;
        public IParticipationRepository ParticipationRepository;

        public CompetitionService(IParticipantRepository participantRepository, ITrialRepository trialRepository, IRegistryRepository registryRepository, IParticipationRepository participationRepository)
        {
            ParticipantRepository = participantRepository;
            TrialRepository = trialRepository;
            RegistryRepository = registryRepository;
            ParticipationRepository = participationRepository;
        }

        public Registry findRegistryByCredetials(string username, string password)
        {
            return RegistryRepository.findRegistryByCredetials(username, password);
        }
    }
}
