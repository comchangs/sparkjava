package com.sh.pojo.config.db;

public interface Repository<Obejct, G> {

    Object findById(G id);

    void deleteById(G id);

}
