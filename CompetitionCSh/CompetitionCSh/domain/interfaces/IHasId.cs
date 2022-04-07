namespace CompetitionCSh.domain.interfaces;

public interface IHasId<TID>
{
    TID Id { get; set; }
}