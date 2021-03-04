package ru.itmo.lab4.repositories;

public interface GenericRepository<T> {
    T create(T t) throws Exception;
    void delete(T t);
    T find(T t);
    T update(T t);
    Iterable<T> findAll();
}
