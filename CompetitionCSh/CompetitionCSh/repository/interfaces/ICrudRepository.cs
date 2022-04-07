namespace CompetitionCSh.repository.interfaces;

public interface ICrudRepository<ID, E>
{
    IEnumerable<E> findAll();
    void save(E entity);

    void update(E e1, E e2);
    E delete(E e);

    E findOne(ID id);
}