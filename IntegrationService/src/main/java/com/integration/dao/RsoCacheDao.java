package com.integration.dao;


import com.integration.model.RsoCache;

/**
 * Created by Kuba on 2017-05-13.
 */
public interface RsoCacheDao {
    void persistOrMerge(RsoCache rsoCache);

    void persist(RsoCache rsoCache);

    void delete(RsoCache rsoCache);
}
