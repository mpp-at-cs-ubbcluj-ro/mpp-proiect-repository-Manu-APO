using CompetitionCSh.domain.interfaces;

namespace CompetitionCSh.domain.dto;

public class ParticipationDTO : IHasId<long>
{
    public ParticipationDTO(string participantId, long trialId, DateTime dateOfSubmission, string registryId)
    {
        ParticipantId = participantId;
        TrialId = trialId;
        DateOfSubmission = dateOfSubmission;
        RegistryId = registryId;
    }

    public string ParticipantId { get; set; }
    public long TrialId { get; set; }
    public DateTime DateOfSubmission { get; set; }
    public string RegistryId { get; set; }
    public long Id { get; set; }
}