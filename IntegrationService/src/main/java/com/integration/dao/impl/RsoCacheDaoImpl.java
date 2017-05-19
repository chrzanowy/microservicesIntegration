package com.integration.dao.impl;


import com.integration.dao.RsoCacheDao;
import com.integration.model.RsoCache;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Kuba on 2017-05-13.
 */
@Repository
public class RsoCacheDaoImpl implements RsoCacheDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void persistOrMerge(RsoCache rsoCache) {

    }

    @Override
    public void persist(RsoCache rsoCache) {

    }

    @Override
    public void delete(RsoCache rsoCache) {

    }
}
