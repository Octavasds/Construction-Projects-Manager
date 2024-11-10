package RepositoryLayer;

import java.util.List;

public interface IRepository<T> {
    void add(T entity);
    void delete(T entity);
    void update(int id, T entity);
    List<T> getAll();
    T getById(int id);
}