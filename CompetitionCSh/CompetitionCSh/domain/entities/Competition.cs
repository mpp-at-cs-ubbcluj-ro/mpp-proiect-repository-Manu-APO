using CompetitionCSh.domain.interfaces;

namespace CompetitionCSh.domain.entities;

public class Competition : IHasId<long>
{
    public Competition(DateTime startDate, DateTime endDate)
    {
        StartDate = startDate;
        EndDate = endDate;
    }

    public DateTime StartDate { get; set; }
    public DateTime EndDate { get; set; }
    public long Id { get; set; }

    public override string ToString()
    {
        return $"Competition Id {Id}, StartDate {StartDate}, EndDate {EndDate}";
    }
}