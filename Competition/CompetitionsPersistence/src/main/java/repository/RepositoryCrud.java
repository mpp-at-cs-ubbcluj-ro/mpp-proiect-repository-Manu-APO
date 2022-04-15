package repository;


public interface RepositoryCrud<ID, T> {

    T add(T entity);

    Iterable<T> findAll();

    T update(ID id, T newEntity);

    T delete(ID id);

    T findOne(ID id);

}