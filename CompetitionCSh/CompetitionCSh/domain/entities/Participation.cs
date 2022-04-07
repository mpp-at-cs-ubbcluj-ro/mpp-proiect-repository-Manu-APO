using CompetitionCSh.domain.interfaces;

namespace CompetitionCSh.domain.entities;

public class Participation : IHasId<long>
{
    public Participation(Participant participant, Trial trial, DateTime dateOfSubmission, Registry registry)
    {
        Participant = participant;
        Trial = trial;
        DateOfSubmission = dateOfSubmission;
        Registry = registry;
    }

    public Participant Participant { get; set; }
    public Trial Trial { get; set; }
    public DateTime DateOfSubmission { get; set; }
    public Registry Registry { get; set; }
    public long Id { get; set; }
}