package ua.danit.clientbank.dao;

import java.util.List;

/**
 * description
 *
 * @author Alexander Isai on 16.07.2024.
 */
public interface Dao<T> {
    T save(T obj);
    boolean delete(T obj);
    void deleteAll(List<T> entities);
    void saveAll(List<T> entities);
    List<T> findAll();
    boolean deleteById(long id);
    T getOne(long id);
}
