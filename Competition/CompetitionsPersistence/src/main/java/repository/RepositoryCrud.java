package repository;


public interface RepositoryCrud<ID,T> {

    T add(T e);
    Iterable<T> findAll();
    T update(ID id, T e2);
    T delete(ID e);
    T findOne(ID id);

}