package com.cogent.adminservice.utils;

import org.hibernate.transform.Transformers;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.function.BiFunction;

public class QueryUtils {
    public static BiFunction<EntityManager, String, Query> getQuery = (EntityManager::createQuery);

    public static <T> List transformQueryToResultList(Query query, Class<T> requestDTO) {
        return query.unwrap(org.hibernate.query.Query.class)
                .setResultTransformer(Transformers.aliasToBean(requestDTO))
                .getResultList();
    }

}
