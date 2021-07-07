package com.sh.pojo.config.db;

import java.util.List;

public interface Repository<T, G> {

    boolean save(T obj);

    Object findById(G id);

    List<T> findByAll();

    Boolean update(T obj);

    Integer deleteById(G id);
}
