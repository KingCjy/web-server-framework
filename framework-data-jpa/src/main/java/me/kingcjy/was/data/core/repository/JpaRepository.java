package me.kingcjy.was.data.core.repository;


import java.util.List;

public interface JpaRepository<T, ID> {
    List<T> findAll();
    T findById(ID id);

    T save(T target);
    Iterable<T> saveAll(Iterable<T> targets);

    void deleteById(ID id);
    void delete(T target);
    void deleteAll();
}
