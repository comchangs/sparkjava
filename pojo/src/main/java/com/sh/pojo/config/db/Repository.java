package com.sh.pojo.config.db;

import java.util.List;

public interface Repository<T, G> {

    boolean save(T obj);

    T findById(G id);

//    List<T> findByAll();

    Boolean update(T obj);

    Boolean deleteById(G id);

    Boolean deleteAll();
}
