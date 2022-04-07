using CompetitionCSh.domain.enums;
using CompetitionCSh.domain.interfaces;

namespace CompetitionCSh.domain.entities;

public class Trial : IHasId<long>
{
    public Trial(Competition parentCompetition, int maxNumberOfParticipants, TRIAL_TYPE trialType,
        AGE_CATEGORY ageCategory, DateTime startDate, DateTime endDate)
    {
        ParentCompetition = parentCompetition;
        MaxNumberOfParticipants = maxNumberOfParticipants;
        TrialType = trialType;
        AgeCategory = ageCategory;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Competition ParentCompetition { get; set; }
    public int MaxNumberOfParticipants { get; set; }
    public TRIAL_TYPE TrialType { get; set; }
    public AGE_CATEGORY AgeCategory { get; set; }

    public DateTime startDate { get; set; }
    public DateTime endDate { get; set; }
    public long Id { get; set; }
}