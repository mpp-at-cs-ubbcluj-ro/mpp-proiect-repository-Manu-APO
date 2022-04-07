using CompetitionCSh.domain.enums;
using CompetitionCSh.domain.interfaces;

namespace CompetitionCSh.domain.dto;

public class TrialDTO : IHasId<long>
{
    public TrialDTO(long parentCompetitionId, int maxNumberOfParticipants, TRIAL_TYPE trialType,
        AGE_CATEGORY ageCategory, DateTime startDate, DateTime endDate)
    {
        ParentCompetitionId = parentCompetitionId;
        MaxNumberOfParticipants = maxNumberOfParticipants;
        TrialType = trialType;
        AgeCategory = ageCategory;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public long ParentCompetitionId { get; set; }
    public int MaxNumberOfParticipants { get; set; }
    public TRIAL_TYPE TrialType { get; set; }
    public AGE_CATEGORY AgeCategory { get; set; }

    public DateTime startDate { get; set; }
    public DateTime endDate { get; set; }
    public long Id { get; set; }
}